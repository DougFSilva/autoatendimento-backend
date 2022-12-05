package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscarTodasSubcategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.CriarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.EditarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.RemoverSubcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@RestController
@RequestMapping(value = "mercadoria/subcategoria")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaRepository repository;

    @PostMapping(value = "/{nome}")
    public ResponseEntity<Subcategoria> criarSubcategoria(@PathVariable String nome) {
        CriarSubcategoria criarSubcategoria = new CriarSubcategoria(repository);
        Subcategoria subcategoria = criarSubcategoria.executar(nome);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}").buildAndExpand(subcategoria.getId())
                .toUri();
                return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removerSubcategoria(@PathVariable Long id){
        RemoverSubcategoria removerSubcategoria = new RemoverSubcategoria(repository);
        removerSubcategoria.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Subcategoria> editarSubcategoria(@RequestBody Subcategoria subcategoriaAtualizada){
        EditarSubcategoria editarSubcategoria = new EditarSubcategoria(repository);
        return ResponseEntity.ok().body(editarSubcategoria.executar(subcategoriaAtualizada));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Subcategoria> buscarSubcategoria(@PathVariable Long id){
        BuscarSubcategoria buscarSubcategoria = new BuscarSubcategoria(repository);
        return ResponseEntity.ok().body(buscarSubcategoria.executar(id));
    }

    @GetMapping
    public ResponseEntity<Page<Subcategoria>> buscarTodasSubcategorias(Pageable paginacao){
        BuscarTodasSubcategorias buscarTodasSubcategorias = new BuscarTodasSubcategorias(repository);
        return ResponseEntity.ok().body(buscarTodasSubcategorias.executar(paginacao));
    }

}
