package com.manmarale.foro_veterinario.repository;

import com.manmarale.foro_veterinario.models.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface iVacunaRepository extends JpaRepository<Vacuna, Integer> {

    List<Vacuna> findByEspecie(String especie);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, Integer id);

    List<Vacuna> findByActivoTrue();
}
