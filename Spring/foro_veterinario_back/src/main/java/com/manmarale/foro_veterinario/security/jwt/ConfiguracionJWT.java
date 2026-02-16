package com.manmarale.foro_veterinario.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ConfiguracionJWT extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final ProveedorDeToken proveedorDeToken;

    public ConfiguracionJWT(ProveedorDeToken proveedorDeToken){
        this.proveedorDeToken = proveedorDeToken;
    }

    public void configure(HttpSecurity http){
        FiltroJWT filtroPersonalizado = new FiltroJWT(proveedorDeToken);
        http.addFilterBefore(filtroPersonalizado, UsernamePasswordAuthenticationFilter.class);
    }
}
