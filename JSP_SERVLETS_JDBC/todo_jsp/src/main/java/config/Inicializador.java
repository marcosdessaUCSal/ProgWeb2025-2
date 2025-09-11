package config;

import java.sql.SQLException;

import org.h2.tools.Server;

import dao.Db;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Inicializador implements ServletContextListener {
	
	private Server server;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContextListener.super.contextInitialized(sce);
		
		try {
			Class.forName("org.h2.Driver");
			
			Db.getInstance();	// ao obter instância, inicializa-se a tabela
			
			server = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
			System.out.println("H2 console iniciado: http://localhost:8082");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (server != null) {
            server.stop();
        }
    }

}
