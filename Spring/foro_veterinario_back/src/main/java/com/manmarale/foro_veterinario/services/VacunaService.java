package com.manmarale.foro_veterinario.services;

import com.manmarale.foro_veterinario.exceptions.BadRequestExcepton;
import com.manmarale.foro_veterinario.exceptions.ResourceNotFoundException;
import com.manmarale.foro_veterinario.models.Vacuna;
import com.manmarale.foro_veterinario.models.dtos.vacuna.VacunaDTO;
import com.manmarale.foro_veterinario.repository.iVacunaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VacunaService {

    private final iVacunaRepository vacunaRepository;

    public List<VacunaDTO> findAll() {
        return vacunaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Page<VacunaDTO> paginate(Pageable pageable) {
        return vacunaRepository.findAll(pageable).map(this::mapToDTO);
    }

    public List<VacunaDTO> findByEspecie(String especie) {
        return vacunaRepository.findByEspecie(especie).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public VacunaDTO findById(Integer id) {
        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacuna no encontrada con ID: " + id));
        return mapToDTO(vacuna);
    }

    public VacunaDTO save(VacunaDTO vacunaDTO) {
        if (vacunaRepository.existsByNombre(vacunaDTO.getNombre())) {
            throw new BadRequestExcepton("Ya existe una vacuna con ese nombre");
        }

        Vacuna vacuna = new Vacuna();
        vacuna.setNombre(vacunaDTO.getNombre());
        vacuna.setDescripcion(vacunaDTO.getDescripcion());
        vacuna.setEspecie(vacunaDTO.getEspecie());
        vacuna.setIntervaloDias(vacunaDTO.getIntervaloDias());
        vacuna.setObligatoria(vacunaDTO.getObligatoria() != null ? vacunaDTO.getObligatoria() : false);
        vacuna.setActivo(true);

        Vacuna saved = vacunaRepository.save(vacuna);
        return mapToDTO(saved);
    }

    public VacunaDTO update(Integer id, VacunaDTO vacunaDTO) {
        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacuna no encontrada con ID: " + id));

        if (vacunaRepository.existsByNombreAndIdNot(vacunaDTO.getNombre(), id)) {
            throw new BadRequestExcepton("Ya existe otra vacuna con ese nombre");
        }

        vacuna.setNombre(vacunaDTO.getNombre());
        vacuna.setDescripcion(vacunaDTO.getDescripcion());
        vacuna.setEspecie(vacunaDTO.getEspecie());
        vacuna.setIntervaloDias(vacunaDTO.getIntervaloDias());
        vacuna.setObligatoria(vacunaDTO.getObligatoria());

        Vacuna saved = vacunaRepository.save(vacuna);
        return mapToDTO(saved);
    }

    public Boolean delete(Integer id) {
        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacuna no encontrada con ID: " + id));
        vacuna.setActivo(false);
        vacunaRepository.save(vacuna);
        return true;
    }

    private VacunaDTO mapToDTO(Vacuna vacuna) {
        VacunaDTO dto = new VacunaDTO();
        dto.setId(vacuna.getId());
        dto.setNombre(vacuna.getNombre());
        dto.setDescripcion(vacuna.getDescripcion());
        dto.setEspecie(vacuna.getEspecie());
        dto.setIntervaloDias(vacuna.getIntervaloDias());
        dto.setObligatoria(vacuna.getObligatoria());
        dto.setActivo(vacuna.getActivo());
        return dto;
    }
}
