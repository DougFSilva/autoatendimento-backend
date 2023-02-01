package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.totemAutoatendimento.aplicacao.imagem.BuscaImagem;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.DeletaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.UploadImagemDaCategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/mercadoria/categoria")
@SecurityRequirement(name = "api-security")
public class CategoriaController {

	@Value("${app.imagens.path}")
	private String pathPastaImagens;

	@Autowired
	private CriaCategoria criaCategoria;

	@Autowired
	private DeletaCategoria deletaCategoria;

	@Autowired
	private EditaCategoria editaCategoria;

	@Autowired
	private BuscaTodasCategorias buscaTodasCategorias;

	@Autowired
	private UploadImagemDaCategoria uploadImagemDaCategoria;
	
	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping("/{nome}")
	@CacheEvict(value = "buscarTodasCategorias", allEntries = true)
	@Operation(summary = "Criar categoria", description = "Cria uma categoria para cadastrar mercadorias")
	public ResponseEntity<Categoria> criaCategoria(@PathVariable String nome) {
		Categoria categoria = criaCategoria.criar(nome, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "buscarTodasCategorias", allEntries = true)
	@Operation(summary = "Deletar categoria", description = "Remove uma categoria existente no sistema")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
		deletaCategoria.deletar(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/nome/{nome}")
	@CacheEvict(value = "buscarTodasCategorias", allEntries = true)
	@Operation(summary = "Editar categoria", description = "Edita uma categoria existente no sistema")
	public ResponseEntity<Categoria> editarCategoria(@PathVariable Long id, @PathVariable String nome) {
		return ResponseEntity.ok().body(editaCategoria.editar(id, nome, usuarioAutenticado()));
	}

	@GetMapping
	@Cacheable("buscarTodasCategorias")
	@Operation(summary = "Buscar todas categorias", description = "Busca todas categorias existentes no sistema")
	public ResponseEntity<List<Categoria>> buscarTodasCategorias() {
		return ResponseEntity.ok().body(buscaTodasCategorias.buscar(usuarioAutenticado()));
	}

	@PostMapping("/{id}/imagem")
	@CacheEvict(value = {"buscarTodasCategorias","buscarImagemDaCategoria"}, allEntries = true)
	@Operation(summary = "Adicionar imagem", description = "Adiciona uma imagem em formato jgp ou png a alguma categoria existente")
	public ResponseEntity<Void> adicionarImagemACategoria(@PathVariable Long id,
			@RequestParam("file") MultipartFile file) {
		String baseUrlBuscarImagem = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.build()
				.toUriString()
				+ "/mercadoria/categoria/imagem/";
		uploadImagemDaCategoria.upload(id, file, pathPastaImagens, baseUrlBuscarImagem, usuarioAutenticado());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/imagem/{nomeDaImagem}")
	@Cacheable("buscarImagemDaCategoria")
	@Operation(summary = "Buscar imagem", description = "Busca imagem da categoria")
	public ResponseEntity<byte[]> buscarImagemDaCategoria(@PathVariable String nomeDaImagem) {
		BuscaImagem buscaImagem = new BuscaImagem();
		byte[] imagem = buscaImagem.buscar(pathPastaImagens, Categoria.class, nomeDaImagem);
		HttpHeaders httpHeaders = new HttpHeaders();
		String extensao = nomeDaImagem.split("\\.")[1];
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
	
	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}
}
