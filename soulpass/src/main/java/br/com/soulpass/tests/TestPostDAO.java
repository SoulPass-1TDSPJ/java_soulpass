package br.com.soulpass.tests;

import br.com.soulpass.dao.PostDAO;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe feita com IA. Já que só vai fazer os testes básicos de DAO, não quero perder muito tempo.
 * Classe de teste MANUAL (console) para PostDAO.
 * Execute a classe (main) e utilize o menu para testar cada operação do CRUD.
 */
public class TestPostDAO {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PostDAO postDAO = new PostDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarPost();
                case 2 -> buscarPostPorId();
                case 3 -> listarPostsPorConta();
                case 4 -> atualizarPost();
                case 5 -> excluirPost();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== TESTE MANUAL - PostDAO =====");
        System.out.println("1 - Cadastrar post");
        System.out.println("2 - Buscar post por id");
        System.out.println("3 - Listar posts por conta");
        System.out.println("4 - Atualizar post");
        System.out.println("5 - Excluir post");
        System.out.println("0 - Sair");
    }

    private static void cadastrarPost() {
        System.out.println("--- Cadastrar post ---");

        Post post = new Post();

        Conta conta = new Conta();
        conta.setIdConta(lerInt("Id_conta vinculada: "));
        post.setConta(conta);

        post.setData(lerDataHora("Data do post (aaaa-mm-dd hh:mm): "));
        post.setTemFoto(lerSimNao("Possui foto? (s/n): "));
        post.setTemvideo(lerSimNao("Possui vídeo? (s/n): "));
        post.setTextoPost(lerString("Texto do post: "));

        try {
            postDAO.cadastrarPost(post);
            System.out.println("Post cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar post: " + e.getMessage());
        }
    }

    private static void buscarPostPorId() {
        System.out.println("--- Buscar post por id ---");
        int id = lerInt("Informe o id_post: ");

        try {
            Post post = postDAO.buscarPostPorId(id);
            if (post == null) {
                System.out.println("Nenhum post encontrado com esse id.");
            } else {
                imprimirPost(post);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar post: " + e.getMessage());
        }
    }

    private static void listarPostsPorConta() {
        System.out.println("--- Listar posts por conta ---");
        int idConta = lerInt("Informe o id_conta: ");

        try {
            List<Post> lista = postDAO.listarPostsPorConta(idConta);
            if (lista.isEmpty()) {
                System.out.println("Nenhum post encontrado para essa conta.");
            } else {
                lista.forEach(TestPostDAO::imprimirPost);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar posts: " + e.getMessage());
        }
    }

    private static void atualizarPost() {
        System.out.println("--- Atualizar post ---");

        Post post = new Post();
        post.setIdPost(lerInt("Id_post a ser atualizado: "));
        post.setTemFoto(lerSimNao("Possui foto? (s/n): "));
        post.setTemvideo(lerSimNao("Possui vídeo? (s/n): "));
        post.setTextoPost(lerString("Novo texto do post: "));

        try {
            postDAO.atualizarPost(post);
            System.out.println("Post atualizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar post: " + e.getMessage());
        }
    }

    private static void excluirPost() {
        System.out.println("--- Excluir post ---");
        int id = lerInt("Informe o id_post a ser excluído: ");

        try {
            postDAO.excluirPost(id);
            System.out.println("Post excluído com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir post: " + e.getMessage());
        }
    }

    private static void imprimirPost(Post post) {
        System.out.println("-----------------------------------");
        System.out.println("id_post    : " + post.getIdPost());
        System.out.println("data       : " + post.getData());
        System.out.println("tem_foto   : " + post.isTemFoto());
        System.out.println("tem_video  : " + post.isTemvideo());
        System.out.println("texto_post : " + post.getTextoPost());
        System.out.println("id_conta   : " + (post.getConta() != null
                ? post.getConta().getIdConta() : "desconhecido"));
        System.out.println("-----------------------------------");
    }

    // ---------- utilitários de leitura ----------

    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static boolean lerSimNao(String mensagem) {
        System.out.print(mensagem);
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("s") || resposta.equals("sim");
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
