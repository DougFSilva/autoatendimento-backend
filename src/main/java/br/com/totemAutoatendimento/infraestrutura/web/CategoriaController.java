package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarImagem;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.RemoverCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.UploadImagemDaCategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;

@RestController
@RequestMapping(value = "/mercadoria/categoria")
public class CategoriaController {

	@Value("${imagens.path}")
	private String path;

	@Autowired
	private CriarCategoria criarCategoria;

	@Autowired
	private RemoverCategoria removerCategoria;

	@Autowired
	private EditarCategoria editarCategoria;

	@Autowired
	private BuscarTodasCategorias buscarTodasCategorias;

	@Autowired
	private UploadImagemDaCategoria uploadImagemDaCategoria;

	@PostMapping(value = "/{nome}")
	@Transactional
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
	@Transactional
	public ResponseEntity<Categoria> editarCategoria(@RequestBody Categoria categoria) {
		return ResponseEntity.ok().body(editarCategoria.executar(categoria));
	}

	@GetMapping
	public ResponseEntity<Page<Categoria>> buscarTodasCategorias(Pageable paginacao) {
		return ResponseEntity.ok().body(buscarTodasCategorias.executar(paginacao));
	}

	@PostMapping(value = "/{id}/imagem")
	@Transactional
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
	public ResponseEntity<byte[]> buscarImagemDaCategoria(@PathVariable String nomeDaImagem) {
		String extensao = nomeDaImagem.split("\\.")[1];
		BuscarImagem buscarImagem = new BuscarImagem();
		byte[] imagem = buscarImagem.executar(path + "mercadoria/categoria/" + nomeDaImagem);
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
