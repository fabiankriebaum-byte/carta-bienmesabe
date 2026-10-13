
package com.bienmesabe.carta;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppConfigService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Path archivo;
    private Config config;

    public WhatsAppConfigService(
            @Value("${carta.whatsapp.archivo:data/whatsapp.json}") String archivo,
            @Value("${carta.whatsapp:5493705102828}") String telefono,
            @Value("${carta.whatsapp.boton-activo:true}") boolean botonActivo) throws IOException {

        this.archivo = Path.of(archivo).toAbsolutePath();

        if (Files.exists(this.archivo)) {
            config = mapper.readValue(this.archivo.toFile(), Config.class);
            if (config.telefono() == null || config.telefono().isBlank()) {
                config = new Config(telefono, config.botonActivo());
            }
        } else {
            config = new Config(telefono, botonActivo);
        }
    }

    public synchronized Config get() {
        return config;
    }

    public synchronized void guardar(String telefono, boolean botonActivo) {
        if (telefono == null || !telefono.matches("[0-9]{8,15}")) {
            throw new IllegalArgumentException(
                    "Ingresá el número con código de país, solo números y sin el signo +.");
        }

        config = new Config(telefono, botonActivo);

        try {
            Path carpeta = archivo.getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(archivo.toFile(), config);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public record Config(String telefono, boolean botonActivo) {}
}
