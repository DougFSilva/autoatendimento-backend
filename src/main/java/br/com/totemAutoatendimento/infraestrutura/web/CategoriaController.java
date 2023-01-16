package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscaImagem;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.RemoveCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.UploadImagemDaCategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/mercadoria/categoria")
@EnableCaching
public class CategoriaController {

	@Value("${imagens.path}")
	private String path;

	@Autowired
	private CriaCategoria criaCategoria;

	@Autowired
	private RemoveCategoria removeCategoria;

	@Autowired
	private EditaCategoria editaCategoria;

	@Autowired
	private BuscaTodasCategorias buscaTodasCategorias;

	@Autowired
	private UploadImagemDaCategoria uploadImagemDaCategoria;

	@PostMapping(value = "/{nome}")
	@CacheEvict(value = "buscarTodasCategorias", allEntries = true)
	@Operation(summary = "Criar categoria", description = "Cria uma categoria para cadastrar mercadorias")
	public ResponseEntity<Categoria> criaCategoria(@PathVariable String nome) {
		Categoria categoria = criaCategoria.criar(nome);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	@CacheEvict(value = "buscarTodasCategorias", allEntries = true)
	@Operation(summary = "Remover categoria", description = "Remove alguma categoria existente")
	public ResponseEntity<Void> removerCategoria(@PathVariable Long id) {
		removeCategoria.remover(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}/nome/{nome}")
	@CacheEvict(value = "buscarTodasCategorias", allEntries = true)
	@Operation(summary = "Editar categoria", description = "Edita alguma categoria existente")
	public ResponseEntity<Categoria> editarCategoria(@PathVariable Long id, @PathVariable String nome) {
		return ResponseEntity.ok().body(editaCategoria.editar(id, nome));
	}

	@GetMapping
	@Cacheable(value = "buscarTodasCategorias")
	@Operation(summary = "Buscar todas categorias", description = "Busca todas categorias existentes")
	public ResponseEntity<List<Categoria>> buscarTodasCategorias() {
		return ResponseEntity.ok().body(buscaTodasCategorias.buscar());
	}

	@PostMapping(value = "/{id}/imagem")
	@CacheEvict(value = {"buscarTodasCategorias","buscarImagemDaCategoria"}, allEntries = true)
	@Operation(summary = "Adicionar imagem", description = "Adiciona uma imagem em formato jgp ou png a alguma categoria existente")
	public ResponseEntity<Void> adicionarImagemACategoria(@PathVariable Long id,
			@RequestParam("file") MultipartFile file) {
		String nomeDaImagem = id + "-" + file.getOriginalFilename();
		String pathLocal = this.path + "/mercadoria/categoria/" + nomeDaImagem;
		String urlServidor = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
				+ "/mercadoria/categoria/imagem/" + nomeDaImagem;
		uploadImagemDaCategoria.executar(id, file, pathLocal, urlServidor);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/imagem/{nomeDaImagem}")
	@Cacheable(value = "buscarImagemDaCategoria")
	@Operation(summary = "Buscar imagem", description = "Busca imagem da categoria")
	public ResponseEntity<byte[]> buscarImagemDaCategoria(@PathVariable String nomeDaImagem) {
		String extensao = nomeDaImagem.split("\\.")[1];
		BuscaImagem buscarImagem = new BuscaImagem();
		byte[] imagem = buscarImagem.buscar(path + "mercadoria/categoria/" + nomeDaImagem);
		HttpHeaders httpHeaders = new HttpHeaders();
		switch (extensao.toLowerCase()) {
			case "jpg":
				httpHeaders.setContentType(MediaType.IMAGE_JPEG);
				break;
			case "png":
				httpHeaders.setContentType(MediaType.IMAGE_PNG);
				break;
		}
		return ResponseEntity.ok().headers(httpHeaders).body(imagem);
	}
}
