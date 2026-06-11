package Jogo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ConfigTXT {
    
    private static String ip;
    private static int porta = -1;
    
    private ConfigTXT() {
    }
    
    private static void readConfig() {
        
        File file = new File("config.txt");
        if( file.exists() ) {
            
            try {
                
                FileReader reader = new FileReader(file);
                BufferedReader buffer = new BufferedReader(reader);
                
                ip = buffer.readLine();
                porta =  Integer.parseInt( buffer.readLine() );
                buffer.close();
                
            } catch ( Exception ex ) {
                ex.printStackTrace();
            }
            
        }
    }

    public static String getIp() {
        
        if ( ip == null )
            readConfig();
        
        return ip;
    }
    
    public static int getPorta() {
        
        if ( porta == -1 )
            readConfig();
        
        return porta;
    }
        
}