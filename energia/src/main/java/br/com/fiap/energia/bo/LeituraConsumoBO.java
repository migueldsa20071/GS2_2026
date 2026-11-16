package br.com.fiap.energia.bo;

import br.com.fiap.energia.dao.LeituraConsumoDAO;
import br.com.fiap.energia.dto.LeituraRequest;
import br.com.fiap.energia.exception.RecursoNaoEncontradoException;
import br.com.fiap.energia.exception.RegraNegocioException;
import br.com.fiap.energia.model.LeituraConsumo;
import br.com.fiap.energia.model.UnidadeConsumidora;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeituraConsumoBO {

    private final LeituraConsumoDAO leituraDAO;
    private final UnidadeConsumidoraBO unidadeBO;

    public LeituraConsumoBO(LeituraConsumoDAO leituraDAO, UnidadeConsumidoraBO unidadeBO) {
        this.leituraDAO = leituraDAO;
        this.unidadeBO = unidadeBO;
    }

    @Transactional(readOnly = true)
    public List<LeituraConsumo> listar(Long unidadeId) {
        if (unidadeId != null) {
            unidadeBO.buscar(unidadeId); // garante 404 se a unidade não existir
            return leituraDAO.findByUnidadeIdOrderByDataLeituraDesc(unidadeId);
        }
        return leituraDAO.findAll();
    }

    @Transactional(readOnly = true)
    public LeituraConsumo buscar(Long id) {
        return leituraDAO.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leitura de consumo", id));
    }

    @Transactional
    public LeituraConsumo criar(LeituraRequest req) {
        UnidadeConsumidora unidade = unidadeBO.buscar(req.unidadeId());
        validarUnidadeAtiva(unidade);
        LeituraConsumo l = new LeituraConsumo();
        l.setUnidade(unidade);
        preencher(l, req);
        return leituraDAO.save(l);
    }

    @Transactional
    public LeituraConsumo atualizar(Long id, LeituraRequest req) {
        LeituraConsumo l = buscar(id);
        UnidadeConsumidora unidade = unidadeBO.buscar(req.unidadeId());
        validarUnidadeAtiva(unidade);
        l.setUnidade(unidade);
        preencher(l, req);
        return leituraDAO.save(l);
    }

    @Transactional
    public void excluir(Long id) {
        leituraDAO.delete(buscar(id));
    }

    private void validarUnidadeAtiva(UnidadeConsumidora unidade) {
        if (!unidade.isAtiva()) {
            throw new RegraNegocioException(
                    "A unidade consumidora " + unidade.getId() + " está inativa e não aceita novas leituras");
        }
    }

    private void preencher(LeituraConsumo l, LeituraRequest req) {
        l.setDataLeitura(req.dataLeitura());
        l.setConsumoKwh(req.consumoKwh());
        l.setValorTarifaKwh(req.valorTarifaKwh());
    }
}
