package br.com.fiap.energia.dao;

import br.com.fiap.energia.model.LeituraConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraConsumoDAO extends JpaRepository<LeituraConsumo, Long> {

    List<LeituraConsumo> findByUnidadeIdOrderByDataLeituraDesc(Long unidadeId);

    boolean existsByUnidadeId(Long unidadeId);
}
