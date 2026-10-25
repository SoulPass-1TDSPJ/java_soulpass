package br.com.soulpass.dto;

public record RedefinirSenhaRequest(
        String email,
        String novaSenha
) { }
