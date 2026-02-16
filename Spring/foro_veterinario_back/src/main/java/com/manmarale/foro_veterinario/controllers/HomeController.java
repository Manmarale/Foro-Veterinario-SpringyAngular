package com.manmarale.foro_veterinario.controllers;
import com.manmarale.foro_veterinario.exceptions.ResourceNotFoundException;
import com.manmarale.foro_veterinario.models.Tema;
import com.manmarale.foro_veterinario.models.dtos.tema.Genero;
import com.manmarale.foro_veterinario.models.dtos.tema.TemaDto;
import com.manmarale.foro_veterinario.repository.iTemaRepository;
import com.manmarale.foro_veterinario.services.iServices.iHomeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/home")
@AllArgsConstructor
public class HomeController implements iHomeService {

    private final iTemaRepository temaRepository;

    @Override
    @GetMapping("/categoria/{genero}")
    public List<TemaDto> findByGenero(@PathVariable(value = "genero") Genero genero) {

        List<Tema> temas = temaRepository.findByGenero(genero);

        if (temas == null || temas.isEmpty()){
            throw new ResourceNotFoundException("Genero no encontrado!");
        }else {
            return temas.stream()
                    .map(this::manejorRespuestaClienteCorta)// Transforma cada Tema a TemaDto reducido
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/fecha/{date}")
    public List<TemaDto> getTemasByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Tema> temasPorFecha = temaRepository.findTemasByDate(date);

        if (temasPorFecha == null || temasPorFecha.isEmpty()){
            throw new ResourceNotFoundException("No hay Temas en la fecha ingresada!");
        }else {
            return temasPorFecha.stream()
                    .map(this::manejorRespuestaClienteCorta)// Transforma cada Tema a TemaDto reducido
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/last-temas")
    List<TemaDto> getLastTemas(){
        List<Tema> temas =temaRepository.findTop9ByOrderByCreatedAtDesc();

        if (temas == null || temas.isEmpty()){
            throw new ResourceNotFoundException("Temas no encontrados!");
        }else {
            return temas.stream()
                    .map(this::manejorRespuestaClienteCorta)// Transforma cada Tema a TemaDto reducido
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/topicos")
    private Page<TemaDto> paginate(@PageableDefault(sort = "createdAt",direction = Sort.Direction.ASC ,size = 10 ) Pageable pageable){
        Page<Tema> temas =temaRepository.findAll(pageable);
       return temas.map(this::manejorRespuestaClienteCorta);

    }

    private TemaDto manejorRespuestaClienteCorta(Tema tema) {
        TemaDto temaDto = new ModelMapper().map(tema, TemaDto.class);
        //TemaDto temaDto = new TemaDto();
//        temaDto.setId(tema.getId());
//        temaDto.setTitulo(tema.getTitulo());
//        temaDto.setMensaje(tema.getMensaje());
//        temaDto.setGenero(tema.getGenero());
//        temaDto.setUsuarioId(tema.getUsuarioId().getId());
//        temaDto.setUsuarioNombre(tema.getUsuarioId().getNombre());
          temaDto.setFilePerfil(tema.getUsuarioId().getFilePerfil());
//        temaDto.setCreatedAt(tema.getCreatedAt());
//        temaDto.setUpdatedAt(tema.getUpdatedAt());
//        temaDto.setActivo(tema.getActivo());
        //temaDto.setRespuestas(tema.getRespuestas()); // Aquí puedes ajustar según tus necesidades
        return temaDto;
    }
}
