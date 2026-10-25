package br.com.soulpass.dto;

/** Campos opcionais: o que vier nulo permanece como está. O e-mail não é alterável pelo back-end atual. */
public record UsuarioAtualizacaoRequest(
        String nome,
        Integer idade,
        Long cpf
) { }
