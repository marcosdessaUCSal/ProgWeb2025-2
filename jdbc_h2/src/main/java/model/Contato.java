package model;

public class Contato {
	
	private Long id;
	private String nome;
	private String tel;
	
	// CONTRUTORES
	public Contato() {}

	public Contato(String nome, String tel) {
		this.nome = nome;
		this.tel = tel;
	}

	
	// GETTERS & SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "Contato [id=" + id + ", nome=" + nome + ", tel=" + tel + "]";
	}

	
	
	

	
	
	
	

}
