package br.com.soulpass.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe que irá estabelecer conexão com os servidores da OracleDB através dos dados de login e do drive
 * */
public class ConnectionFactory {
    /**
     * Método estático que será usado para tentar estabelecer a conexão com os servidores OracleDB com os dados de login
     * e do drive, permitindo a integração do SQL no sistema
     * */
    public static Connection obterConexao(){
        //Criado objeto que não tem nada
        Connection conexao = null;

        //Usa tratativa de erro "try catch" para iniciar a conexão com o gerenciador do drive. Caso dê errado,
        //ele retorna um erro e a conexão continua nula, sem nada.
        try{
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "rm570933",
                    "200308");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Como é um metodo fixo, ele tem que retornar algo, e então ele retorna a conexão.
        return conexao;
    }
}
