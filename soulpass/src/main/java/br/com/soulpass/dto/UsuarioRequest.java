package br.com.soulpass.dto;

/** Cadastro de usuário. A senha só trafega neste sentido: nunca é devolvida nas respostas. */
public record UsuarioRequest(
        String nome,
        int idade,
        long cpf,
        String email,
        String senha
) { }
