package com.bienmesabe.carta;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/** Guarda el menú en un archivo JSON (sin base de datos). */
@Service
public class MenuService {
    private final ObjectMapper om = new ObjectMapper();
    private final Path file;
    private final List<Categoria> menu;

    public MenuService(@Value("${carta.archivo}") String archivo) throws IOException {
        file = Path.of(archivo).toAbsolutePath();
        if (!Files.exists(file)) {
            Files.createDirectories(file.getParent());
            try (var in = new ClassPathResource("menu-inicial.json").getInputStream()) {
                Files.copy(in, file);
            }
        }
        menu = om.readValue(file.toFile(), new TypeReference<List<Categoria>>() {});
    }

    public synchronized List<Categoria> getMenu() { return menu; }

    public synchronized void guardar() {
        try {
            om.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), menu);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
