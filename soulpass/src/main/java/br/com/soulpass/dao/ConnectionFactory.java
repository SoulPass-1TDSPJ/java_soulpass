package br.com.soulpass.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe que irá estabelecer conexão com os servidores da OracleDB através dos dados de login e do drive.
 * Os dados de acesso NÃO ficam no código: são lidos das variáveis de ambiente SPRING_DATASOURCE_URL,
 * SPRING_DATASOURCE_USERNAME e SPRING_DATASOURCE_PASSWORD (configuradas no .env, no docker-compose
 * ou no painel da Render).
 * */
public class ConnectionFactory {
    /**
     * Método estático que será usado para tentar estabelecer a conexão com os servidores OracleDB com os dados de login
     * e do drive, permitindo a integração do SQL no sistema
     * */
    public static Connection obterConexao(){
        String url = System.getenv("SPRING_DATASOURCE_URL");
        String usuario = System.getenv("SPRING_DATASOURCE_USERNAME");
        String senha = System.getenv("SPRING_DATASOURCE_PASSWORD");

        //Sem as variáveis de ambiente não há como conectar, então avisa claramente o que está faltando
        if (url == null || usuario == null || senha == null) {
            throw new IllegalStateException(
                    "Variáveis de ambiente do Oracle não configuradas."
            );
        }

        //Usa tratativa de erro "try catch" para iniciar a conexão com o gerenciador do drive.
        try{
            return DriverManager.getConnection(url, usuario, senha);
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco Oracle.", e);
        }
    }
}
