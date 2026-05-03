package rede.jogo;

public class Jogador {
    private int vida;
    private int armadura;

    public Jogador() {
        this.vida = 50;      // determina a vida inicial do jogador
        this.armadura = 11;  // valor médio de 11 (para equilibrar entre acerto e erro, mas podemos mudar depois também)
    }

    // lógica pra receber o ataque
    public String receberDano(int rolagemAtaque, int dano) {
        
        if (rolagemAtaque >= armadura) {
            vida -= dano;
            return "ACERTOU! Dano: " + dano + " | Vida restante: " + vida;
        } else {
            return "ERROU! Nenhum dano causado.";
        }
    }

    public int getVida(){
        return vida; 
    }
    
    public int getArmadura() {
        return armadura; 
    }
}