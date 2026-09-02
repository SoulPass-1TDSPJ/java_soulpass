package br.com.soulpass.tests;

import br.com.soulpass.models.Usuario;

import java.util.Scanner;

public class TestUsuario {
    public static void main(String[] args) {
        try {
            Scanner inputInt = new Scanner(System.in);
            Scanner inputLong = new Scanner(System.in);
            Scanner inputStr = new Scanner(System.in);

            Usuario usuario = new Usuario();

            System.out.println("Digite o id do usuário: ");
            usuario.setIdUser(inputInt.nextInt());
            System.out.println("Digite seu nome: ");
            usuario.setNome(inputStr.nextLine());
            System.out.println("Digite sua idade: ");
            usuario.setIdade(inputInt.nextInt());
            System.out.println("Digite seu cpf: ");
            usuario.setCpf(inputLong.nextLong());
            System.out.println("Digite seu e-mail: ");
            usuario.setEmail(inputStr.nextLine());

            System.out.println(usuario.mostrarDadosDev());
        } catch (Exception e) {
            System.out.println("Erro: Valor inválido para o campo");
        }
    }
}
