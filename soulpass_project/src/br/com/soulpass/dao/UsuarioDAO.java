package br.com.soulpass.dao;

import br.com.soulpass.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pública que permite usar o*/
public class UsuarioDAO {
    private Connection conexao;

    public void cadastroUsuario(){
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;

        Usuario usuario = new Usuario();

        try{
            ps = conexao.prepareStatement("insert into tbl_usuarios (nome, idade, cpf, email, senha)" +
                    " values (?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNome());
            ps.setInt(2, usuario.getIdade());
            ps.setLong(3, usuario.getCpf());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getSenha());
            ps.executeUpdate();

            ps.close();
            conexao.close();

            System.out.println("Usuario cadastrado com sucesso!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Usuario verificarUsuario(int id){
        conexao = ConnectionFactory.obterConexao();
        PreparedStatement ps = null;

        Usuario usuario = new Usuario();

        try{
            ps = conexao.prepareStatement("select * from tbl_usuarios where id_user=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                usuario.setIdUser(rs.getInt("id_user"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdade(rs.getInt("idade"));
                usuario.setCpf(rs.getLong("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
            }

            ps.close();
            conexao.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return usuario;
    }
}
