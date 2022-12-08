package br.com.totemAutoatendimento.infraestrutura.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.RemoverCategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;

@RestController
@RequestMapping(value = "/mercadoria/categoria")
public class CategoriaController {

	@Autowired
	private CriarCategoria criarCategoria;

	@Autowired
	private RemoverCategoria removerCategoria;

	@Autowired
	private EditarCategoria editarCategoria;

	@Autowired
	private BuscarTodasCategorias buscarTodasCategorias;

	@PostMapping(value = "/{nome}")
	public ResponseEntity<Categoria> criarCategoria(@PathVariable String nome) {
		Categoria categoria = criarCategoria.executar(nome);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removerCategoria(@PathVariable Long id) {
		removerCategoria.executar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<Categoria> editarCategoria(@RequestBody Categoria categoria) {
		return ResponseEntity.ok().body(editarCategoria.executar(categoria));
	}

	@GetMapping
	public ResponseEntity<Page<Categoria>> buscarTodasCategorias(Pageable paginacao) {
		return ResponseEntity.ok().body(buscarTodasCategorias.executar(paginacao));
	}

	@GetMapping(value = "/imagem/{nomeDaImagem}")
	public ResponseEntity<byte[]> buscarImagem(@PathVariable String nomeDaImagem) {
		FileInputStream fis;
		byte[] imagem = null;
		try {
			fis = new FileInputStream("C:/Users/dougl/Documents/Fotos/" + nomeDaImagem);
			try {
				imagem = fis.readAllBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagem);
	}
}
