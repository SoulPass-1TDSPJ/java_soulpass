package br.com.soulpass.dao;

import br.com.soulpass.models.Conta;
import br.com.soulpass.models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pública que permite usar o CRUD pelo java em relação aos posts cadastrados
 * */
public class PostDAO {

    private Connection conexao;

    /**
     * Insere um novo post no banco de dados, vinculado à conta que o publicou.
     *
     * @param post objeto contendo a conta, a data, se possui foto/vídeo e o texto do post.
     */
    public void cadastrarPost(Post post) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "INSERT INTO tbl_post (id_conta, data, tem_foto, tem_video, texto_post) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, post.getConta().getIdConta());
            ps.setTimestamp(2, Timestamp.valueOf(post.getData()));
            ps.setInt(3, post.isTemFoto() ? 1 : 0);
            ps.setInt(4, post.isTemvideo() ? 1 : 0);
            ps.setString(5, post.getTextoPost());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca um único post a partir do seu identificador (id_post).
     *
     * @param id identificador do post na tabela tbl_post.
     * @return o {@link Post} correspondente, ou {@code null} caso nenhum registro seja encontrado.
     */
    public Post buscarPostPorId(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_post WHERE id_post = ?";
        Post post = new Post();
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                post.setIdPost(rs.getInt("id_post"));
                post.setData(rs.getTimestamp("data").toLocalDateTime());
                post.setTemFoto(rs.getInt("tem_foto") != 0);
                post.setTemvideo(rs.getInt("tem_video") != 0);
                post.setTextoPost(rs.getString("texto_post"));
                Conta conta = new ContaDAO().buscarContaPorId(rs.getInt("id_conta"));
                post.setConta(conta);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    /**
     * Lista todos os posts de uma determinada conta, ordenados da data mais recente para a mais antiga.
     *
     * @param idConta identificador da conta (id_conta) cujos posts serão listados.
     * @return uma {@link List} com todos os posts encontrados (lista vazia caso não haja nenhum registro).
     */
    public List<Post> listarPostsPorConta(int idConta) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_post WHERE id_conta = ? ORDER BY data DESC";
        List<Post> lista = new ArrayList<>();
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idConta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setIdPost(rs.getInt("id_post"));
                post.setData(rs.getTimestamp("data").toLocalDateTime());
                post.setTemFoto(rs.getInt("tem_foto") != 0);
                post.setTemvideo(rs.getInt("tem_video") != 0);
                post.setTextoPost(rs.getString("texto_post"));
                Conta conta = new ContaDAO().buscarContaPorId(rs.getInt("id_conta"));
                post.setConta(conta);
                lista.add(post);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Atualiza se o post possui foto/vídeo e o seu texto. A conta e a data do post não são alteradas.
     *
     * @param post objeto com o {@code idPost} do registro a ser atualizado e os novos valores
     *             de temFoto, temVideo e texto.
     */
    public void atualizarPost(Post post) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "UPDATE tbl_post SET tem_foto = ?, tem_video = ?, texto_post = ? WHERE id_post = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, post.isTemFoto() ? 1 : 0);
            ps.setInt(2, post.isTemvideo() ? 1 : 0);
            ps.setString(3, post.getTextoPost());
            ps.setInt(4, post.getIdPost());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove um post do banco de dados a partir do seu identificador.
     *
     * @param id identificador (id_post) do post a ser excluído.
     */
    public void excluirPost(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "DELETE FROM tbl_post WHERE id_post = ?";
        try {
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