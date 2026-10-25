package br.com.soulpass.dto;

import br.com.soulpass.enums.OrigemPontos;

import java.time.LocalDateTime;

public record HistoricoPontosResponse(
        int idHistorico,
        int idConta,
        OrigemPontos origem,
        int pontosGanhos,
        LocalDateTime dataRegistro
) { }
