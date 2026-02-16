package com.manmarale.foro_veterinario.models.dtos.vacuna;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CalendarioVacunaDTO {

    private Integer id;

    @NotNull
    private Integer mascotaId;

    private String mascotaNombre;

    @NotNull
    private Integer vacunaId;

    private String vacunaNombre;

    private LocalDate fechaAplicacion;

    @NotNull
    private LocalDate fechaProxima;

    private Boolean aplicada;

    private Boolean recordatorioEnviado;

    private String notas;

    private Integer veterinarioId;

    private String veterinarioNombre;

    private String propietarioEmail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
