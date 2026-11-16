package br.com.fiap.energia.bo;

import br.com.fiap.energia.dao.LeituraConsumoDAO;
import br.com.fiap.energia.dao.UnidadeConsumidoraDAO;
import br.com.fiap.energia.dto.UnidadeRequest;
import br.com.fiap.energia.exception.RecursoNaoEncontradoException;
import br.com.fiap.energia.exception.RegraNegocioException;
import br.com.fiap.energia.model.UnidadeConsumidora;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UnidadeConsumidoraBO {

    private final UnidadeConsumidoraDAO unidadeDAO;
    private final LeituraConsumoDAO leituraDAO;

    public UnidadeConsumidoraBO(UnidadeConsumidoraDAO unidadeDAO, LeituraConsumoDAO leituraDAO) {
        this.unidadeDAO = unidadeDAO;
        this.leituraDAO = leituraDAO;
    }

    @Transactional(readOnly = true)
    public List<UnidadeConsumidora> listar(String cidade) {
        if (cidade != null && !cidade.isBlank()) {
            return unidadeDAO.findByCidadeIgnoreCase(cidade.trim());
        }
        return unidadeDAO.findAll();
    }

    @Transactional(readOnly = true)
    public UnidadeConsumidora buscar(Long id) {
        return unidadeDAO.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade consumidora", id));
    }

    @Transactional
    public UnidadeConsumidora criar(UnidadeRequest req) {
        UnidadeConsumidora u = new UnidadeConsumidora();
        preencher(u, req);
        u.setDataCadastro(LocalDate.now());
        u.setAtiva(req.ativa() == null || req.ativa());
        return unidadeDAO.save(u);
    }

    @Transactional
    public UnidadeConsumidora atualizar(Long id, UnidadeRequest req) {
        UnidadeConsumidora u = buscar(id);
        preencher(u, req);
        if (req.ativa() != null) {
            u.setAtiva(req.ativa());
        }
        return unidadeDAO.save(u);
    }

    @Transactional
    public void excluir(Long id) {
        UnidadeConsumidora u = buscar(id);
        if (leituraDAO.existsByUnidadeId(id)) {
            throw new RegraNegocioException(
                    "Não é possível excluir a unidade: existem leituras de consumo associadas a ela");
        }
        unidadeDAO.delete(u);
    }

    private void preencher(UnidadeConsumidora u, UnidadeRequest req) {
        u.setNome(req.nome().trim());
        u.setEndereco(req.endereco().trim());
        u.setCidade(req.cidade().trim());
        u.setTipo(req.tipo());
    }
}
