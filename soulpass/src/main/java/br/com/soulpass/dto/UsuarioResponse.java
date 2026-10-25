package br.com.soulpass.dto;

public record UsuarioResponse(
        int idUser,
        String nome,
        int idade,
        long cpf,
        String email,
        BilheteResponse bilhete
) { }
