package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;

import java.time.LocalDateTime;

public class Post {
    private int idPost;
    private Conta conta;
    private LocalDateTime data;
    private boolean temFoto;
    private boolean temvideo;
    private String textoPost;

    public int getIdPost() {return idPost;}

    public void setIdPost(int idPost) {this.idPost = idPost;}

    public Conta getConta() {return conta;}

    public void setConta(Conta conta) {this.conta = conta;}

    public LocalDateTime getData() {return data;}

    public void setData(LocalDateTime data) {this.data = data;}

    public boolean isTemFoto() {return temFoto;}

    public void setTemFoto(boolean temFoto) {this.temFoto = temFoto;}

    public boolean isTemvideo() {return temvideo;}

    public void setTemvideo(boolean temvideo) {this.temvideo = temvideo;}

    public String getTextoPost() {return textoPost;}

    public void setTextoPost(String textoPost) {this.textoPost = textoPost;}

    /**
     * Método de Post responsável por */
    public int postar(){
        int pontosGanhos = calcularPontos(temFoto, temvideo);
        conta.adicionarPontos(pontosGanhos, OrigemPontos.POST);
        return pontosGanhos;
    }

    public int calcularPontos(boolean temFoto, boolean temvideo) {
        int pontos = 10;
        if (temFoto) {pontos += 20;}
        if (temvideo) {pontos += 30;}
        return pontos;
    }
}