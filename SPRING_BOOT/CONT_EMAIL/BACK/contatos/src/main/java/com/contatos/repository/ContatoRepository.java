package com.contatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contatos.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
	
	void deleteByMarcadoTrue();
	
	@Modifying
	@Query("UPDATE Contato cont SET cont.marcado = :marcado")
	void updateAllMarcado(@Param("marcado") boolean m);
	

}
