package dto;

import model.Contato;

public class ContatoDTO {
	
	public Long id;
	public String nome;
	public String email;
	public boolean marcado;
	
	public ContatoDTO() {}
	
	public ContatoDTO(Contato contato) {
		this.id = contato.getId();
		this.nome = contato.getNome();
		this.email = contato.getEmail();
		this.marcado = contato.isMarcado();
		
	}
	
	

}
