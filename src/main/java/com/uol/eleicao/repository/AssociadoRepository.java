package com.uol.eleicao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uol.eleicao.model.Associado;


public interface AssociadoRepository extends JpaRepository<Associado, Long> {

	List<Associado> findAllByCargoPoliticoIgnoreCase(String cargoPolitico);

}
