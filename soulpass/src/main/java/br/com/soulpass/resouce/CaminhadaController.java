package br.com.soulpass.resouce;

import br.com.soulpass.bo.CaminhadaService;
import br.com.soulpass.dto.CaminhadaRegistradaResponse;
import br.com.soulpass.dto.CaminhadaRequest;
import br.com.soulpass.dto.CaminhadaResponse;
import br.com.soulpass.mapper.CaminhadaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Tela "Minhas caminhadas" e botão "Registrar caminhada".
 */
@RestController
@RequestMapping("/caminhadas")
public class CaminhadaController {

    private final CaminhadaService caminhadaService;

    public CaminhadaController(CaminhadaService caminhadaService) {
        this.caminhadaService = caminhadaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CaminhadaRegistradaResponse registrar(@RequestBody CaminhadaRequest dto) {
        return caminhadaService.registrar(CaminhadaMapper.toEntity(dto));
    }

    @GetMapping("/conta/{idConta}")
    public List<CaminhadaResponse> listarPorConta(@PathVariable int idConta) {
        return caminhadaService.listarPorConta(idConta).stream().map(CaminhadaMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public CaminhadaResponse buscarPorId(@PathVariable int id) {
        return CaminhadaMapper.toResponse(caminhadaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable int id) {
        caminhadaService.excluir(id);
    }
}
