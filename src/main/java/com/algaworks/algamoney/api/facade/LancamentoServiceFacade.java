/**
 * 
 */
package com.algaworks.algamoney.api.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.projection.ResumoLancamento;

/**
 * @author Tomás
 *
 */
public interface LancamentoServiceFacade {
	
	
	public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	
	public Lancamento buscar(Long codigo);
	
	
	public Lancamento salvar(Lancamento lancamento);


	public void excluir(Long codigo);


	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
