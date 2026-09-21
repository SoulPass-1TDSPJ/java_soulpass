package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {
    private int idPost;
    private Conta conta;
    private LocalDateTime data;
    private boolean temFoto;
    private boolean temvideo;
    private String textoPost;

    /**
     * Método de Post responsável por executar o método calcularPontos, adicionar à conta e
     * retornar os pontosGanhos pela ação de postar.
     * */
    public int postar(){
        int pontosGanhos = calcularPontos(temFoto, temvideo);
        conta.adicionarPontos(pontosGanhos, OrigemPontos.POST);
        return pontosGanhos;
    }

    /**
     * Método de Post responsável por calcular os contos que o usuário vai receber ao postar
     *
     * @Param temFoto identifica se o post contem ou não uma foto.
     * @Param temvideo identifica se o post contém ou não um vídeo.
     * */
    public int calcularPontos(boolean temFoto, boolean temvideo) {
        int pontos = 10;
        if (temFoto) {pontos += 20;}
        if (temvideo) {pontos += 30;}
        return pontos;
    }
}