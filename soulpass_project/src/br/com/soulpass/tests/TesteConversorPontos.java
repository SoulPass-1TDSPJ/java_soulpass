package br.com.soulpass.tests;

import br.com.soulpass.models.Conta;
import br.com.soulpass.models.ConversorPontos;

public class TesteConversorPontos {
    public static void main(String[] args) {

        int pontos = 550;

        double creditos = Conta.converterParaCredito(pontos);

        System.out.println("Pontos: " + pontos);
        System.out.println("Créditos: " + creditos);
    }
}

