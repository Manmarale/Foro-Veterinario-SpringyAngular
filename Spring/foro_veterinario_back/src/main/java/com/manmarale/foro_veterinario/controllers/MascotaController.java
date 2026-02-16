package com.manmarale.foro_veterinario.controllers;

import com.manmarale.foro_veterinario.models.dtos.mascota.MascotaDTO;
import com.manmarale.foro_veterinario.services.MascotaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/mascotas")
@AllArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<Page<MascotaDTO>> paginate(Pageable pageable) {
        return ResponseEntity.ok(mascotaService.paginate(pageable));
    }

    @GetMapping(value = "/propietario/{propietarioId}")
    public ResponseEntity<List<MascotaDTO>> findByPropietario(@PathVariable Integer propietarioId) {
        return ResponseEntity.ok(mascotaService.findByPropietario(propietarioId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MascotaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mascotaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> save(@RequestBody @Valid MascotaDTO mascotaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.save(mascotaDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MascotaDTO> update(@PathVariable Integer id, @RequestBody @Valid MascotaDTO mascotaDTO) {
        return ResponseEntity.ok(mascotaService.update(id, mascotaDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(mascotaService.delete(id));
    }
}
