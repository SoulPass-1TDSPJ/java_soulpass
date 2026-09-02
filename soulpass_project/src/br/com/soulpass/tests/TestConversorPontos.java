package br.com.soulpass.tests;

import br.com.soulpass.models.Conta;

public class TestConversorPontos {
    public static void main(String[] args) {

        int pontos = 550;

        double creditos = Conta.converterParaCredito(pontos);

        System.out.println("Pontos: " + pontos);
        System.out.println("Créditos: " + creditos);
    }
}

