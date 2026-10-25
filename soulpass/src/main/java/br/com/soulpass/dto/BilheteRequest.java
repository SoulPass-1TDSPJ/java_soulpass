package br.com.soulpass.dto;

/** O número do bilhete deve ter exatamente 9 dígitos. */
public record BilheteRequest(
        long numBilhete
) { }
