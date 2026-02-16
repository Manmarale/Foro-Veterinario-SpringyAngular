package com.manmarale.foro_veterinario.services;
import com.manmarale.foro_veterinario.models.dtos.tema.Genero;
import com.manmarale.foro_veterinario.models.dtos.tema.TemaDto;
import com.manmarale.foro_veterinario.services.iServices.iHomeService;
import java.time.LocalDate;
import java.util.List;

public class HomeService implements iHomeService {

    @Override
    public List<TemaDto> findByGenero(Genero genero) {
        return null;
    }

    @Override
    public List<TemaDto> getTemasByDate(LocalDate localDate) {
        return null;
    }
}
