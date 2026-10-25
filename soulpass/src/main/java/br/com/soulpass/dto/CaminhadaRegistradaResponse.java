package br.com.soulpass.dto;

import java.time.LocalDateTime;

/** Resultado de registrar uma caminhada: o que foi salvo e quantos pontos rendeu. */
public record CaminhadaRegistradaResponse(
        LocalDateTime diaCaminhada,
        double kmAndados,
        int pontosGanhos,
        double saldoPontos
) { }
