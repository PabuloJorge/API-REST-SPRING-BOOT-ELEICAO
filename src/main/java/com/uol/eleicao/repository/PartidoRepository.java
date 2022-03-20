package com.uol.eleicao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uol.eleicao.model.Associado;
import com.uol.eleicao.model.Partido;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

	@Query(nativeQuery = false,value="SELECT a FROM Associado a")
	List<Associado> findAssociadosByPartido();

	List<Partido> findAllByIdeologiaIgnoreCase(String ideologia);


}
