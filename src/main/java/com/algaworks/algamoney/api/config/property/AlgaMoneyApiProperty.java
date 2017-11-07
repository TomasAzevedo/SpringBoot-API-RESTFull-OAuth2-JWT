/**
 * 
 */
package com.algaworks.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Tomás
 *
 */
@ConfigurationProperties("algamoney")
public class AlgaMoneyApiProperty {
	
	private final Seguranca seguranca = new Seguranca();
	
	private String origemPermitida = "http://localhost:4200";
	
	public Seguranca getSeguranca() {
		return seguranca;
	}	

	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}



	public static class Seguranca {
		
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
		
	}
	

}
