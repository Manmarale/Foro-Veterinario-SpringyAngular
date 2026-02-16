package com.manmarale.foro_veterinario.services;
import com.manmarale.foro_veterinario.exceptions.BadRequestExcepton;
import com.manmarale.foro_veterinario.exceptions.ResourceNotFoundException;
import com.manmarale.foro_veterinario.models.Usuario;
import com.manmarale.foro_veterinario.models.dtos.usuario.UsuarioDTO;
import com.manmarale.foro_veterinario.models.dtos.usuario.UsuarioRegistroDTO;
import com.manmarale.foro_veterinario.repository.iUsuarioRepository;
import com.manmarale.foro_veterinario.services.iServices.iUsuarioServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService implements iUsuarioServices {

    private final iUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::manejoRespuestaUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UsuarioDTO> paginate(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(this::manejoRespuestaUsuario);
    }

    @Override
    public UsuarioDTO findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: id no encontrado en la base de datos!"));
        return manejoRespuestaUsuario(usuario);
    }

    public UsuarioDTO save(UsuarioRegistroDTO usuarioRegistroDTO) {
        boolean usuarioExiste =usuarioRepository.existsByEmail(usuarioRegistroDTO.getEmail());

        if (usuarioExiste){
            throw new BadRequestExcepton("El email ya existe!");
        }

        Usuario usuario = null;
        try{
            usuario = new ModelMapper().map(usuarioRegistroDTO,Usuario.class);
            //Usuario usuario = new ModelMapper().map(usuarioRegistroDTO,Usuario.class);
            //usuario = new Usuario();
//            usuario.setNombre(usuarioRegistroDTO.getNombre());
//            usuario.setEmail(usuarioRegistroDTO.getEmail());
//            usuario.setFilePerfil(usuarioRegistroDTO.getFilePerfil());
//            usuario.setRole(usuarioRegistroDTO.getRole());

            usuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
            usuario.setActivo(Boolean.TRUE);
            usuario.setCreatedAt(LocalDateTime.now());
            usuario = usuarioRepository.save(usuario);
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR CREACION: Falla no es posible realizar el proceso!", e);
        }
        return manejoRespuestaUsuario(usuario);
    }

    @Override
    public UsuarioDTO update(Integer id, UsuarioRegistroDTO usuarioRegistroDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));

        boolean usuarioExiste =usuarioRepository.existsByEmailAndIdNot(usuarioRegistroDTO.getEmail(), id);

        if (usuarioExiste){
            throw new BadRequestExcepton("El email ya existe!");
        }

        try {
            if (usuario != null){
                usuario.setNombre(usuarioRegistroDTO.getNombre());
                usuario.setEmail(usuarioRegistroDTO.getEmail());
                usuario.setRole(usuarioRegistroDTO.getRole());
                usuario.setFilePerfil(usuarioRegistroDTO.getFilePerfil());
                usuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
                usuario.setUpdatedAt(LocalDateTime.now());
            }else {
                throw new BadRequestExcepton("ERROR ACTUALIZAR: Usuario no se pudo actualizar!");
            }
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }
        usuario = usuarioRepository.save(usuario);
        return manejoRespuestaUsuario(usuario);
    }


    public ResponseEntity<?> eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuario.desactivarUsuario();
        usuarioRepository.save(usuario); // Assuming you need to save the changes
        return ResponseEntity.noContent().build();
    }


    private UsuarioDTO manejoRespuestaUsuario(Usuario usuario) {

        UsuarioDTO usuarioDTO = new ModelMapper().map(usuario,UsuarioDTO.class);
        //UsuarioDTO  usuarioDTO = new UsuarioDTO();
//        usuarioDTO.setId(usuario.getId());
//        usuarioDTO.setNombre(usuario.getNombre());
//        usuarioDTO.setEmail(usuario.getEmail());
//        usuarioDTO.setPassword(usuario.getPassword());
//        usuarioDTO.setRole(usuario.getRole());
//        usuarioDTO.setFilePerfil(usuario.getFilePerfil());
//        usuarioDTO.setCreatedAt(usuario.getCreatedAt());
//        usuarioDTO.setUpdatedAt(usuario.getUpdatedAt());
        //usuarioDTO.setActivo(usuario.getActivo()); // No se establecen las respuestas ni temas aquí, ya que son datos reducidos
        return  usuarioDTO;
    }
}

