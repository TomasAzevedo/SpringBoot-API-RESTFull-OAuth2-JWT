/**
 * 
 */
package com.algaworks.algamoney.api.repository.pessoa;

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

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;

/**
 * @author Tomás
 *
 */
public class PessoaRepositoryImpl implements PessoaRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
		
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		Predicate[] predicates = this.criarRestricoes(pessoaFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Pessoa> typedQuery = entityManager.createQuery(criteriaQuery);
		adicionarRestricaoDePaginacao(typedQuery, pageable);
		
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, this.total(pessoaFilter));
	}
	
	
	private Long total(PessoaFilter pessoaFilter) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		Predicate[] predicates = this.criarRestricoes(pessoaFilter, criteriaBuilder, root);
		
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



	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder criteriaBuilder, Root<Pessoa> root) {
		
		List<Predicate> listaPredicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(pessoaFilter.getNome())) {
			listaPredicates.add(criteriaBuilder.like(
									criteriaBuilder.lower(root.get("nome")), 
									"%"+pessoaFilter.getNome()+"%"));
		}
		
		if(!StringUtils.isEmpty(pessoaFilter.getAtivo())) {
			listaPredicates.add(criteriaBuilder.equal(
									root.get("ativo"), 
									pessoaFilter.getAtivo()));
		}
		
		return listaPredicates.toArray(new Predicate[listaPredicates.size()]);
	}

}
