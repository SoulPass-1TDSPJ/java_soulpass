package br.com.soulpass.mapper;

import br.com.soulpass.dto.BilheteAtualizacaoRequest;
import br.com.soulpass.dto.BilheteRequest;
import br.com.soulpass.dto.BilheteResponse;
import br.com.soulpass.models.Bilhete;

//Responsável pelas conversões objeto -> DTO e DTO -> objeto
public class BilheteMapper {

    public static Bilhete toEntity(BilheteRequest dto) {
        Bilhete bilhete = new Bilhete();
        bilhete.setNumBilhete(dto.numBilhete());
        return bilhete;
    }

    /** Aplica sobre a entidade existente apenas os campos que vieram preenchidos. */
    public static void atualizarEntity(Bilhete bilhete, BilheteAtualizacaoRequest dto) {
        if (dto.credito() != null) bilhete.setCredito(dto.credito());
        if (dto.status() != null) bilhete.setStatus(dto.status());
    }

    public static BilheteResponse toResponse(Bilhete bilhete) {
        if (bilhete == null) return null;
        return new BilheteResponse(
                bilhete.getIdTicket(),
                bilhete.getCredito(),
                bilhete.getNumBilhete(),
                bilhete.getStatus()
        );
    }
}
