package com.hello.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hello.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	ArrayList<Pessoa> findAll();

}
