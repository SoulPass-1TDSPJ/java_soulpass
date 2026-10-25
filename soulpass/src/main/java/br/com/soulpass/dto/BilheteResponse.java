package br.com.soulpass.dto;

import br.com.soulpass.enums.StatusBilhete;

public record BilheteResponse(
        int idTicket,
        double credito,
        long numBilhete,
        StatusBilhete status
) { }
