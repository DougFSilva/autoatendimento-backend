package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.cliente;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteEntityJpaRepository extends JpaRepository<ClienteEntity, Long> {

	Optional<ClienteEntity> findByCpf(String cpf);

	Page<ClienteEntity> findAllByEnderecoCidade(Pageable paginacao, String cidade);
	
}
