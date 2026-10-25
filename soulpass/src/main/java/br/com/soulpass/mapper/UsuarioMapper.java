package br.com.soulpass.mapper;

import br.com.soulpass.dto.UsuarioAtualizacaoRequest;
import br.com.soulpass.dto.UsuarioRequest;
import br.com.soulpass.dto.UsuarioResponse;
import br.com.soulpass.models.Usuario;

//Responsável pelas conversões objeto -> DTO e DTO -> objeto
public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setIdade(dto.idade());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        return usuario;
    }

    /** Aplica sobre a entidade existente apenas os campos que vieram preenchidos. */
    public static void atualizarEntity(Usuario usuario, UsuarioAtualizacaoRequest dto) {
        if (dto.nome() != null && !dto.nome().isBlank()) usuario.setNome(dto.nome());
        if (dto.idade() != null) usuario.setIdade(dto.idade());
        if (dto.cpf() != null) usuario.setCpf(dto.cpf());
    }

    /** Nunca expõe a senha. */
    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getIdUser(),
                usuario.getNome(),
                usuario.getIdade(),
                usuario.getCpf(),
                usuario.getEmail(),
                BilheteMapper.toResponse(usuario.getBilhete())
        );
    }
}
