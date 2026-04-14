package rede.jogo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorJogo {
    
    private ServerSocket servidor;
    
    private Socket magoBranco;
    private ObjectOutputStream entradaMB; // Entrada jogador mago branco
    private ObjectInputStream saidaMB; // saida jogador mago branco
    private Socket magoNegro; 
    private ObjectOutputStream entradaMN; // entrada jogador mago negro
    private ObjectInputStream saidaMN; // saida jogador mago negro
    
    // iniciar servidor
    public void iniciar() throws Exception {
        
        servidor = new ServerSocket( ConfigTXT.getPorta(), 2, InetAddress.getByName( ConfigTXT.getIp() ) );
        // porta, máximo de conexoes (2 jogadores), ip
        System.out.println("Servidor RPG Inicializado ( " + servidor + " ).\n");
        
    }
    
    // conectar jogadores
    public void conectar() throws Exception {
        
        System.out.println( "Esperando por Conexão (Mago Branco)." );
        magoBranco =  servidor.accept();
        System.out.println( "Conexão Recebida: " + magoBranco.toString() + ":" + magoBranco.getPort() + "\n" );
        
        entradaMB = new ObjectOutputStream( magoBranco.getOutputStream() );
        entradaMB.flush();        
        entradaMB.writeObject("Mago Branco;true");
        
        System.out.println( "Esperando por Conexão (Mago Negro)." );
        magoNegro =  servidor.accept();
        System.out.println( "Conexão Recebida: " + magoNegro.toString() + ":" + magoBranco.getPort() + "\n" );
        
        entradaMN = new ObjectOutputStream( magoNegro.getOutputStream() );
        entradaMN.flush();        
        entradaMN.writeObject("Mago Negro;false");
        
    }
    
    // iniciar comunicação entre jogadores
    public void comunicar() throws Exception {
        
        saidaMB = new ObjectInputStream( magoBranco.getInputStream() );
        saidaMN = new ObjectInputStream( magoNegro.getInputStream() );
        
        Thread thread1 = new Thread( new GerenciadorDeJogadas(saidaMN, entradaMB) );
       
        
        Thread thread2 = new Thread( new GerenciadorDeJogadas(saidaMB, entradaMN) );
        
        
        thread1.start();
        thread2.start();
        
    }

}