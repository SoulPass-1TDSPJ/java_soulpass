package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;
import br.com.soulpass.enums.StatusConta;
import br.com.soulpass.interfaces.ConverterParaCredito;

public class Conta implements ConverterParaCredito {
    private int idConta;
    private StatusConta atividade;
    private Usuario usuario;
    private double qtdePontos;
    private double limitePontos;

    public int getIdConta() {return idConta;}

    public void setIdConta(int idConta) {this.idConta = idConta;}

    public StatusConta getAtividade() {return atividade;}

    public void setAtividade(StatusConta atividade) {this.atividade = atividade;}

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public double getLimitePontos() {return limitePontos;}

    public void setLimitePontos(double limitePontos) {this.limitePontos = limitePontos;}

    public double getQtdePontos() {return qtdePontos;}

    public void setQtdePontos(double qtdePontos) {this.qtdePontos = qtdePontos;}

    public void adicionarPontos(double pontos, OrigemPontos origem){
        qtdePontos += pontos;
        System.out.println("----------------" + "\nAdicionado " + pontos + " ponto(s).");
    }

    public static double converterParaCredito(double pontos) {return pontos/110;}
}