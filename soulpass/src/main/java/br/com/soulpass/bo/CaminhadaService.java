package br.com.soulpass.bo;

import br.com.soulpass.dao.CaminhadaDAO;
import br.com.soulpass.dto.CaminhadaRegistradaResponse;
import br.com.soulpass.enums.OrigemPontos;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import br.com.soulpass.mapper.CaminhadaMapper;
import br.com.soulpass.models.Caminhada;
import br.com.soulpass.models.Conta;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Regras de negócio da caminhada: 40 pontos a cada 2 km (até 6 km) e limite de 120 pontos por dia.
 */
@Service
public class CaminhadaService {

    private final ContaService contaService;
    private final HistoricoPontosService historicoService;

    public CaminhadaService(ContaService contaService, HistoricoPontosService historicoService) {
        this.contaService = contaService;
        this.historicoService = historicoService;
    }

    /**
     * Registra a caminhada, calcula os pontos (respeitando o limite diário), atualiza a conta e grava o
     * histórico — mesmo fluxo do fazerCaminhada do TestSoulPass, acrescido do limite diário do README.
     */
    public CaminhadaRegistradaResponse registrar(Caminhada caminhada) {
        if (caminhada.getKmAndados() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A distância deve ser maior que zero");
        }
        Conta conta = contaService.buscarContaAtiva(caminhada.getConta().getIdConta());
        caminhada.setConta(conta);
        caminhada.setDiaCaminhada(LocalDateTime.now());

        new CaminhadaDAO().registrarCaminhada(caminhada);

        int jaGanhosHoje = historicoService.pontosGanhosNoDia(conta.getIdConta(), OrigemPontos.CAMINHADA,
                caminhada.getDiaCaminhada().toLocalDate());
        int restanteHoje = Math.max(0, ContaService.LIMITE_PONTOS_DIA_CAMINHADA - jaGanhosHoje);
        int pontosGanhos = Math.min(caminhada.calcularPontos(caminhada.getKmAndados()), restanteHoje);

        if (pontosGanhos > 0) {
            conta.adicionarPontos(pontosGanhos, OrigemPontos.CAMINHADA);
            contaService.atualizar(conta);
            historicoService.registrar(conta, OrigemPontos.CAMINHADA, pontosGanhos);
        }
        return CaminhadaMapper.toRegistradaResponse(caminhada, pontosGanhos, conta.getQtdePontos());
    }

    /** Caminhadas da conta, da mais recente para a mais antiga. */
    public List<Caminhada> listarPorConta(int idConta) {
        contaService.buscarPorId(idConta);
        return new CaminhadaDAO().listarCaminhadasPorConta(idConta).stream()
                .sorted(Comparator.comparing(Caminhada::getDiaCaminhada).reversed())
                .toList();
    }

    /**
     * Usa o CaminhadaDAO.buscarPostPorId (nome herdado de uma cópia antiga de DAO, mas busca em tbl_caminhada).
     * O DAO devolve uma Caminhada vazia quando não encontra e não preenche o id, por isso a checagem
     * pelo dia e o setIdCaminhada.
     */
    public Caminhada buscarPorId(int id) {
        Caminhada caminhada = new CaminhadaDAO().buscarPostPorId(id);
        if (caminhada == null || caminhada.getDiaCaminhada() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhada não encontrada");
        }
        caminhada.setIdCaminhada(id);
        return caminhada;
    }

    public void excluir(int id) {
        buscarPorId(id);
        new CaminhadaDAO().excluirCaminhada(id);
    }
}
