package br.com.fiap.energia.controller;

import br.com.fiap.energia.bo.LeituraConsumoBO;
import br.com.fiap.energia.dto.LeituraRequest;
import br.com.fiap.energia.dto.LeituraResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/leituras")
public class LeituraConsumoController {

    private final LeituraConsumoBO bo;

    public LeituraConsumoController(LeituraConsumoBO bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<LeituraResponse> listar(@RequestParam(required = false) Long unidadeId) {
        return bo.listar(unidadeId).stream().map(LeituraResponse::de).toList();
    }

    @GetMapping("/{id}")
    public LeituraResponse buscar(@PathVariable Long id) {
        return LeituraResponse.de(bo.buscar(id));
    }

    @PostMapping
    public ResponseEntity<LeituraResponse> criar(@RequestBody @Valid LeituraRequest req) {
        LeituraResponse criada = LeituraResponse.de(bo.criar(req));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    @PutMapping("/{id}")
    public LeituraResponse atualizar(@PathVariable Long id, @RequestBody @Valid LeituraRequest req) {
        return LeituraResponse.de(bo.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        bo.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
