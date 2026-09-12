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
