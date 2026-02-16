package com.manmarale.foro_veterinario.models.dtos.respuesta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RespuestaDTO {

    private Integer id;

    @NotNull
    @Size(min = 3, message = "Respuesta debe tener almenos 3 caracteres!")
    @Size(max = 150, message = "Respuesta puede tener maximo 500 caracteres!")
    private String mensajeRespuesta;

    @NotNull
    private Integer temaId;

    @NotNull
    private Integer usuarioId;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
