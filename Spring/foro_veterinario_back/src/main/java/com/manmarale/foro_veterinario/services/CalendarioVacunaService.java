package com.manmarale.foro_veterinario.services;

import com.manmarale.foro_veterinario.exceptions.ResourceNotFoundException;
import com.manmarale.foro_veterinario.models.CalendarioVacuna;
import com.manmarale.foro_veterinario.models.Mascota;
import com.manmarale.foro_veterinario.models.Usuario;
import com.manmarale.foro_veterinario.models.Vacuna;
import com.manmarale.foro_veterinario.models.dtos.vacuna.CalendarioVacunaDTO;
import com.manmarale.foro_veterinario.repository.iCalendarioVacunaRepository;
import com.manmarale.foro_veterinario.repository.iMascotaRepository;
import com.manmarale.foro_veterinario.repository.iUsuarioRepository;
import com.manmarale.foro_veterinario.repository.iVacunaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalendarioVacunaService {

    private final iCalendarioVacunaRepository calendarioRepository;
    private final iMascotaRepository mascotaRepository;
    private final iVacunaRepository vacunaRepository;
    private final iUsuarioRepository usuarioRepository;

    public List<CalendarioVacunaDTO> findByMascota(Integer mascotaId) {
        return calendarioRepository.findByMascotaIdId(mascotaId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CalendarioVacunaDTO> findPendientesByMascota(Integer mascotaId) {
        return calendarioRepository.findByMascotaIdIdAndAplicadaFalseOrderByFechaProximaAsc(mascotaId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CalendarioVacunaDTO findById(Integer id) {
        CalendarioVacuna cv = calendarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de vacuna no encontrado con ID: " + id));
        return mapToDTO(cv);
    }

    public CalendarioVacunaDTO save(CalendarioVacunaDTO dto) {
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + dto.getMascotaId()));

        Vacuna vacuna = vacunaRepository.findById(dto.getVacunaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacuna no encontrada con ID: " + dto.getVacunaId()));

        CalendarioVacuna cv = new CalendarioVacuna();
        cv.setMascotaId(mascota);
        cv.setVacunaId(vacuna);
        cv.setFechaAplicacion(dto.getFechaAplicacion());
        cv.setFechaProxima(dto.getFechaProxima());
        cv.setAplicada(dto.getAplicada() != null ? dto.getAplicada() : false);
        cv.setRecordatorioEnviado(false);
        cv.setNotas(dto.getNotas());
        cv.setCreatedAt(LocalDateTime.now());

        if (dto.getVeterinarioId() != null) {
            Usuario veterinario = usuarioRepository.findById(dto.getVeterinarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con ID: " + dto.getVeterinarioId()));
            cv.setVeterinarioId(veterinario);
        }

        CalendarioVacuna saved = calendarioRepository.save(cv);
        return mapToDTO(saved);
    }

    public CalendarioVacunaDTO aplicarVacuna(Integer id, CalendarioVacunaDTO dto) {
        CalendarioVacuna cv = calendarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de vacuna no encontrado con ID: " + id));

        cv.setAplicada(true);
        cv.setFechaAplicacion(dto.getFechaAplicacion() != null ? dto.getFechaAplicacion() : LocalDate.now());
        cv.setNotas(dto.getNotas());
        cv.setUpdatedAt(LocalDateTime.now());

        if (dto.getVeterinarioId() != null) {
            Usuario veterinario = usuarioRepository.findById(dto.getVeterinarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con ID: " + dto.getVeterinarioId()));
            cv.setVeterinarioId(veterinario);
        }

        CalendarioVacuna saved = calendarioRepository.save(cv);

        // Si la vacuna tiene intervalo, crear la siguiente cita automáticamente
        Vacuna vacuna = cv.getVacunaId();
        if (vacuna.getIntervaloDias() != null && vacuna.getIntervaloDias() > 0) {
            CalendarioVacuna siguiente = new CalendarioVacuna();
            siguiente.setMascotaId(cv.getMascotaId());
            siguiente.setVacunaId(vacuna);
            siguiente.setFechaProxima(cv.getFechaAplicacion().plusDays(vacuna.getIntervaloDias()));
            siguiente.setAplicada(false);
            siguiente.setRecordatorioEnviado(false);
            siguiente.setCreatedAt(LocalDateTime.now());
            siguiente.setNotas("Generada automáticamente - Próxima dosis");
            calendarioRepository.save(siguiente);
        }

        return mapToDTO(saved);
    }

    public Boolean delete(Integer id) {
        calendarioRepository.deleteById(id);
        return true;
    }

    public List<CalendarioVacunaDTO> findVacunasPendientesRecordatorio() {
        LocalDate hoy = LocalDate.now();
        LocalDate enTresDias = hoy.plusDays(3);
        return calendarioRepository.findVacunasPendientesEntreFechas(hoy, enTresDias).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CalendarioVacunaDTO mapToDTO(CalendarioVacuna cv) {
        CalendarioVacunaDTO dto = new CalendarioVacunaDTO();
        dto.setId(cv.getId());
        dto.setMascotaId(cv.getMascotaId().getId());
        dto.setMascotaNombre(cv.getMascotaId().getNombre());
        dto.setVacunaId(cv.getVacunaId().getId());
        dto.setVacunaNombre(cv.getVacunaId().getNombre());
        dto.setFechaAplicacion(cv.getFechaAplicacion());
        dto.setFechaProxima(cv.getFechaProxima());
        dto.setAplicada(cv.getAplicada());
        dto.setRecordatorioEnviado(cv.getRecordatorioEnviado());
        dto.setNotas(cv.getNotas());
        dto.setCreatedAt(cv.getCreatedAt());
        dto.setUpdatedAt(cv.getUpdatedAt());

        if (cv.getVeterinarioId() != null) {
            dto.setVeterinarioId(cv.getVeterinarioId().getId());
            dto.setVeterinarioNombre(cv.getVeterinarioId().getNombre());
        }

        // Email del propietario para recordatorios
        dto.setPropietarioEmail(cv.getMascotaId().getPropietarioId().getEmail());

        return dto;
    }
}
