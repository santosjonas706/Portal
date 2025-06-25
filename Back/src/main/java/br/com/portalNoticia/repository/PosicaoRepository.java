package br.com.portalNoticia.repository;

import br.com.portalNoticia.entity.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Long> {}