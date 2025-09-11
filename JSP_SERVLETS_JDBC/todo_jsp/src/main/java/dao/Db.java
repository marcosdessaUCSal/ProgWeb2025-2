package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Tarefa;

public class Db {
	
	private static Db instance = null;

	private final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private final String USER = "sa";
	private final String PASSWORD = "";
	
	private Db() {
		criarTabela();
		resetTarefas();
	}
	
	public static Db getInstance() {
		if (instance == null) {
			instance = new Db();
		}
		return instance;
	}
	
	public void criarTabela() {
		StringBuilder sb = new StringBuilder();
		sb
		.append("CREATE TABLE TAREFA (")
		.append("	ID BIGINT AUTO_INCREMENT PRIMARY KEY,")
		.append("	TXT VARCHAR(40),")
		.append("	MARCADO BOOLEAN")
		.append(")");
		String query = sb.toString();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement st = conn.createStatement();
			st.execute(query);
			st.close();
			conn.close();
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
	}
	
	
	/*
	 * ****************************************************************
	 * CRUD
	 * ****************************************************************
	 */

	// CRUD READ
	
	public List<Tarefa> getTarefas() {
		String query = "SELECT ID, TXT, MARCADO FROM TAREFA";
		List<Tarefa> tarefas = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getLong("ID"));
				tarefa.setTxt(rs.getString("TXT"));
				tarefa.setMarcado(rs.getBoolean("MARCADO"));
				tarefas.add(tarefa);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
		return tarefas;
	}
	
	public Tarefa findTarefaById(Long id) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT ID, TXT, MARCADO ")
		.append("	FROM TAREFA ")
		.append("	WHERE ID = ?");
		String query = sb.toString();
		Tarefa tarefa = null;
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				tarefa = new Tarefa();
				tarefa.setId(rs.getLong("ID"));
				tarefa.setTxt(rs.getString("TXT"));
				tarefa.setMarcado(rs.getBoolean("MARCADO"));
				rs.close();
				pst.close();
				conn.close();
				return tarefa;
			}
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
		return tarefa;
	}
	
	// CRUD CREATE
	
	public void addTarefa(Tarefa tarefa) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("INSERT INTO TAREFA ")
		.append("(TXT, MARCADO) ")
		.append("VALUES (?, ?)");
		String query = sb.toString();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, tarefa.getTxt());
			pst.setBoolean(2, tarefa.isMarcado());
			pst.execute();
			conn.close();
		} catch (Exception e) {
			System.out.println("=== FALHOU NO DB ===");
			// TODO: handle exception
		}
	}
	
	
	// CRUD UPDATE
	
	public void inverteMarcado(Long id) {
		Tarefa tarefa = findTarefaById(id);
		if (tarefa == null) return;
		
		StringBuilder sb = new StringBuilder();
		sb
		.append("UPDATE TAREFA SET ")
		.append("MARCADO = ? ")
		.append("WHERE ID = ? ");
		String query = sb.toString();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setLong(2, id);
			pst.setBoolean(1, !tarefa.isMarcado());
			pst.execute();
			pst.close();
			conn.close();
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
	}
	
	public void marcarOuDesmarcarTudo(boolean marcado) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("UPDATE TAREFA SET ")
		.append("MARCADO = ? ");
		String query = sb.toString();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setBoolean(1, marcado);
			pst.execute();
			pst.close();
			conn.close();
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
	}
	
	
	// CRUD DELETE
	private void limparDb() {
		String query = "DELETE FROM TAREFA";
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement st = conn.createStatement();
			st.execute(query);
			st.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteById(Long id) {
		String query = "DELETE FROM TAREFA WHERE ID = ?";
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setLong(1, id);
			pst.execute();
			pst.close();
			conn.close();
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
	}
	
	public void removeMarcados() {
		String query = "DELETE FROM TAREFA WHERE MARCADO = TRUE";
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement st = conn.createStatement();
			st.execute(query);
			st.close();
			conn.close();
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
	}
	
	
	
	/*
	 * PARA EFEITO DE TESTES
	 */
	
	public void resetTarefas() {
		limparDb();
		addTarefa(new Tarefa(null, "Tomar banho", false));
		addTarefa(new Tarefa(null, "Escovar os dentes", false));
		addTarefa(new Tarefa(null, "Lavar os pratos", false));
		addTarefa(new Tarefa(null, "Varrer a sala", false));
	}

}
