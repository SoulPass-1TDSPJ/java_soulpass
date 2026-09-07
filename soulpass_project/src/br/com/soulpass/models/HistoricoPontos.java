package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;

import java.time.LocalDateTime;

public class HistoricoPontos {
    private int idHistorico;
    private Conta conta;
    private OrigemPontos origem;
    private int pontosGanhos;
    private LocalDateTime dataRegistro;

    public int getIdHistorico() {return idHistorico;}

    public void setIdHistorico(int idHistorico) {this.idHistorico = idHistorico;}

    public Conta getConta() {return conta;}

    public void setConta(Conta conta) {this.conta = conta;}

    public OrigemPontos getOrigem() {return origem;}

    public void setOrigem(OrigemPontos origem) {this.origem = origem;}

    public int getPontosGanhos() {return pontosGanhos;}

    public void setPontosGanhos(int pontosGanhos) {this.pontosGanhos = pontosGanhos;}

    public LocalDateTime getDataRegistro() {return dataRegistro;}

    public void setDataRegistro(LocalDateTime dataRegistro) {this.dataRegistro = dataRegistro;}
}
