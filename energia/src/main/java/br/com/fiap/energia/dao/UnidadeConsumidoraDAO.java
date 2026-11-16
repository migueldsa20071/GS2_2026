package br.com.fiap.energia.dao;

import br.com.fiap.energia.model.UnidadeConsumidora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadeConsumidoraDAO extends JpaRepository<UnidadeConsumidora, Long> {

    List<UnidadeConsumidora> findByCidadeIgnoreCase(String cidade);
}
