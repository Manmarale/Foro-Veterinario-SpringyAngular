package com.manmarale.foro_veterinario.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calendario_vacuna")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CalendarioVacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascotaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacuna_id", nullable = false)
    private Vacuna vacunaId;

    @Column(name = "fecha_aplicacion")
    private LocalDate fechaAplicacion;

    @Column(name = "fecha_proxima", nullable = false)
    private LocalDate fechaProxima;

    @Column(name = "aplicada")
    private Boolean aplicada = false;

    @Column(name = "recordatorio_enviado")
    private Boolean recordatorioEnviado = false;

    @Column(length = 500)
    private String notas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinario_id")
    private Usuario veterinarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
