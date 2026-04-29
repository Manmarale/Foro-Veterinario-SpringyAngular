package com.manmarale.foro_veterinario.controllers;
import com.manmarale.foro_veterinario.exceptions.BadRequestExcepton;
import com.manmarale.foro_veterinario.models.Usuario;
import com.manmarale.foro_veterinario.models.dtos.Role;
import com.manmarale.foro_veterinario.models.dtos.autenticacion.PerfilUsuarioDTO;
import com.manmarale.foro_veterinario.models.dtos.usuario.UsuarioRegistroDTO;
import com.manmarale.foro_veterinario.repository.iUsuarioRepository;
import com.manmarale.foro_veterinario.services.EmailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class CuentaController {

    private final iUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping(value = "/registro")
    public PerfilUsuarioDTO registro(@RequestBody @Validated UsuarioRegistroDTO usuarioRegistroDTO){
        boolean emailExists = usuarioRepository.existsByEmail(usuarioRegistroDTO.getEmail());
        if (emailExists){
            throw new BadRequestExcepton("ERROR EMAIL DUPLICADO: El email ya existe!");
        }
        //Usuario usuario = new Usuario();
        Usuario usuario = new ModelMapper().map(usuarioRegistroDTO,Usuario.class);
        usuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        usuario.setActivo(Boolean.TRUE);
        usuario.setRole(Role.USER);
        usuario.setCreatedAt(LocalDateTime.now());

        // Si no se sube foto de perfil, asignar avatar por defecto
        if (usuario.getFilePerfil() == null || usuario.getFilePerfil().isBlank()) {
            usuario.setFilePerfil("default-avatar.png");
        }

        usuarioRepository.save(usuario);

    // Enviar bienvenida por correo sin bloquear el registro ante fallos SMTP
    emailService.enviarBienvenida(usuario.getEmail(), usuario.getNombre());
//        usuario.setFilePerfil(usuarioRegistroDTO.getFilePerfil());
//        usuario.setNombre(usuarioRegistroDTO.getNombre());
//        usuario.setEmail(usuarioRegistroDTO.getEmail());

        //return new PerfilUsuarioDTO(usuario.getNombre(),usuario.getEmail(), usuario.getPassword(),usuario.getRole(), usuario.getId());
        return new ModelMapper().map(usuario, PerfilUsuarioDTO.class);
    }

}
