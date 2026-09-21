package br.com.soulpass.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {
    private int idUser;
    private String nome;
    private int idade;
    private long cpf;
    private String email;
    private String senha;
    private Bilhete bilhete;

    /**
     * Método simples para mostrar os dados de usuário para o próprio
     * */
    public String mostrarDados() {
        return "\n======Usuário======" +
                "\nNome: " + nome +
                "\nIdade: " + idade +
                "\nEmail: " + email +
                "\nCPF: " + cpf;
    }

    /**
     * Método simples para mostrar os dados do usuário para os devs
     * */
    public String mostrarDadosDev() {
        return "\n======Usuário======" +
                "\nID Usuário: " + idUser +
                "\nNome: " + nome +
                "\nIdade: " + idade +
                "\nCPF: " + cpf +
                "\nEmail: " + email +
                "\nSenha: " + senha +
                "\n" + bilhete;
    }
}
