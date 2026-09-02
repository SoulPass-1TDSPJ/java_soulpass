package br.com.soulpass.tests;

import br.com.soulpass.dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    static void main(String[] args){
        System.out.println("\nTestando conexão com servidor de DataBase");
        if (ConnectionFactory.obterConexao() != null){
            System.out.println("\n✅Conectado com sucesso!");
        } else {
            System.out.println("\n❎Erro ao conectar!");
        }
    }
}
