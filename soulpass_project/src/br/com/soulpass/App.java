package br.com.soulpass;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        /*Leitores para que o usuário possa inserir as informações que o sistema pede que ele retorne. Temos um para
         * String, um para números inteiros e outro para números com casa decimal. Fazemos isso para que não tenhamos
         * que resetar o leitor para inserir outro tipo de informação*/
        Scanner leitorStr = new Scanner(System.in);
        Scanner leitorInt = new Scanner(System.in);
        Scanner leitorDouble = new Scanner(System.in);

        /*Aqui, começamos um laço de repetição while, como parâmetro uma variável ('op') que recebe um número inteiro
         * e negativado, pois o laço de repeição ocorre enquando o usuário não digitar '0'*/
        int opi = -1;
        while (opi != 0) {

            /*Pequeno menu inicial, onde as duas primeiras opções levam para o segundo menu, a terceira ajuda o
             * usuário ao esquecer a senha, a quarta opção vai nos levar para infomações sensíveis, e a quinta
             * irá encerrar o sistema saindo do loop
             *
             * Além disso, a variável 'op' irá receber um número referente ao menu que o usuário digitar*/
            System.out.println("\n======ENTRADA DA SOULPASS======");
            System.out.println("1 - Fazer Sign-In");
            System.out.println("2 - Fazer Log-In");
            System.out.println("3 - Esqueci minha senha");
            System.out.println("4 - Área dos Devs");
            System.out.println("0 - Encerrar Sistema");
            /*System.out.println("\n======SOUL PASS======" + "\n1 - Cadastrar usuário" + "\n2 - Verificar usuário" + "\n3 - " +
                            "Mudar usuário" + "\n4 - Cadastrar bilhete único" + "\n5 - Verificar bilhete único" +
                            "\n6 - Mudar bilhete único" + "\n7 - Verificar pontos" + "\n8 - Converter pontos" + "\n9 - Fazer post" +
                            "\n0 - Encerrar sessão" + "\n----------------" + "\nDigite a opção desejada: ");*/
            System.out.println("----------------------------");
            System.out.println("⬇️ Digite uma opção:");
            opi = leitorInt.nextInt();

            /*Switch é para quando temos muitos casos de variáveis, perfeito para menu*/
            switch (opi) {
                /**/
                case 1:


                /*Ao usuário digitar a opção para encerrar o sistema, ele retornará uma mensagem para que ele
                 * saiba disso*/
                case 0:
                    System.out.println("----------------------------");
                    System.out.println("👋 Até uma próxima!");
                    System.out.println("Encerrando sistema...");
                    break;

                /*Para caso qualquer outra coisa seja digitada, ele dará uma mensagem de erro e instruirá o
                 * usuário à digitar um número equivalente à uma opção do menu*/
                default:
                    System.out.println("----------------------------");
                    System.out.println("⚠️ Opção Inválida ⚠️");
                    System.out.println("Digite um número equivalente à uma opção");
                    break;
            }
        }
    }

    /**
     * Pequeno menu inicial, onde as duas primeiras opções levam para o segundo menu, a terceira ajuda o
     * usuário ao esquecer a senha, a quarta opção vai nos levar para infomações sensíveis, e a quinta
     * irá encerrar o sistema saindo do 'loop'.
     * */
    private static void menuInicial(){
        System.out.println("\n======ENTRADA DA SOULPASS======");
        System.out.println("1 - Fazer Sign-In");
        System.out.println("2 - Fazer Log-In");
        System.out.println("3 - Esqueceu a senha?");
        System.out.println("4 - Área dos Devs");
        System.out.println("0 - Encerrar Sistema");
        System.out.println("----------------------------");
        System.out.println("⬇️ Digite uma opção:");
    }

    private static void menuFinalDoUser(){
        System.out.println("\n======SOULPASS======");
        System.out.println("1 - Conferir Conta e Usuário");
        System.out.println("2 - Editar Usuário");
        System.out.println("3 - Adicionar Bilhete");
        System.out.println("4 - Editar Bilhete");
        System.out.println("5 - Fazer Post");
        System.out.println("6 - Excluit Post");
        System.out.println("7 - Fazer Caminhada");
        System.out.println("8 - Exibir Histórico");
        System.out.println("9 - Desativar Conta");
        System.out.println("0 - Voltar à Entrada");
        System.out.println("----------------------------");
        System.out.println("⬇️ Digite uma opção:");
    }

    private static void menuFinalDoDev(){
        System.out.println("\n======ÁREA DEV======");
        System.out.println("1 - Listar contas e usuários");
        System.out.println("2 - Editar usuário");
        System.out.println("3 - Deletar usuário");
        System.out.println("4 - Editar conta");
        System.out.println("5 - Deletar conta");
        System.out.println("6 - Listar bilhetes");
        System.out.println("7 - Editar bilhete");
        System.out.println("8 - Alterar bilhete");
        System.out.println("9 - Listar posts");
        System.out.println("10 - Deletar post");
        System.out.println("11 - Listar caminhada");
        System.out.println("12 - Deletar caminhada");
        System.out.println("13 - Exibir históricos");
        System.out.println("14 - Deletar histórico");
        System.out.println("0 - Voltar à entrada");
        System.out.println("----------------------------");
        System.out.println("⬇️ Digite uma opção:");
    }


}

