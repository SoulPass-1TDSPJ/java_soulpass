package br.com.soulpass.dto;

import java.time.LocalDateTime;

public record CaminhadaResponse(
        int idCaminhada,
        LocalDateTime diaCaminhada,
        double kmAndados,
        int idConta
) { }
