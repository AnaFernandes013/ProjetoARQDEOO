package rede.jogo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorJogo {
    
    private ServerSocket servidor;
    
    private Socket jogadorX;
    private ObjectOutputStream entradaJogadorX;
    private ObjectInputStream saidaJogadorX;
//   OutputStream -> envia dados
//   InputStream -> recebe dados 
    private Socket jogadorO;
    private ObjectOutputStream entradaJogadorO;
    private ObjectInputStream saidaJogadorO;
    
    // iniciar servidor
    public void iniciar() throws Exception {
        
        servidor = new ServerSocket( ConfigTXT.getPorta(), 2, InetAddress.getByName( ConfigTXT.getIp() ) );
        // porta, máximo de conexoes (2 jogadores), ip
        System.out.println("Servidor JogoDaVelha Inicializado ( " + servidor + " ).\n");
        
    }
    
    // conectar jogadores
    public void conectar() throws Exception {
        
        System.out.println( "Esperando por Conexão (Jogador X)." );
        jogadorX =  servidor.accept();
        System.out.println( "Conexão Recebida: " + jogadorX.toString() + ":" + jogadorX.getPort() + "\n" );
        
        entradaJogadorX = new ObjectOutputStream( jogadorX.getOutputStream() );
        entradaJogadorX.flush();        
        entradaJogadorX.writeObject("X;true");
        
        System.out.println( "Esperando por Conexão (Jogador O)." );
        jogadorO =  servidor.accept();
        System.out.println( "Conexão Recebida: " + jogadorO.toString() + ":" + jogadorX.getPort() + "\n" );
        
        entradaJogadorO = new ObjectOutputStream( jogadorO.getOutputStream() );
        entradaJogadorO.flush();        
        entradaJogadorO.writeObject("O;false");
        
    }
    
    // iniciar comunicação entre jogadores
    public void comunicar() throws Exception {
        
        saidaJogadorX = new ObjectInputStream( jogadorX.getInputStream() );
        saidaJogadorO = new ObjectInputStream( jogadorO.getInputStream() );
        
        Thread thread1 = new Thread( new GerenciadorDeJogadas(saidaJogadorO, entradaJogadorX) );
        //Jogador O -> servidor -> Jogador X
        
        Thread thread2 = new Thread( new GerenciadorDeJogadas(saidaJogadorX, entradaJogadorO) );
        //Jogador X -> servidor -> Jogador O
        
        thread1.start();
        thread2.start();
        
    }

}