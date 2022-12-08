package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditarCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.RemoverCategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria.CategoriaEntityRepository;

@Configuration
public class CategoriaBeanConfiguration {
    
    @Autowired
    private CategoriaEntityRepository repository;

    @Bean
    public CriarCategoria criarCategoria() {
        return new CriarCategoria(repository);
    }

    @Bean
    public RemoverCategoria removerCategoria() {
        return new RemoverCategoria(repository);
    }

    @Bean
    public EditarCategoria editarCategoria() {
        return new EditarCategoria(repository);
    }

    @Bean
    public BuscarTodasCategorias buscarTodasCategorias() {
        return new BuscarTodasCategorias(repository);
    }

}
