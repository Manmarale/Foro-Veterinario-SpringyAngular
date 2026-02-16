package com.manmarale.foro_veterinario.models.dtos.mascota;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.manmarale.foro_veterinario.models.dtos.vacuna.CalendarioVacunaDTO;

@Data
public class MascotaDTO {

    private Integer id;

    @NotNull
    @Size(min = 2, message = "Nombre debe tener al menos 2 caracteres!")
    @Size(max = 100, message = "Nombre puede tener máximo 100 caracteres!")
    private String nombre;

    @NotNull
    private String especie;

    private String raza;

    private LocalDate fechaNacimiento;

    private String sexo;

    private Double pesoKg;

    private String foto;

    private String observaciones;

    @NotNull
    private Integer propietarioId;

    private String propietarioNombre;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean activo;

    private List<CalendarioVacunaDTO> calendarioVacunas;
}
