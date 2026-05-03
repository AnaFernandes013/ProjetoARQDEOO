package rede.jogo;

import javax.swing.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TelaJogo {

    JFrame janela;
    JTextArea areaLog;
    JProgressBar vidaBar, armaduraBar;

    JButton fraco, medio, forte;

    Jogador jogador;

    // REDE (igual ao professor)
    private Socket servidorConexao;
    private ObjectInputStream servidorEntrada;
    private ObjectOutputStream servidorSaida;

    private boolean suaVez;
    private boolean fim;

    public TelaJogo() throws Exception {

        jogador = new Jogador();

        // ===== INTERFACE =====
        janela = new JFrame("RPG Multiplayer");
        janela.setSize(800, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(null);

        vidaBar = new JProgressBar(0, 100);
        vidaBar.setBounds(50, 50, 200, 20);
        janela.add(vidaBar);

        armaduraBar = new JProgressBar(0, 50);
        armaduraBar.setBounds(50, 80, 200, 20);
        janela.add(armaduraBar);

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setBounds(50, 400, 500, 120);
        janela.add(scroll);

        fraco = new JButton("Ataque Fraco");
        fraco.setBounds(580, 400, 150, 30);
        janela.add(fraco);

        medio = new JButton("Ataque Médio");
        medio.setBounds(580, 440, 150, 30);
        janela.add(medio);

        forte = new JButton("Ataque Forte");
        forte.setBounds(580, 480, 150, 30);
        janela.add(forte);

        configurarEventos();

        atualizarTela();

        janela.setVisible(true);

        // ===== REDE =====
        conectar();
        escutarServidor();
    }

    // ================= EVENTOS =================
    private void configurarEventos() {

        fraco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarJogada("ATAQUE_FRACO");
            }
        });

        medio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarJogada("ATAQUE_MEDIO");
            }
        });

        forte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarJogada("ATAQUE_FORTE");
            }
        });
    }

    // ================= ENVIO =================
    private void enviarJogada(String msg) {

        if (!suaVez || fim) {
            areaLog.append("Espere sua vez!\n");
            return;
        }

        try {
            servidorSaida.writeObject(msg);
            servidorSaida.flush();

            areaLog.append("Você usou: " + msg + "\n");

            suaVez = false;

            checarTermino();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= CONEXÃO =================
    private void conectar() throws Exception {

        servidorConexao = new Socket(ConfigTXT.getIp(), ConfigTXT.getPorta());

        servidorSaida = new ObjectOutputStream(servidorConexao.getOutputStream());
        servidorEntrada = new ObjectInputStream(servidorConexao.getInputStream());

        String mensagem = (String) servidorEntrada.readObject();
        String[] info = mensagem.split(";");

        suaVez = info[1].equals("true");
        fim = false;

        if (suaVez) {
            areaLog.append("Sua vez!\n");
        } else {
            areaLog.append("Espere sua vez...\n");
        }
    }

    // ================= RECEBIMENTO =================
    private void escutarServidor() {

        new Thread(() -> {
            while (!fim) {
                try {

                    if (!suaVez) {

                        String mensagem = (String) servidorEntrada.readObject();

                        processarMensagem(mensagem);

                        suaVez = true;

                        areaLog.append("Sua vez!\n");

                        checarTermino();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // ================= PROCESSAMENTO =================
    private void processarMensagem(String msg) {
        
    String[] partes = msg.split(";");
    
    if (partes[0].equals("ATAQUE")) {

        int rolagem = Integer.parseInt(partes[1]);
        int dano = Integer.parseInt(partes[2]);

        String resultado = jogador.receberDano(rolagem, dano);

        areaLog.append("Você sofreu um ataque!\n");
        areaLog.append(resultado + "\n");
    }

    atualizarTela();
}

    // ================= TELA =================
    private void atualizarTela() {
        vidaBar.setValue(jogador.getVida());
        armaduraBar.setValue(jogador.getArmadura());
    }

    // ================= FIM =================
    private void checarTermino() {

        if (jogador.getVida() <= 0) {
            fim = true;
            areaLog.append("Você perdeu!\n");
        }
    }
}