package br.com.soulpass.dto;

import java.time.LocalDate;

public record DistanciaDiaResponse(
        LocalDate data,
        double km
) { }
