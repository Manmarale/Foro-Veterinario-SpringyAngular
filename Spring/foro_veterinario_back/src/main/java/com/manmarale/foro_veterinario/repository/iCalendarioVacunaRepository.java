package com.manmarale.foro_veterinario.repository;

import com.manmarale.foro_veterinario.models.CalendarioVacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface iCalendarioVacunaRepository extends JpaRepository<CalendarioVacuna, Integer> {

    List<CalendarioVacuna> findByMascotaIdId(Integer mascotaId);

    List<CalendarioVacuna> findByAplicadaFalse();

    @Query("SELECT cv FROM CalendarioVacuna cv WHERE cv.fechaProxima <= :fecha AND cv.aplicada = false AND cv.recordatorioEnviado = false")
    List<CalendarioVacuna> findVacunasPendientesRecordatorio(@Param("fecha") LocalDate fecha);

    @Query("SELECT cv FROM CalendarioVacuna cv WHERE cv.fechaProxima BETWEEN :inicio AND :fin AND cv.aplicada = false")
    List<CalendarioVacuna> findVacunasPendientesEntreFechas(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    List<CalendarioVacuna> findByMascotaIdIdAndAplicadaFalseOrderByFechaProximaAsc(Integer mascotaId);
}
