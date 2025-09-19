package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Contato;

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
		.append("CREATE TABLE CONTATO (")
		.append("	ID BIGINT AUTO_INCREMENT PRIMARY KEY,")
		.append("	NOME VARCHAR(40),")
		.append("	EMAIL VARCHAR(40),")
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
	
	public List<Contato> getContatos() {
		String query = "SELECT ID, NOME, EMAIL, MARCADO FROM CONTATO";
		List<Contato> contatos = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Contato contato = new Contato();
				contato.setId(rs.getLong("ID"));
				contato.setNome(rs.getString("NOME"));
				contato.setEmail(rs.getString("EMAIL"));
				contato.setMarcado(rs.getBoolean("MARCADO"));
				contatos.add(contato);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
		return contatos;
	}
	
	public Contato findContatoById(Long id) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT ID, NOME, EMAIL, MARCADO ")
		.append("	FROM CONTATO ")
		.append("	WHERE ID = ?");
		String query = sb.toString();
		Contato contato = null;
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				contato = new Contato();
				contato.setId(rs.getLong("ID"));
				contato.setNome(rs.getString("NOME"));
				contato.setEmail(rs.getString("EMAIL"));
				contato.setMarcado(rs.getBoolean("MARCADO"));
				rs.close();
				pst.close();
				conn.close();
				return contato;
			}
		} catch (Exception e) {
			// TODO: DECIDA O QUE FAZER
		}
		return contato;
	}
	
	// CRUD CREATE
	
	public void addContato(Contato contato) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("INSERT INTO CONTATO ")
		.append("(NOME, EMAIL, MARCADO) ")
		.append("VALUES (?, ?, ?)");
		String query = sb.toString();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getEmail());
			pst.setBoolean(3, contato.isMarcado());
			pst.execute();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	// CRUD UPDATE
	
	public void inverteMarcado(Long id) {
		Contato contato = findContatoById(id);
		if (contato == null) return;
		
		StringBuilder sb = new StringBuilder();
		sb
		.append("UPDATE CONTATO SET ")
		.append("MARCADO = ? ")
		.append("WHERE ID = ? ");
		String query = sb.toString();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setLong(2, id);
			pst.setBoolean(1, !contato.isMarcado());
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
		.append("UPDATE CONTATO SET ")
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
		String query = "DELETE FROM CONTATO";
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
		String query = "DELETE FROM CONTATO WHERE ID = ?";
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
		String query = "DELETE FROM CONTATO WHERE MARCADO = TRUE";
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
		addContato(new Contato(null, "John Lennon", "lennon@mail.com", false));
		addContato(new Contato(null, "Bruce Lee", "leekungfu@email.com", false));
		addContato(new Contato(null, "Coringa", "joker@email.com", false));
		addContato(new Contato(null, "Batman", "batman@email.com", false));
	}

}
