package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.EmailEntity;

@Repository
public interface UsuarioEntityJpaRepository extends JpaRepository<UsuarioEntity, Long>{

	Optional<UsuarioEntity> findByCpf(String cpf);

	Optional<UsuarioEntity> findByRegistro(String registro);
	
	Optional<UsuarioEntity> findByEmail(EmailEntity email);

}
