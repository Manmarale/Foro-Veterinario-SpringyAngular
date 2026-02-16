package com.manmarale.foro_veterinario.controllers;

import com.manmarale.foro_veterinario.models.dtos.vacuna.VacunaDTO;
import com.manmarale.foro_veterinario.services.VacunaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/vacunas")
@AllArgsConstructor
public class VacunaController {

    private final VacunaService vacunaService;

    @GetMapping
    public ResponseEntity<List<VacunaDTO>> findAll() {
        return ResponseEntity.ok(vacunaService.findAll());
    }

    @GetMapping(value = "/paginate")
    public ResponseEntity<Page<VacunaDTO>> paginate(Pageable pageable) {
        return ResponseEntity.ok(vacunaService.paginate(pageable));
    }

    @GetMapping(value = "/especie/{especie}")
    public ResponseEntity<List<VacunaDTO>> findByEspecie(@PathVariable String especie) {
        return ResponseEntity.ok(vacunaService.findByEspecie(especie));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VacunaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(vacunaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VacunaDTO> save(@RequestBody @Valid VacunaDTO vacunaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacunaService.save(vacunaDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VacunaDTO> update(@PathVariable Integer id, @RequestBody @Valid VacunaDTO vacunaDTO) {
        return ResponseEntity.ok(vacunaService.update(id, vacunaDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(vacunaService.delete(id));
    }
}
