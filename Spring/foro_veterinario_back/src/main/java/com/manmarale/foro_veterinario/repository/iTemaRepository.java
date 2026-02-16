package com.manmarale.foro_veterinario.repository;
import com.manmarale.foro_veterinario.models.Tema;
import com.manmarale.foro_veterinario.models.dtos.tema.Genero;
import com.manmarale.foro_veterinario.models.dtos.tema.TemaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface iTemaRepository extends JpaRepository<Tema,Integer> {
    boolean existsByTitulo(String titulo);
    boolean existsByTituloAndIdNot(String titulo ,Integer id);
    boolean existsByMensaje(String mensaje);
    boolean existsByMensajeAndIdNot(String mensaje, Integer id);
    List<Tema> findByGenero(Genero genero);
    @Query("SELECT t FROM Tema t WHERE DATE(t.createdAt) = :date")
    List<Tema> findTemasByDate(@Param("date") LocalDate date);


    List<Tema> findTop9ByOrderByCreatedAtDesc();

    Page<Tema> findAll(Pageable pageable);
}
