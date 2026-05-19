package br.com.soulpass.models;

import java.util.List;

public class Usuario {
    private int id;
    private String nome;
    private int idade;
    private int cpf;
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

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Usuario(List<Usuario> usuarios, int id, String nome, int idade, int cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.email = email;
    }

    public String mostrarDados(String nome, int idade, int cpf, String email) {
        if (id == this.id){
            return "Nome: " + nome + "Idade: " + idade + "CPF: " + cpf + "Email: " + email;
        }
        return "Não foi possível adquirir o ID de usuário";
    }
}
