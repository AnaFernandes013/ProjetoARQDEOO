package Jogo;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JogoGUI extends JFrame {

    private JButton cara;
    private JButton coroa;
    private JButton jogarMoeda;

    private int escolha;

    public JogoGUI() {

        setTitle("Cara ou Coroa");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        construirInterface();

        iniciar();

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

        habilitarOpcoes();

        System.out.println("Escolha Cara ou Coroa.");
    }

    private void escolherCara() {

        escolha = 0;

        JOptionPane.showMessageDialog(this,
                "Você escolheu Cara.");
    }

    private void escolherCoroa() {

        escolha = 1;

        JOptionPane.showMessageDialog(this,
                "Você escolheu Coroa.");
    }

    private void jogar() {

        if (escolha == -1) {
            JOptionPane.showMessageDialog(this,
                    "Escolha Cara ou Coroa primeiro!");
            return;
        }

        desabilitarOpcoes();

        int resultado = jogarMoeda();

        mostrarResultado(resultado);

        checarResultado(resultado);
    }

    private int jogarMoeda() {

        return (int) (Math.random() * 2);
    }

    private void mostrarResultado(int resultado) {

        String lado;

        if (resultado == 0) {
            lado = "Cara";
        } else {
            lado = "Coroa";
        }

        JOptionPane.showMessageDialog(this,
                "A moeda caiu em: " + lado);
    }

    private void checarResultado(int resultado) {

        if (resultado == escolha) {

            JOptionPane.showMessageDialog(this,
                    "Parabéns! Você acertou!");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Que pena! Você errou!");

        }

        checarReinicio();
    }

    private void checarReinicio() {

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja jogar novamente?",
                "Novo Jogo",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {

            iniciar();

        } else {

            dispose();
        }
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
    
    
// Ficaria faltando implementar os metodos 
//    private void conectar();
//    private void enviarEscolha();
//    private void receberResultado();

    public static void main(String[] args) {

        new JogoGUI();
    }
}
