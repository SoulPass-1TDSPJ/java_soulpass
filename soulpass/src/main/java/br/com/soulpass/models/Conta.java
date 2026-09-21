package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;
import br.com.soulpass.enums.StatusConta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Conta{
    private int idConta;
    private StatusConta atividade;
    private Usuario usuario;
    private double qtdePontos;
    private double limitePontos;

    /**
     * Método adicionar Pontos que adiciona os pontos à conta e retorna que a operação foi um sucesso
     *
     * @Param pontos identifica quantos pontos serão adicionados
     * @Param origem identifica de onde veio os pontos (CAMINHADA ou POST) para, na classe teste, fazer o histórico
     * de pontos*/
    public void adicionarPontos(double pontos, OrigemPontos origem){
        qtdePontos += pontos;
        System.out.println("----------------" + "\nAdicionado " + pontos + " ponto(s).");
    }

    /**
     * Método simples que converte os pontos selecionados da conta e o divide por 110
     *
     * @Param pontos identifica quantos pontos serão convertidos*/
    public static double converterParaCredito(double pontos) {return pontos/110;}
}