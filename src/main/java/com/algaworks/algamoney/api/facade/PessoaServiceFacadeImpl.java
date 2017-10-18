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

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;

/**
 * @author Tomás
 *
 */
@Service
public class PessoaServiceFacadeImpl implements PessoaServiceFacade {

	@Autowired
	private PessoaRepository pessoaRepository;

	
	
	@Override
	public Page<Pessoa> listar(PessoaFilter pessoaFilter, Pageable pageable) {
		
		return pessoaRepository.filtrar(pessoaFilter, pageable);
		
	}
	
	

	@Override
	public Pessoa salvar(Pessoa pessoa) {

		return pessoaRepository.save(pessoa);
		
	}
	
	

	@Override
	public Pessoa buscar(Long codigo) {
		
		Pessoa pessoaBanco = obterPessoa(codigo);
		
		return pessoaBanco;
		
	}

	

	@Override
	public void excluir(Long codigo) {
		
		pessoaRepository.delete(codigo);
		
	}
	
	

	@Override
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		
		Pessoa pessoaBanco = obterPessoa(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaBanco, "codigo");
		
		return pessoaRepository.save(pessoaBanco);
	}
	
	
	
	private Pessoa obterPessoa(Long codigo) {
		
		Pessoa pessoaBanco = pessoaRepository.findOne(codigo);
		
		if(null == pessoaBanco) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return pessoaBanco;
		
	}
	
	
}
