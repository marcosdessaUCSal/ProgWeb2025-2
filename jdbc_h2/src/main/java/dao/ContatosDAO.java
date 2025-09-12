package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Contato;

public class ContatosDAO {
	
	private static final String DRIVER = "org.h2.Driver";
	private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = 	"";
	
	
	// CRIAÇÃO DA TABELA
	public void criaTabela() throws Exception {
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		// CRIANDO A TABELA
		String query =
				"CREATE TABLE TABELA (" +
				"  ID INT AUTO_INCREMENT PRIMARY KEY," +
				"  NOME VARCHAR(100)," +
				"  TEL VARCHAR(15)" +
				")";
		Statement st = conn.createStatement();
		st.execute(query);
		st.close();
		conn.close();
	}
	
	// POPULANDO A TABELA COM OS DADOS INICIAIS
	public void populaTabela() throws Exception {
		inserirContato(new Contato("Marcos", "(71) 8888-1234"));
		inserirContato(new Contato("Cláudio", "(71) 9999-4321"));
		inserirContato(new Contato("Ricardo", "(71) 8877-4645"));
	}
	
	// INSERINDO UM CONTATO
	public void inserirContato(Contato contato) throws Exception {
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		String query = "INSERT INTO TABELA (NOME, TEL) VALUES (?,?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, contato.getNome());
		ps.setString(2, contato.getTel());
		ps.execute();
		ps.close();
		conn.close();
	}
	
	// LENDO TODOS OS CONTATOS
	public List<Contato> getContatos() throws Exception {
		List<Contato> lista = new ArrayList<Contato>();
		
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		String query = "SELECT ID, NOME, TEL FROM TABELA";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			Contato contato = new Contato();
			contato.setId(rs.getLong("ID"));
			contato.setNome(rs.getString("NOME"));
			contato.setTel(rs.getString("TEL"));
			lista.add(contato);
		}
		st.close();
		rs.close();
		conn.close();
		return lista;
	}
    	

}
