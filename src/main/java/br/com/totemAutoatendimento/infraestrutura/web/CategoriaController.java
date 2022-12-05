package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.RemoverCategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

@RestController
@RequestMapping(value = "/mercadoria/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaRepository repository;
	
	@PostMapping(value = "/{nome}")
	public ResponseEntity<Categoria> criarCategoria(@PathVariable String nome){
		CriarCategoria criarCategoria = new CriarCategoria(repository);
		Categoria categoria = criarCategoria.executar(nome);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removerCategoria(@PathVariable Long id){
		RemoverCategoria removerCategoria = new RemoverCategoria(repository);
		removerCategoria.executar(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Categoria> editarCategoria(@RequestBody Categoria categoria){
		EditarCategoria editarCategoria = new EditarCategoria(repository);
		return ResponseEntity.ok().body(editarCategoria.executar(categoria));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscarCategoria(@PathVariable Long id){
		BuscarCategoria buscarCategoria = new BuscarCategoria(repository);
		return ResponseEntity.ok().body(buscarCategoria.executar(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Categoria>> buscarTodasCategorias(){
		BuscarTodasCategorias buscarTodasCategorias = new BuscarTodasCategorias(repository);
		return ResponseEntity.ok().body(buscarTodasCategorias.executar());
	}
}
