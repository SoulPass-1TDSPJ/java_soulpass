package br.com.soulpass.dao;

import br.com.soulpass.enums.StatusBilhete;
import br.com.soulpass.models.Bilhete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pública que permite usar o CRUD pelo java em relação aos bilhetes cadastrados.
 * */
public class BilheteDAO {

    private Connection conexao;

    /**
     * Insere um novo bilhete no banco de dados.
     *
     * @param bilhete objeto contendo crédito, número do bilhete e status a serem persistidos.
     */
    public void cadastrarBilhete(Bilhete bilhete) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "INSERT INTO tbl_bilhete (credito, num_bilhete, status) VALUES (?, ?, ?)";
        try {
            ps = conexao.prepareStatement(sql);
            ps.setDouble(1, bilhete.getCredito());
            ps.setLong(2, bilhete.getNumBilhete());
            ps.setString(3, bilhete.getStatus().name());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca um único bilhete a partir do seu identificador (id_ticket).
     *
     * @param id identificador do bilhete na tabela tbl_bilhete.
     * @return o {@link Bilhete} correspondente, ou {@code null} caso nenhum registro seja encontrado.
     */
    public Bilhete buscarBilhetePorId(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_bilhete WHERE id_ticket = ?";
        Bilhete bilhete = new Bilhete();
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bilhete.setIdTicket(rs.getInt("id_ticket"));
                bilhete.setCredito(rs.getDouble("credito"));
                bilhete.setNumBilhete(rs.getLong("num_bilhete"));
                bilhete.setStatus(StatusBilhete.valueOf(rs.getString("status")));;
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bilhete;
    }

    /**
     * Lista todos os bilhetes cadastrados na tabela tbl_bilhete.
     *
     * @return uma {@link List} com todos os bilhetes encontrados (lista vazia caso não haja nenhum registro).
     */
    public List<Bilhete> listarBilhetes() {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "SELECT * FROM tbl_bilhete";
        List<Bilhete> listaBilhetes = new ArrayList<>();
        try{
            ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bilhete bilhete = new Bilhete();
                bilhete.setIdTicket(rs.getInt("id_ticket"));
                bilhete.setCredito(rs.getDouble("credito"));
                bilhete.setNumBilhete(rs.getLong("num_bilhete"));
                bilhete.setStatus(StatusBilhete.valueOf(rs.getString("status")));
                listaBilhetes.add(bilhete);
            }
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaBilhetes;
    }

    /**
     * Atualiza crédito, número e status de um bilhete já existente, localizado pelo id_ticket.
     *
     * @param bilhete objeto com o {@code idTicket} do registro a ser atualizado e os novos valores
     *                de crédito, número de bilhete e status.
     */
    public void atualizarBilhete(Bilhete bilhete) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "UPDATE tbl_bilhete SET credito = ?, num_bilhete = ?, status = ? WHERE id_ticket = ?";
        try{
            ps = conexao.prepareStatement(sql);
            ps.setDouble(1, bilhete.getCredito());
            ps.setLong(2, bilhete.getNumBilhete());
            ps.setString(3, bilhete.getStatus().name());
            ps.setInt(4, bilhete.getIdTicket());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove um bilhete do banco de dados a partir do seu identificador.
     *
     * @param id identificador (id_ticket) do bilhete a ser excluído.
     */
    public void excluirBilhete(int id) {
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;
        String sql = "DELETE FROM tbl_bilhete WHERE id_ticket = ?";
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