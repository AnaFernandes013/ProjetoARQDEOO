package rede.jogo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// carrega configurações do jogo. lê um arquivo config e pega o ip e a porta.
public class ConfigTXT { // essa classe so serve para ler e fornecer configs
    
    private static String ip;
    private static int porta = -1;
    
    private ConfigTXT() {
    }
    
    private static void readConfig() { //método que lê o arquivo
        
        File file = new File("config.txt");
        if( file.exists() ) { // verifica se ele existe
            
            try {
                
                FileReader reader = new FileReader(file);
                BufferedReader buffer = new BufferedReader(reader);// prepara-se pra ler o arquivo linha por linha
                
                ip = buffer.readLine();
                porta =  Integer.parseInt( buffer.readLine() ); // pega os dados 
                
            } catch ( Exception ex ) { // se der erro ele mostra no console
                ex.printStackTrace();
            }
            
        }
    }

    public static String getIp() { // método para "devolver o ip"
        
        if ( ip == null )
            readConfig();
        
        return ip;
    }
    
    public static int getPorta() { // mesma lógica do ip
        
        if ( porta == -1 )
            readConfig();
        
        return porta;
    }
        
}