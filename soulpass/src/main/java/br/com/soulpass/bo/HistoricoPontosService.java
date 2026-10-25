package br.com.soulpass.bo;

import br.com.soulpass.dao.HistoricoPontosDAO;
import br.com.soulpass.enums.OrigemPontos;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.HistoricoPontos;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Regras de negócio do histórico de pontos (pontos ganhos por caminhada).
 */
@Service
public class HistoricoPontosService {

    private final ContaService contaService;

    public HistoricoPontosService(ContaService contaService) {
        this.contaService = contaService;
    }

    /** Histórico da conta, do mais recente para o mais antigo, opcionalmente filtrado por origem. */
    public List<HistoricoPontos> listarPorConta(int idConta, OrigemPontos origem) {
        contaService.buscarPorId(idConta);
        return new HistoricoPontosDAO().listarHistoricoPorConta(idConta).stream()
                .filter(h -> origem == null || h.getOrigem() == origem)
                .sorted(Comparator.comparing(HistoricoPontos::getDataRegistro).reversed())
                .toList();
    }

    /** O DAO devolve um registro vazio (idHistorico = 0) quando não encontra, por isso a checagem do id. */
    public HistoricoPontos buscarPorId(int id) {
        HistoricoPontos historico = new HistoricoPontosDAO().buscarHistoricoPorId(id);
        if (historico == null || historico.getIdHistorico() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado");
        }
        return historico;
    }

    /** Grava um registro de pontos ganhos. Não registra quando a ação não rendeu pontos. */
    public void registrar(Conta conta, OrigemPontos origem, int pontosGanhos) {
        if (pontosGanhos <= 0) {
            return;
        }
        HistoricoPontos historico = new HistoricoPontos();
        historico.setConta(conta);
        historico.setOrigem(origem);
        historico.setPontosGanhos(pontosGanhos);
        historico.setDataRegistro(LocalDateTime.now());
        new HistoricoPontosDAO().registrarHistorico(historico);
    }

    /** Soma dos pontos ganhos pela origem informada em um dia (usado para o limite diário de 120 pontos). */
    public int pontosGanhosNoDia(int idConta, OrigemPontos origem, LocalDate dia) {
        return new HistoricoPontosDAO().listarHistoricoPorConta(idConta).stream()
                .filter(h -> h.getOrigem() == origem)
                .filter(h -> h.getDataRegistro().toLocalDate().equals(dia))
                .mapToInt(HistoricoPontos::getPontosGanhos)
                .sum();
    }

    public void excluir(int id) {
        buscarPorId(id);
        new HistoricoPontosDAO().excluirHistorico(id);
    }
}
