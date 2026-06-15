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
        
        // jogador 1 escolhe a opção, e o 2 fica ocm a opção que sobrou
        int escolhaJogador1 = (int) saidaJogador1.readObject();
        int sinalJogador2   = (int) saidaJogador2.readObject(); // ignorado de propósito
        
        int ladoJogador1 = escolhaJogador1;
        int ladoJogador2 = 1 - escolhaJogador1; // lado oposto, automático
        
        int resultado = (int) (Math.random() * 2);
        
        // envia "resultado;ladoDoProprioJogador" para cada cliente
        entradaJogador1.writeObject(resultado + ";" + ladoJogador1);
        entradaJogador1.flush();
        entradaJogador2.writeObject(resultado + ";" + ladoJogador2);
        entradaJogador2.flush();
        
        registrarPartida(ladoJogador1, ladoJogador2, resultado);
        
    }
    
    // converte 0/1 em texto
    private String lado(int valor) {
        return (valor == 0) ? "Cara" : "Coroa";
    }
    
    // grava o resultado de cada partida (com o ganhador) em arquivo texto
    private void registrarPartida(int ladoJogador1, int ladoJogador2, int resultado) {
        
        // como os lados sao opostos, sempre terá um vencedor
        String vencedor = (resultado == ladoJogador1) ? "Jogador 1" : "Jogador 2";
        
        String data = java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") );
        
        String linha = data
                + "  Jogador 1 escolheu " + lado(ladoJogador1)
                + " e sobrou " + lado(ladoJogador2) + " para o Jogador 2 "
                + " | Resultado: " + lado(resultado)
                + " | Vencedor: " + vencedor;
        
        try ( java.io.BufferedWriter escritor =
                new java.io.BufferedWriter( new java.io.FileWriter("historico.txt", true) ) ) {
            escritor.write(linha);
            escritor.newLine();
        } catch (Exception ex) {
            System.out.println("Erro ao gravar o historico: " + ex.getMessage());
        }
        
        System.out.println(linha);
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