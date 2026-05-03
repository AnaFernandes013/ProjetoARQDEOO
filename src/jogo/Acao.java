package rede.jogo;

import java.util.Random;

public class Acao {

    //será utilizado para jogar o dado de 20 lados de forma aleatoria 
    private Random random = new Random();

    public String ataqueNormal(Jogador alvo) {
        int rolagem = random.nextInt(20) + 1;
        int dano = 10;
        return alvo.receberDano(rolagem, dano);
    }

    public String ataqueVantagem(Jogador alvo) {
        int dado1 = random.nextInt(20) + 1;
        int dado2 = random.nextInt(20) + 1;
        int rolagem = Math.max(dado1, dado2); // recupera o número amis alto dentre os dois resultados (vantagem)
        int dano = 5;
        return alvo.receberDano(rolagem, dano);
    }

    public String ataqueDesvantagem(Jogador alvo) {
        int dado1 = random.nextInt(20) + 1;
        int dado2 = random.nextInt(20) + 1;
        int rolagem = Math.min(dado1, dado2); // recupera o número mais baixo dentre os dois resultados (desvantagem)
        int dano = 20;
        return alvo.receberDano(rolagem, dano);
    }
}