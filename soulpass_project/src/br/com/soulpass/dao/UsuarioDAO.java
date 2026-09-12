package br.com.soulpass.dao;

import br.com.soulpass.models.Bilhete;
import br.com.soulpass.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pública que permite usar o CRUD pelo java em relação aos usuários que serão cadastrados.
 * */
public class UsuarioDAO {

    private Connection conexao;

    /**
     * Insere um novo usuário no banco de dados.
     * Caso o usuário já possua um {@link Bilhete} vinculado, o id_ticket é salvo junto;
     * caso contrário, a coluna id_ticket é gravada como nula.
     * @param usuario objeto contendo os dados (nome, idade, cpf, email, senha e, opcionalmente, bilhete)
     *                a serem persistidos.
     */
    public void cadastrarUsuario(Usuario usuario) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;

        String sql = "INSERT INTO tbl_usuario (nome, idade, cpf, email, senha, id_ticket) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try{
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setInt(2, usuario.getIdade());
            ps.setLong(3, usuario.getCpf());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getSenha());
            if (usuario.getBilhete() != null) {
                ps.setInt(6, usuario.getBilhete().getIdTicket());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca um único usuário a partir do seu identificador (id_user).
     * @param id identificador do usuário na tabela tbl_usuario.
     * @return o {@link Usuario} correspondente, já com o {@link Bilhete} vinculado carregado (se existir),
     *         ou {@code null} caso nenhum registro seja encontrado com esse id.
     */
    public Usuario buscarUsuarioPorId(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_usuario WHERE id_user = ?";
        Usuario usuario = new Usuario();
        try{
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setIdUser(rs.getInt("id_user"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdade(rs.getInt("idade"));
                usuario.setCpf(rs.getLong("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                int idTicket = rs.getInt("id_ticket");
                if (!rs.wasNull()) {
                    Bilhete bilhete = new BilheteDAO().buscarBilhetePorId(idTicket);
                    usuario.setBilhete(bilhete);
                }
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    /**
     * Lista todos os usuários cadastrados na tabela tbl_usuario.
     * @return uma {@link List} com todos os usuários encontrados (lista vazia caso não haja nenhum registro).
     */
    public List<Usuario> listarUsuarios() {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_usuario";
        List<Usuario> listaUsuarios = new ArrayList<>();
        try{
            ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUser(rs.getInt("id_user"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdade(rs.getInt("idade"));
                usuario.setCpf(rs.getLong("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                int idTicket = rs.getInt("id_ticket");
                if (!rs.wasNull()) {
                    Bilhete bilhete = new BilheteDAO().buscarBilhetePorId(idTicket);
                    usuario.setBilhete(bilhete);
                }
                listaUsuarios.add(usuario);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaUsuarios;
    }

    /**
     * Realiza o login verificando se existe, no banco, um usuário com o e-mail e a senha informados.
     * @param usuario objeto contendo apenas o {@code email} e a {@code senha} a serem validados.
     * @return o {@link Usuario} completo correspondente em caso de sucesso, ou {@code null} caso
     *         as credenciais não confiram com nenhum registro.
     */
    public Usuario login(Usuario usuario) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_usuario WHERE email = ? AND senha = ?";
        Usuario usuarioEncontrado = null;
        try{
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setIdUser(rs.getInt("id_user"));
                usuarioEncontrado.setNome(rs.getString("nome"));
                usuarioEncontrado.setIdade(rs.getInt("idade"));
                usuarioEncontrado.setCpf(rs.getLong("cpf"));
                usuarioEncontrado.setEmail(rs.getString("email"));
                usuarioEncontrado.setSenha(rs.getString("senha"));
                int idTicket = rs.getInt("id_ticket");
                if (!rs.wasNull()) {
                    Bilhete bilhete = new BilheteDAO().buscarBilhetePorId(idTicket);
                    usuarioEncontrado.setBilhete(bilhete);
                }
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (usuarioEncontrado == null) {
            System.out.println("-----------------------\nE-mail ou senha inválidos.");
        }
        return usuarioEncontrado;
    }

    /**
     * Atualiza nome, idade, cpf e email de um usuário já existente, localizado pelo par
     * email/senha atuais (usados como "chave" de validação antes da alteração).
     * @param usuario objeto com os novos dados (nome, idade, cpf, email) e as credenciais
     *                atuais (email, senha) usadas para localizar o registro.
     */
    public void atualizarUsuario(Usuario usuario) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "UPDATE tbl_usuario SET nome = ?, idade = ?, cpf = ?, email = ? " +
                "WHERE email = ? AND senha = ?";
        try{
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setInt(2, usuario.getIdade());
            ps.setLong(3, usuario.getCpf());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getSenha());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Vincula um bilhete já existente a um usuário já cadastrado, atualizando a coluna id_ticket.
     * @param idUser identificador do usuário que receberá o bilhete.
     * @param idTicket identificador do bilhete a ser vinculado.
     */
    public void vincularBilhete(int idUser, int idTicket) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "UPDATE tbl_usuario SET id_ticket = ? WHERE id_user = ?";
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idTicket);
            ps.setInt(2, idUser);
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Altera a senha de um usuário, localizado pelo e-mail.
     * @param usuario objeto contendo o {@code email} do usuário e a {@code nova senha} a ser gravada.
     */
    public void mudarSenha(Usuario usuario) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "UPDATE tbl_usuario SET senha = ? WHERE email = ?";
        try{
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getSenha());
            ps.setString(2, usuario.getEmail());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove um usuário do banco de dados a partir do seu identificador.
     * @param id identificador (id_user) do usuário a ser excluído.
     */
    public void excluirUsuario(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;

        String sql = "DELETE FROM tbl_usuario WHERE id_user = ?";

        try{
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}