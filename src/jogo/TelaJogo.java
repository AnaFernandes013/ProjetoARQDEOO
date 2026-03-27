package rede.jogo;


import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaJogo{
    JFrame janela;
    JPanel tituloPanel;
    JLabel tituloLabel;
    Font tituloFonte = new Font("Serif", Font.PLAIN, 90);
    
    // acho q pra colocar os bonequin, talvez usar dois botoes? 
    
    public TelaJogo(){
        
        //fundo
        janela = new JFrame();
        janela.setSize(800,600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Pra colocar imagem
        ImageIcon imagem = new ImageIcon(
            getClass().getResource("/rede/jogo/imagem/fundo.png")
        );
        JLabel fundo = new JLabel(imagem);
        fundo.setLayout(null); 
        janela.setContentPane(fundo); 

        janela.setVisible(true);
        
        // jogadores
        ImageIcon playerImg = new ImageIcon(
            getClass().getResource("/rede/jogo/imagem/player1.png") // 100 x 100
        );

        JLabel player = new JLabel(playerImg);
        player.setBounds(400, 300, playerImg.getIconWidth(), playerImg.getIconHeight());

        fundo.add(player);
        
        // titulo aventura
        tituloPanel = new JPanel();
        tituloPanel.setBounds(100,100,600,150);
        tituloPanel.setOpaque(false); // pra deixar o fundo transparente
        tituloLabel = new JLabel("AVENTURA");
        tituloLabel.setForeground(Color.white);
        tituloLabel.setFont(tituloFonte);
        
        tituloPanel.add(tituloLabel);
        fundo.add(tituloPanel);
    }
    
}
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
////TESTANDO INTERFACE JAVA
//public class TelaJogo extends JFrame {
//
//    private JButton btnAtaque;
//    private JButton btnAtaqueForte;
//    private JButton btnDefesa;
//
//    private JLabel lblVida;
//
//    private Jogador jogador;
//
//    public TelaJogo() {
//
//        jogador = new Jogador("Você");
//
//        setTitle("RPG - Batalha");
//        setSize(300, 300);
//        setLayout(null);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        lblVida = new JLabel("Vida: " + jogador.getVida());
//        lblVida.setBounds(50, 20, 200, 30);
//        add(lblVida);
//
//        btnAtaque = new JButton("Ataque");
//        btnAtaque.setBounds(50, 70, 200, 30);
//        add(btnAtaque);
//
//        btnAtaqueForte = new JButton("Ataque Forte");
//        btnAtaqueForte.setBounds(50, 110, 200, 30);
//        add(btnAtaqueForte);
//
//        btnDefesa = new JButton("Defesa");
//        btnDefesa.setBounds(50, 150, 200, 30);
//        add(btnDefesa);
//
//        // ações dos botões
//        btnAtaque.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "Você atacou!");
//            }
//        });
//
//        btnAtaqueForte.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "Ataque forte!");
//            }
//        });
//
//        btnDefesa.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "Defesa ativada!");
//            }
//        });
//
//        setVisible(true);
//    }
//}