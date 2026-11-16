package br.com.fiap.energia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TB_LEITURA_CONSUMO")
public class LeituraConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEITURA")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_UNIDADE", nullable = false)
    private UnidadeConsumidora unidade;

    @Column(name = "DT_LEITURA", nullable = false)
    private LocalDate dataLeitura;

    @Column(name = "NR_CONSUMO_KWH", nullable = false, precision = 12, scale = 2)
    private BigDecimal consumoKwh;

    @Column(name = "VL_TARIFA_KWH", nullable = false, precision = 8, scale = 4)
    private BigDecimal valorTarifaKwh;

    public LeituraConsumo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnidadeConsumidora getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeConsumidora unidade) {
        this.unidade = unidade;
    }

    public LocalDate getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(LocalDate dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public BigDecimal getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(BigDecimal consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public BigDecimal getValorTarifaKwh() {
        return valorTarifaKwh;
    }

    public void setValorTarifaKwh(BigDecimal valorTarifaKwh) {
        this.valorTarifaKwh = valorTarifaKwh;
    }
}
