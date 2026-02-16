package com.manmarale.foro_veterinario.services.iServices;
import com.manmarale.foro_veterinario.models.dtos.tema.Genero;
import com.manmarale.foro_veterinario.models.dtos.tema.TemaDto;

import java.time.LocalDate;
import java.util.List;

public interface iHomeService {

    List<TemaDto> findByGenero(Genero genero);
    List<TemaDto> getTemasByDate(LocalDate localDate);


}
