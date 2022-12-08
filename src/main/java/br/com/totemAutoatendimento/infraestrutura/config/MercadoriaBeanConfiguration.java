package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarDadosDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarMercadoriaPorCodigo;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarMercadoriasEmPromocao;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarMercadoriasPorCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarMercadoriasPorSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarTodasMercadorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.CriarMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.EditarMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.RemoverMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.UploadImagemMercadoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.MercadoriaEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria.CategoriaEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria.SubcategoriaEntityRepository;

@Configuration
public class MercadoriaBeanConfiguration {

    @Autowired
    private MercadoriaEntityRepository repository;

    @Autowired
    private CategoriaEntityRepository categoriaRepository;

    @Autowired
    private SubcategoriaEntityRepository subcategoriaRepository;

    @Bean
    public CriarMercadoria criarMercadoria() {
        return new CriarMercadoria(repository, categoriaRepository,subcategoriaRepository);
    }

    @Bean
    public RemoverMercadoria removerMercadoria() {
        return new RemoverMercadoria(repository);
    }

    @Bean
    public EditarMercadoria editarMercadoria() {
        return new EditarMercadoria(repository, categoriaRepository, subcategoriaRepository);
    }

    @Bean
    public BuscarDadosDeMercadoria buscarDadosDeMercadoria() {
        return new BuscarDadosDeMercadoria(repository);
    }

    @Bean
    public BuscarMercadoriaPorCodigo buscarMercadoriaPorCodigo() {
        return new BuscarMercadoriaPorCodigo(repository);
    }

    @Bean
    public BuscarMercadoriasPorCategoria buscarMercadoriasPorCategoria() {
        return new BuscarMercadoriasPorCategoria(repository, categoriaRepository);
    }

    @Bean
    public BuscarMercadoriasPorSubcategoria buscarMercadoriasPorSubcategoria() {
        return new BuscarMercadoriasPorSubcategoria(repository, subcategoriaRepository);
    }

    @Bean
    public BuscarMercadoriasEmPromocao buscarMercadoriasEmPromocao() {
        return new BuscarMercadoriasEmPromocao(repository);
    }

    @Bean
    public BuscarTodasMercadorias buscarTodasMercadorias() {
        return new BuscarTodasMercadorias(repository);
    }

    @Bean
    public UploadImagemMercadoria uploadImagemDeMercadoria() {
        return new UploadImagemMercadoria(repository);
    }

}
