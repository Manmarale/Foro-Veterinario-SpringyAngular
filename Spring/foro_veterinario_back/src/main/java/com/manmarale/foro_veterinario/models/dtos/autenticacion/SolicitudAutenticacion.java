package com.manmarale.foro_veterinario.models.dtos.autenticacion;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SolicitudAutenticacion {

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "[a-z0-9-]+")
    @Size(min = 5, message = "El password debe tener al menos 5 caracteres!")
    private String password;
}
