package br.com.soulpass.mapper;

import br.com.soulpass.dto.HistoricoPontosResponse;
import br.com.soulpass.dto.HistoricoResponse;
import br.com.soulpass.models.HistoricoPontos;

import java.util.List;

//Responsável pelas conversões objeto -> DTO e DTO -> objeto
public class HistoricoPontosMapper {

    public static HistoricoPontosResponse toResponse(HistoricoPontos historico) {
        return new HistoricoPontosResponse(
                historico.getIdHistorico(),
                historico.getConta() != null ? historico.getConta().getIdConta() : 0,
                historico.getOrigem(),
                historico.getPontosGanhos(),
                historico.getDataRegistro()
        );
    }

    /** Monta a resposta da tela de histórico: totais + registros. */
    public static HistoricoResponse toHistoricoResponse(List<HistoricoPontos> lista) {
        return new HistoricoResponse(
                lista.size(),
                lista.stream().mapToInt(HistoricoPontos::getPontosGanhos).sum(),
                lista.stream().map(HistoricoPontosMapper::toResponse).toList()
        );
    }
}
