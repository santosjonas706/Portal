package br.com.portalNoticia.repository;

import br.com.portalNoticia.entity.Publicidade;
import br.com.portalNoticia.entity.Posicao;
import br.com.portalNoticia.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PublicidadeRepository extends JpaRepository<Publicidade, Long> {
    // Buscar anúncios ativos e vigentes
    @Query("SELECT p FROM Publicidade p WHERE p.ativo = true AND :dataAtual BETWEEN p.dataInicio AND p.dataFim")
    List<Publicidade> findAtivosEVigentes(LocalDate dataAtual);

    // Buscar por posição
    List<Publicidade> findByPosicao(Posicao posicao);

    // Buscar por categoria
    List<Publicidade> findByCategorias(Categoria categoria);
}