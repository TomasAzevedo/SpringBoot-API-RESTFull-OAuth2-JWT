/**
 * 
 */
package com.algaworks.algamoney.api.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;

/**
 * @author Tomás
 *
 */
public interface PessoaServiceFacade {
	
	
	public Page<Pessoa> listar(PessoaFilter pessoaFilter, Pageable pageable);
	
	public Pessoa salvar(Pessoa pessoa);
	
	public Pessoa buscar(Long codigo);
	
	public void excluir(Long codigo);
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa);
	
	
	
	

}
