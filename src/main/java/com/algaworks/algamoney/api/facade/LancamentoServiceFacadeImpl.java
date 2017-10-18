/**
 * 
 */
package com.algaworks.algamoney.api.facade;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.facade.exception.PessoaInexistenteOuInativaException;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.projection.ResumoLancamento;

/**
 * @author Tomás
 *
 */
@Service
public class LancamentoServiceFacadeImpl implements LancamentoServiceFacade {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	@Override
	public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);		
	}

	
	
	@Override
	public Lancamento buscar(Long codigo) {

		Lancamento lancamento = obterLancamento(codigo);
		
		return lancamento;		
	}


	
	@Override
	public Lancamento salvar(Lancamento lancamento) {
		
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		
		if(null == pessoa || !pessoa.getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}
	


	private Lancamento obterLancamento(Long codigo) {
		
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		
		if(null == lancamento) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return lancamento;
	}



	@Override
	public void excluir(Long codigo) {

		lancamentoRepository.delete(codigo);		
	}



	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {

		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
	
	
	
	@Override
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	
	
	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		}

		if (pessoa == null || pessoa.getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
	

	private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findOne(codigo);
		if (lancamentoSalvo == null) {
			throw new IllegalArgumentException();
		}
		return lancamentoSalvo;
	}




}
