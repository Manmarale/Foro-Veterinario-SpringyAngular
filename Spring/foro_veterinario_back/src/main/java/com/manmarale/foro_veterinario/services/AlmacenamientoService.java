package com.manmarale.foro_veterinario.services;
import com.manmarale.foro_veterinario.exceptions.BadRequestExcepton;
import com.manmarale.foro_veterinario.exceptions.ResourceNotFoundException;
import com.manmarale.foro_veterinario.services.iServices.iAlmacenamientoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
//por si falla poner @AllArgsConstructor
public class AlmacenamientoService implements iAlmacenamientoService {

    //private final static String ALMACENAMIENTO_LOCAL = "mediafile";

    @Value("${app.foro-veterinario.location}")
    private String almacenamientoLocal;

    @PostConstruct
    @Override
    public void iniciar() {
        try {
            Files.createDirectories(Paths.get(almacenamientoLocal));
        } catch (IOException e) {
            throw new BadRequestExcepton("ERROR ALMACENAMIENTO: Falla al inicializar ruta de almacenamiento!", e);
        }
    }

    @Override
    public String almacenar(MultipartFile archivo) {
        String nombreArchivoOriginal = archivo.getOriginalFilename();
        String nombreArchivo = UUID.randomUUID()+"."+ StringUtils.getFilenameExtension(nombreArchivoOriginal);

        if (archivo.isEmpty()){
            throw new ResourceNotFoundException("ERROR VACIO: No es posible almacenar un archivo vacio!");
        }

        try {
            InputStream entrada = archivo.getInputStream();
            Files.copy(entrada, Paths.get(almacenamientoLocal).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BadRequestExcepton("ERROR:Falla al almacener el archivo ",e);
        }
        return nombreArchivo;
    }

    @Override
    public Path cargar(String nombreArchivo) {
        return Paths.get(almacenamientoLocal).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarRecurso(String nombreArchivo) {
        try {
            Path archivo = cargar(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());

            if (recurso.exists() || recurso.isReadable()){
                return recurso;
            }else {
                throw new ResourceNotFoundException("ERROR LECTURA: No es posible leer el archivo! " + nombreArchivo);
            }
        }catch (MalformedURLException e){
            throw new BadRequestExcepton("ERROR: No se puede leer el archivo! "+nombreArchivo, e);
        }
    }

    @Override
    public void eliminarRecurso(String nombreArchivo) {
        Path archivo = cargar(nombreArchivo);
        try {
            FileSystemUtils.deleteRecursively(archivo);
        } catch (IOException e) {
            throw new BadRequestExcepton("ERROR: No se puede eliminar el archivo! " + nombreArchivo);
        }
    }
}
