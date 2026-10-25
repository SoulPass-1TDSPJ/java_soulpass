package br.com.soulpass.dto;

import java.util.List;

/** Dados da tela "Visão geral". */
public record ResumoContaResponse(
        String nome,
        ContaResponse conta,
        double kmSemana,
        double kmHoje,
        double metaDiariaKm,
        int percentualMeta,
        double kmFaltantes,
        int limitePontosDia,
        List<DistanciaDiaResponse> ultimos7Dias,
        List<HistoricoPontosResponse> ultimasAtividades
) { }
