package com.bienmesabe.carta;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AvisoService {
    private static final String ETIQUETA_PREDETERMINADA = "Para hoy";
    private static final String BOTON_PREDETERMINADO = "Consultar por WhatsApp";
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path archivo;
    private Aviso aviso;

    public AvisoService(@Value("${carta.aviso.archivo:data/aviso.json}") String archivo,
            @Value("${carta.aviso.activo:true}") boolean activo,
            @Value("${carta.aviso.etiqueta:Para hoy}") String etiqueta,
            @Value("${carta.aviso.boton:Consultar por WhatsApp}") String textoBoton,
            @Value("${carta.aviso.titulo}") String titulo,
            @Value("${carta.aviso.mensaje}") String mensaje) throws IOException {
        this.archivo = Path.of(archivo).toAbsolutePath();
        if (Files.exists(this.archivo)) {
            Aviso guardado = mapper.readValue(this.archivo.toFile(), Aviso.class);
            String etiquetaGuardada = guardado.etiqueta() == null || guardado.etiqueta().isBlank()
                    ? ETIQUETA_PREDETERMINADA : guardado.etiqueta();
                String botonGuardado = guardado.textoBoton() == null || guardado.textoBoton().isBlank()
                    ? BOTON_PREDETERMINADO : guardado.textoBoton();
                aviso = new Aviso(guardado.activo(), etiquetaGuardada, botonGuardado,
                    guardado.titulo(), guardado.mensaje());
        } else {
                aviso = new Aviso(activo, etiqueta, textoBoton, titulo, mensaje);
        }
    }

    public synchronized Aviso get() {
        return aviso;
    }

    public synchronized void guardar(boolean activo, String etiqueta, String textoBoton,
            String titulo, String mensaje) {
        aviso = new Aviso(activo, etiqueta, textoBoton, titulo, mensaje);
        try {
            Files.createDirectories(archivo.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo.toFile(), aviso);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public record Aviso(boolean activo, String etiqueta, String textoBoton, String titulo, String mensaje) {}
}