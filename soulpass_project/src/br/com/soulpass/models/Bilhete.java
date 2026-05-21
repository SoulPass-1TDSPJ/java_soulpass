package br.com.soulpass.models;

import br.com.soulpass.enums.StatusBilhete;

import java.util.List;

public class Bilhete {
    private double credito;
    private int numBilhete;
    private StatusBilhete status;


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
        return "======Bilhete Único======" + "\nCrédito: R$" + credito + "\nNúmero de bilhete: " + numBilhete +
                "\nStatus: " + status;
    }

    public void cadastrarBilhete(int numBilhete, StatusBilhete status) {
        if (numBilhete < 100000000 || numBilhete > 999999999 || status == StatusBilhete.NAO_ATIVO) {
            System.out.println("-----------------------" + "\nImpossivel cadastrar bilhete, número de bilhete inexistente ou bilhete não ativo");
        } else {
            System.out.println("Cadastrando Bilhete...");
            System.out.println("Bilhete cadastrado com sucesso!");
        }
    }
}
