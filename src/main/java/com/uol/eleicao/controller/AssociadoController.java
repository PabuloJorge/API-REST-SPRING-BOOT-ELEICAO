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
import com.uol.eleicao.repository.AssociadoRepository;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

	@Autowired
	private AssociadoRepository associadoRepository;

	@GetMapping
	public List<Associado> findAll() {
		List<Associado> associados = this.associadoRepository.findAll();
		return associados;
	}

	@GetMapping("/findByCargoPolitico/{cargoPolitico}")
	public List<Associado> findByIdeologia(@PathVariable("cargoPolitico") String cargoPolitico) {
		List<Associado> associados = this.associadoRepository.findAllByCargoPoliticoIgnoreCase(cargoPolitico);
		return associados;
	}

	@GetMapping("/{id}")
	public Associado findById(@PathVariable("id") Long id) {
		Optional<Associado> associadoFind = this.associadoRepository.findById(id);

		if (associadoFind.isPresent()) {
			return associadoFind.get();
		}

		return null;
	}

	@PostMapping
	public ResponseEntity<Associado> saveAssociado(@RequestBody @Valid Associado associado,
			UriComponentsBuilder uriBuilder) {
		this.associadoRepository.save(associado);
		URI uri = uriBuilder.path("/associado/{id}").buildAndExpand(associado.getId()).toUri();
		return ResponseEntity.created(uri).body(associado);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Associado> updateAssociado(@PathVariable Long id, @RequestBody @Valid Associado associadoNew) {
		Optional<Associado> optional = this.associadoRepository.findById(id);
		if (optional.isPresent()) {
			Associado associado = optional.get();

			associado.setNome(associadoNew.getNome());
			associado.setCargoPolitico(associadoNew.getCargoPolitico());
			associado.setDataNascimento(associadoNew.getDataNascimento());
			associado.setSexo(associadoNew.getSexo());

			return ResponseEntity.ok(associado);
		}

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteAssociado(@PathVariable Long id) {
		Optional<Associado> optional = this.associadoRepository.findById(id);
		if (optional.isPresent()) {
			this.associadoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
}
