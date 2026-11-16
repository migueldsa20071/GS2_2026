package br.com.fiap.energia.dto;

import br.com.fiap.energia.model.TipoUnidade;
import br.com.fiap.energia.model.UnidadeConsumidora;

import java.time.LocalDate;

public record UnidadeResponse(
        Long id,
        String nome,
        String endereco,
        String cidade,
        TipoUnidade tipo,
        LocalDate dataCadastro,
        boolean ativa
) {
    public static UnidadeResponse de(UnidadeConsumidora u) {
        return new UnidadeResponse(
                u.getId(), u.getNome(), u.getEndereco(), u.getCidade(),
                u.getTipo(), u.getDataCadastro(), u.isAtiva());
    }
}
