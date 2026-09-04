package br.com.soulpass.tests;

import br.com.soulpass.dao.ConnectionFactory;

public class TestConnection {
    static void main(){
        System.out.println("Teste de conexão com o banco de dados...");
        if(ConnectionFactory.obterConexao() == null){
            System.out.println("⚠️ Erro ao estabelecer conexão ⚠️");
        }
        else{
            System.out.println("✅Conexão estabelecida✅");
        }
    }
}
