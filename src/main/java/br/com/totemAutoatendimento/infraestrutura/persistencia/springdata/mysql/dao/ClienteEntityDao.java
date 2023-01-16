package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ClienteEntity;

@Repository
public interface ClienteEntityDao extends JpaRepository<ClienteEntity, Long> {

	Optional<ClienteEntity> findByCpf(String cpf);

	Page<ClienteEntity> findAllByEnderecoCidade(Pageable paginacao, String cidade);
	
}
