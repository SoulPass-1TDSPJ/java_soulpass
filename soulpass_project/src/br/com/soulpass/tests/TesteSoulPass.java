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

        int op = -1;
        while (op != 0) {
            System.out.println("======SOUL PASS======" + "\n1 - Cadastrar usuário" + "\n2 - Verificar usuário" + "\n3 - " +
                            "Mudar usuário" + "\n4 - Cadastrar bilhete único" + "\n5 - Verificar bilhete único" +
                            "\n6 - Verificar pontos" + "\n7 - Converter pontos" + "\n0 - Encerrar sessão" + "\n----------------" +
                            "\nDigite a opção desejada: ");
            op = leitorInt.nextInt();
            switch (op) {
                case 1:
                    if (usuario.getNome() != null) {
                        System.out.println("----------------" + "\nVocê já cadastrou um usuário");
                        break;
                    } else {
                        System.out.println("----------------" + "\nCadastro de usuário" + "\n----------------");
                        System.out.print("Digite seu nome: ");
                        usuario.setNome(leitorStr.nextLine());
                        System.out.print("Digite a idade: ");
                        usuario.setIdade(leitorInt.nextInt());
                        System.out.print("Digite o CPF: ");
                        usuario.setCpf(leitorStr.nextLine());
                        System.out.print("Digite o email: ");
                        usuario.setEmail(leitorStr.nextLine());
                        usuario.setId(new Random().nextInt(999999) + 1);
                        System.out.println("----------------" + "\nCadastro concluído com sucesso");
                        break;
                    }

                case 2:
                    if (usuario.getNome() == null) {
                        System.out.println("----------------" + "\nNenhum usuário cadastrado ainda.");
                        break;
                    } else {
                        System.out.println(usuario.mostrarDados());
                    }
                    break;

                case 0:
                    System.out.println("----------------" + "\nEncerrando o sistema. Até mais!");
                    break;

                default:
                    System.out.println("----------------" + "\nOpção inválida. Tente novamente.");

            }

        }
    }
}
