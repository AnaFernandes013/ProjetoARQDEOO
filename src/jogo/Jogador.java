package rede.jogo;

public class Jogador {
    private int vida;
    private int armadura;

    public Jogador() {
        setVida(vida);
        setArmadura(armadura);
    }

    // logica pra receber o dano com a armadura
    public String receberDano(int dano) {
        if (armadura > 0) {
            int resto = armadura - dano;

            if (resto >= 0) {
                armadura -= dano;
                return "Armadura absorveu o dano!";
            } else {
                armadura = 0;
                vida += resto;
                return "Armadura quebrou!";
            }
        } else {
            vida -= dano;
            return "Dano direto na vida!";
        }
    }

    public void setVida(int vida)
    {
        this.vida = vida;
    }

    public void setArmadura(int armadura)
    {
        this.armadura = armadura;
    }

    public int getVida() { return vida; }
    public int getArmadura() { return armadura; }
}