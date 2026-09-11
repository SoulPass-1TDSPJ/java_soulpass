package br.com.soulpass.tests;

import br.com.soulpass.dao.UsuarioDAO;
import br.com.soulpass.models.Bilhete;
import br.com.soulpass.models.Usuario;

import java.util.List;
import java.util.Scanner;

/**
 * Classe feita com IA. Já que só vai fazer os testes básicos de DAO, não quero perder muito tempo.
 * Classe de teste MANUAL (console) para UsuarioDAO.
 * Execute a classe (main) e utilize o menu para testar cada operação do CRUD.
 */
public class TestUsuarioDAO {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> buscarUsuarioPorId();
                case 3 -> listarUsuarios();
                case 4 -> login();
                case 5 -> atualizarUsuario();
                case 6 -> mudarSenha();
                case 7 -> excluirUsuario();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== TESTE MANUAL - UsuarioDAO =====");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Buscar usuário por id");
        System.out.println("3 - Listar usuários");
        System.out.println("4 - Login");
        System.out.println("5 - Atualizar usuário");
        System.out.println("6 - Mudar senha");
        System.out.println("7 - Excluir usuário");
        System.out.println("0 - Sair");
    }

    private static void cadastrarUsuario() {
        System.out.println("--- Cadastrar usuário ---");

        Usuario usuario = new Usuario();
        usuario.setNome(lerString("Nome: "));
        usuario.setIdade(lerInt("Idade: "));
        usuario.setCpf(lerLong("CPF: "));
        usuario.setEmail(lerString("E-mail: "));
        usuario.setSenha(lerString("Senha: "));

        if (lerSimNao("Possui bilhete vinculado? (s/n): ")) {
            int idTicket = lerInt("Informe o id_ticket do bilhete: ");
            Bilhete bilhete = new Bilhete();
            bilhete.setIdTicket(idTicket);
            usuario.setBilhete(bilhete);
        }

        try {
            usuarioDAO.cadastrarUsuario(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private static void buscarUsuarioPorId() {
        System.out.println("--- Buscar usuário por id ---");
        int id = lerInt("Informe o id_user: ");

        try {
            Usuario usuario = usuarioDAO.buscarUsuarioPorId(id);
            if (usuario == null) {
                System.out.println("Nenhum usuário encontrado com esse id.");
            } else {
                imprimirUsuario(usuario);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    private static void listarUsuarios() {
        System.out.println("--- Listar usuários ---");

        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário cadastrado.");
            } else {
                usuarios.forEach(TestUsuarioDAO::imprimirUsuario);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.println("--- Login ---");

        Usuario credenciais = new Usuario();
        credenciais.setEmail(lerString("E-mail: "));
        credenciais.setSenha(lerString("Senha: "));

        try {
            Usuario logado = usuarioDAO.login(credenciais);
            if (logado == null) {
                System.out.println("Login inválido.");
            } else {
                System.out.println("Login realizado com sucesso!");
                imprimirUsuario(logado);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
        }
    }

    private static void atualizarUsuario() {
        System.out.println("--- Atualizar usuário ---");
        System.out.println("Obs.: a busca do registro é feita por e-mail + senha atuais.");

        Usuario usuario = new Usuario();
        usuario.setNome(lerString("Novo nome: "));
        usuario.setIdade(lerInt("Nova idade: "));
        usuario.setCpf(lerLong("Novo CPF: "));
        usuario.setEmail(lerString("E-mail (atual, usado também como novo valor): "));
        usuario.setSenha(lerString("Senha atual (usada para localizar o registro): "));

        try {
            usuarioDAO.atualizarUsuario(usuario);
            System.out.println("Usuário atualizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    private static void mudarSenha() {
        System.out.println("--- Mudar senha ---");

        Usuario usuario = new Usuario();
        usuario.setEmail(lerString("E-mail: "));
        usuario.setSenha(lerString("Nova senha: "));

        try {
            usuarioDAO.mudarSenha(usuario);
            System.out.println("Senha alterada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao mudar senha: " + e.getMessage());
        }
    }

    private static void excluirUsuario() {
        System.out.println("--- Excluir usuário ---");
        int id = lerInt("Informe o id_user a ser excluído: ");

        try {
            usuarioDAO.excluirUsuario(id);
            System.out.println("Usuário excluído com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    private static void imprimirUsuario(Usuario usuario) {
        System.out.println("-----------------------------------");
        System.out.println("id_user : " + usuario.getIdUser());
        System.out.println("nome    : " + usuario.getNome());
        System.out.println("idade   : " + usuario.getIdade());
        System.out.println("cpf     : " + usuario.getCpf());
        System.out.println("email   : " + usuario.getEmail());
        System.out.println("bilhete : " + (usuario.getBilhete() != null
                ? usuario.getBilhete().getIdTicket() : "nenhum"));
        System.out.println("-----------------------------------");
    }

    // ---------- utilitários de leitura ----------

    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
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

    private static boolean lerSimNao(String mensagem) {
        System.out.print(mensagem);
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("s") || resposta.equals("sim");
    }
}