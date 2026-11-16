package br.com.fiap.energia.dto;

import br.com.fiap.energia.model.LeituraConsumo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public record LeituraResponse(
        Long id,
        Long unidadeId,
        String unidadeNome,
        LocalDate dataLeitura,
        BigDecimal consumoKwh,
        BigDecimal valorTarifaKwh,
        BigDecimal valorTotal
) {
    public static LeituraResponse de(LeituraConsumo l) {
        BigDecimal total = l.getConsumoKwh()
                .multiply(l.getValorTarifaKwh())
                .setScale(2, RoundingMode.HALF_UP);
        return new LeituraResponse(
                l.getId(),
                l.getUnidade().getId(),
                l.getUnidade().getNome(),
                l.getDataLeitura(),
                l.getConsumoKwh(),
                l.getValorTarifaKwh(),
                total);
    }
}
