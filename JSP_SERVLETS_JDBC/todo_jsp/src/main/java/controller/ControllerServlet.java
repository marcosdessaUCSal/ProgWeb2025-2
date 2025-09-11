package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.Db;
import dto.TarefaDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tarefa;

@WebServlet(urlPatterns = { "/tarefas", "/marcaTudo", "/desmarcaTudo", "/removeMarcados", "/inverteMarcado/*",
		"/delete/*", "/add", "/reset" })
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ControllerServlet() {
		super();
	}

	// GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getServletPath();
		if (acao.isBlank() || acao.isEmpty())
			return;
		if (acao.equals("/tarefas")) {
			findAll(request, response);
		}
	}

	// POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getServletPath();
		if (acao.isBlank() || acao.isEmpty())
			return;
		if (acao.equals("/add")) {
			add(request, response);
		}
	}

	// PUT
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getServletPath();
		if (acao.isBlank() || acao.isEmpty())
			return;
		Db db = Db.getInstance();
		switch (acao) {
		case "/inverteMarcado":
			Long id = Long.parseLong(request.getPathInfo().substring(1));
			db.inverteMarcado(id);
			break;
		case "/marcaTudo":
			db.marcarOuDesmarcarTudo(true);
			break;
		case "/desmarcaTudo":
			db.marcarOuDesmarcarTudo(false);
			break;
		case "/reset":
			db.resetTarefas();
			break;
		}
	}
	
	// DELETE
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getServletPath();
		if (acao.isBlank() || acao.isEmpty())
			return;
		Db db = Db.getInstance();
		switch(acao) {
		case "/delete":
			Long id = Long.parseLong(request.getPathInfo().substring(1));
			db.deleteById(id);
			break;
		case "/removeMarcados":
			db.removeMarcados();
			break;
		}
	}

	/*
	 ************ MÉTODOS PRIVADOS ***********
	 */

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		try {
			// obtendo os dtos
			List<Tarefa> tarefas = Db.getInstance().getTarefas();
			List<TarefaDTO> dtos = new ArrayList<>();
			for (Tarefa t : tarefas) {
				dtos.add(new TarefaDTO(t));
			}

			// obtendo o JSON para response
			Gson gson = new Gson();
			String json = gson.toJson(dtos);

			// configurando a response
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter saida = response.getWriter();
			saida.print(json);
			saida.flush();

		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
	}


	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			// obtendo o código da request
			BufferedReader reader = request.getReader();
			Gson gson = new Gson();
			TarefaDTO dto = gson.fromJson(reader, TarefaDTO.class);

			// efetuando o registro
			Db.getInstance().addTarefa(new Tarefa(dto));
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
	}
	
	

}
