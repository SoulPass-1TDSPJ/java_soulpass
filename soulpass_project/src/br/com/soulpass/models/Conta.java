package br.com.soulpass.models;

import br.com.soulpass.interfaces.ConverterParaCredito;

public class Conta implements ConverterParaCredito {
    private int idConta;
    private Usuario usuario;
    private double qtdePontos;
    private double limitePontos;

    public double getQtdePontos() {return qtdePontos;}

    public void setQtdePontos(double qtdePontos) {this.qtdePontos = qtdePontos;}

    public void adicionarPontos(double pontos){
        qtdePontos += pontos;
        System.out.println("----------------" + "\nAdicionado " + pontos + " ponto(s).");
    }

    public static double converterParaCredito(double pontos) {return pontos/110;}
}
