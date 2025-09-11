package model;

import java.io.Serializable;

import dto.TarefaDTO;

public class Tarefa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String txt;
	private boolean marcado;
	
	public Tarefa() {}

	public Tarefa(Long id, String txt, boolean marcado) {
		super();
		this.id = id;
		this.txt = txt;
		this.marcado = marcado;
	}
	
	public Tarefa(TarefaDTO dto) {
		this.id = dto.id;
		this.txt = dto.txt;
		this.marcado = dto.marcado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	
	

}
