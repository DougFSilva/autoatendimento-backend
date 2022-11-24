package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

@Repository
public class ComandaSpringDataRepository implements ComandaRepository{

	@Autowired ComandaJpaRepository jpaRepository;

	@Override
	public Comanda criar(Comanda comanda) {
		return jpaRepository.save(comanda);
	}

	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Comanda editar(Long id, Comanda comandaAtualizada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comanda buscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comanda> buscarPorCliente(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comanda> buscarPorData(LocalDateTime dataInicial, LocalDateTime dataFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comanda> buscarPorTipoDePagamento(TipoPagamento tipoPagamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comanda> buscarAbertas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comanda> buscarTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
