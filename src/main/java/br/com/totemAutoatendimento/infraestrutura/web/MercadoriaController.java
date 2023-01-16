package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
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

import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscaDadosDeMercadorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscaImagem;
import br.com.totemAutoatendimento.aplicacao.mercadoria.CriaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.EditaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.RemoveMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.UploadImagemMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosCriarMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosEditarMercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/mercadoria")
@EnableCaching
public class MercadoriaController {

	@Value("${imagens.path}")
	private String path;

	@Autowired
	private CriaMercadoria criaMercadoria;

	@Autowired
	private RemoveMercadoria removeMercadoria;

	@Autowired
	private EditaMercadoria editaMercadoria;

	@Autowired
	private BuscaDadosDeMercadorias buscaDadosDeMercadorias;

	@Autowired
	private UploadImagemMercadoria uploadImagemDeMercadoria;

	@PostMapping
	@CacheEvict(value = { "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Criar mercadoria", description = "Cria uma mercadoria no sistema")
	public ResponseEntity<Mercadoria> criarMercadoria(@RequestBody @Valid DadosCriarMercadoria dados) {
		Mercadoria mercadoria = criaMercadoria.criar(dados);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mercadoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	@CacheEvict(value = { "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Remover mercadoria", description = "Remove alguma mercadoria existente")
	public ResponseEntity<Void> removerMercadoria(@PathVariable Long id) {
		removeMercadoria.remover(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	@CacheEvict(value = { "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Editar mercadoria", description = "Edita alguma mercadoria existente")
	public ResponseEntity<DadosDeMercadoria> editarMercadoria(@RequestBody @Valid DadosEditarMercadoria dados) {
		return ResponseEntity.ok().body(editaMercadoria.editar(dados));
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar mercadoria", description = "Busca alguma mercadoria pelo id")
	public ResponseEntity<DadosDeMercadoria> buscarMercadoria(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarPeloId(id));
	}

	@GetMapping(value = "/codigo/{codigo}")
	@Operation(summary = "Buscar mercadoria por codigo", description = "Busca alguma mercadoria pelo codigo")
	public ResponseEntity<DadosDeMercadoria> buscarMercadoriaPeloCodigo(@PathVariable String codigo) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarPeloCodigo(codigo));
	}

	@GetMapping(value = "/subcategoria/{subcategoria}")
	@Cacheable(value = "buscarMercadoriasPorSubcategoria")
	@Operation(summary = "Buscar mercadorias por subcategoria", description = "Busca mercadorias pela subcategoria")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarMercadoriasPelaSubcategoria(Pageable paginacao,
			@PathVariable Long subcategoriaId) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarPelaSubcategoria(paginacao, subcategoriaId));
	}

	@GetMapping(value = "/com-promocao")
	@Cacheable(value = "buscarMercadoriasEmPromocao")
	@Operation(summary = "Buscar mercadorias em promoção", description = "Busca mercadorias que estão em promoção")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarMercadoriasEmPromocao(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarEmPromocao(paginacao, true));
	}

	@GetMapping(value = "/sem-promocao")
	@Cacheable(value = "buscarMercadoriasSemPromocao")
	@Operation(summary = "Buscar mercadorias sem promoção", description = "Busca mercadorias que estão sem promoção")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarMercadoriasSemPromocao(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarEmPromocao(paginacao, false));
	}

	@GetMapping
	@Cacheable(value = "buscarTodasMercadorias")
	@Operation(summary = "Buscar todas mercadorias", description = "Busca todas mercadorias existentes")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarTodasMercadorias(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarTodas(paginacao));
	}

	@PostMapping(value = "/{id}/imagem")
	@CacheEvict(value = { "buscarImagemDaMercadoria", "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Adicionar imagem à mercadoria", description = "Adiciona uma imagem em formato jpg ou png a alguma mercadoria existente")
	public ResponseEntity<Void> adicionarImagemAMercadoria(@PathVariable Long id,
			@RequestParam("file") MultipartFile file) {
		String nomeDaImagem = id + "-" + file.getOriginalFilename();
		String pathLocal = this.path + "/mercadoria/" + nomeDaImagem;
		String urlServidor = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
				+ "/mercadoria/imagem/" + nomeDaImagem;

		uploadImagemDeMercadoria.executar(id, file, pathLocal, urlServidor);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/imagem/{nomeDaImagem}")
	@Cacheable(value = "buscarImagemDaMercadoria")
	@Operation(summary = "Buscar imagem da mercadoria", description = "Busca imagem da mercadoria")
	public ResponseEntity<byte[]> buscarImagemDaMercadoria(@PathVariable String nomeDaImagem) {
		String extensao = nomeDaImagem.split("\\.")[1];
		BuscaImagem buscarImagem = new BuscaImagem();
		byte[] imagem = buscarImagem.buscar(path + "mercadoria/" + nomeDaImagem);
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
