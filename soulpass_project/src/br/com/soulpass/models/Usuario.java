package br.com.soulpass.models;

public class Usuario {
    private int idUser;
    private String nome;
    private int idade;
    private long cpf;
    private String email;
    private String senha;
    private Bilhete bilhete;

    public int getIdUser() {return idUser;}

    public void setIdUser(int idUser) {this.idUser = idUser;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public int getIdade() {return idade;}

    public void setIdade(int idade) {this.idade = idade;}

    public long getCpf() {return cpf;}

    public void setCpf(long cpf) {this.cpf = cpf;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getSenha() {return senha;}

    public void setSenha(String senha) {this.senha = senha;}

    public Bilhete getBilhete() {return bilhete;}

    public void setBilhete(Bilhete bilhete) {this.bilhete = bilhete;}

    public String mostrarDadosDev() {
        return "\n======Usuário======" +
                "\nID Usuário: " + this.idUser +
                "\nNome: " + this.nome +
                "\nIdade: " + this.idade +
                "\nCPF: " + this.cpf +
                "\nEmail: " + this.email +
                "\nSenha: " + this.senha +
                "\n" + this.bilhete;
    }
}
