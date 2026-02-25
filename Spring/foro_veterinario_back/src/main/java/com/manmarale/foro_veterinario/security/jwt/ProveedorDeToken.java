package com.manmarale.foro_veterinario.security.jwt;
import com.manmarale.foro_veterinario.security.jwt.ijwt.iProveedorDeToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class ProveedorDeToken implements iProveedorDeToken {
    private final SecretKey key;
    private final JwtParser jwtParser;
    private final long tokenValidacionEnMiliSegundos;
    private static final String CLAVE_AUTORIDAD = "auth";

    public ProveedorDeToken(String jwtSecreto, Long jwtValidacionEnSegundos){
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecreto);

        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parser().verifyWith(key).build();
        tokenValidacionEnMiliSegundos = jwtValidacionEnSegundos * 1000;
    }

    // Crear el Token

    @Override
    public String crearToken(Authentication autenticacion) {
        long now = new Date().getTime();
        Date validez = new Date(now + tokenValidacionEnMiliSegundos);

        String role = autenticacion.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts
                .builder()
                .subject(autenticacion.getName())
                .claim(CLAVE_AUTORIDAD, role)
                .signWith(key, Jwts.SIG.HS512)
                .expiration(validez)
                .compact();
    }

    // Método para obtener la autenticación a partir de un token JWT
    @Override
    public Authentication obtenerAuthenticacion(String token) {
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(CLAVE_AUTORIDAD).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token,authorities);
    }

    // Método para validar la autenticidad de un token JWT
    @Override
    public boolean validacionToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        }catch (JwtException e){
            log.error("Token validation error {}", e.getMessage());
        }
        return false;
    }
}
