package br.com.soulpass.dto;

import br.com.soulpass.enums.StatusConta;

public record ContaResponse(
        int idConta,
        int idUser,
        StatusConta atividade,
        double qtdePontos,
        double limitePontos,
        double equivalenteEmCredito,
        int pontosFaltantesPassagem,
        BilheteResponse bilhete
) { }
