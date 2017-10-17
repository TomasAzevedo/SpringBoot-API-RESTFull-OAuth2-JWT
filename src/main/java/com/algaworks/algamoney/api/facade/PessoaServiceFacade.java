/**
 * 
 */
package com.algaworks.algamoney.api.facade;

import java.util.List;

import com.algaworks.algamoney.api.model.Pessoa;

/**
 * @author Tomás
 *
 */
public interface PessoaServiceFacade {
	
	
	public List<Pessoa> listar();
	
	public Pessoa salvar(Pessoa pessoa);
	
	public Pessoa buscar(Long codigo);
	
	public void excluir(Long codigo);
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa);
	
	
	
	

}
