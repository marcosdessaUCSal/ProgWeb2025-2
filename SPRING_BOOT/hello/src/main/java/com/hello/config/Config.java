package com.hello.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.hello.model.Pessoa;
import com.hello.repository.PessoaRepository;

@Configuration
public class Config implements CommandLineRunner {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public void run(String... args) throws Exception {
		Pessoa p1 = new Pessoa(null, "Fulano", "de Tal");
		Pessoa p2 = new Pessoa(null, "Sicrano", "de Tal");
		Pessoa p3 = new Pessoa(null, "John", "Doe");
		
		pessoaRepository.saveAll(Arrays.asList(p1, p2, p3));
		
	}

}
