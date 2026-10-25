package br.com.soulpass.dto;

public record SimulacaoConversaoResponse(
        double pontosNecessarios,
        double saldoAposConversao,
        double creditoRecebido,
        boolean saldoSuficiente
) { }
