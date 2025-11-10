package com.contatos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contatos.dto.ContatoDto;
import com.contatos.model.Contato;
import com.contatos.repository.ContatoRepository;

import jakarta.transaction.Transactional;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;
	
	@Autowired
	private IniciaService iniciaService;

	public List<ContatoDto> getAll() {
		List<Contato> contatos = contatoRepository.findAll();
		List<ContatoDto> dtos = new ArrayList<ContatoDto>();
		for (Contato cto : contatos) {
			dtos.add(new ContatoDto(cto));
		}
		return dtos;
	}
	
	// Marca (ou desmarca) tudo
	@Transactional
	public void marcarTudo(boolean marcado) {
		contatoRepository.updateAllMarcado(marcado);
	}
	
	public void removerMarcados() {
		contatoRepository.deleteByMarcadoTrue();
	}
	
	public void inverterMarcado(Long id) {
		Optional<Contato> optional = contatoRepository.findById(id);
		if (optional.isPresent()) {
			Contato contato = optional.get();
			contato.setMarcado(!contato.isMarcado());
			contatoRepository.save(contato);
		}
	}
	
	public void reset() {
		contatoRepository.deleteAll();
		iniciaService.iniciaDb();
	}
	
	public void addContato(ContatoDto dto) {
		contatoRepository.save(new Contato(dto));
	}
	

}
