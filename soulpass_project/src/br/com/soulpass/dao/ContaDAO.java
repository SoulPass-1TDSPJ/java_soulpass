package br.com.soulpass.dao;

import br.com.soulpass.enums.StatusConta;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pública que permite usar o CRUD pelo java em relação às contas cadastradas.
 * */
public class ContaDAO {

    private Connection conexao;

    /**
     * Insere uma nova conta no banco de dados, vinculada a um usuário já existente.
     *
     * @param conta objeto contendo a atividade (status), o usuário dono da conta e a quantidade
     *              e o limite de pontos iniciais.
     */
    public void cadastrarConta(Conta conta) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "INSERT INTO tbl_conta (atividade, id_user, qtde_pontos, limite_pontos) " +
                "VALUES (?, ?, ?, ?)";
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, conta.getAtividade().name());
            ps.setInt(2, conta.getUsuario().getIdUser());
            ps.setDouble(3, conta.getQtdePontos());
            ps.setDouble(4, conta.getLimitePontos());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca uma única conta a partir do seu identificador (id_conta).
     *
     * @param id identificador da conta na tabela tbl_conta.
     * @return a {@link Conta} correspondente, já com o {@link Usuario} vinculado carregado,
     *         ou {@code null} caso nenhum registro seja encontrado.
     */
    public Conta buscarContaPorId(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_conta WHERE id_conta = ?";
        Conta conta = new  Conta();
        try{
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                conta.setIdConta(rs.getInt("id_conta"));
                conta.setAtividade(StatusConta.valueOf(rs.getString("atividade")));
                conta.setQtdePontos(rs.getDouble("qtde_pontos"));
                conta.setLimitePontos(rs.getDouble("limite_pontos"));

                Usuario usuario = new UsuarioDAO().buscarUsuarioPorId(rs.getInt("id_user"));
                conta.setUsuario(usuario);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conta;
    }

    /**
     * Busca a conta pertencente a um determinado usuário.
     *
     * @param idUser identificador do usuário (id_user) dono da conta.
     * @return a {@link Conta} vinculada a esse usuário, ou {@code null} caso ele não possua conta.
     */
    public Conta buscarContaPorUsuario(int idUser) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        Conta conta = new Conta();
        String sql = "SELECT * FROM tbl_conta WHERE id_user = ?";
        try{
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                conta.setIdConta(rs.getInt("id_conta"));
                conta.setAtividade(StatusConta.valueOf(rs.getString("atividade")));
                conta.setQtdePontos(rs.getDouble("qtde_pontos"));
                conta.setLimitePontos(rs.getDouble("limite_pontos"));

                Usuario usuario = new UsuarioDAO().buscarUsuarioPorId(rs.getInt("id_user"));
                conta.setUsuario(usuario);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conta;
    }

    /**
     * Lista todas as contas cadastradas na tabela tbl_conta.
     *
     * @return uma {@link List} com todas as contas encontradas (lista vazia caso não haja nenhum registro).
     */
    public List<Conta> listarContas() {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_conta";
        List<Conta> listaContas = new ArrayList<>();
        try{
            ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Conta conta = new Conta();
                conta.setIdConta(rs.getInt("id_conta"));
                conta.setAtividade(StatusConta.valueOf(rs.getString("atividade")));
                conta.setQtdePontos(rs.getDouble("qtde_pontos"));
                conta.setLimitePontos(rs.getDouble("limite_pontos"));
                Usuario usuario = new UsuarioDAO().buscarUsuarioPorId(rs.getInt("id_user"));
                conta.setUsuario(usuario);
                listaContas.add(conta);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaContas;
    }

    /**
     * Atualiza a atividade (status), a quantidade de pontos e o limite de pontos de uma conta existente.
     *
     * @param conta objeto com o {@code idConta} do registro a ser atualizado e os novos valores.
     */
    public void atualizarConta(Conta conta) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "UPDATE tbl_conta SET atividade = ?, qtde_pontos = ?, limite_pontos = ? " +
                "WHERE id_conta = ?";
        try{
            ps = conexao.prepareStatement(sql);
            ps.setString(1, conta.getAtividade().name());
            ps.setDouble(2, conta.getQtdePontos());
            ps.setDouble(3, conta.getLimitePontos());
            ps.setInt(4, conta.getIdConta());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove uma conta do banco de dados a partir do seu identificador.
     *
     * @param id identificador (id_conta) da conta a ser excluída.
     */
    public void excluirConta(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "DELETE FROM tbl_conta WHERE id_conta = ?";
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