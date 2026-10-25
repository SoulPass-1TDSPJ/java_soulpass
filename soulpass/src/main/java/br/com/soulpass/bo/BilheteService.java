package br.com.soulpass.bo;

import br.com.soulpass.dao.BilheteDAO;
import br.com.soulpass.dao.UsuarioDAO;
import br.com.soulpass.enums.StatusBilhete;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import br.com.soulpass.models.Bilhete;
import br.com.soulpass.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Regras de negócio do bilhete: cadastro vinculado ao usuário (número de 9 dígitos) e manutenção.
 */
@Service
public class BilheteService {

    private static final long NUM_MINIMO = 100_000_000L;
    private static final long NUM_MAXIMO = 999_999_999L;

    private final UsuarioService usuarioService;

    public BilheteService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public List<Bilhete> listar() {
        return new BilheteDAO().listarBilhetes();
    }

    /** O DAO devolve um Bilhete vazio (idTicket = 0) quando não encontra, por isso a checagem do id. */
    public Bilhete buscarPorId(int id) {
        Bilhete bilhete = new BilheteDAO().buscarBilhetePorId(id);
        if (bilhete == null || bilhete.getIdTicket() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bilhete não encontrado");
        }
        return bilhete;
    }

    public Bilhete buscarPorUsuario(int idUser) {
        Bilhete bilhete = usuarioService.buscarPorId(idUser).getBilhete();
        if (bilhete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não possui bilhete cadastrado");
        }
        return bilhete;
    }

    /** Cadastra um bilhete novo e o vincula ao usuário (mesmo fluxo do adicionarBilhete do TestSoulPass). */
    public Bilhete vincularAoUsuario(int idUser, Bilhete novo) {
        Usuario usuario = usuarioService.buscarPorId(idUser);
        if (usuario.getBilhete() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já possui um bilhete cadastrado");
        }

        long numero = novo.getNumBilhete();
        if (numero < NUM_MINIMO || numero > NUM_MAXIMO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número do bilhete deve ter exatamente 9 dígitos");
        }

        BilheteDAO dao = new BilheteDAO();
        if (dao.listarBilhetes().stream().anyMatch(b -> b.getNumBilhete() == numero)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Número de bilhete já cadastrado");
        }

        // Regra do próprio modelo: valida o número e deixa o bilhete ATIVO
        if (!novo.cadastrarBilhete((int) numero, StatusBilhete.ATIVO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bilhete inválido");
        }
        dao.cadastrarBilhete(novo);

        // O DAO não devolve o id gerado, então localizamos o bilhete recém-criado pelo número
        Bilhete criado = dao.listarBilhetes().stream()
                .filter(b -> b.getNumBilhete() == numero)
                .reduce((primeiro, ultimo) -> ultimo)
                .orElseThrow(() -> new IllegalStateException("Não foi possível cadastrar o bilhete"));

        new UsuarioDAO().vincularBilhete(idUser, criado.getIdTicket());
        return criado;
    }

    public void atualizar(Bilhete bilhete) {
        if (bilhete.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status do bilhete é obrigatório");
        }
        if (bilhete.getCredito() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Crédito não pode ser negativo");
        }
        new BilheteDAO().atualizarBilhete(bilhete);
    }

    public void excluir(int id) {
        buscarPorId(id);
        new BilheteDAO().excluirBilhete(id);
    }
}
