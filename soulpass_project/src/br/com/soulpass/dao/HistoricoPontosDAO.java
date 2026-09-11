package br.com.soulpass.dao;

import br.com.soulpass.enums.OrigemPontos;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.HistoricoPontos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pública que permite usar o CRUD pelo java em relação ao histórico de pontos.
 * */
public class HistoricoPontosDAO {

    private Connection conexao;

    /**
     * Insere um novo registro de histórico de pontos, vinculado à conta que os recebeu.
     *
     * @param historico objeto contendo a conta, a origem dos pontos (ex: caminhada ou post),
     *                  a quantidade de pontos ganhos e a data do registro.
     */
    public void registrarHistorico(HistoricoPontos historico) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "INSERT INTO tbl_historico_pontos (id_conta, origem, pontos_ganhos, data_registro) " +
                "VALUES (?, ?, ?, ?)";
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, historico.getConta().getIdConta());
            ps.setString(2, historico.getOrigem().name());
            ps.setInt(3, historico.getPontosGanhos());
            ps.setTimestamp(4, Timestamp.valueOf(historico.getDataRegistro()));
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca um único registro de histórico a partir do seu identificador (id_historico).
     *
     * @param id identificador do histórico na tabela tbl_historico_pontos.
     * @return o {@link HistoricoPontos} correspondente, ou {@code null} caso não seja encontrado.
     */
    public HistoricoPontos buscarHistoricoPorId(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_historico_pontos WHERE id_historico = ?";
        HistoricoPontos historico = new HistoricoPontos();
        try{
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                historico.setIdHistorico(rs.getInt("id_historico"));
                historico.setOrigem(OrigemPontos.valueOf(rs.getString("origem")));
                historico.setPontosGanhos(rs.getInt("pontos_ganhos"));
                historico.setDataRegistro(rs.getTimestamp("data_registro").toLocalDateTime());
                Conta conta = new ContaDAO().buscarContaPorId(rs.getInt("id_conta"));
                historico.setConta(conta);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historico;
    }

    /**
     * Lista todo o histórico de pontos de uma determinada conta.
     *
     * @param idConta identificador da conta (id_conta) cujo histórico será listado.
     * @return uma {@link List} com todos os registros encontrados (lista vazia caso não haja nenhum).
     */
    public List<HistoricoPontos> listarHistoricoPorConta(int idConta) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_historico_pontos WHERE id_conta = ?";
        List<HistoricoPontos> lista = new ArrayList<>();
        try{
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idConta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HistoricoPontos historico = new HistoricoPontos();
                historico.setIdHistorico(rs.getInt("id_historico"));
                historico.setOrigem(OrigemPontos.valueOf(rs.getString("origem")));
                historico.setPontosGanhos(rs.getInt("pontos_ganhos"));
                historico.setDataRegistro(rs.getTimestamp("data_registro").toLocalDateTime());
                Conta conta = new ContaDAO().buscarContaPorId(rs.getInt("id_conta"));
                historico.setConta(conta);
                lista.add(historico);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Remove um registro de histórico do banco de dados a partir do seu identificador.
     *
     * @param id identificador (id_historico) do registro a ser excluído.
     */
    public void excluirHistorico(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "DELETE FROM tbl_historico_pontos WHERE id_historico = ?";
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

    /**
     * Método auxiliar que converte a linha atual de um {@link ResultSet} da tabela tbl_historico_pontos
     * em um objeto {@link HistoricoPontos}, convertendo a coluna de texto origem de volta para o enum
     * {@link OrigemPontos} e carregando a {@link Conta} vinculada via {@link ContaDAO}.
     *
     * @param rs ResultSet posicionado em uma linha válida da tabela tbl_historico_pontos.
     * @return o {@link HistoricoPontos} preenchido com os dados da linha atual.
     * @throws SQLException caso ocorra erro ao ler alguma coluna do ResultSet.
     */
    private HistoricoPontos montarHistorico(ResultSet rs) throws SQLException {
        HistoricoPontos historico = new HistoricoPontos();
        historico.setIdHistorico(rs.getInt("id_historico"));
        historico.setOrigem(OrigemPontos.valueOf(rs.getString("origem")));
        historico.setPontosGanhos(rs.getInt("pontos_ganhos"));
        historico.setDataRegistro(rs.getTimestamp("data_registro").toLocalDateTime());

        Conta conta = new ContaDAO().buscarContaPorId(rs.getInt("id_conta"));
        historico.setConta(conta);

        return historico;
    }
}