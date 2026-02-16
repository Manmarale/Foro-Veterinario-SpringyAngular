package com.manmarale.foro_veterinario.models.dtos.vacuna;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VacunaDTO {

    private Integer id;

    @NotNull
    @Size(min = 2, message = "Nombre debe tener al menos 2 caracteres!")
    @Size(max = 100, message = "Nombre puede tener máximo 100 caracteres!")
    private String nombre;

    private String descripcion;

    @NotNull
    private String especie;

    private Integer intervaloDias;

    private Boolean obligatoria;

    private Boolean activo;
}
