package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria.CategoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria.SubcategoriaEntity;

@Repository
public class MercadoriaEntityRepository implements MercadoriaRepository {

    @Autowired
    private MercadoriaEntityJpaRepository repository;

    @Override
    public Mercadoria criar(Mercadoria mercadoria) {
        return repository.save(new MercadoriaEntity(mercadoria)).converterParaMercadoria();
    }

    @Override
    public void remover(Mercadoria mercadoria) {
        repository.delete(new MercadoriaEntity(mercadoria));
    }

    @Override
    public Mercadoria editar(Mercadoria mercadoriaAtualizada) {
        return repository.save(new MercadoriaEntity(mercadoriaAtualizada)).converterParaMercadoria();
    }

    @Override
    public Optional<Mercadoria> buscar(Long id) {
        Optional<MercadoriaEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            return Optional.of(entity.get().converterParaMercadoria());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Mercadoria> buscarPorCodigo(String codigo){
        Optional<MercadoriaEntity> entity = repository.findByCodigo(codigo);
        if (entity.isPresent()) {
            return Optional.of(entity.get().converterParaMercadoria());
        }
        return Optional.empty();
    }

    @Override
    public Page<Mercadoria> buscarPorCategoria(Pageable paginacao, Categoria categoria) {
        return repository.findAllByCategoria(paginacao, new CategoriaEntity(categoria))
                .map(MercadoriaEntity::converterParaMercadoria);
    }

    @Override
    public Page<Mercadoria> buscarPorSubcategoria(Pageable paginacao, Subcategoria subcatergoria) {
        return repository.findAllBySubcategoria(paginacao, new SubcategoriaEntity(subcatergoria))
                .map(MercadoriaEntity::converterParaMercadoria);
    }

    @Override
    public Page<Mercadoria> buscarEmPromocao(Pageable paginacao, Boolean promocao) {
        return repository.findAllByPromocao(paginacao, promocao).map(MercadoriaEntity::converterParaMercadoria);
    }

    @Override
    public Page<Mercadoria> buscarTodas(Pageable paginacao) {
        return repository.findAll(paginacao).map(MercadoriaEntity::converterParaMercadoria);
    }

}
