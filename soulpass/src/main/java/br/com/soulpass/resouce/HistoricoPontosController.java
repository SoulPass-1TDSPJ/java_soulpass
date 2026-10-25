package br.com.soulpass.resouce;

import br.com.soulpass.bo.HistoricoPontosService;
import br.com.soulpass.dto.HistoricoPontosResponse;
import br.com.soulpass.dto.HistoricoResponse;
import br.com.soulpass.enums.OrigemPontos;
import br.com.soulpass.mapper.HistoricoPontosMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Tela "Meu histórico": pontos conquistados por caminhada.
 */
@RestController
@RequestMapping("/historicos")
public class HistoricoPontosController {

    private final HistoricoPontosService historicoService;

    public HistoricoPontosController(HistoricoPontosService historicoService) {
        this.historicoService = historicoService;
    }

    /** Aceita filtro opcional: /historicos/conta/1?origem=CAMINHADA (abas "Todas" e "Caminhadas"). */
    @GetMapping("/conta/{idConta}")
    public HistoricoResponse listarPorConta(@PathVariable int idConta,
                                            @RequestParam(required = false) OrigemPontos origem) {
        return HistoricoPontosMapper.toHistoricoResponse(historicoService.listarPorConta(idConta, origem));
    }

    @GetMapping("/{id}")
    public HistoricoPontosResponse buscarPorId(@PathVariable int id) {
        return HistoricoPontosMapper.toResponse(historicoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable int id) {
        historicoService.excluir(id);
    }
}
