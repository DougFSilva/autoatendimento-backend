package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@Repository
public class SubcategoriaEntityRepository implements SubcategoriaRepository{

    @Autowired
    private SubcategoriaEntityJpaRepository repository;

    @Override
    public Subcategoria criar(Subcategoria subcategoria) {
        return repository.save(new SubcategoriaEntity(subcategoria)).converterParaSubcategoria();
    }

    @Override
    public void remover(Subcategoria subcategoria) {
        repository.delete(new SubcategoriaEntity(subcategoria));
    }

    @Override
    public Subcategoria editar(Long id, Subcategoria subcategoriaAtualizada) {
        return repository.save(new SubcategoriaEntity(subcategoriaAtualizada)).converterParaSubcategoria();
    }

    @Override
    public Optional<Subcategoria> buscar(Long id) {
        Optional<SubcategoriaEntity> entity = repository.findById(id);
        if(entity.isPresent()){
            return Optional.of(entity.get().converterParaSubcategoria());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Subcategoria> buscarPorNome(String nome) {
        Optional<SubcategoriaEntity> entity = repository.findByNome(nome);
        if(entity.isPresent()){
            return Optional.of(entity.get().converterParaSubcategoria());
        }
        return Optional.empty();
    }

    @Override
    public Page<Subcategoria> buscarTodas(Pageable paginacao) {
        return repository.findAll(paginacao).map(SubcategoriaEntity::converterParaSubcategoria);
    }
    
}
