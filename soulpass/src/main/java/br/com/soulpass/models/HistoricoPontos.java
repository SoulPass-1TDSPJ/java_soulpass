package br.com.soulpass.models;

import br.com.soulpass.enums.OrigemPontos;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoricoPontos {
    private int idHistorico;
    private Conta conta;
    private OrigemPontos origem;
    private int pontosGanhos;
    private LocalDateTime dataRegistro;
}
