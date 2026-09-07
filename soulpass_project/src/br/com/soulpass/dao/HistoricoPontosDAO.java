package br.com.soulpass.dao;

import br.com.soulpass.models.HistoricoPontos;
import br.com.soulpass.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoPontosDAO {
    private Connection conexao;

    public void inserirNoHist(){
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;

        HistoricoPontos historico = new HistoricoPontos();

        try{
            ps = conexao.prepareStatement( "insert into tbl_historico_pontos (id_conta, origem, pontos_ganhos, " +
                    "data_registro) values (?, ?, ?, ?)");
            ps.setInt(1, historico.getConta().getIdConta());
            ps.setString(2, historico.getOrigem().name());
            ps.setInt(3, historico.getPontosGanhos());
            ps.setString(4, historico.getDataRegistro().toString());
            ps.executeUpdate();

            ps.close();
            conexao.close();

            System.out.println("Histórico de pontos alterado com sucesso!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
