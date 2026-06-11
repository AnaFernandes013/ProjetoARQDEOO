package Jogo;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorJogo {
    
    private ServerSocket servidor;
    
    private Socket jogador1;
    private ObjectOutputStream entradaJogador1;
    private ObjectInputStream saidaJogador1;
            
    private Socket jogador2;
    private ObjectOutputStream entradaJogador2;
    private ObjectInputStream saidaJogador2;
    
    // iniciar servidor
    public void iniciar() throws Exception {
        
        servidor = new ServerSocket( ConfigTXT.getPorta(), 2, InetAddress.getByName( ConfigTXT.getIp() ) );
        System.out.println("Servidor Cara ou Coroa Inicializado ( " + servidor + " ).\n");
        
    }
    
    // conectar jogadores
    public void conectar() throws Exception {
        
        System.out.println( "Esperando por Conexão (Jogador 1)." );
        jogador1 =  servidor.accept();
        System.out.println( "Conexão Recebida: " + jogador1.toString() + ":" + jogador1.getPort() + "\n" );
        
        entradaJogador1 = new ObjectOutputStream( jogador1.getOutputStream() );
        entradaJogador1.flush();        
        entradaJogador1.writeObject("1;true");
        
        System.out.println( "Esperando por Conexão (Jogador 2)." );
        jogador2 =  servidor.accept();
        System.out.println( "Conexão Recebida: " + jogador2.toString() + ":" + jogador2.getPort() + "\n" );
        
        entradaJogador2 = new ObjectOutputStream( jogador2.getOutputStream() );
        entradaJogador2.flush();        
        entradaJogador2.writeObject("2;false");
        
    }
    
    // sorteia e envia os resultados
    public void sortear() throws Exception{      
        
        // Recebe a escolha de cada jogador (0 = Cara e  1 = Coroa)
        int escolhaJogador1 = (int) saidaJogador1.readObject();
        int escolhaJogador2 = (int) saidaJogador2.readObject();
            
        int resultado = (int) (Math.random() * 2);
        
        entradaJogador1.writeObject(resultado);
        entradaJogador1.flush();
        entradaJogador2.writeObject(resultado);
        entradaJogador2.flush();
        
    }

    public void comunicar() throws Exception {
        
        saidaJogador1 = new ObjectInputStream(jogador1.getInputStream());
        saidaJogador2 = new ObjectInputStream(jogador2.getInputStream());
        
        new Thread(() ->{
            while(true){
                try{
                    sortear();          
                }catch(Exception ex){
                    System.out.println("Jogaor desconectou");
                    break;
                }
            }     
        }).start();
                            
    }

}