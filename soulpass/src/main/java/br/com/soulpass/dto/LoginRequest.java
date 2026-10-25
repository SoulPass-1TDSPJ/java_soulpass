package br.com.soulpass.dto;

public record LoginRequest(
        String email,
        String senha
) { }
