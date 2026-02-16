package com.manmarale.foro_veterinario.exceptions;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

public class BadRequestExcepton extends RuntimeException{

    public BadRequestExcepton(String s) {
        super(s);
    }
    public BadRequestExcepton(String s, DataAccessException e) {
        super(s,e);
    }

    public BadRequestExcepton(String s, IOException e) {
        super(s,e);
    }
}
