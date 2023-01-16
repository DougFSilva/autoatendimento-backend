package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.EmailEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Repository
public interface UsuarioEntityDao extends JpaRepository<UsuarioEntity, Long>{

	Optional<UsuarioEntity> findByCpf(String cpf);

	Optional<UsuarioEntity> findByRegistro(String registro);
	
	Optional<UsuarioEntity> findByEmail(EmailEntity email);

}