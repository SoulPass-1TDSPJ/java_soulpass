package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;
import br.com.soulpass.enums.StatusConta;

public class Conta{
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

    /**
     * Método adicionar Pontos que adiciona os pontos à conta e retorna que a operação foi um sucesso
     *
     * @Param pontos identifica quantos pontos serão adicionados
     * @Param origem identifica de onde veio os pontos (CAMINHADA ou POST) para, na classe teste, fazer o histórico
     * de pontos*/
    public void adicionarPontos(double pontos, OrigemPontos origem){
        qtdePontos += pontos;
        System.out.println("----------------" + "\nAdicionado " + pontos + " ponto(s).");
    }

    /**
     * Método simples que converte os pontos selecionados da conta e o divide por 110
     *
     * @Param pontos identifica quantos pontos serão convertidos*/
    public static double converterParaCredito(double pontos) {return pontos/110;}
}