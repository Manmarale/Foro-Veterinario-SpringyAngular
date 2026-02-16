package com.manmarale.foro_veterinario.services;

import com.manmarale.foro_veterinario.models.CalendarioVacuna;
import com.manmarale.foro_veterinario.repository.iCalendarioVacunaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class VacunaScheduler {

    private final iCalendarioVacunaRepository calendarioRepository;
    private final EmailService emailService;

    /**
     * Se ejecuta todos los días a las 8:00 AM
     * Busca vacunas pendientes en los próximos 3 días y envía recordatorios por email
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void enviarRecordatoriosVacunas() {
        log.info("Iniciando envío de recordatorios de vacunas...");

        LocalDate hoy = LocalDate.now();
        LocalDate enTresDias = hoy.plusDays(3);

        List<CalendarioVacuna> vacunasPendientes = calendarioRepository
                .findVacunasPendientesEntreFechas(hoy, enTresDias);

        for (CalendarioVacuna cv : vacunasPendientes) {
            if (!cv.getRecordatorioEnviado()) {
                try {
                    String emailPropietario = cv.getMascotaId().getPropietarioId().getEmail();
                    String nombreMascota = cv.getMascotaId().getNombre();
                    String nombreVacuna = cv.getVacunaId().getNombre();
                    String fechaProxima = cv.getFechaProxima().toString();
                    String nombrePropietario = cv.getMascotaId().getPropietarioId().getNombre();

                    emailService.enviarRecordatorioVacuna(
                            emailPropietario,
                            nombreMascota,
                            nombreVacuna,
                            fechaProxima,
                            nombrePropietario
                    );

                    cv.setRecordatorioEnviado(true);
                    calendarioRepository.save(cv);

                    log.info("Recordatorio enviado para mascota: {} - Vacuna: {}", nombreMascota, nombreVacuna);
                } catch (Exception e) {
                    log.error("Error al enviar recordatorio para calendario ID {}: {}", cv.getId(), e.getMessage());
                }
            }
        }

        log.info("Proceso de recordatorios finalizado. {} vacunas procesadas.", vacunasPendientes.size());
    }
}
