/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jogo;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sons {

    private final File selecionar, vencedor, perdedor, moeda;

    public Sons(String selecionar, String vencedor, String perdedor, String moeda) {
        this.selecionar = new File( selecionar );
        this.vencedor = new File( vencedor );
        this.perdedor = new File( perdedor );
        this.moeda = new File( moeda );
    }

    private void play(File arquivo) throws Exception {
        Clip clip = AudioSystem.getClip();
        clip.open( AudioSystem.getAudioInputStream(arquivo) );
        clip.start();
    }

    public void selecionar() throws Exception {
        play(selecionar);
    }

    public void vencedor() throws Exception {
        play(vencedor);
    }

    public void perdedor() throws Exception {
        play(perdedor);
    }

    public void moeda() throws Exception {
        play(moeda);
    }
}

