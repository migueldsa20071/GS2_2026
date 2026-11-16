package br.com.fiap.energia.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LeituraRequest(
        @NotNull(message = "O id da unidade consumidora é obrigatório")
        Long unidadeId,

        @NotNull(message = "A data da leitura é obrigatória")
        @PastOrPresent(message = "A data da leitura não pode ser futura")
        LocalDate dataLeitura,

        @NotNull(message = "O consumo em kWh é obrigatório")
        @DecimalMin(value = "0.01", message = "O consumo deve ser maior que zero")
        BigDecimal consumoKwh,

        @NotNull(message = "O valor da tarifa por kWh é obrigatório")
        @DecimalMin(value = "0.0001", message = "A tarifa deve ser maior que zero")
        BigDecimal valorTarifaKwh
) {
}
