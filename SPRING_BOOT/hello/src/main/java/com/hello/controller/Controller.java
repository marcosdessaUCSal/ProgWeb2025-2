package com.hello.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.model.Pessoa;
import com.hello.repository.PessoaRepository;

@RestController
public class Controller {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> response() {
		ArrayList<Pessoa> pessoas = pessoaRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(pessoas);
	}

}
