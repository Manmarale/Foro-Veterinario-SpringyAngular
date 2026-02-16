package com.manmarale.foro_veterinario.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "vacuna")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Vacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 50, nullable = false)
    private String especie; // PERRO, GATO, AVE, etc.

    @Column(name = "intervalo_dias")
    private Integer intervaloDias; // Intervalo en días para la próxima dosis

    @Column(name = "obligatoria")
    private Boolean obligatoria = false;

    private Boolean activo = true;

    @OneToMany(mappedBy = "vacunaId", fetch = FetchType.LAZY)
    private List<CalendarioVacuna> calendarioVacunas;
}
