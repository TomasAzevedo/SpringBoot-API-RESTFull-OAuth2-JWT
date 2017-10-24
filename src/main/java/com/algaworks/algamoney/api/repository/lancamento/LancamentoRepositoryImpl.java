/**
 * 
 */
package com.algaworks.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.projection.ResumoLancamento;

/**
 * @author Tomás
 *
 */
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = this.criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Lancamento> typedQuery = entityManager.createQuery(criteriaQuery);
		adicionarRestricaoDePaginacao(typedQuery, pageable);
		
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, this.total(lancamentoFilter));
	}
	
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get("codigo"), root.get("descricao")
				, root.get("dataVencimento"), root.get("dataPagamento")
				, root.get("valor"), root.get("tipo")
				, root.get("categoria").get("nome")
				, root.get("pessoa").get("nome")));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = entityManager.createQuery(criteria);
		adicionarRestricaoDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}



	private Long total(LancamentoFilter lancamentoFilter) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = this.criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		
		criteriaQuery.where(predicates);
		
		criteriaQuery.select(criteriaBuilder.count(root));
		
		return entityManager.createQuery(criteriaQuery).getSingleResult();
		
	}



	private void adicionarRestricaoDePaginacao(TypedQuery<?> typedQuery, Pageable pageable) {
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		typedQuery.setFirstResult(primeiroRegistroDaPagina);
		typedQuery.setMaxResults(totalRegistroPorPagina);
	}



	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder criteriaBuilder, Root<Lancamento> root) {
		
		List<Predicate> listaPredicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			listaPredicates.add(criteriaBuilder.like(
									criteriaBuilder.lower(root.get("descricao")), 
									"%"+lancamentoFilter.getDescricao()+"%"));
		}
		
		if(null != lancamentoFilter.getDataVencimentoDe()) {
			listaPredicates.add(criteriaBuilder.greaterThanOrEqualTo(
									root.get("dataVencimento"), 
									lancamentoFilter.getDataVencimentoDe()));
		}
		
		if(null != lancamentoFilter.getDataVencimentoAte()) {
			listaPredicates.add(criteriaBuilder.lessThanOrEqualTo(
									root.get("dataVencimento"), 
									lancamentoFilter.getDataVencimentoAte()));
		}
		
		return listaPredicates.toArray(new Predicate[listaPredicates.size()]);
	}

}
