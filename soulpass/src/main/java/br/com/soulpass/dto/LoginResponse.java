package br.com.soulpass.dto;

/** Resposta de login/cadastro: traz a conta junto, pois as demais telas trabalham com o idConta. */
public record LoginResponse(
        UsuarioResponse usuario,
        ContaResponse conta
) { }
