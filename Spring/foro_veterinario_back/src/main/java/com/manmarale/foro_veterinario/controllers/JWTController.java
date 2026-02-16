package com.manmarale.foro_veterinario.controllers;
import com.manmarale.foro_veterinario.exceptions.BadRequestExcepton;
import com.manmarale.foro_veterinario.exceptions.ResourceNotFoundException;
import com.manmarale.foro_veterinario.models.Usuario;
import com.manmarale.foro_veterinario.models.dtos.autenticacion.PerfilUsuarioDTO;
import com.manmarale.foro_veterinario.models.dtos.autenticacion.RespuestaAutenticacion;
import com.manmarale.foro_veterinario.models.dtos.autenticacion.SolicitudAutenticacion;
import com.manmarale.foro_veterinario.repository.iUsuarioRepository;
import com.manmarale.foro_veterinario.security.jwt.ProveedorDeToken;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class JWTController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ProveedorDeToken proveedorDeToken;
    private final iUsuarioRepository usuarioRepository;

    @PostMapping(value = "/autenticacion")
    public ResponseEntity<?> autenticacion(@RequestBody @Valid SolicitudAutenticacion solicitudAutenticacion){

        try {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    solicitudAutenticacion.getEmail(),
                    solicitudAutenticacion.getPassword()
            );

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = proveedorDeToken.crearToken(authentication);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+ token);

            Usuario usuario = usuarioRepository
                    .findByEmail(solicitudAutenticacion.getEmail())
                    .orElseThrow(ResourceNotFoundException::new);

            RespuestaAutenticacion respuestaAutenticacion = new RespuestaAutenticacion(token, new PerfilUsuarioDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getPassword(),
                    usuario.getRole(),
                    usuario.getFilePerfil()
            ));

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(respuestaAutenticacion);

        } catch (org.springframework.security.authentication.BadCredentialsException ex){
            throw new BadRequestExcepton("ERROR EMAIL O CONTRASEÑA: INCORRECTA!");
        }

    }

}
