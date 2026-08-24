package br.com.soulpass.models;

import java.util.List;

public class Usuario {
    private int id;
    private String nome;
    private int idade;
    private String cpf;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void signIn(){}

    public String mostrarDados() {
        return "======Usuário======" +
                "\nNome: " + this.nome +
                "\nIdade: " + this.idade +
                "\nCPF: " + this.cpf +
                "\nEmail: " + this.email +
                "\nID: " + this.id;
    }
}
