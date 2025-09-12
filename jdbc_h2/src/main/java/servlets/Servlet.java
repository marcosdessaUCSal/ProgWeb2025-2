package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import dao.ContatosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Contato;

@WebServlet("/contatos")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Servlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ContatosDAO dao = new ContatosDAO();
		try {
			ArrayList<Contato> contatos = (ArrayList<Contato>) dao.getContatos();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter saida = response.getWriter();
			saida.print(contatos.toString());
			saida.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
