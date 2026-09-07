# 🚍 SoulPass

## 📓 O que esperar:

- ### 👣 Captura de Dados de Mobilidade:
---
O sistema vai registrar a distância percorrida a pé pelo usuário via GPS; isso até
a meta diária de 6km; E deve manter histórico de corrida por dia.

- ### 🦵 Mobilidade Sustentável:
---
O sistema irá atribuir 40 pontos a cada 2km (Até 6km) percorridos; O limite é de 120
pontos por dia. O usuário deve poder visualizar que completou os quiilômetros.

- ### 📒 Gestão de Pontos do Usuário:
---
O sistema deve exibir o saldo total de pontos acumulados pelo usuário. Também deve
apresentar histórico diário de pontos ganhos, com data e distância correspondente.

O usuário deve poder consultar sua evolução ao longo do tempo.

- ### 🔄️ Conversão de Pontos em Créditos de Transporte:
---
O sistema deve permitir que o usuário solicite a conversão de pontos acumulados em
créditos, e então debitar do saldo de pontos o valor correspondente aos créditos
convertidos. Além disso, antes, o sistema exibirá quantos créditos o usuário pode
obter com o saldo atual de pontos. E se faltar pontos para atingir uma passagem
completa, mostrar quanto falta em pontos.

**Como funciona a conversão:** A cada 110 pontos, o usuário recebe 1 real em créditos
em transporte público. O valor de uma passagem é atualmente de 5,30 reais, o
equivalente a 605 pontos.

- ### 💳 Uso de Créditos no Transporte Público:
---
O sistema deve gerar um código de validação *(QRCode)* para uso dos créditos na
bilhetagem do transporte público. Assim, ele deve debitar automaticamente o saldo de
créditos correspondente ao valor utilizado.

- ### 📲 Acesso à Plataforma:
---
O sistema permitirá o cadastro via *e-mail* e senha, até mesmo login social *(Google).*
Então, ele deve validar o *e-mail* do usuário antes de liberar o uso completo do *app.*
Para caso o usuário esqueça a senha, ele poderá recuperar via *e-mail*. As senhas
devem ser armazenadas com criptografia *hash + salt.*

O usuário deve aceitar os termos de uso e política de privacidade no cadastro. Caso uma
conta fique inativa por mais de 12 meses, tem os pontos zerados por segurança.

## Feedback anterior, da professora:

A interface definida não apresenta implementação clara. É importante garantir que haja uma separação adequada entre contrato (interface) e comportamento (classes que implementam essa interface).


A responsabilidade da classe ConversorPontos não ficou bem definida. Atualmente, ela zera os pontos, o que parece ser uma regra de negócio mais adequada à própria entidade de conta. Recomenda-se avaliar se esse comportamento deveria ser um método da classe de conta.


Ao cadastrar um bilhete, não está claro a qual usuário ele pertence, pois não há relacionamento explícito entre essas entidades. É essencial definir corretamente os vínculos entre classes para manter a consistência dos dados.


No fluxo de criação de post, o usuário seleciona o tipo de conteúdo (texto), porém não é permitido digitar. Isso gera inconsistência, especialmente considerando que há acúmulo de pontos associado a essa ação. A regra de negócio nesse ponto precisa ser revisada.


Existe cadastro de usuário, mas não há diferenciação de ambiente após o login. Espera-se que o sistema apresente um contexto diferente para usuários autenticados.


Faltam relacionamentos entre várias classes, o que dificulta o entendimento do modelo de domínio e prejudica a manutenção do código.


Há uso excessivo de métodos estáticos sem justificativa clara. Esse padrão pode limitar a extensibilidade e dificultar testes.


As implementações das regras de negócio estão muito simplificadas e poderiam ser melhor estruturadas, refletindo de forma mais adequada os requisitos do sistema.

``switch (op) {
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
                                "\nS - Sim" + "\nN - Não");
                        String entr = leitorStr.nextLine();
                        if (entr.equalsIgnoreCase("n")) {
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
                    if (usuario.getNome() == null) {
                        System.out.println("----------------" + "\nVocê não tem usuário para cadastrar um Bilhete");
                        break;
                    } else {
                        System.out.println("----------------" + "\nCadastro de Bilhete Único" + "\n----------------");
                        System.out.print("Digite o número do seu bilhete: ");
                        bilhete.setNumBilhete(leitorInt.nextInt());
                        bilhete.setCredito(bilhete.getCredito());
                        bilhete.cadastrarBilhete(bilhete.getNumBilhete(), bilhete.getStatus());
                    }
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
                    if (bilhete.getNumBilhete() == 0){
                        System.out.println("----------------" + "\nNão há Bilhete Único para mudar");
                    } else{
                        System.out.println("----------------" + "\nTem certeza que quer mudar suas informações do Bilhete Único?" +
                                "\nS - Sim" + "\nN - Não");
                        String ent = leitorStr.nextLine();
                        if (ent.equalsIgnoreCase("n")) {
                            System.out.println("----------------" + "\nVoltando para o menu");
                            break;
                        } else {
                            System.out.println("----------------" + "\nNúmero anterior: " + bilhete.getNumBilhete());
                            System.out.println("Digite o número atualizado: ");
                            usuario.setNome(leitorStr.nextLine());
                            System.out.println("----------------" + "\nBilhete atualizado");
                        }
                    }
                    break;

                case 7:
                    if (usuario.getNome() == null){
                        System.out.println("----------------" + "\nVocê não tem usuário para ter pontos");
                        break;
                    } else {
                        System.out.println("----------------" + "\nPontos: " + conta.getQtdePontos());
                    }
                    break;

                case 8:
                    if (bilhete.getNumBilhete() == 0) {
                        System.out.println("----------------" + "\nVocê não tem bilhete cadastrado ainda.");
                        break;
                    } else if (conta.getQtdePontos() < 110) {
                        System.out.println("----------------" + "\nVocê não tem pontos suficientes. (Mínimo de 110 pontos)");
                        break;
                    }else {
                        bilhete.setCredito(Conta.converterParaCredito(conta.getQtdePontos()));
                        System.out.println("----------------" + "\nCréditos: " + bilhete.getCredito());
                        bilhete.setCredito(bilhete.getCredito());
                    }
                    break;

                case 9:
                    if (usuario.getNome() == null) {
                        System.out.println("----------------" + "\nVocê não tem usuário para ter postar ou ganhar pontos");
                        break;
                    }
                    int opt = -1;
                    while (opt != 0){
                        System.out.println("----------------" + "\nVai postar o que hoje?" + "\n1 - Foto (Dá 110 pts)"
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

            }``