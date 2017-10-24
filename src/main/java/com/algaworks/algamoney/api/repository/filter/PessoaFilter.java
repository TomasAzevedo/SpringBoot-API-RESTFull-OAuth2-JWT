/**
 * 
 */
package com.algaworks.algamoney.api.repository.filter;

/**
 * @author Tomás
 *
 */
public class PessoaFilter {

	private String nome;

	private Boolean ativo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
