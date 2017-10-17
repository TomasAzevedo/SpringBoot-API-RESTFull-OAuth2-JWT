/**
 * 
 */
package com.algaworks.algamoney.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.api.model.Usuario;

/**
 * @author Tomás
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	
	public Optional<Usuario> findByEmail(String email);

}
