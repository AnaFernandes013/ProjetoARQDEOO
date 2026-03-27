package rede.jogo;

public class Jogador {
    private String nome;
    private int vida;

    public Jogador(String nome) {
        this.nome = nome;
        this.vida = 100;
    }

    public void receberDano(int dano) {
        vida -= dano;
    }

//    public void curar(int valor) {
//        vida += valor;
//    }

    public int getVida() {
        return vida;
    }
}