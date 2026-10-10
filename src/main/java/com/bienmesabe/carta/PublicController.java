package com.bienmesabe.carta;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {
    private final MenuService menu;
    private final AvisoService avisos;
    @Value("${carta.nombre}") String nombre;
    @Value("${carta.direccion}") String direccion;
    @Value("${carta.telefono1}") String telefono1;
    @Value("${carta.telefono2}") String telefono2;
    @Value("${carta.whatsapp}") String whatsapp;
    @Value("${carta.mensaje-whatsapp}") String mensaje;
    @Value("${carta.foto-frente}") String fotoFrente;

    public PublicController(MenuService menu, AvisoService avisos) {
        this.menu = menu;
        this.avisos = avisos;
    }

    @GetMapping("/")
    public String carta(Model m) {
        m.addAttribute("categorias", menu.getMenu());
        m.addAttribute("nombre", nombre);
        m.addAttribute("direccion", direccion);
        m.addAttribute("telefono1", telefono1);
        m.addAttribute("telefono2", telefono2);
        m.addAttribute("fotoFrente", fotoFrente);
        AvisoService.Aviso aviso = avisos.get();
        m.addAttribute("avisoActivo", aviso.activo());
        m.addAttribute("avisoEtiqueta", aviso.etiqueta());
        m.addAttribute("avisoTextoBoton", aviso.textoBoton());
        m.addAttribute("avisoTitulo", aviso.titulo());
        m.addAttribute("avisoMensaje", aviso.mensaje());
        m.addAttribute("waUrl", "https://wa.me/" + whatsapp + "?text="
                + URLEncoder.encode(mensaje, StandardCharsets.UTF_8));
        return "menu";
    }
}
