package br.com.soulpass.resouce;

import br.com.soulpass.bo.ContaService;
import br.com.soulpass.dto.*;
import br.com.soulpass.mapper.ContaMapper;
import br.com.soulpass.models.Conta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Conta SoulPass: "Visão geral" (resumo) e "Pontos e créditos" (saldo, simulação e conversão).
 */
@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public List<ContaResponse> listar() {
        return contaService.listar().stream().map(ContaMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ContaResponse buscarPorId(@PathVariable int id) {
        return ContaMapper.toResponse(contaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{idUser}")
    public ContaResponse buscarPorUsuario(@PathVariable int idUser) {
        return ContaMapper.toResponse(contaService.buscarPorUsuario(idUser));
    }

    /** Tela "Visão geral". */
    @GetMapping("/{id}/resumo")
    public ResumoContaResponse resumo(@PathVariable int id) {
        return contaService.resumo(id);
    }

    /** Tela "Pontos e créditos": prévia da conversão (ex.: /contas/1/conversao?pontos=605). */
    @GetMapping("/{id}/conversao")
    public SimulacaoConversaoResponse simularConversao(@PathVariable int id, @RequestParam double pontos) {
        return contaService.simularConversao(id, pontos);
    }

    /** Tela "Pontos e créditos": botão "Converter pontos". */
    @PostMapping("/{id}/conversao")
    public ConversaoResponse converter(@PathVariable int id, @RequestBody ConversaoRequest dto) {
        return contaService.converter(id, dto.pontos());
    }

    @PutMapping("/{id}")
    public ContaResponse atualizar(@PathVariable int id, @RequestBody ContaAtualizacaoRequest dto) {
        Conta conta = contaService.buscarPorId(id);
        ContaMapper.atualizarEntity(conta, dto);
        contaService.atualizar(conta);
        return ContaMapper.toResponse(conta);
    }

    @PatchMapping("/{id}/desativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable int id) {
        contaService.desativar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable int id) {
        contaService.excluir(id);
    }
}
