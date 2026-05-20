package br.com.soulpass.tests;

import br.com.soulpass.enums.StatusBilhete;
import br.com.soulpass.models.Usuario;

import java.util.Random;
import java.util.Scanner;

public class TesteSoulPass {
    public static void main(String[] args) {
        Scanner leitorStr = new Scanner(System.in);
        Scanner leitorInt = new Scanner(System.in);
        Scanner leitorDouble = new Scanner(System.in);
        Usuario usuario = new Usuario();

        int op = 0;
        while (op == 0) {
            System.out.println("======SOUL PASS======" + "\n1 - Cadastrar usuário" + "\n2 - Verificar usuário" + "\n3 - " +
                            "Mudar usuário" + "\n4 - Cadastrar bilhete único" + "\n5 - Verificar bilhete único" +
                            "\n6 - Verificar pontos" + "\n7 - Converter pontos" + "\n----------------" +
                            "\nDigite a opção desejada: ");
            op = leitorInt.nextInt();
            switch (op) {
                case 1:
                    System.out.println("----------------" + "\nCadastro de usuário" + "\n----------------");
                    System.out.println("Digite seu nome: ");
                    String nome = leitorStr.nextLine();
                    usuario.setNome(nome);
                    System.out.println("Digite a idade: ");
                    int idade = leitorInt.nextInt();
                    usuario.setIdade(idade);
                    System.out.println("Digite o CPF: ");
                    int cpf = leitorInt.nextInt();
                    usuario.setCpf(cpf);
                    System.out.println("Digite o email: ");
                    String email = leitorInt.nextLine();
                    usuario.setEmail(email);
                    Random idn = new Random();
                    int id = idn.nextInt((999999 - 1) + 1) + 1;
                    usuario.setId(id);
                    return;

                case 2:
                    

            }

        }
    }
}
