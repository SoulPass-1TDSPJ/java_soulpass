package br.com.soulpass.bo;

import br.com.soulpass.dao.UsuarioDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import br.com.soulpass.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Regras de negócio de usuário: cadastro (já criando a conta SoulPass), login, edição e troca de senha.
 */
@Service
public class UsuarioService {

    private final ContaService contaService;

    public UsuarioService(ContaService contaService) {
        this.contaService = contaService;
    }

    public List<Usuario> listar() {
        return new UsuarioDAO().listarUsuarios();
    }

    /** O DAO devolve um Usuario vazio (idUser = 0) quando não encontra, por isso a checagem do id. */
    public Usuario buscarPorId(int id) {
        Usuario usuario = new UsuarioDAO().buscarUsuarioPorId(id);
        if (usuario == null || usuario.getIdUser() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return usuario;
    }

    /** Cadastra o usuário e cria a conta vinculada (mesmo fluxo do signIn do TestSoulPass). */
    public Usuario cadastrar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório");
        }
        if (usuario.getIdade() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Idade inválida");
        }
        if (usuario.getCpf() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF inválido");
        }
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail inválido");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha é obrigatória");
        }
        usuario.setEmail(usuario.getEmail().trim());
        if (buscarPorEmail(usuario.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }

        UsuarioDAO dao = new UsuarioDAO();
        dao.cadastrarUsuario(usuario);
        Usuario criado = dao.logar(usuario);
        if (criado == null) {
            throw new IllegalStateException("Não foi possível concluir o cadastro");
        }
        contaService.criarParaUsuario(criado);
        return criado;
    }

    public Usuario logar(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe e-mail e senha");
        }
        Usuario credenciais = new Usuario();
        credenciais.setEmail(email.trim());
        credenciais.setSenha(senha);

        Usuario usuario = new UsuarioDAO().logar(credenciais);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
        }
        return usuario;
    }

    /**
     * Persiste nome, idade e CPF. O DAO localiza o registro pelo par e-mail/senha atuais, por isso a entidade
     * deve vir de {@link #buscarPorId(int)} (que carrega os dois) e o e-mail não pode ser alterado.
     */
    public void atualizar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório");
        }
        if (usuario.getIdade() <= 0 || usuario.getCpf() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Idade e CPF devem ser válidos");
        }
        new UsuarioDAO().atualizarUsuario(usuario);
    }

    public void redefinirSenha(String email, String novaSenha) {
        if (email == null || email.isBlank() || novaSenha == null || novaSenha.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe e-mail e nova senha");
        }
        Usuario existente = buscarPorEmail(email.trim());
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "E-mail não cadastrado");
        }
        existente.setSenha(novaSenha);
        new UsuarioDAO().mudarSenha(existente);
    }

    public void excluir(int id) {
        buscarPorId(id);
        new UsuarioDAO().excluirUsuario(id);
    }

    private Usuario buscarPorEmail(String email) {
        return new UsuarioDAO().listarUsuarios().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
