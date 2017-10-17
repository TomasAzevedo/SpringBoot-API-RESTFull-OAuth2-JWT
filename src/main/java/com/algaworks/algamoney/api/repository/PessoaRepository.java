/**
 * 
 */
package com.algaworks.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.api.model.Pessoa;

/**
 * @author Tomás
 *
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
