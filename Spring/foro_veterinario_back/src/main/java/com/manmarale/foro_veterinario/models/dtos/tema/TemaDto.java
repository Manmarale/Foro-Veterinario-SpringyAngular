package com.manmarale.foro_veterinario.models.dtos.tema;
import com.manmarale.foro_veterinario.models.dtos.respuesta.RespuestaTemaDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TemaDto {

    private Integer id;

    @NotNull
    @Size(min = 3, message = "Titulo debe tener almenos 3 caracteres!")
    @Size(max = 150, message = "Titulo puede tener maximo 45 caracteres!")
    private String titulo;

    @NotNull
    @Size(min = 3, message = "Mensaje debe tener almenos 3 caracteres!")
    @Size(max = 1500, message = "Mensaje puede tener maximo 1500 caracteres!")
    private String mensaje;

    @NotNull
    private Genero genero;

    @NotNull
    private Integer usuarioId;

    private String usuarioNombre;

    private String filePerfil;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean activo;

    private List<RespuestaTemaDTO> respuestas;

}
