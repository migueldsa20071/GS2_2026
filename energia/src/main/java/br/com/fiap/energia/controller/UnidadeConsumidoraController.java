package br.com.fiap.energia.controller;

import br.com.fiap.energia.bo.UnidadeConsumidoraBO;
import br.com.fiap.energia.dto.UnidadeRequest;
import br.com.fiap.energia.dto.UnidadeResponse;
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
@RequestMapping("/unidades")
public class UnidadeConsumidoraController {

    private final UnidadeConsumidoraBO bo;

    public UnidadeConsumidoraController(UnidadeConsumidoraBO bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<UnidadeResponse> listar(@RequestParam(required = false) String cidade) {
        return bo.listar(cidade).stream().map(UnidadeResponse::de).toList();
    }

    @GetMapping("/{id}")
    public UnidadeResponse buscar(@PathVariable Long id) {
        return UnidadeResponse.de(bo.buscar(id));
    }

    @PostMapping
    public ResponseEntity<UnidadeResponse> criar(@RequestBody @Valid UnidadeRequest req) {
        UnidadeResponse criada = UnidadeResponse.de(bo.criar(req));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    @PutMapping("/{id}")
    public UnidadeResponse atualizar(@PathVariable Long id, @RequestBody @Valid UnidadeRequest req) {
        return UnidadeResponse.de(bo.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        bo.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
