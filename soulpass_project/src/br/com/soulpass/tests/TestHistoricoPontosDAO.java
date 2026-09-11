package br.com.soulpass.tests;

import br.com.soulpass.dao.HistoricoPontosDAO;
import br.com.soulpass.enums.OrigemPontos;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.HistoricoPontos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe feita com IA. Já que só vai fazer os testes básicos de DAO, não quero perder muito tempo.
 * Classe de teste MANUAL (console) para HistoricoPontosDAO.
 * Execute a classe (main) e utilize o menu para testar cada operação do CRUD.
 */
public class TestHistoricoPontosDAO {

    private static final Scanner scanner = new Scanner(System.in);
    private static final HistoricoPontosDAO historicoDAO = new HistoricoPontosDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> registrarHistorico();
                case 2 -> buscarHistoricoPorId();
                case 3 -> listarHistoricoPorConta();
                case 4 -> excluirHistorico();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== TESTE MANUAL - HistoricoPontosDAO =====");
        System.out.println("1 - Registrar histórico de pontos");
        System.out.println("2 - Buscar histórico por id");
        System.out.println("3 - Listar histórico por conta");
        System.out.println("4 - Excluir histórico");
        System.out.println("0 - Sair");
    }

    private static void registrarHistorico() {
        System.out.println("--- Registrar histórico de pontos ---");

        HistoricoPontos historico = new HistoricoPontos();

        Conta conta = new Conta();
        conta.setIdConta(lerInt("Id_conta vinculada: "));
        historico.setConta(conta);

        historico.setOrigem(lerOrigem());
        historico.setPontosGanhos(lerInt("Pontos ganhos: "));
        historico.setDataRegistro(lerDataHora("Data do registro (aaaa-mm-dd hh:mm): "));

        try {
            historicoDAO.registrarHistorico(historico);
            System.out.println("Histórico registrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao registrar histórico: " + e.getMessage());
        }
    }

    private static void buscarHistoricoPorId() {
        System.out.println("--- Buscar histórico por id ---");
        int id = lerInt("Informe o id_historico: ");

        try {
            HistoricoPontos historico = historicoDAO.buscarHistoricoPorId(id);
            if (historico == null) {
                System.out.println("Nenhum histórico encontrado com esse id.");
            } else {
                imprimirHistorico(historico);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar histórico: " + e.getMessage());
        }
    }

    private static void listarHistoricoPorConta() {
        System.out.println("--- Listar histórico por conta ---");
        int idConta = lerInt("Informe o id_conta: ");

        try {
            List<HistoricoPontos> lista = historicoDAO.listarHistoricoPorConta(idConta);
            if (lista.isEmpty()) {
                System.out.println("Nenhum histórico encontrado para essa conta.");
            } else {
                lista.forEach(TestHistoricoPontosDAO::imprimirHistorico);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar histórico: " + e.getMessage());
        }
    }

    private static void excluirHistorico() {
        System.out.println("--- Excluir histórico ---");
        int id = lerInt("Informe o id_historico a ser excluído: ");

        try {
            historicoDAO.excluirHistorico(id);
            System.out.println("Histórico excluído com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir histórico: " + e.getMessage());
        }
    }

    private static void imprimirHistorico(HistoricoPontos historico) {
        System.out.println("-----------------------------------");
        System.out.println("id_historico   : " + historico.getIdHistorico());
        System.out.println("origem         : " + historico.getOrigem());
        System.out.println("pontos_ganhos  : " + historico.getPontosGanhos());
        System.out.println("data_registro  : " + historico.getDataRegistro());
        System.out.println("id_conta       : " + (historico.getConta() != null
                ? historico.getConta().getIdConta() : "desconhecido"));
        System.out.println("-----------------------------------");
    }

    // ---------- utilitários de leitura ----------

    private static OrigemPontos lerOrigem() {
        while (true) {
            System.out.print("Origem (" + valoresEnum(OrigemPontos.values()) + "): ");
            String valor = scanner.nextLine().trim().toUpperCase();
            try {
                return OrigemPontos.valueOf(valor);
            } catch (IllegalArgumentException e) {
                System.out.println("Origem inválida, tente novamente.");
            }
        }
    }

    private static String valoresEnum(Enum<?>[] valores) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valores.length; i++) {
            sb.append(valores[i].name());
            if (i < valores.length - 1) sb.append("/");
        }
        return sb.toString();
    }

    private static LocalDateTime lerDataHora(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim().replace(" ", "T");
            try {
                return LocalDateTime.parse(valor);
            } catch (Exception e) {
                System.out.println("Data/hora inválida, use o formato aaaa-mm-dd hh:mm.");
            }
        }
    }

    private static int lerInt(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Valor inválido. " + mensagem);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
