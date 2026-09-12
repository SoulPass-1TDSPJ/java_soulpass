package br.com.soulpass;

import br.com.soulpass.dao.BilheteDAO;
import br.com.soulpass.dao.CaminhadaDAO;
import br.com.soulpass.dao.ContaDAO;
import br.com.soulpass.dao.HistoricoPontosDAO;
import br.com.soulpass.dao.PostDAO;
import br.com.soulpass.dao.UsuarioDAO;
import br.com.soulpass.enums.OrigemPontos;
import br.com.soulpass.enums.StatusBilhete;
import br.com.soulpass.enums.StatusConta;
import br.com.soulpass.models.Bilhete;
import br.com.soulpass.models.Caminhada;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.HistoricoPontos;
import br.com.soulpass.models.Post;
import br.com.soulpass.models.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class App {

    /*Um único leitor para todo o sistema, evitando conflito entre múltiplos Scanners
     * lendo o mesmo System.in*/
    private static final Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {

        int opi = -1;
        while (opi != 0) {
            menuInicial();
            opi = lerInt("");

            switch (opi) {
                case 1:
                    signIn();
                    break;

                case 2:
                    Usuario usuarioLogado = logIn();
                    if (usuarioLogado != null) {
                        menuUsuarioLoop(usuarioLogado);
                    }
                    break;

                case 3:
                    esqueciSenha();
                    break;

                case 4:
                    menuDevLoop();
                    break;

                case 0:
                    System.out.println("----------------------------");
                    System.out.println("👋 Até uma próxima!");
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("----------------------------");
                    System.out.println("⚠️ Opção Inválida ⚠️");
                    System.out.println("Digite um número equivalente à uma opção");
                    break;
            }
        }
    }

    /**
     * Cadastra um novo usuário e já cria a conta SoulPass vinculada a ele.
     */
    private static void signIn() {
        System.out.println("\n======SIGN-IN======");
        String nome = lerString("Nome: ");
        int idade = lerInt("Idade: ");
        long cpf = lerLong("CPF (somente números): ");
        String email = lerString("Email: ");
        String senha = lerString("Senha: ");

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setIdade(idade);
        novoUsuario.setCpf(cpf);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.cadastrarUsuario(novoUsuario);

        Usuario usuarioCriado = usuarioDAO.login(novoUsuario);
        if (usuarioCriado == null) {
            System.out.println("-----------------------\nNão foi possível concluir o cadastro.");
            return;
        }

        Conta novaConta = new Conta();
        novaConta.setUsuario(usuarioCriado);
        novaConta.setAtividade(StatusConta.ATIVO);
        novaConta.setQtdePontos(0);
        novaConta.setLimitePontos(1000);
        new ContaDAO().cadastrarConta(novaConta);

        System.out.println("-----------------------\n✅ Conta criada com sucesso! Você já pode fazer log-in.");
    }

    /**
     * Autentica um usuário existente pelo par email/senha.
     * @return o {@link Usuario} autenticado, ou {@code null} caso as credenciais estejam erradas.
     */
    private static Usuario logIn() {
        System.out.println("\n======LOG-IN======");
        String email = lerString("Email: ");
        String senha = lerString("Senha: ");

        Usuario credenciais = new Usuario();
        credenciais.setEmail(email);
        credenciais.setSenha(senha);

        return new UsuarioDAO().login(credenciais);
    }

    /**
     * Permite trocar a senha de um usuário a partir do e-mail cadastrado.
     */
    private static void esqueciSenha() {
        System.out.println("\n======ESQUECI MINHA SENHA======");
        String email = lerString("Email cadastrado: ");
        String novaSenha = lerString("Nova senha: ");

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(novaSenha);
        new UsuarioDAO().mudarSenha(usuario);

        System.out.println("-----------------------\n✅ Senha alterada com sucesso!");
    }

    private static void menuUsuarioLoop(Usuario usuarioLogado) {
        Conta conta = new ContaDAO().buscarContaPorUsuario(usuarioLogado.getIdUser());

        int op = -1;
        while (op != 0) {
            menuFinalDoUser();
            op = lerInt("");

            switch (op) {
                case 1:
                    conferirContaUsuario(usuarioLogado, conta);
                    break;
                case 2:
                    usuarioLogado = editarUsuario(usuarioLogado);
                    break;
                case 3:
                    adicionarBilhete(usuarioLogado);
                    break;
                case 4:
                    converterPontosCredito(usuarioLogado, conta);
                    break;
                case 5:
                    fazerPost(conta);
                    break;
                case 6:
                    excluirPost(conta);
                    break;
                case 7:
                    fazerCaminhada(conta);
                    break;
                case 8:
                    exibirHistorico(conta);
                    break;
                case 9:
                    desativarConta(conta);
                    op = 0;
                    break;
                case 0:
                    System.out.println("Voltando à entrada...");
                    break;
                default:
                    System.out.println("----------------------------");
                    System.out.println("⚠️ Opção Inválida ⚠️");
                    break;
            }
        }
    }

    private static void conferirContaUsuario(Usuario usuario, Conta conta) {
        System.out.println(usuario.mostrarDados());
        System.out.println("------------------------");
        System.out.println("Status da conta: " + conta.getAtividade());
        System.out.println("Pontos: " + conta.getQtdePontos());
        System.out.println("Limite de pontos: " + conta.getLimitePontos());
        if (usuario.getBilhete() != null) {
            System.out.println(usuario.getBilhete().mostrarBilhete());
        } else {
            System.out.println("Nenhum bilhete cadastrado.");
        }
    }

    /**
     * Edita os dados do usuário logado. O e-mail não pode ser alterado por aqui, pois
     * {@code UsuarioDAO.atualizarUsuario} localiza o registro pelo par email/senha atual.
     */
    private static Usuario editarUsuario(Usuario usuarioAtual) {
        System.out.println("\n======EDITAR USUÁRIO======");
        System.out.println("(o e-mail não pode ser alterado por aqui; deixe o nome em branco para não mudar)");
        String nome = lerString("Novo nome [" + usuarioAtual.getNome() + "]: ");
        int idade = lerInt("Nova idade: ");
        long cpf = lerLong("Novo CPF: ");

        Usuario atualizado = new Usuario();
        atualizado.setNome(nome.isBlank() ? usuarioAtual.getNome() : nome);
        atualizado.setIdade(idade);
        atualizado.setCpf(cpf);
        atualizado.setEmail(usuarioAtual.getEmail());
        atualizado.setSenha(usuarioAtual.getSenha());

        new UsuarioDAO().atualizarUsuario(atualizado);

        atualizado.setIdUser(usuarioAtual.getIdUser());
        atualizado.setBilhete(usuarioAtual.getBilhete());
        System.out.println("-----------------------\n✅ Usuário atualizado com sucesso!");
        return atualizado;
    }

    /**
     * Cadastra um bilhete novo e o vincula ao usuário logado, usando o UsuarioDAO.vincularBilhete().
     */
    private static void adicionarBilhete(Usuario usuario) {
        if (usuario.getBilhete() != null) {
            System.out.println("-----------------------\n⚠️ Você já possui um bilhete cadastrado.");
            return;
        }

        System.out.println("\n======ADICIONAR BILHETE======");
        int numBilhete = lerInt("Número do bilhete (9 dígitos): ");

        Bilhete bilhete = new Bilhete();
        boolean valido = bilhete.cadastrarBilhete(numBilhete, StatusBilhete.ATIVO);
        if (!valido) {
            return;
        }

        BilheteDAO bilheteDAO = new BilheteDAO();
        bilheteDAO.cadastrarBilhete(bilhete);

        Bilhete criado = null;
        for (Bilhete b : bilheteDAO.listarBilhetes()) {
            if (b.getNumBilhete() == bilhete.getNumBilhete()) {
                criado = b;
            }
        }

        if (criado != null) {
            new UsuarioDAO().vincularBilhete(usuario.getIdUser(), criado.getIdTicket());
            usuario.setBilhete(criado);
            System.out.println("-----------------------\n✅ Bilhete vinculado com sucesso!");
        }
    }

    /**
     * Converte uma quantidade de pontos da conta em crédito, somando o valor convertido
     * ao bilhete do usuário (via Conta.converterParaCredito) e descontando os pontos usados.
     */
    private static void converterPontosCredito(Usuario usuario, Conta conta) {
        if (usuario.getBilhete() == null) {
            System.out.println("-----------------------\n⚠️ Você precisa ter um bilhete cadastrado para converter pontos em crédito.");
            return;
        }

        System.out.println("\n======CONVERTER PONTOS EM CRÉDITO======");
        System.out.println("Pontos disponíveis: " + conta.getQtdePontos());
        double pontos = lerDouble("Quantos pontos deseja converter? ");

        if (pontos <= 0 || pontos > conta.getQtdePontos()) {
            System.out.println("-----------------------\n⚠️ Quantidade de pontos inválida.");
            return;
        }

        double credito = Conta.converterParaCredito(pontos);

        Bilhete bilhete = usuario.getBilhete();
        bilhete.setCredito(bilhete.getCredito() + credito);
        new BilheteDAO().atualizarBilhete(bilhete);

        conta.setQtdePontos(conta.getQtdePontos() - pontos);
        new ContaDAO().atualizarConta(conta);

        System.out.println("-----------------------\n✅ Convertido! Você recebeu R$" + credito + " de crédito no bilhete.");
    }

    /**
     * Cria o post, persiste, calcula os pontos via Post.postar() e grava a conta
     * e o histórico de pontos atualizados.
     */
    private static void fazerPost(Conta conta) {
        System.out.println("\n======FAZER POST======");
        String texto = lerString("Texto do post: ");
        boolean temFoto = lerString("Possui foto? (s/n): ").equalsIgnoreCase("s");
        boolean temVideo = lerString("Possui vídeo? (s/n): ").equalsIgnoreCase("s");

        Post post = new Post();
        post.setConta(conta);
        post.setData(LocalDateTime.now());
        post.setTemFoto(temFoto);
        post.setTemvideo(temVideo);
        post.setTextoPost(texto);

        new PostDAO().cadastrarPost(post);

        int pontosGanhos = post.postar();
        new ContaDAO().atualizarConta(conta);
        registrarHistorico(conta, OrigemPontos.POST, pontosGanhos);

        System.out.println("-----------------------\n✅ Post publicado! Você ganhou " + pontosGanhos + " pontos.");
    }

    private static void excluirPost(Conta conta) {
        List<Post> posts = new PostDAO().listarPostsPorConta(conta.getIdConta());
        if (posts.isEmpty()) {
            System.out.println("-----------------------\n⚠️ Você não possui posts.");
            return;
        }

        System.out.println("\n======SEUS POSTS======");
        for (Post p : posts) {
            System.out.println("ID: " + p.getIdPost() + " | " + p.getData() + " | " + p.getTextoPost());
        }
        int id = lerInt("Digite o ID do post a excluir: ");
        new PostDAO().excluirPost(id);

        System.out.println("-----------------------\n✅ Post excluído com sucesso!");
    }

    /**
     * Registra a caminhada, calcula os pontos via Caminhada.caminhar() e grava a conta
     * e o histórico de pontos atualizados.
     */
    private static void fazerCaminhada(Conta conta) {
        System.out.println("\n======FAZER CAMINHADA======");
        double km = lerDouble("Quantos km você andou? ");

        Caminhada caminhada = new Caminhada();
        caminhada.setConta(conta);
        caminhada.setDiaCaminhada(LocalDateTime.now());
        caminhada.setKmAndados(km);

        new CaminhadaDAO().registrarCaminhada(caminhada);

        int pontosGanhos = caminhada.caminhar();
        new ContaDAO().atualizarConta(conta);
        registrarHistorico(conta, OrigemPontos.CAMINHADA, pontosGanhos);

        System.out.println("-----------------------\n✅ Caminhada registrada! Você ganhou " + pontosGanhos + " pontos.");
    }

    private static void exibirHistorico(Conta conta) {
        List<HistoricoPontos> lista = new HistoricoPontosDAO().listarHistoricoPorConta(conta.getIdConta());
        if (lista.isEmpty()) {
            System.out.println("-----------------------\n⚠️ Nenhum histórico encontrado.");
            return;
        }

        System.out.println("\n======HISTÓRICO DE PONTOS======");
        for (HistoricoPontos h : lista) {
            System.out.println(h.getDataRegistro() + " | " + h.getOrigem() + " | +" + h.getPontosGanhos() + " pontos");
        }
    }

    private static void desativarConta(Conta conta) {
        conta.setAtividade(StatusConta.INATIVO);
        new ContaDAO().atualizarConta(conta);
        System.out.println("-----------------------\n✅ Conta desativada. Você será redirecionado à entrada.");
    }

    /**
     * Método auxiliar que grava um novo registro de histórico de pontos para a conta informada,
     * usado tanto por fazerPost() quanto por fazerCaminhada().
     */
    private static void registrarHistorico(Conta conta, OrigemPontos origem, int pontosGanhos) {
        HistoricoPontos historico = new HistoricoPontos();
        historico.setConta(conta);
        historico.setOrigem(origem);
        historico.setPontosGanhos(pontosGanhos);
        historico.setDataRegistro(LocalDateTime.now());
        new HistoricoPontosDAO().registrarHistorico(historico);
    }

    private static void menuDevLoop() {
        int op = -1;
        while (op != 0) {
            menuFinalDoDev();
            op = lerInt("");

            switch (op) {
                case 1:
                    devListarContasUsuarios();
                    break;
                case 2:
                    devEditarUsuario();
                    break;
                case 3:
                    devDeletarUsuario();
                    break;
                case 4:
                    devEditarConta();
                    break;
                case 5:
                    devDeletarConta();
                    break;
                case 6:
                    devListarBilhetes();
                    break;
                case 7:
                    devEditarBilhete();
                    break;
                case 8:
                    devListarPosts();
                    break;
                case 9:
                    devDeletarPost();
                    break;
                case 10:
                    devListarCaminhadas();
                    break;
                case 11:
                    devDeletarCaminhada();
                    break;
                case 12:
                    devListarHistoricos();
                    break;
                case 13:
                    devDeletarHistorico();
                    break;
                case 0:
                    System.out.println("Voltando à entrada...");
                    break;
                default:
                    System.out.println("----------------------------");
                    System.out.println("⚠️ Opção Inválida ⚠️");
                    break;
            }
        }
    }

    private static void devListarContasUsuarios() {
        List<Conta> contas = new ContaDAO().listarContas();
        if (contas.isEmpty()) {
            System.out.println("-----------------------\n⚠️ Nenhuma conta cadastrada.");
            return;
        }

        System.out.println("\n======CONTAS E USUÁRIOS======");
        for (Conta c : contas) {
            System.out.println("Conta ID: " + c.getIdConta() + " | Status: " + c.getAtividade()
                    + " | Pontos: " + c.getQtdePontos());
            System.out.println(c.getUsuario().mostrarDadosDev());
            System.out.println("------------------------");
        }
    }

    private static void devEditarUsuario() {
        int id = lerInt("ID do usuário: ");
        Usuario usuario = new UsuarioDAO().buscarUsuarioPorId(id);
        if (usuario.getNome() == null) {
            System.out.println("-----------------------\n⚠️ Usuário não encontrado.");
            return;
        }

        System.out.println("(o e-mail não pode ser alterado por aqui; deixe o nome em branco para não mudar)");
        String nome = lerString("Novo nome [" + usuario.getNome() + "]: ");
        int idade = lerInt("Nova idade: ");
        long cpf = lerLong("Novo CPF: ");

        usuario.setNome(nome.isBlank() ? usuario.getNome() : nome);
        usuario.setIdade(idade);
        usuario.setCpf(cpf);

        new UsuarioDAO().atualizarUsuario(usuario);
        System.out.println("-----------------------\n✅ Usuário atualizado com sucesso!");
    }

    private static void devDeletarUsuario() {
        int id = lerInt("ID do usuário a excluir: ");
        new UsuarioDAO().excluirUsuario(id);
        System.out.println("-----------------------\n✅ Usuário excluído com sucesso!");
    }

    private static void devEditarConta() {
        int id = lerInt("ID da conta: ");
        Conta conta = new ContaDAO().buscarContaPorId(id);
        if (conta.getUsuario() == null) {
            System.out.println("-----------------------\n⚠️ Conta não encontrada.");
            return;
        }

        String statusStr = lerString("Novo status ATIVO/INATIVO [" + conta.getAtividade() + "] (branco p/ manter): ");
        double qtdePontos = lerDouble("Nova quantidade de pontos [" + conta.getQtdePontos() + "]: ");
        double limitePontos = lerDouble("Novo limite de pontos [" + conta.getLimitePontos() + "]: ");

        if (!statusStr.isBlank()) {
            conta.setAtividade(StatusConta.valueOf(statusStr.trim().toUpperCase()));
        }
        conta.setQtdePontos(qtdePontos);
        conta.setLimitePontos(limitePontos);

        new ContaDAO().atualizarConta(conta);
        System.out.println("-----------------------\n✅ Conta atualizada com sucesso!");
    }

    private static void devDeletarConta() {
        int id = lerInt("ID da conta a excluir: ");
        new ContaDAO().excluirConta(id);
        System.out.println("-----------------------\n✅ Conta excluída com sucesso!");
    }

    private static void devListarBilhetes() {
        List<Bilhete> lista = new BilheteDAO().listarBilhetes();
        if (lista.isEmpty()) {
            System.out.println("-----------------------\n⚠️ Nenhum bilhete cadastrado.");
            return;
        }

        System.out.println("\n======BILHETES======");
        for (Bilhete b : lista) {
            System.out.println("ID: " + b.getIdTicket() + " | " + b.mostrarBilhete());
        }
    }

    private static void devEditarBilhete() {
        int id = lerInt("ID do bilhete: ");
        Bilhete bilhete = new BilheteDAO().buscarBilhetePorId(id);
        if (bilhete.getStatus() == null) {
            System.out.println("-----------------------\n⚠️ Bilhete não encontrado.");
            return;
        }

        double credito = lerDouble("Novo crédito [" + bilhete.getCredito() + "]: ");
        String statusStr = lerString("Novo status ATIVO/NAO_ATIVO [" + bilhete.getStatus() + "] (branco p/ manter): ");

        bilhete.setCredito(credito);
        if (!statusStr.isBlank()) {
            bilhete.setStatus(StatusBilhete.valueOf(statusStr.trim().toUpperCase()));
        }

        new BilheteDAO().atualizarBilhete(bilhete);
        System.out.println("-----------------------\n✅ Bilhete atualizado com sucesso!");
    }

    private static void devListarPosts() {
        int idConta = lerInt("ID da conta para listar os posts: ");
        List<Post> lista = new PostDAO().listarPostsPorConta(idConta);
        if (lista.isEmpty()) {
            System.out.println("-----------------------\n⚠️ Nenhum post encontrado para essa conta.");
            return;
        }

        System.out.println("\n======POSTS======");
        for (Post p : lista) {
            System.out.println("ID: " + p.getIdPost() + " | " + p.getData() + " | Foto: " + p.isTemFoto()
                    + " | Vídeo: " + p.isTemvideo() + " | " + p.getTextoPost());
        }
    }

    private static void devDeletarPost() {
        int id = lerInt("ID do post a excluir: ");
        new PostDAO().excluirPost(id);
        System.out.println("-----------------------\n✅ Post excluído com sucesso!");
    }

    private static void devListarCaminhadas() {
        int idConta = lerInt("ID da conta para listar as caminhadas: ");
        List<Caminhada> lista = new CaminhadaDAO().listarCaminhadasPorConta(idConta);
        if (lista.isEmpty()) {
            System.out.println("-----------------------\n⚠️ Nenhuma caminhada encontrada para essa conta.");
            return;
        }

        System.out.println("\n======CAMINHADAS======");
        for (Caminhada c : lista) {
            System.out.println("ID: " + c.getIdCaminhada() + " | " + c.getDiaCaminhada() + " | " + c.getKmAndados() + "km");
        }
    }

    private static void devDeletarCaminhada() {
        int id = lerInt("ID da caminhada a excluir: ");
        new CaminhadaDAO().excluirCaminhada(id);
        System.out.println("-----------------------\n✅ Caminhada excluída com sucesso!");
    }

    private static void devListarHistoricos() {
        int idConta = lerInt("ID da conta para listar o histórico: ");
        List<HistoricoPontos> lista = new HistoricoPontosDAO().listarHistoricoPorConta(idConta);
        if (lista.isEmpty()) {
            System.out.println("-----------------------\n⚠️ Nenhum histórico encontrado para essa conta.");
            return;
        }

        System.out.println("\n======HISTÓRICO======");
        for (HistoricoPontos h : lista) {
            System.out.println("ID: " + h.getIdHistorico() + " | " + h.getDataRegistro() + " | " + h.getOrigem()
                    + " | +" + h.getPontosGanhos());
        }
    }

    private static void devDeletarHistorico() {
        int id = lerInt("ID do histórico a excluir: ");
        new HistoricoPontosDAO().excluirHistorico(id);
        System.out.println("-----------------------\n✅ Histórico excluído com sucesso!");
    }

    private static void menuInicial() {
        System.out.println("\n======ENTRADA DA SOULPASS======");
        System.out.println("1 - Fazer Sign-In");
        System.out.println("2 - Fazer Log-In");
        System.out.println("3 - Esqueceu a senha?");
        System.out.println("4 - Área dos Devs");
        System.out.println("0 - Encerrar Sistema");
        System.out.println("----------------------------");
        System.out.println("⬇️ Digite uma opção:");
    }

    private static void menuFinalDoUser() {
        System.out.println("\n======SOULPASS======");
        System.out.println("1 - Conferir Conta e Usuário");
        System.out.println("2 - Editar Usuário");
        System.out.println("3 - Adicionar Bilhete");
        System.out.println("4 - Converter Pontos em Crédito");
        System.out.println("5 - Fazer Post");
        System.out.println("6 - Excluir Post");
        System.out.println("7 - Fazer Caminhada");
        System.out.println("8 - Exibir Histórico");
        System.out.println("9 - Desativar Conta");
        System.out.println("0 - Voltar à Entrada");
        System.out.println("----------------------------");
        System.out.println("⬇️ Digite uma opção:");
    }

    private static void menuFinalDoDev() {
        System.out.println("\n======ÁREA DEV======");
        System.out.println("1 - Listar contas e usuários");
        System.out.println("2 - Editar usuário");
        System.out.println("3 - Deletar usuário");
        System.out.println("4 - Editar conta");
        System.out.println("5 - Deletar conta");
        System.out.println("6 - Listar bilhetes");
        System.out.println("7 - Editar bilhete");
        System.out.println("8 - Listar posts");
        System.out.println("9 - Deletar post");
        System.out.println("10 - Listar caminhada");
        System.out.println("11 - Deletar caminhada");
        System.out.println("12 - Exibir históricos");
        System.out.println("13 - Deletar histórico");
        System.out.println("0 - Voltar à entrada");
        System.out.println("----------------------------");
        System.out.println("⬇️ Digite uma opção:");
    }

    private static int lerInt(String prompt) {
        while (true) {
            if (!prompt.isEmpty()) System.out.print(prompt);
            try {
                return Integer.parseInt(leitor.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Valor inválido, digite um número inteiro.");
            }
        }
    }

    private static long lerLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(leitor.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Valor inválido, digite apenas números.");
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(leitor.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Valor inválido, digite um número (ex: 5.5).");
            }
        }
    }

    private static String lerString(String prompt) {
        System.out.print(prompt);
        return leitor.nextLine();
    }
}