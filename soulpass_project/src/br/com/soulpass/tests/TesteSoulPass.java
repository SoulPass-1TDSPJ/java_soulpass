package br.com.soulpass.tests;

import br.com.soulpass.enums.StatusBilhete;
import br.com.soulpass.models.Bilhete;
import br.com.soulpass.models.Conta;
import br.com.soulpass.models.ConversorPontos;
import br.com.soulpass.models.Usuario;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class TesteSoulPass {
    public static void main(String[] args) {
        Scanner leitorStr = new Scanner(System.in);
        Scanner leitorInt = new Scanner(System.in);
        Scanner leitorDouble = new Scanner(System.in);
        Usuario usuario = new Usuario();
        Bilhete bilhete = new Bilhete();
        bilhete.setStatus(StatusBilhete.ATIVO);
        Conta conta = new Conta();
        int pontos = 0;
        conta.setQtdePontos(0);
        conta.setCreditos(0);

        int op = -1;
        while (op != 0) {
            System.out.println("======SOUL PASS======" + "\n1 - Cadastrar usuário" + "\n2 - Verificar usuário" + "\n3 - " +
                            "Mudar usuário" + "\n4 - Cadastrar bilhete único" + "\n5 - Verificar bilhete único" +
                            "\n6 - Verificar pontos" + "\n7 - Converter pontos" + "\n8 - Fazer post" +
                            "\n0 - Encerrar sessão" + "\n----------------" + "\nDigite a opção desejada: ");
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

                case 3:
                    if (usuario.getNome() == null){
                        System.out.println("----------------" + "\nNão há usuário para mudar");
                    } else{
                        System.out.println("----------------" + "\nTem certeza que quer mudar suas informações de usuário?" +
                                "\ns - Sim" + "\nn - Não");
                        String entr = leitorStr.nextLine();
                        if (Objects.equals(entr, "n")) {
                            System.out.println("----------------" + "\nVoltando para o menu");
                            break;
                        } else {
                            System.out.println("----------------" + "\nNome anterior: " + usuario.getNome());
                            System.out.println("Digite o nome atualizado: ");
                            usuario.setNome(leitorStr.nextLine());
                            System.out.println("----------------" + "\nIdade anterior: " + usuario.getIdade());
                            System.out.println("Digite a idade atualizada: ");
                            usuario.setIdade(leitorInt.nextInt());
                            System.out.println("----------------" + "\nCPF anterior: " + usuario.getCpf());
                            System.out.println("Digite o CPF atualizado: ");
                            usuario.setCpf(leitorStr.nextLine());
                            System.out.println("----------------" + "\nEmail anterior: " + usuario.getEmail());
                            System.out.println("Digite o email atualizado: ");
                            usuario.setEmail(leitorStr.nextLine());
                            System.out.println("----------------" + "\nUsuário atualizado");
                        }
                    }
                    break;

                case 4:
                    System.out.println("----------------" + "\nCadastro de Bilhete Único" + "\n----------------");
                    System.out.print("Digite o número do seu bilhete: ");
                    bilhete.setNumBilhete(leitorInt.nextInt());
                    bilhete.setCredito(conta.getCreditos());
                    bilhete.cadastrarBilhete(bilhete.getNumBilhete(), bilhete.getStatus());
                    break;

                case 5:
                    if (bilhete.getNumBilhete() == 0) {
                        System.out.println("----------------" + "\nNenhum Bilhete Único cadastrado ainda.");
                        break;
                    } else {
                        System.out.println(bilhete.mostrarBilhete());
                    }
                    break;

                case 6:
                    if (usuario.getNome() == null){
                        System.out.println("----------------" + "\nVocê não tem usuário para ter pontos");
                        break;
                    } else {
                        System.out.println("----------------" + "\nPontos: " + conta.getQtdePontos());
                    }
                    break;

                case 7:
                    conta.setCreditos(ConversorPontos.converterParaCredito(conta.getQtdePontos()));
                    System.out.println("----------------" + "\nCréditos: " + conta.getCreditos());
                    bilhete.setCredito(conta.getCreditos());
                    break;

                case 8:
                    int opt = -1;
                    while (opt != 0){
                        System.out.println("----------------" + "\nVai postar o que?" + "\n1 - Foto (Dá 110 pts)"
                                + "\n2 - Texto (Dá 55 pts)" + "\n0 - Nada");
                        opt = leitorInt.nextInt();
                        switch (opt) {
                            case 1:
                                pontos += 110;
                                conta.adicionarPontos(pontos);
                                break;

                            case 2:
                                pontos += 55;
                                conta.adicionarPontos(pontos);
                                break;

                            case 0:
                                System.out.println("----------------" + "\nVoltando ao menu principal");
                                break;

                            default:
                                System.out.println("----------------" + "\nOpção inválida, tente 1, 2 ou 0");
                        }
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
