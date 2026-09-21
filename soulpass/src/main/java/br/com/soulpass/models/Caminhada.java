package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Classe referente à função do sistema que permite a caminhada e o registro de quando se caminhou para a
 * obtenção de pontos
 * */
@Getter
@Setter
public class Caminhada {
    private int idCaminhada;
    private LocalDateTime diaCaminhada;
    private double kmAndados;
    private Conta conta;

    /**
     * Método de Caminhada responsável por executar o método calcularPontos, adicionar à conta e
     * retornar os pontosGanhos pela ação de caminhar.
     * */
    public int caminhar() {
        int pontosGanhos = calcularPontos(kmAndados);
        conta.adicionarPontos(pontosGanhos, OrigemPontos.CAMINHADA);
        return pontosGanhos;
    }

    /**
     * Método de Caminhada responsável por calcular os contos que o usuário vai receber ao caminhar
     *
     * @Param kmAndados identifica a quantidade de quilomêtros andados pelo usuário
     * */
    public int calcularPontos(double kmAndados) {
        // Respeita o limite diário de km, caso o usuário ultrapasse
        double kmValidos = Math.min(kmAndados, 6);
        int pontosGanhos = 0;
        if (kmValidos >= 2){
            pontosGanhos += 40;
        }
        if (kmValidos >= 4){
            pontosGanhos += 40;
        }
        if (kmValidos >= 6){
            pontosGanhos += 40;
        }
        return pontosGanhos;
    }
}
