package com.manmarale.foro_veterinario.services.iServices;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

public interface iMediaController {

    Map<String, String> upload(MultipartFile multipartFile);
    ResponseEntity<Resource> getResource(String nombreArchivo) throws IOException;
}
