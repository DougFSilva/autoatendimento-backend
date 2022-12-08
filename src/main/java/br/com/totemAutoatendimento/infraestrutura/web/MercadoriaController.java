package br.com.totemAutoatendimento.infraestrutura.web;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.totemAutoatendimento.dominio.mercadoria.DadosCriarMercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@RestController
@RequestMapping(value = "/mercadoria")
public class MercadoriaController {
    
    @Autowired
    private MercadoriaRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Mercadoria> criarMercadoria(@RequestBody @Valid DadosCriarMercadoria dados){
        return null;
    }
}
