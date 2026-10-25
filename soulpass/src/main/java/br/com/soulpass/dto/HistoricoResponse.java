package br.com.soulpass.dto;

import java.util.List;

/** Dados da tela "Meu histórico": totais para os cartões do topo e a lista de registros. */
public record HistoricoResponse(
        int totalRegistros,
        int pontosConquistados,
        List<HistoricoPontosResponse> registros
) { }
