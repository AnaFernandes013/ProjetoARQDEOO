package Jogo;

import java.awt.FlowLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class JogoGUI extends JFrame {

    private JButton cara;
    private JButton coroa;
    private JButton jogarMoeda;
    
    private Socket servidorConexao;
    private ObjectOutputStream servidorSaida;
    private ObjectInputStream servidorEntrada;

    private int escolha;
    private boolean podeEscolher; // true = Jogador 1 (escolhe lado); false = Jogador 2 (lado automatico)

    public JogoGUI() throws Exception{

        setTitle("Cara ou Coroa");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        construirInterface();

        iniciar();
        conectar();

        setVisible(true);
    }

    private void construirInterface() {

        setLayout(new FlowLayout());

        cara = new JButton("Cara");
        coroa = new JButton("Coroa");
        jogarMoeda = new JButton("Jogar Moeda");

        cara.addActionListener(e -> escolherCara());
        coroa.addActionListener(e -> escolherCoroa());
        jogarMoeda.addActionListener(e -> jogar());

        add(cara);
        add(coroa);
        add(jogarMoeda);
    }

    private void iniciar() {

        escolha = -1; // flag

        configurarBotoes();

        System.out.println("Nova rodada.");
    }

    private void escolherCara() {

        escolha = 0;

        JOptionPane.showMessageDialog(this, "Você escolheu Cara.");
    }

    private void escolherCoroa() {

        escolha = 1;

        JOptionPane.showMessageDialog(this, "Você escolheu Coroa.");
    }

    private void jogar(){
        // só o jogador 1 escolhe uma opção, pois o segundo jogador ficará com o resultado restante
        //o jogador 2 só precisa clicar em jogar a moeda
        if (podeEscolher && escolha == -1) {
            JOptionPane.showMessageDialog(this, "Escolha Cara ou Coroa primeiro!");
            return;
        }

        try{
            desabilitarOpcoes();
            enviarEscolha();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
            dispose();
            return;
        }

        //espera o resultado em uma thread separada para a janela nao travar enquanto o outro jogador ainda nao jogou
        
        new Thread(this::receberResultado).start();
    }

    
    private String nomeLado(int valor) {
        return (valor == 0) ? "Cara" : "Coroa";
    }

    private void mostrarResultado(int resultado, int meuLado) {

        JOptionPane.showMessageDialog(this, "A moeda caiu em: " + nomeLado(resultado) + "\nSeu lado: " + nomeLado(meuLado));
    }

    private void checarResultado(int resultado, int meuLado) {

        if (resultado == meuLado) {

            JOptionPane.showMessageDialog(this, "Parabéns! Você venceu!");

        } else {

            JOptionPane.showMessageDialog(this, "Que pena! Você perdeu!");

        }

        checarReinicio();
    }

    private void checarReinicio() {

        int resposta = JOptionPane.showConfirmDialog(
                this, "Deseja jogar novamente?", "Novo Jogo",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {

            iniciar();

        } else {

            dispose();
        }
    }

    // habilita os botoes de escolha apenas pro jogador 1
    // jogador 2 apenas clica em jogar a moeda
    private void configurarBotoes() {
        jogarMoeda.setEnabled(true);
        cara.setEnabled(podeEscolher);
        coroa.setEnabled(podeEscolher);
    }

    private void habilitarOpcoes() {

        cara.setEnabled(true);
        coroa.setEnabled(true);
        jogarMoeda.setEnabled(true);
    }

    private void desabilitarOpcoes() {

        cara.setEnabled(false);
        coroa.setEnabled(false);
        jogarMoeda.setEnabled(false);
    }
    
    
    private void conectar()throws Exception{
        servidorConexao = new Socket(InetAddress.getByName(ConfigTXT.getIp()), ConfigTXT.getPorta());
        
        servidorSaida = new ObjectOutputStream(servidorConexao.getOutputStream());
        servidorSaida.flush();
        servidorEntrada = new ObjectInputStream(servidorConexao.getInputStream());
        
        String mensagem = (String) servidorEntrada.readObject();
        String[] info = mensagem.split(";");
        
        podeEscolher = info[1].equals("true");

        if (podeEscolher) {
            setTitle("Cara ou Coroa - Jogador 1 (você escolhe o lado)");
        } else {
            setTitle("Cara ou Coroa - Jogador 2 (lado automático)");
            JOptionPane.showMessageDialog(this, "Você é o Jogador 2.\nSeu lado será sempre o OPOSTO ao do Jogador 1 (automático)."
                            + "\nÉ só clicar em \"Jogar Moeda\".");
        }

        configurarBotoes();
    }
    
    private void enviarEscolha() throws Exception{
        servidorSaida.writeObject(escolha);
        servidorSaida.flush();
    }
    
    private void receberResultado() {

        try {

            String mensagem = (String) servidorEntrada.readObject();
            String[] partes = mensagem.split(";");
            int resultado = Integer.parseInt(partes[0]);
            int meuLado = Integer.parseInt(partes[1]);

            SwingUtilities.invokeLater(() -> {
                mostrarResultado(resultado, meuLado);
                checarResultado(resultado, meuLado);
            });

        } catch (Exception ex) {

            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "O oponente saiu ou a conexao foi encerrada.");
                dispose();
            });
        }
    }

    public static void main(String[] args) {
        try{
            new JogoGUI();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }     
    }
}