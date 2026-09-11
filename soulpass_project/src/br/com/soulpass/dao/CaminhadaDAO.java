package br.com.soulpass.dao;

import br.com.soulpass.models.Caminhada;
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
 * Classe pública que permite usar o CRUD pelo java em relação às caminhadas registradas
 * */
public class CaminhadaDAO {

    private Connection conexao;

    /**
     * Insere uma nova caminhada no banco de dados, vinculada à conta que a realizou.
     *
     * @param caminhada objeto contendo o dia da caminhada, os km andados e a {@link Conta} vinculada.
     */
    public void registrarCaminhada(Caminhada caminhada) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "INSERT INTO tbl_caminhada (dia_caminhada, km_andados, id_conta) VALUES (?, ?, ?)";
        try {
            ps = conexao.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(caminhada.getDiaCaminhada()));
            ps.setDouble(2, caminhada.getKmAndados());
            ps.setInt(3, caminhada.getConta().getIdConta());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca uma única caminhada a partir do seu identificador (id_caminhada).
     *
     * @param id identificador da caminhada na tabela tbl_caminhada.
     * @return o {@link Caminhada} correspondente, ou {@code null} caso nenhum registro seja encontrado.
     */
    public Caminhada buscarPostPorId(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_caminhada WHERE id_caminhada = ?";
        Caminhada caminhada = new Caminhada();
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                caminhada.setDiaCaminhada(rs.getTimestamp("dia_caminhada").toLocalDateTime());
                caminhada.setKmAndados(rs.getDouble("km_andados"));
                Conta conta = new ContaDAO().buscarContaPorId(rs.getInt("id_conta"));
                caminhada.setConta(conta);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return caminhada;
    }

    /**
     * Lista todas as caminhadas registradas para uma determinada conta.
     *
     * @param idConta identificador da conta (id_conta) cujas caminhadas serão listadas.
     * @return uma {@link List} com todas as caminhadas encontradas (lista vazia caso não haja nenhum registro).
     */
    public List<Caminhada> listarCaminhadasPorConta(int idConta) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_caminhada WHERE id_conta = ?";
        List<Caminhada> lista = new ArrayList<>();
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idConta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Caminhada caminhada = new Caminhada();
                caminhada.setDiaCaminhada(rs.getTimestamp("dia_caminhada").toLocalDateTime());
                caminhada.setKmAndados(rs.getDouble("km_andados"));
                Conta conta = new ContaDAO().buscarContaPorId(rs.getInt("id_conta"));
                caminhada.setConta(conta);
                lista.add(caminhada);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    /**
     * Remove um registro de caminhada do banco de dados a partir do seu identificador.
     *
     * @param idCaminhada identificador (id_caminhada) do registro a ser excluído.
     */
    public void excluirCaminhada(int idCaminhada) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "DELETE FROM tbl_caminhada WHERE id_caminhada = ?";
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idCaminhada);
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}