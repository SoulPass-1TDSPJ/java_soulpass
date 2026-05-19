package br.com.soulpass.tests;

import br.com.soulpass.models.ConversorPontos;

public class TesteConversorPontos {
    public static void main(String[] args) {

        int pontos = 550;

        double creditos = ConversorPontos.converterParaCredito(pontos);

        System.out.println("Pontos: " + pontos);
        System.out.println("Créditos: " + creditos);
    }
}

