package br.com.soulpass.mapper;

import br.com.soulpass.bo.ContaService;
import br.com.soulpass.dto.ContaAtualizacaoRequest;
import br.com.soulpass.dto.ContaResponse;
import br.com.soulpass.models.Conta;

//Responsável pelas conversões objeto -> DTO e DTO -> objeto
public class ContaMapper {

    /** Aplica sobre a entidade existente apenas os campos que vieram preenchidos. */
    public static void atualizarEntity(Conta conta, ContaAtualizacaoRequest dto) {
        if (dto.atividade() != null) conta.setAtividade(dto.atividade());
        if (dto.qtdePontos() != null) conta.setQtdePontos(dto.qtdePontos());
        if (dto.limitePontos() != null) conta.setLimitePontos(dto.limitePontos());
    }

    public static ContaResponse toResponse(Conta conta) {
        var usuario = conta.getUsuario();
        return new ContaResponse(
                conta.getIdConta(),
                usuario != null ? usuario.getIdUser() : 0,
                conta.getAtividade(),
                conta.getQtdePontos(),
                conta.getLimitePontos(),
                ContaService.pontosParaCredito(conta.getQtdePontos()),
                ContaService.pontosFaltantesPassagem(conta.getQtdePontos()),
                usuario != null ? BilheteMapper.toResponse(usuario.getBilhete()) : null
        );
    }
}
