package br.com.soulpass.models;

public class Conta {
    private double qtdePontos;
    private double limitePontos;
    private Usuario usuario;

    public double getQtdePontos() {
        return qtdePontos;
    }

    public void setQtdePontos(double qtdePontos) {
        this.qtdePontos = qtdePontos;
    }

    public double getLimitePontos() {
        return limitePontos;
    }

    public void setLimitePontos(double limitePontos) {
        this.limitePontos = 5500;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void limitarPontos(double limitePontos, double qtdePontos){
        if (this.qtdePontos < this.limitePontos){
            System.out.println("Ainda falta(m) " + (this.limitePontos - this.qtdePontos) + " ponto(s) para atingir o limite.");
        }
        else{
            this.qtdePontos = this.limitePontos;
            System.out.println("Limite de pontos atingido.");
        }
    }

    public void adicionarPontos(double pontos){
        if (pontos < 0) {
            System.out.println("Erro ao adicionar pontos, não há como ter pontos negativos.");
        }
        qtdePontos += pontos;
        System.out.println("Adicionado " + pontos + " ponto(s).");
    }
}
