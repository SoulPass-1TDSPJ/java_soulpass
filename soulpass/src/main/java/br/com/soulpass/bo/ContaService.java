package br.com.soulpass.bo;

import br.com.soulpass.dao.BilheteDAO;
import br.com.soulpass.dao.CaminhadaDAO;
import br.com.soulpass.dao.ContaDAO;
import br.com.soulpass.dao.HistoricoPontosDAO;
import br.com.soulpass.dto.ConversaoResponse;
import br.com.soulpass.dto.DistanciaDiaResponse;
import br.com.soulpass.dto.ResumoContaResponse;
import br.com.soulpass.dto.SimulacaoConversaoResponse;
import br.com.soulpass.enums.StatusConta;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import br.com.soulpass.mapper.ContaMapper;
import br.com.soulpass.mapper.HistoricoPontosMapper;
import br.com.soulpass.models.Bilhete;
import br.com.soulpass.models.Caminhada;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.HistoricoPontos;
import br.com.soulpass.models.Usuario;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Regras de negócio da conta: saldo de pontos, conversão em crédito e resumo da tela "Visão geral".
 * Os DAOs são criados a cada chamada (e não guardados em campo) porque guardam a Connection em atributo
 * e este BO é um singleton atendendo várias requisições ao mesmo tempo.
 */
@Service
public class ContaService {

    /** Pontos equivalentes a uma passagem completa (R$ 5,50). */
    public static final int PONTOS_PASSAGEM = 605;

    /** Limite de pontos ganhos por caminhada em um mesmo dia. */
    public static final int LIMITE_PONTOS_DIA_CAMINHADA = 120;

    /** Meta diária de quilômetros caminhados. */
    public static final double META_DIARIA_KM = 6;

    /** Limite de pontos de uma conta nova (mesmo valor usado no cadastro do TestSoulPass). */
    private static final double LIMITE_INICIAL_PONTOS = 1000;

    /**
     * Converte pontos em crédito (R$) usando {@link Conta#converterParaCredito(double)} e arredondando
     * sempre para baixo no centavo, para nunca gerar crédito que o usuário não conquistou.
     */
    public static double pontosParaCredito(double pontos) {
        return BigDecimal.valueOf(Conta.converterParaCredito(pontos)).setScale(2, RoundingMode.DOWN).doubleValue();
    }

    /** Quantos pontos ainda faltam para completar uma passagem (0 se já completou). */
    public static int pontosFaltantesPassagem(double pontos) {
        return (int) Math.ceil(Math.max(0, PONTOS_PASSAGEM - pontos));
    }

    /** Arredonda (meio para cima) para a quantidade de casas decimais informada. */
    public static double arredondar(double valor, int casas) {
        return BigDecimal.valueOf(valor).setScale(casas, RoundingMode.HALF_UP).doubleValue();
    }

    public List<Conta> listar() {
        return new ContaDAO().listarContas();
    }

    /** O DAO devolve uma Conta vazia (idConta = 0) quando não encontra, por isso a checagem do id. */
    public Conta buscarPorId(int id) {
        Conta conta = new ContaDAO().buscarContaPorId(id);
        if (conta == null || conta.getIdConta() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada");
        }
        return conta;
    }

    public Conta buscarPorUsuario(int idUser) {
        Conta conta = new ContaDAO().buscarContaPorUsuario(idUser);
        if (conta == null || conta.getIdConta() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada para o usuário informado");
        }
        return conta;
    }

    /** Busca a conta e garante que ela esteja ativa (necessário para caminhar e converter). */
    public Conta buscarContaAtiva(int id) {
        Conta conta = buscarPorId(id);
        if (conta.getAtividade() == StatusConta.INATIVO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conta inativa");
        }
        return conta;
    }

    /** Cria a conta SoulPass de um usuário recém-cadastrado: ativa, 0 pontos. */
    public Conta criarParaUsuario(Usuario usuario) {
        Conta conta = new Conta();
        conta.setUsuario(usuario);
        conta.setAtividade(StatusConta.ATIVO);
        conta.setQtdePontos(0);
        conta.setLimitePontos(LIMITE_INICIAL_PONTOS);
        new ContaDAO().cadastrarConta(conta);
        return buscarPorUsuario(usuario.getIdUser());
    }

