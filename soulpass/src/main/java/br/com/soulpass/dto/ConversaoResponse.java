package br.com.soulpass.dto;

public record ConversaoResponse(
        double pontosConvertidos,
        double creditoRecebido,
        double saldoPontos,
        double saldoBilhete
) { }
