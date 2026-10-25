package br.com.soulpass.resouce;

import br.com.soulpass.bo.ContaService;
import br.com.soulpass.bo.UsuarioService;
import br.com.soulpass.dto.*;
import br.com.soulpass.mapper.ContaMapper;
import br.com.soulpass.mapper.UsuarioMapper;
import br.com.soulpass.models.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Cadastro, login, perfil ("Meu perfil") e recuperação de senha.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ContaService contaService;

    public UsuarioController(UsuarioService usuarioService, ContaService contaService) {
        this.usuarioService = usuarioService;
        this.contaService = contaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse cadastrar(@RequestBody UsuarioRequest dto) {
        Usuario criado = usuarioService.cadastrar(UsuarioMapper.toEntity(dto));
        return montarLogin(criado);
    }

    @PostMapping("/login")
    public LoginResponse logar(@RequestBody LoginRequest dto) {
        Usuario usuario = usuarioService.logar(dto.email(), dto.senha());
        return montarLogin(usuario);
    }

    @PutMapping("/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void redefinirSenha(@RequestBody RedefinirSenhaRequest dto) {
        usuarioService.redefinirSenha(dto.email(), dto.novaSenha());
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar().stream().map(UsuarioMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable int id) {
        return UsuarioMapper.toResponse(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(@PathVariable int id, @RequestBody UsuarioAtualizacaoRequest dto) {
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioMapper.atualizarEntity(usuario, dto);
        usuarioService.atualizar(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable int id) {
        usuarioService.excluir(id);
    }

    private LoginResponse montarLogin(Usuario usuario) {
        var conta = contaService.buscarPorUsuario(usuario.getIdUser());
        return new LoginResponse(UsuarioMapper.toResponse(usuario), ContaMapper.toResponse(conta));
    }
}
