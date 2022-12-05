package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteEntityJpaRepository extends JpaRepository<ClienteEntity, Long> {

	Optional<ClienteEntity> findByCpf(String cpf);

	List<ClienteEntity> findAllByEnderecoCidade(String cidade);

}
