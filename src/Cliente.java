package aulas.rede;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws Exception {
        
        int porta = 12345;
        InetAddress endereco = InetAddress.getByName("10.105.68.107");
        
        Socket conexao = new Socket(endereco, porta);
        System.out.println( "Conexão Realizada: " + conexao.getLocalAddress().getHostName() + ":" + conexao.getLocalPort() + "\n" );
        
        ObjectOutputStream output;
        output = new ObjectOutputStream( conexao.getOutputStream() );
        output.flush();
        
        ObjectInputStream input; // deve ser criado após o output pos este último denife cabeçalho utilizado em input
        input = new ObjectInputStream( conexao.getInputStream() );
        System.out.println( "Obtenção de Fluxos de Entrada e Saída.\n" );
        
        String mensagem;        
        mensagem = (String) input.readObject();
        System.out.println( "Mensagem \"" + mensagem +"\" Recebida.\n" );
       
        mensagem = "Olá Servidor.";        
        output.writeObject(mensagem);
        output.flush();
        System.out.println( "Mensagem \"" + mensagem +"\" Enviada com Sucesso.\n" );
        
        output.close();
        input.close();
        conexao.close();
        System.out.println( "Conexão Fechada (Encerrada).\n" );
        
    }
}