    public void atualizar(Conta conta) {
        if (conta.getAtividade() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status da conta é obrigatório");
        }
        if (conta.getQtdePontos() < 0 || conta.getLimitePontos() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pontos e limite não podem ser negativos");
        }
        new ContaDAO().atualizarConta(conta);
    }

    public void desativar(int id) {
        Conta conta = buscarPorId(id);
        conta.setAtividade(StatusConta.INATIVO);
        atualizar(conta);
    }

    public void excluir(int id) {
        buscarPorId(id);
        new ContaDAO().excluirConta(id);
    }

    /** Tela "Pontos e créditos": mostra quanto o usuário receberia e como ficaria o saldo. */
    public SimulacaoConversaoResponse simularConversao(int idConta, double pontos) {
        Conta conta = buscarPorId(idConta);
        if (pontos <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe uma quantidade de pontos maior que zero");
        }
        return new SimulacaoConversaoResponse(
                pontos,
                conta.getQtdePontos() - pontos,
                pontosParaCredito(pontos),
                pontos <= conta.getQtdePontos()
        );
    }

    /**
     * Converte pontos em crédito no bilhete do usuário e debita os pontos da conta
     * (regra do converterPontosCredito do TestSoulPass).
     */
    public ConversaoResponse converter(int idConta, double pontos) {
        Conta conta = buscarContaAtiva(idConta);
        Bilhete bilhete = conta.getUsuario() != null ? conta.getUsuario().getBilhete() : null;
        if (bilhete == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cadastre um bilhete antes de converter pontos em crédito");
        }
        if (pontos <= 0 || pontos > conta.getQtdePontos()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de pontos inválida ou maior que o saldo");
        }
        double credito = pontosParaCredito(pontos);
        if (credito <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de pontos insuficiente para gerar crédito");
        }

        bilhete.setCredito(arredondar(bilhete.getCredito() + credito, 2));
        new BilheteDAO().atualizarBilhete(bilhete);

        conta.setQtdePontos(conta.getQtdePontos() - pontos);
        atualizar(conta);

        return new ConversaoResponse(pontos, credito, conta.getQtdePontos(), bilhete.getCredito());
    }

    /** Tela "Visão geral": saldo, distância da semana, meta do dia e últimas atividades. */
    public ResumoContaResponse resumo(int idConta) {
        Conta conta = buscarPorId(idConta);

        LocalDate hoje = LocalDate.now();
        Map<LocalDate, Double> kmPorDia = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            kmPorDia.put(hoje.minusDays(i), 0.0);
        }
        for (Caminhada c : new CaminhadaDAO().listarCaminhadasPorConta(idConta)) {
            kmPorDia.computeIfPresent(c.getDiaCaminhada().toLocalDate(), (dia, km) -> km + c.getKmAndados());
        }

        List<DistanciaDiaResponse> ultimos7Dias = kmPorDia.entrySet().stream()
                .map(e -> new DistanciaDiaResponse(e.getKey(), arredondar(e.getValue(), 2)))
                .toList();
        double kmSemana = arredondar(kmPorDia.values().stream().mapToDouble(Double::doubleValue).sum(), 2);
        double kmHoje = arredondar(kmPorDia.get(hoje), 2);
        int percentual = (int) Math.min(100, Math.round(kmHoje / META_DIARIA_KM * 100));
        double kmFaltantes = arredondar(Math.max(0, META_DIARIA_KM - kmHoje), 2);

        var ultimasAtividades = new HistoricoPontosDAO().listarHistoricoPorConta(idConta).stream()
                .sorted(Comparator.comparing(HistoricoPontos::getDataRegistro).reversed())
                .limit(4)
                .map(HistoricoPontosMapper::toResponse)
                .toList();

        return new ResumoContaResponse(
                conta.getUsuario() != null ? conta.getUsuario().getNome() : null,
                ContaMapper.toResponse(conta),
                kmSemana,
                kmHoje,
                META_DIARIA_KM,
                percentual,
                kmFaltantes,
                LIMITE_PONTOS_DIA_CAMINHADA,
                ultimos7Dias,
                ultimasAtividades
        );
    }
}
