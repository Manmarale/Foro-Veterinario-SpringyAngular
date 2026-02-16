package com.manmarale.foro_veterinario.controllers;
import com.manmarale.foro_veterinario.models.dtos.usuario.UsuarioDTO;
import com.manmarale.foro_veterinario.models.dtos.usuario.UsuarioRegistroDTO;
import com.manmarale.foro_veterinario.services.iServices.iUsuarioServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/usuario")
public class UsuarioController {

    @Autowired
    private iUsuarioServices usuarioServices;

    @GetMapping(value = "/list")
    private List<UsuarioDTO> findAll(){
        return usuarioServices.findAll();
    }

    @GetMapping
    public Page<UsuarioDTO> paginate(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        return usuarioServices.paginate(pageable);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    private UsuarioDTO findById(@PathVariable(value = "id") Integer id){
        return usuarioServices.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private UsuarioDTO save(@RequestBody @Valid UsuarioRegistroDTO usuarioRegistroDTO){
        return usuarioServices.save(usuarioRegistroDTO);
    }

    @PutMapping(value = "/{id}")
    private UsuarioDTO update(@PathVariable(value = "id") Integer id,@RequestBody @Valid UsuarioRegistroDTO usuarioRegistroDTO){
        return usuarioServices.update(id, usuarioRegistroDTO);
    }

//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    Boolean delete(@PathVariable(value = "id") Integer id){
//        return usuarioServices.delete(id);
//    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> eliminarUsuario(@PathVariable(value = "id") Integer id){
        return usuarioServices.eliminarUsuario(id);
    }

}
