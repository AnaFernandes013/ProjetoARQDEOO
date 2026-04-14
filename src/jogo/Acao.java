
package rede.jogo;


public class Acao {
    
    public String ataqueForte(Jogador j) {
        return j.receberDano(20);
    }
    
    public String ataqueFraco(Jogador j) {
        return j.receberDano(5);
    }
    
    public String ataqueMedio(Jogador j) {
        return j.receberDano(15);
    }
}