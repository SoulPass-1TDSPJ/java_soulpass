package br.com.soulpass.models;

import br.com.soulpass.enums.StatusBilhete;

import java.util.List;

public class Bilhete {
    private double credito;
    private int numBilhete;
    private StatusBilhete status;

    private Bilhete(List<Bilhete> bilhetes, int credito, int numBilhete, StatusBilhete status) {
        this.credito = credito;
        this.numBilhete = numBilhete;
        this.status = status;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public int getNumBilhete() {
        return numBilhete;
    }

    public void setNumBilhete(int numBilhete) {
        this.numBilhete = numBilhete;
    }

    public StatusBilhete getStatus() {
        return status;
    }

    public void setStatus(StatusBilhete status) {
        this.status = status;
    }

    public String mostrarBilhete(){
        return "======Bilhete Único======" + "\nCrédito: " + credito + "\nNúmero de bilhete: " + numBilhete +
                "\nStatus: " + status;
    }

    public void cadastrarBilhete(int numBilhete, StatusBilhete status) {
        if (numBilhete < 100000000 || numBilhete > 999999999 || status == StatusBilhete.NAO_ATIVO) {
            System.out.println("-----------------------" + "\nImpossivel cadastrar bilhete, número de bilhete inexistente ou bilhete não ativo"
                    + "-----------------------");
        }
        System.out.println("Cadastrando Bilhete...");
        this.numBilhete = numBilhete;
        System.out.println("Bilhete cadastrado com sucesso!");

    }
}
