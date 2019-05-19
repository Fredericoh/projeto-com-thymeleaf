package br.edu.faculdadedelta.projetothymeleaf.repository;

import br.edu.faculdadedelta.projetothymeleaf.modelo.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
