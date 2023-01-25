package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.totemAutoatendimento.aplicacao.imagem.BuscaImagem;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscaDadosDeMercadorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.CriaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.EditaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.RemoveMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.UploadImagemMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosCriarMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosEditarMercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasDeMaiorFaturamento;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasMaisVendidas;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/mercadoria")
@SecurityRequirement(name = "api-security")
public class MercadoriaController {

	@Value("${app.imagens.path}")
	private String pathPastaImagens;

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

	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping
	@CacheEvict(value = { "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Criar mercadoria", description = "Cria uma mercadoria no sistema")
	public ResponseEntity<Mercadoria> criarMercadoria(@RequestBody @Valid DadosCriarMercadoria dados) {
		Mercadoria mercadoria = criaMercadoria.criar(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mercadoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = { "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Remover mercadoria", description = "Remove alguma mercadoria existente")
	public ResponseEntity<Void> removerMercadoria(@PathVariable Long id) {
		removeMercadoria.remover(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@CacheEvict(value = { "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Editar mercadoria", description = "Edita alguma mercadoria existente")
	public ResponseEntity<DadosDeMercadoria> editarMercadoria(@PathVariable Long id,
			@RequestBody @Valid DadosEditarMercadoria dados) {
		return ResponseEntity.ok().body(editaMercadoria.editar(id, dados, usuarioAutenticado()));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar mercadoria", description = "Busca alguma mercadoria pelo id")
	public ResponseEntity<DadosDeMercadoria> buscarMercadoria(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarPeloId(id, usuarioAutenticado()));
	}

	@GetMapping("/codigo/{codigo}")
	@Operation(summary = "Buscar mercadoria por codigo", description = "Busca alguma mercadoria pelo codigo")
	public ResponseEntity<DadosDeMercadoria> buscarMercadoriaPeloCodigo(@PathVariable String codigo) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarPeloCodigo(codigo, usuarioAutenticado()));
	}

	@GetMapping("/subcategoria/{subcategoriaId}")
	@Cacheable("buscarMercadoriasPorSubcategoria")
	@Operation(summary = "Buscar mercadorias por subcategoria", description = "Busca mercadorias pela subcategoria")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarMercadoriasPelaSubcategoria(Pageable paginacao,
			@PathVariable Long subcategoriaId) {
		return ResponseEntity.ok()
				.body(buscaDadosDeMercadorias.buscarPelaSubcategoria(paginacao, subcategoriaId, usuarioAutenticado()));
	}

	@GetMapping("/com-promocao")
	@Cacheable("buscarMercadoriasEmPromocao")
	@Operation(summary = "Buscar mercadorias em promoção", description = "Busca mercadorias que estão em promoção")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarMercadoriasEmPromocao(Pageable paginacao) {
		return ResponseEntity.ok()
				.body(buscaDadosDeMercadorias.buscarEmPromocao(paginacao, true, usuarioAutenticado()));
	}

	@GetMapping("/sem-promocao")
	@Cacheable("buscarMercadoriasSemPromocao")
	@Operation(summary = "Buscar mercadorias sem promoção", description = "Busca mercadorias que estão sem promoção")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarMercadoriasSemPromocao(Pageable paginacao) {
		return ResponseEntity.ok()
				.body(buscaDadosDeMercadorias.buscarEmPromocao(paginacao, false, usuarioAutenticado()));
	}

	@GetMapping("/relatorio/mais-vendidas/data/{dataInicial}/{dataFinal}")
	@Operation(summary = "Buscar mercadorias mais vendidas pela data", description = "Busca as mercadorias mais vendidas pela data inicial e data final")
	public ResponseEntity<Page<RelatorioMercadoriasMaisVendidas>> buscarMercadoriasMaisVendidasPelaData(
			Pageable paginacao, @PathVariable String dataInicial, @PathVariable String dataFinal) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarMercadoriasMaisVendidasPelaData(paginacao,
				LocalDate.parse(dataInicial), LocalDate.parse(dataFinal), usuarioAutenticado()));
	}
	
	@GetMapping("/relatorio/maior-faturamento/data/{dataInicial}/{dataFinal}")
	@Operation(summary = "Buscar mercadorias com maior faturamento pela data", description = "Busca as mercadorias com maior faturamento pela data inicial e data final")
	public ResponseEntity<Page<RelatorioMercadoriasDeMaiorFaturamento>> buscarMercadoriasComMaiorFaturamentoPelaData(
			Pageable paginacao, @PathVariable String dataInicial, @PathVariable String dataFinal) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarMercadoriasDeMaiorFaturamentoPelaData(paginacao,
				LocalDate.parse(dataInicial), LocalDate.parse(dataFinal), usuarioAutenticado()));
	}

	@GetMapping
	@Cacheable("buscarTodasMercadorias")
	@Operation(summary = "Buscar todas mercadorias", description = "Busca todas mercadorias existentes")
	public ResponseEntity<Page<DadosDeMercadoria>> buscarTodasMercadorias(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeMercadorias.buscarTodas(paginacao, usuarioAutenticado()));
	}

	@PostMapping(value = "/{id}/imagem")
	@CacheEvict(value = { "buscarImagemDaMercadoria", "buscarMercadoriasPorSubcategoria", "buscarMercadoriasEmPromocao",
			"buscarMercadoriasSemPromocao", "buscarTodasMercadorias" }, allEntries = true)
	@Operation(summary = "Adicionar imagem à mercadoria", description = "Adiciona uma imagem em formato jpg ou png a alguma mercadoria existente")
	public ResponseEntity<Void> adicionarImagemAMercadoria(@PathVariable Long id,
			@RequestParam("file") MultipartFile file) {
		String baseUrlBuscarImagem = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
				+ "/mercadoria/imagem/";
		uploadImagemDeMercadoria.executar(id, file, pathPastaImagens, baseUrlBuscarImagem, usuarioAutenticado());
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/imagem/{nomeDaImagem}")
	@Cacheable("buscarImagemDaMercadoria")
	@Operation(summary = "Buscar imagem da mercadoria", description = "Busca imagem da mercadoria")
	public ResponseEntity<byte[]> buscarImagemDaMercadoria(@PathVariable String nomeDaImagem) {
		BuscaImagem buscaImagem = new BuscaImagem();
		byte[] imagem = buscaImagem.buscar(pathPastaImagens, Mercadoria.class, nomeDaImagem);
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
