package br.com.soulpass.tests;

import br.com.soulpass.dao.CaminhadaDAO;
import br.com.soulpass.models.Caminhada;
import br.com.soulpass.models.Conta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe feita com IA. Já que só vai fazer os testes básicos de DAO, não quero perder muito tempo.
 * Classe de teste MANUAL (console) para CaminhadaDAO.
 * Execute a classe (main) e utilize o menu para testar cada operação do CRUD.
 */
public class TestCaminhadaDAO {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CaminhadaDAO caminhadaDAO = new CaminhadaDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> registrarCaminhada();
                case 2 -> listarCaminhadasPorConta();
                case 3 -> excluirCaminhada();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== TESTE MANUAL - CaminhadaDAO =====");
        System.out.println("1 - Registrar caminhada");
        System.out.println("2 - Listar caminhadas por conta");
        System.out.println("3 - Excluir caminhada");
        System.out.println("0 - Sair");
    }

    private static void registrarCaminhada() {
        System.out.println("--- Registrar caminhada ---");

        Caminhada caminhada = new Caminhada();
        caminhada.setDiaCaminhada(lerDataHora("Dia da caminhada (aaaa-mm-dd hh:mm): "));
        caminhada.setKmAndados(lerDouble("Km andados: "));

        Conta conta = new Conta();
        conta.setIdConta(lerInt("Id_conta vinculada: "));
        caminhada.setConta(conta);

        try {
            caminhadaDAO.registrarCaminhada(caminhada);
            System.out.println("Caminhada registrada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao registrar caminhada: " + e.getMessage());
        }
    }

    private static void listarCaminhadasPorConta() {
        System.out.println("--- Listar caminhadas por conta ---");
        int idConta = lerInt("Informe o id_conta: ");

        try {
            List<Caminhada> lista = caminhadaDAO.listarCaminhadasPorConta(idConta);
            if (lista.isEmpty()) {
                System.out.println("Nenhuma caminhada encontrada para essa conta.");
            } else {
                lista.forEach(TestCaminhadaDAO::imprimirCaminhada);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar caminhadas: " + e.getMessage());
        }
    }

    private static void excluirCaminhada() {
        System.out.println("--- Excluir caminhada ---");
        int idCaminhada = lerInt("Informe o id_caminhada a ser excluído: ");

        try {
            caminhadaDAO.excluirCaminhada(idCaminhada);
            System.out.println("Caminhada excluída com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir caminhada: " + e.getMessage());
        }
    }

    private static void imprimirCaminhada(Caminhada caminhada) {
        System.out.println("-----------------------------------");
        System.out.println("dia_caminhada : " + caminhada.getDiaCaminhada());
        System.out.println("km_andados    : " + caminhada.getKmAndados());
        System.out.println("id_conta      : " + (caminhada.getConta() != null
                ? caminhada.getConta().getIdConta() : "desconhecido"));
        System.out.println("-----------------------------------");
    }

    // ---------- utilitários de leitura ----------

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
