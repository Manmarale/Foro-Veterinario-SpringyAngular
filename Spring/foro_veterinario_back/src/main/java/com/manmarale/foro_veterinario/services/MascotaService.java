package com.manmarale.foro_veterinario.services;

import com.manmarale.foro_veterinario.exceptions.BadRequestExcepton;
import com.manmarale.foro_veterinario.exceptions.ResourceNotFoundException;
import com.manmarale.foro_veterinario.models.Mascota;
import com.manmarale.foro_veterinario.models.Usuario;
import com.manmarale.foro_veterinario.models.dtos.mascota.MascotaDTO;
import com.manmarale.foro_veterinario.repository.iMascotaRepository;
import com.manmarale.foro_veterinario.repository.iUsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MascotaService {

    private final iMascotaRepository mascotaRepository;
    private final iUsuarioRepository usuarioRepository;

    public List<MascotaDTO> findAll() {
        return mascotaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Page<MascotaDTO> paginate(Pageable pageable) {
        return mascotaRepository.findAll(pageable).map(this::mapToDTO);
    }

    public List<MascotaDTO> findByPropietario(Integer propietarioId) {
        return mascotaRepository.findByPropietarioIdId(propietarioId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MascotaDTO findById(Integer id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + id));
        return mapToDTO(mascota);
    }

    public MascotaDTO save(MascotaDTO mascotaDTO) {
        Usuario propietario = usuarioRepository.findById(mascotaDTO.getPropietarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Propietario no encontrado con ID: " + mascotaDTO.getPropietarioId()));

        Mascota mascota = new Mascota();
        mascota.setNombre(mascotaDTO.getNombre());
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setFechaNacimiento(mascotaDTO.getFechaNacimiento());
        mascota.setSexo(mascotaDTO.getSexo());
        mascota.setPesoKg(mascotaDTO.getPesoKg());
        mascota.setFoto(mascotaDTO.getFoto());
        mascota.setObservaciones(mascotaDTO.getObservaciones());
        mascota.setPropietarioId(propietario);
        mascota.setActivo(Boolean.TRUE);
        mascota.setCreatedAt(LocalDateTime.now());

        // Si no se sube foto, asignar foto por defecto según especie
        if (mascota.getFoto() == null || mascota.getFoto().isBlank()) {
            mascota.setFoto("default-mascota.png");
        }

        Mascota saved = mascotaRepository.save(mascota);
        return mapToDTO(saved);
    }

    public MascotaDTO update(Integer id, MascotaDTO mascotaDTO) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + id));

        mascota.setNombre(mascotaDTO.getNombre());
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setFechaNacimiento(mascotaDTO.getFechaNacimiento());
        mascota.setSexo(mascotaDTO.getSexo());
        mascota.setPesoKg(mascotaDTO.getPesoKg());
        mascota.setObservaciones(mascotaDTO.getObservaciones());
        if (mascotaDTO.getFoto() != null && !mascotaDTO.getFoto().isBlank()) {
            mascota.setFoto(mascotaDTO.getFoto());
        }
        mascota.setUpdatedAt(LocalDateTime.now());

        Mascota saved = mascotaRepository.save(mascota);
        return mapToDTO(saved);
    }

    public Boolean delete(Integer id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + id));
        mascota.setActivo(false);
        mascota.setUpdatedAt(LocalDateTime.now());
        mascotaRepository.save(mascota);
        return true;
    }

    private MascotaDTO mapToDTO(Mascota mascota) {
        MascotaDTO dto = new MascotaDTO();
        dto.setId(mascota.getId());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());
        dto.setRaza(mascota.getRaza());
        dto.setFechaNacimiento(mascota.getFechaNacimiento());
        dto.setSexo(mascota.getSexo());
        dto.setPesoKg(mascota.getPesoKg());
        dto.setFoto(mascota.getFoto());
        dto.setObservaciones(mascota.getObservaciones());
        dto.setPropietarioId(mascota.getPropietarioId().getId());
        dto.setPropietarioNombre(mascota.getPropietarioId().getNombre());
        dto.setActivo(mascota.getActivo());
        dto.setCreatedAt(mascota.getCreatedAt());
        dto.setUpdatedAt(mascota.getUpdatedAt());
        return dto;
    }
}
