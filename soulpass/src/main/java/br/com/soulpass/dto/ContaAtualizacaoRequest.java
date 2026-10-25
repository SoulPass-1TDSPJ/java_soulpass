package br.com.soulpass.dto;

import br.com.soulpass.enums.StatusConta;

/** Campos opcionais: o que vier nulo permanece como está. */
public record ContaAtualizacaoRequest(
        StatusConta atividade,
        Double qtdePontos,
        Double limitePontos
) { }
