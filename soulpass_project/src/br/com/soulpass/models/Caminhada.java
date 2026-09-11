package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;

import java.time.LocalDateTime;

/**
 * Classe referente à função do sistema que permite a caminhada e o registro de quando se caminhou para a
 * obtenção de pontos
 * */
public class Caminhada {
    //Parâmetros do método caminhada
    private int idCaminhada;
    private LocalDateTime diaCaminhada;
    private double kmAndados;
    private Conta conta;

    //Métodos getter e setters dos parâmetros

    public int getIdCaminhada() {return idCaminhada;}

    public void setIdCaminhada(int idCaminhada) {this.idCaminhada = idCaminhada;}

    public LocalDateTime getDiaCaminhada() {return diaCaminhada;}

    public void setDiaCaminhada(LocalDateTime diaCaminhada) {this.diaCaminhada = diaCaminhada;}

    public double getKmAndados() {return kmAndados;}

    public void setKmAndados(double kmAndados) {this.kmAndados = kmAndados;}

    public Conta getConta() {return conta;}

    public void setConta(Conta conta) {this.conta = conta;}

    public int caminhar() {
        int pontosGanhos = calcularPontos(kmAndados);
        conta.adicionarPontos(pontosGanhos, OrigemPontos.CAMINHADA);
        return pontosGanhos;
    }

    public int calcularPontos(double kmAndados) {
        // Respeita o limite diário de km, caso o usuário ultrapasse
        double kmValidos = Math.min(kmAndados, 6);
        int pontosGanhos = 0;
        if (kmValidos >= 2){
            pontosGanhos += 40;
        }
        if (kmValidos >= 4){
            pontosGanhos += 40;
        }
        if (kmValidos >= 6){
            pontosGanhos += 40;
        }
        return pontosGanhos;
    }
}
