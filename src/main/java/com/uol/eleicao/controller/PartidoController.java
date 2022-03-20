package com.uol.eleicao.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.uol.eleicao.model.Associado;
import com.uol.eleicao.model.Partido;
import com.uol.eleicao.repository.PartidoRepository;




@RestController
@RequestMapping("/partidos")
public class PartidoController {

	@Autowired
	private PartidoRepository partidoRepository;

	@GetMapping
	public List<Partido> findAll() {
		List<Partido> partidos = this.partidoRepository.findAll();
		return partidos;
	}
	
	@GetMapping("/findByIdeologia/{ideologia}")
	public List<Partido> findByIdeologia(@PathVariable("ideologia") String ideologia) {
		List<Partido> partidos = this.partidoRepository.findAllByIdeologiaIgnoreCase(ideologia);
		return partidos;
	}
	
	@GetMapping("/{id}")
	public Partido findById(@PathVariable("id") Long id) {
		Optional<Partido> partidoFind = this.partidoRepository.findById(id);

		if (partidoFind.isPresent()) {
			return partidoFind.get();
		}

		return null;
	}
	
	@GetMapping("/{id}/associados")
	public List<Associado> findAssociadosByPartido() {
		List<Associado> associados = this.partidoRepository.findAssociadosByPartido();
		return associados;
	}
	
	@PostMapping
	public ResponseEntity<Partido> savePartido(@RequestBody @Valid Partido partido, UriComponentsBuilder uriBuilder) {
		this.partidoRepository.save(partido);
		URI uri = uriBuilder.path("/partidos/{id}").buildAndExpand(partido.getId()).toUri();
		return ResponseEntity.created(uri).body(partido);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Partido> updatePartido(@PathVariable Long id, @RequestBody @Valid Partido PartidoNew) {
		Optional<Partido> optional = this.partidoRepository.findById(id);
		if (optional.isPresent()) {
			Partido partido = optional.get();

			partido.setNome(PartidoNew.getNome());
			partido.setSigla(PartidoNew.getSigla());
			partido.setIdeologia(PartidoNew.getIdeologia());
			partido.setDataFundacao(PartidoNew.getDataFundacao());
			

			return ResponseEntity.ok(partido);
		}

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletePartido(@PathVariable Long id) {
		Optional<Partido> optional = this.partidoRepository.findById(id);
		if (optional.isPresent()) {
			this.partidoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
}
