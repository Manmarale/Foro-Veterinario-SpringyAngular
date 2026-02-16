package com.manmarale.foro_veterinario.controllers;

import com.manmarale.foro_veterinario.models.dtos.vacuna.CalendarioVacunaDTO;
import com.manmarale.foro_veterinario.services.CalendarioVacunaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/calendario-vacunas")
@AllArgsConstructor
public class CalendarioVacunaController {

    private final CalendarioVacunaService calendarioService;

    @GetMapping(value = "/mascota/{mascotaId}")
    public ResponseEntity<List<CalendarioVacunaDTO>> findByMascota(@PathVariable Integer mascotaId) {
        return ResponseEntity.ok(calendarioService.findByMascota(mascotaId));
    }

    @GetMapping(value = "/mascota/{mascotaId}/pendientes")
    public ResponseEntity<List<CalendarioVacunaDTO>> findPendientesByMascota(@PathVariable Integer mascotaId) {
        return ResponseEntity.ok(calendarioService.findPendientesByMascota(mascotaId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CalendarioVacunaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(calendarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CalendarioVacunaDTO> save(@RequestBody @Valid CalendarioVacunaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarioService.save(dto));
    }

    @PutMapping(value = "/{id}/aplicar")
    public ResponseEntity<CalendarioVacunaDTO> aplicarVacuna(@PathVariable Integer id, @RequestBody CalendarioVacunaDTO dto) {
        return ResponseEntity.ok(calendarioService.aplicarVacuna(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(calendarioService.delete(id));
    }

    @GetMapping(value = "/pendientes-recordatorio")
    public ResponseEntity<List<CalendarioVacunaDTO>> findPendientesRecordatorio() {
        return ResponseEntity.ok(calendarioService.findVacunasPendientesRecordatorio());
    }
}
