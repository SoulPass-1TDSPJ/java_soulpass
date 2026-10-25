package br.com.soulpass.dto;

import br.com.soulpass.enums.StatusBilhete;

/** Campos opcionais: o que vier nulo permanece como está. */
public record BilheteAtualizacaoRequest(
        Double credito,
        StatusBilhete status
) { }
