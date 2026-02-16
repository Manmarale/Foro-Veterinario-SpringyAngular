package com.manmarale.foro_veterinario.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.veterinaria.email-remitente:vetforo@example.com}")
    private String emailRemitente;

    @Value("${app.veterinaria.nombre:VetForo}")
    private String nombreApp;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarRecordatorioVacuna(String emailDestino, String nombreMascota,
                                         String nombreVacuna, String fechaProxima,
                                         String nombrePropietario) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(emailRemitente);
            mensaje.setTo(emailDestino);
            mensaje.setSubject("🐾 " + nombreApp + " - Recordatorio de Vacuna para " + nombreMascota);
            mensaje.setText(
                "Hola " + nombrePropietario + ",\n\n" +
                "Le recordamos que su mascota " + nombreMascota + " tiene una vacuna pendiente:\n\n" +
                "💉 Vacuna: " + nombreVacuna + "\n" +
                "📅 Fecha programada: " + fechaProxima + "\n\n" +
                "Por favor, agende una cita con su veterinario lo antes posible.\n\n" +
                "Atentamente,\n" +
                "El equipo de " + nombreApp + " 🐾"
            );

            mailSender.send(mensaje);
            log.info("Recordatorio de vacuna enviado a {} para mascota {}", emailDestino, nombreMascota);
        } catch (Exception e) {
            log.error("Error al enviar recordatorio de vacuna a {}: {}", emailDestino, e.getMessage());
        }
    }

    public void enviarBienvenida(String emailDestino, String nombreUsuario) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(emailRemitente);
            mensaje.setTo(emailDestino);
            mensaje.setSubject("🐾 Bienvenido a " + nombreApp + "!");
            mensaje.setText(
                "Hola " + nombreUsuario + ",\n\n" +
                "¡Bienvenido a " + nombreApp + "! 🎉\n\n" +
                "Tu cuenta ha sido creada exitosamente. Ahora puedes:\n\n" +
                "🐕 Registrar tus mascotas\n" +
                "💉 Gestionar el calendario de vacunas\n" +
                "💬 Participar en el foro veterinario\n" +
                "📅 Recibir recordatorios de vacunación\n\n" +
                "¡Gracias por unirte a nuestra comunidad veterinaria!\n\n" +
                "Atentamente,\n" +
                "El equipo de " + nombreApp + " 🐾"
            );

            mailSender.send(mensaje);
            log.info("Email de bienvenida enviado a {}", emailDestino);
        } catch (Exception e) {
            log.error("Error al enviar email de bienvenida a {}: {}", emailDestino, e.getMessage());
        }
    }
}
