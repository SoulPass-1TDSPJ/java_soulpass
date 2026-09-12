package br.com.soulpass.models;

import br.com.soulpass.enums.StatusBilhete;

import java.util.List;

public class Bilhete {
    private int idTicket;
    private double credito;
    private long numBilhete;
    private StatusBilhete status;

    public int getIdTicket() {return idTicket;}

    public void setIdTicket(int idTicket) {this.idTicket = idTicket;}

    public double getCredito() {return credito;}

    public void setCredito(double credito) {this.credito = credito;}

    public long getNumBilhete() {return numBilhete;}

    public void setNumBilhete(long numBilhete) {this.numBilhete = numBilhete;}

    public StatusBilhete getStatus() {return status;}

    public void setStatus(StatusBilhete status) {this.status = status;}

    /**
     * Método simples para mostrar o bilhete
     * */
    public String mostrarBilhete(){
        return "======Bilhete Único======" + "\nCrédito: R$" + credito + "\nNúmero de bilhete: " + numBilhete +
                "\nStatus: " + status;
    }

    /**
     * Método para o cadastro do bilhete, caso ele não tenha exatamente 9 dígitos, ele dá erro e retorna falso.
     * Agora, caso ele tenha 9 dígitos, ele fa o cadastro.
     *
     * @Param numBilhete identifica o número do bilhete que será adicionado
     * @Param status identifia se o bilhete está ativo*/
    public boolean cadastrarBilhete(int numBilhete, StatusBilhete status) {
        if (numBilhete < 100000000 || numBilhete > 999999999 || status == StatusBilhete.NAO_ATIVO) {
            System.out.println("-----------------------" + "\nImpossivel cadastrar bilhete, número de bilhete inexistente ou bilhete não ativo");
            return false;
        } else {
            this.numBilhete = numBilhete;
            this.status = status;
            System.out.println("Cadastrando Bilhete...");
            System.out.println("Bilhete cadastrado com sucesso!");
            return true;
        }
    }
}