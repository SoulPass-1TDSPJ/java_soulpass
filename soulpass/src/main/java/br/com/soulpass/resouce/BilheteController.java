package br.com.soulpass.resouce;

import br.com.soulpass.bo.BilheteService;
import br.com.soulpass.dto.BilheteAtualizacaoRequest;
import br.com.soulpass.dto.BilheteRequest;
import br.com.soulpass.dto.BilheteResponse;
import br.com.soulpass.mapper.BilheteMapper;
import br.com.soulpass.models.Bilhete;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Bilhete único: "Gerenciar bilhete" (consulta e cadastro vinculado ao usuário).
 */
@RestController
@RequestMapping("/bilhetes")
public class BilheteController {

    private final BilheteService bilheteService;

    public BilheteController(BilheteService bilheteService) {
        this.bilheteService = bilheteService;
    }

    @GetMapping
    public List<BilheteResponse> listar() {
        return bilheteService.listar().stream().map(BilheteMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public BilheteResponse buscarPorId(@PathVariable int id) {
        return BilheteMapper.toResponse(bilheteService.buscarPorId(id));
    }

    @GetMapping("/usuario/{idUser}")
    public BilheteResponse buscarPorUsuario(@PathVariable int idUser) {
        return BilheteMapper.toResponse(bilheteService.buscarPorUsuario(idUser));
    }

    /** Cadastra um bilhete (9 dígitos) e já o vincula ao usuário. */
    @PostMapping("/usuario/{idUser}")
    @ResponseStatus(HttpStatus.CREATED)
    public BilheteResponse vincular(@PathVariable int idUser, @RequestBody BilheteRequest dto) {
        Bilhete criado = bilheteService.vincularAoUsuario(idUser, BilheteMapper.toEntity(dto));
        return BilheteMapper.toResponse(criado);
    }

    @PutMapping("/{id}")
    public BilheteResponse atualizar(@PathVariable int id, @RequestBody BilheteAtualizacaoRequest dto) {
        Bilhete bilhete = bilheteService.buscarPorId(id);
        BilheteMapper.atualizarEntity(bilhete, dto);
        bilheteService.atualizar(bilhete);
        return BilheteMapper.toResponse(bilhete);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable int id) {
        bilheteService.excluir(id);
    }
}
