package br.com.soulpass.dto;

/** Quantidade de pontos a converter em crédito (110 pontos = R$ 1,00). */
public record ConversaoRequest(
        double pontos
) { }
