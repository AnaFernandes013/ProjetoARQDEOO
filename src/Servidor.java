package aulas.rede;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws Exception {
        
        int porta = 12345;
        int backlog = 2;
        InetAddress endereco = InetAddress.getByName("10.105.68.107"); //endereço do servidor - ip

        ServerSocket servidor;
        servidor = new ServerSocket(porta, backlog, endereco);
        System.out.println("Servidor Inicializado ( " + servidor + " ).\n");
        
        Socket conexao;        
        System.out.println( "Esperando por Conexão.\n" );
        conexao =  servidor.accept();
        System.out.println( "Conexão Recebida: " + conexao.getLocalAddress().getHostName() + ":" + conexao.getPort() + "\n" );
        
        ObjectOutputStream output;
        output = new ObjectOutputStream( conexao.getOutputStream() ); // abre esse primeiro senão nao funfa
        output.flush();
        
        ObjectInputStream input; // deve ser criado após o output pos este último denife cabeçalho utilizado em input
        input = new ObjectInputStream( conexao.getInputStream() );
        System.out.println( "Obtenção de Fluxos de Entrada e Saída.\n" );
        
        String mensagem;
        mensagem = "Olá Cliente.";        
        output.writeObject(mensagem);
        output.flush();
        System.out.println( "Mensagem \"" + mensagem + "\" Enviada com Sucesso.\n" );
        
        mensagem = (String) input.readObject();
        System.out.println( "Mensagem \"" + mensagem +"\" Recebida.\n" );
        
        output.close();
        input.close();
        conexao.close();
        System.out.println( "Conexão Fechada (Encerrada).\n" );
        
        servidor.close();
        System.out.println( "Servidor Fechado (Encerrado).\n" );
        
    }
}