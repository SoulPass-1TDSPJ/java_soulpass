package br.com.soulpass.tests;

import java.util.Scanner;

public class TesteSoulPass {
    public static void main(String[] args) {
        Scanner leitorStr = new Scanner(System.in);
        Scanner leitorInt = new Scanner(System.in);
        Scanner leitorDouble = new Scanner(System.in);

        int op = 0;
        while (op == 0) {
            System.out.println("======SOUL PASS======" + "\n1 - Cadastrar usuário" + "\n2 - Verificar usuário" + "\n3 - " +
                    "Cadastrar bilhete único" + "\n4 - Verificar bilhete único" + "\n5 - Converter pontos" + "\n----------------" +
                    "\nDigite a opção desejada: ");
            op = leitorInt.nextInt();
            switch (op) {
                case 1:
                    System.out.println("----------------" + "\n");
            }

        }
    }
}
