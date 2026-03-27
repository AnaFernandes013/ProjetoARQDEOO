package rede.jogo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// Essa classe pega a mensagem de um jogador e envia para outro

public class GerenciadorDeJogadas implements Runnable {
    
    private ObjectInputStream saidaJogador1;
    private ObjectOutputStream entradaJogador2;
    // Jogador 1 -> readObject -> servidor -> writeObject -> Jogador 2

    public GerenciadorDeJogadas(ObjectInputStream saidaJogador1, ObjectOutputStream entradaJogador2) {
        this.saidaJogador1 = saidaJogador1;
        this.entradaJogador2 = entradaJogador2;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                
                String mensagem = (String) saidaJogador1.readObject();
                entradaJogador2.writeObject(mensagem);
                
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }    

}