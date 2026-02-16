package com.manmarale.foro_veterinario.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.manmarale.foro_veterinario.models.dtos.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String nombre;

    @Column(length = 100)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "file_perfil")
    private String filePerfil;

    @Column(length = 20)
    private String telefono;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Boolean activo;
    @OneToMany(mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<Tema> temas;

    @OneToMany(mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<Respuesta> respuestas;

    @OneToMany(mappedBy = "propietarioId", fetch = FetchType.LAZY)
    private List<Mascota> mascotas;

    public void desactivarUsuario(){
        this.activo = false;
    }

}

