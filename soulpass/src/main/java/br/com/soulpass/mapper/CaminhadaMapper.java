package br.com.soulpass.mapper;

import br.com.soulpass.dto.CaminhadaRegistradaResponse;
import br.com.soulpass.dto.CaminhadaRequest;
import br.com.soulpass.dto.CaminhadaResponse;
import br.com.soulpass.models.Caminhada;
import br.com.soulpass.models.Conta;

//Responsável pelas conversões objeto -> DTO e DTO -> objeto
public class CaminhadaMapper {

    /** A conta vem apenas com o id; o BO carrega a conta completa. */
    public static Caminhada toEntity(CaminhadaRequest dto) {
        Conta conta = new Conta();
        conta.setIdConta(dto.idConta());

        Caminhada caminhada = new Caminhada();
        caminhada.setKmAndados(dto.kmAndados());
        caminhada.setConta(conta);
        return caminhada;
    }

    public static CaminhadaResponse toResponse(Caminhada caminhada) {
        return new CaminhadaResponse(
                caminhada.getIdCaminhada(),
                caminhada.getDiaCaminhada(),
                caminhada.getKmAndados(),
                caminhada.getConta() != null ? caminhada.getConta().getIdConta() : 0
        );
    }

    public static CaminhadaRegistradaResponse toRegistradaResponse(Caminhada caminhada, int pontosGanhos,
                                                                   double saldoPontos) {
        return new CaminhadaRegistradaResponse(
                caminhada.getDiaCaminhada(),
                caminhada.getKmAndados(),
                pontosGanhos,
                saldoPontos
        );
    }
}
