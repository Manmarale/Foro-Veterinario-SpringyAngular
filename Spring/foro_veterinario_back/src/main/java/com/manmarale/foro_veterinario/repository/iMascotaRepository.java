package com.manmarale.foro_veterinario.repository;

import com.manmarale.foro_veterinario.models.Mascota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface iMascotaRepository extends JpaRepository<Mascota, Integer> {

    List<Mascota> findByPropietarioIdId(Integer propietarioId);

    Page<Mascota> findByPropietarioIdId(Integer propietarioId, Pageable pageable);

    List<Mascota> findByActivoTrue();
}
