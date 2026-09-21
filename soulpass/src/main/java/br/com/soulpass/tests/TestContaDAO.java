package br.com.soulpass.tests;

import br.com.soulpass.dao.ContaDAO;
import br.com.soulpass.enums.StatusConta;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.Usuario;

import java.util.List;
import java.util.Scanner;

/**
 * Classe feita com IA. Já que só vai fazer os testes básicos de DAO, não quero perder muito tempo.
 * Classe de teste MANUAL (console) para ContaDAO.
 * Execute a classe (main) e utilize o menu para testar cada operação do CRUD.
 */
public class TestContaDAO {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ContaDAO contaDAO = new ContaDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarConta();
                case 2 -> buscarContaPorId();
                case 3 -> buscarContaPorUsuario();
                case 4 -> listarContas();
                case 5 -> atualizarConta();
                case 6 -> excluirConta();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== TESTE MANUAL - ContaDAO =====");
        System.out.println("1 - Cadastrar conta");
        System.out.println("2 - Buscar conta por id");
        System.out.println("3 - Buscar conta por usuário");
        System.out.println("4 - Listar contas");
        System.out.println("5 - Atualizar conta");
        System.out.println("6 - Excluir conta");
        System.out.println("0 - Sair");
    }

    private static void cadastrarConta() {
        System.out.println("--- Cadastrar conta ---");

        Conta conta = new Conta();
        conta.setAtividade(lerStatusConta());

        Usuario usuario = new Usuario();
        usuario.setIdUser(lerInt("Id_user do dono da conta: "));
        conta.setUsuario(usuario);

        conta.setQtdePontos(lerDouble("Quantidade de pontos inicial: "));
        conta.setLimitePontos(lerDouble("Limite de pontos: "));

        try {
            contaDAO.cadastrarConta(conta);
            System.out.println("Conta cadastrada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar conta: " + e.getMessage());
        }
    }

    private static void buscarContaPorId() {
        System.out.println("--- Buscar conta por id ---");
        int id = lerInt("Informe o id_conta: ");

        try {
            Conta conta = contaDAO.buscarContaPorId(id);
            if (conta == null) {
                System.out.println("Nenhuma conta encontrada com esse id.");
            } else {
                imprimirConta(conta);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar conta: " + e.getMessage());
        }
    }

    private static void buscarContaPorUsuario() {
        System.out.println("--- Buscar conta por usuário ---");
        int idUser = lerInt("Informe o id_user: ");

        try {
            Conta conta = contaDAO.buscarContaPorUsuario(idUser);
            if (conta == null) {
                System.out.println("Esse usuário não possui conta.");
            } else {
                imprimirConta(conta);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar conta: " + e.getMessage());
        }
    }

    private static void listarContas() {
        System.out.println("--- Listar contas ---");

        try {
            List<Conta> contas = contaDAO.listarContas();
            if (contas.isEmpty()) {
                System.out.println("Nenhuma conta cadastrada.");
            } else {
                contas.forEach(TestContaDAO::imprimirConta);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar contas: " + e.getMessage());
        }
    }

    private static void atualizarConta() {
        System.out.println("--- Atualizar conta ---");

        Conta conta = new Conta();
        conta.setIdConta(lerInt("Id_conta a ser atualizada: "));
        conta.setAtividade(lerStatusConta());
        conta.setQtdePontos(lerDouble("Nova quantidade de pontos: "));
        conta.setLimitePontos(lerDouble("Novo limite de pontos: "));

        try {
            contaDAO.atualizarConta(conta);
            System.out.println("Conta atualizada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar conta: " + e.getMessage());
        }
    }

    private static void excluirConta() {
        System.out.println("--- Excluir conta ---");
        int id = lerInt("Informe o id_conta a ser excluído: ");

        try {
            contaDAO.excluirConta(id);
            System.out.println("Conta excluída com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir conta: " + e.getMessage());
        }
    }

    private static void imprimirConta(Conta conta) {
        System.out.println("-----------------------------------");
        System.out.println("id_conta      : " + conta.getIdConta());
        System.out.println("atividade     : " + conta.getAtividade());
        System.out.println("qtde_pontos   : " + conta.getQtdePontos());
        System.out.println("limite_pontos : " + conta.getLimitePontos());
        System.out.println("id_user       : " + (conta.getUsuario() != null
                ? conta.getUsuario().getIdUser() : "desconhecido"));
        System.out.println("-----------------------------------");
    }

    // ---------- utilitários de leitura ----------

    private static StatusConta lerStatusConta() {
        while (true) {
            System.out.print("Atividade (" + valoresEnum(StatusConta.values()) + "): ");
            String valor = scanner.nextLine().trim().toUpperCase();
            try {
                return StatusConta.valueOf(valor);
            } catch (IllegalArgumentException e) {
                System.out.println("Valor inválido, tente novamente.");
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

    private static double lerDouble(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextDouble()) {
            System.out.print("Valor inválido. " + mensagem);
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }
}
