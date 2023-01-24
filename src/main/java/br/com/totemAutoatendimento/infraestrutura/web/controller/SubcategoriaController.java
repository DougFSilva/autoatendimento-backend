package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.imagem.BuscaImagem;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscaSubcategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.CriaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.EditaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.RemoveSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.UploadImagemDaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosDeSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosEditarSubcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/mercadoria/subcategoria")
@SecurityRequirement(name = "api-security")
public class SubcategoriaController {

    @Value("${app.imagens.path}")
    private String pathPastaImagens;

    @Autowired
    private CriaSubcategoria criaSubcategoria;

    @Autowired
    private RemoveSubcategoria removeSubcategoria;

    @Autowired
    private EditaSubcategoria editaSubcategoria;

    @Autowired
    private BuscaSubcategorias buscarSubcategorias;

    @Autowired
    private UploadImagemDaSubcategoria uploadImagemDaSubcategoria;
    
    @Autowired
	private AutenticacaoService autenticacaoService;

    @PostMapping("/{nome}/categoria/{idCategoria}")
    @CacheEvict(value = {"buscarTodasSubcategorias", "buscarSubcategoriasPelaCategoria"}, allEntries = true)
    @Operation(summary = "Criar subcategoria", description = "Cria uma subcategoria para cadastrar as mercadorias")
    public ResponseEntity<Subcategoria> criarSubcategoria(@PathVariable Long idCategoria, @PathVariable String nome) {
        Subcategoria subcategoria = criaSubcategoria.criar(idCategoria, nome, usuarioAutenticado());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}").buildAndExpand(subcategoria.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"buscarTodasSubcategorias", "buscarSubcategoriasPelaCategoria"}, allEntries = true)
    @Operation(summary = "Remover subcategoria", description = "Remove alguma categoria existente")
    public ResponseEntity<Void> removerSubcategoria(@PathVariable Long id) {
        removeSubcategoria.remover(id, usuarioAutenticado());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @CacheEvict(value = {"buscarTodasSubcategorias", "buscarSubcategoriasPelaCategoria"}, allEntries = true)
    @Operation(summary = "Editar subcategoria", description = "Edita alguma categoria existente")
    public ResponseEntity<DadosDeSubcategoria> editarSubcategoria(@PathVariable Long id, @RequestBody @Valid DadosEditarSubcategoria dados) {
    	return ResponseEntity.ok().body(editaSubcategoria.editar(id, dados, usuarioAutenticado()));
    }
    
    @GetMapping("/categoria/{categoriaId}")
    @Cacheable("buscarSubcategoriasPelaCategoria")
    @Operation(summary = "Buscar subcategorias pela categoria", description = "Busca todas subcategorias pertencentes à uma categoria")
    public ResponseEntity<List<DadosDeSubcategoria>> buscarSubcategoriasPelaCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok().body(buscarSubcategorias.buscarPelaCategoria(categoriaId, usuarioAutenticado()));
    }

    @GetMapping
    @Cacheable("buscarTodasSubcategorias")
    @Operation(summary = "Buscar todas subcategorias", description = "Busca todas subcategorias existentes")
    public ResponseEntity<List<Subcategoria>> buscarTodasSubcategorias() {
        return ResponseEntity.ok().body(buscarSubcategorias.buscarTodas());
    }

    @PostMapping("/{id}/imagem")
    @CacheEvict(value = {"buscarTodasSubcategorias", "buscarImagemDaSubcategoria", "buscarSubcategoriasPelaCategoria"}, allEntries = true)
    @Operation(summary = "Adicionar imagem à subcategoria", description = "Adiciona uma imagem em jpg ou png à uma subcategoria existente")
    public ResponseEntity<Void> adicionarImagemASubcategoria(@PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String baseUrlBuscarImagem = ServletUriComponentsBuilder
        		.fromCurrentContextPath()
        		.build()
        		.toUriString()
                + "/mercadoria/subcategoria/imagem/";
        uploadImagemDaSubcategoria.executar(id, file, pathPastaImagens, baseUrlBuscarImagem, usuarioAutenticado());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/imagem/{nomeDaImagem}")
    @Cacheable("buscarImagemDaSubcategoria")
    @Operation(summary = "Buscar imagem", description = "Busca imagem da subcategoria")
    public ResponseEntity<byte[]> buscarImagemDaSubcategoria(@PathVariable String nomeDaImagem) {
        BuscaImagem buscaImagem = new BuscaImagem();
        byte[] imagem = buscaImagem.buscar(pathPastaImagens, Subcategoria.class, nomeDaImagem);
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
		return autenticacaoService.recuperarAutenticado();
	}

}
