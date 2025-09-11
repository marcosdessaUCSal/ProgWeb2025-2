package dto;

import model.Tarefa;

public class TarefaDTO {
	
	public Long id;
	public String txt;
	public boolean marcado;
	
	public TarefaDTO() {}
	
	public TarefaDTO(Tarefa tarefa) {
		this.id = tarefa.getId();
		this.txt = tarefa.getTxt();
		this.marcado = tarefa.isMarcado();
	}
}
