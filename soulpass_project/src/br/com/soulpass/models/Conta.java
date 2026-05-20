package br.com.soulpass.models;

public class Conta {
    private double qtdePontos;
    private double limitePontos;
    private Usuario usuario;
    private double creditos;

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

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /*public void limitarPontos(double limitePontos, double qtdePontos){
        if (this.qtdePontos < this.limitePontos){
            System.out.println("----------------" + "\nAinda falta(m) " + (this.limitePontos - this.qtdePontos) + " ponto(s) para atingir o limite.");
        } else {
            this.qtdePontos = this.limitePontos;
            System.out.println("----------------" + "\nLimite de pontos atingido.");
        }
    }*/

    public void adicionarPontos(double pontos){
        this.qtdePontos += pontos;
        System.out.println("----------------" + "\nAdicionado " + pontos + " ponto(s).");
    }
}
