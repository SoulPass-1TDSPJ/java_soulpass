package br.com.soulpass.tests;

import br.com.soulpass.dao.BilheteDAO;
import br.com.soulpass.enums.StatusBilhete;
import br.com.soulpass.models.Bilhete;

import java.util.List;
import java.util.Scanner;

/**
 * Classe feita com IA. Já que só vai fazer os testes básicos de DAO, não quero perder muito tempo.
 * Classe de teste MANUAL (console) para BilheteDAO.
 * Execute a classe (main) e utilize o menu para testar cada operação do CRUD.
 */
public class TestBilheteDAO {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BilheteDAO bilheteDAO = new BilheteDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarBilhete();
                case 2 -> buscarBilhetePorId();
                case 3 -> listarBilhetes();
                case 4 -> atualizarBilhete();
                case 5 -> excluirBilhete();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== TESTE MANUAL - BilheteDAO =====");
        System.out.println("1 - Cadastrar bilhete");
        System.out.println("2 - Buscar bilhete por id");
        System.out.println("3 - Listar bilhetes");
        System.out.println("4 - Atualizar bilhete");
        System.out.println("5 - Excluir bilhete");
        System.out.println("0 - Sair");
    }

    private static void cadastrarBilhete() {
        System.out.println("--- Cadastrar bilhete ---");

        Bilhete bilhete = new Bilhete();
        bilhete.setCredito(lerDouble("Crédito: "));
        bilhete.setNumBilhete(lerLong("Número do bilhete: "));
        bilhete.setStatus(lerStatus());

        try {
            bilheteDAO.cadastrarBilhete(bilhete);
            System.out.println("Bilhete cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar bilhete: " + e.getMessage());
        }
    }

    private static void buscarBilhetePorId() {
        System.out.println("--- Buscar bilhete por id ---");
        int id = lerInt("Informe o id_ticket: ");

        try {
            Bilhete bilhete = bilheteDAO.buscarBilhetePorId(id);
            if (bilhete == null) {
                System.out.println("Nenhum bilhete encontrado com esse id.");
            } else {
                imprimirBilhete(bilhete);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar bilhete: " + e.getMessage());
        }
    }

    private static void listarBilhetes() {
        System.out.println("--- Listar bilhetes ---");

        try {
            List<Bilhete> bilhetes = bilheteDAO.listarBilhetes();
            if (bilhetes.isEmpty()) {
                System.out.println("Nenhum bilhete cadastrado.");
            } else {
                bilhetes.forEach(TestBilheteDAO::imprimirBilhete);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar bilhetes: " + e.getMessage());
        }
    }

    private static void atualizarBilhete() {
        System.out.println("--- Atualizar bilhete ---");

        Bilhete bilhete = new Bilhete();
        bilhete.setIdTicket(lerInt("Id_ticket a ser atualizado: "));
        bilhete.setCredito(lerDouble("Novo crédito: "));
        bilhete.setNumBilhete(lerLong("Novo número do bilhete: "));
        bilhete.setStatus(lerStatus());

        try {
            bilheteDAO.atualizarBilhete(bilhete);
            System.out.println("Bilhete atualizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar bilhete: " + e.getMessage());
        }
    }

    private static void excluirBilhete() {
        System.out.println("--- Excluir bilhete ---");
        int id = lerInt("Informe o id_ticket a ser excluído: ");

        try {
            bilheteDAO.excluirBilhete(id);
            System.out.println("Bilhete excluído com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir bilhete: " + e.getMessage());
        }
    }

    private static void imprimirBilhete(Bilhete bilhete) {
        System.out.println("-----------------------------------");
        System.out.println("id_ticket   : " + bilhete.getIdTicket());
        System.out.println("crédito     : " + bilhete.getCredito());
        System.out.println("num_bilhete : " + bilhete.getNumBilhete());
        System.out.println("status      : " + bilhete.getStatus());
        System.out.println("-----------------------------------");
    }

    // ---------- utilitários de leitura ----------

    private static StatusBilhete lerStatus() {
        while (true) {
            System.out.print("Status (" + valoresEnum(StatusBilhete.values()) + "): ");
            String valor = scanner.nextLine().trim().toUpperCase();
            try {
                return StatusBilhete.valueOf(valor);
            } catch (IllegalArgumentException e) {
                System.out.println("Status inválido, tente novamente.");
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

    private static long lerLong(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextLong()) {
            System.out.print("Valor inválido. " + mensagem);
            scanner.next();
        }
        long valor = scanner.nextLong();
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
