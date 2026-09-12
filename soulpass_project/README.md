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