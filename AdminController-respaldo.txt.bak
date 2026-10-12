package com.bienmesabe.carta;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MenuService svc;
    private final AvisoService avisos;

    public AdminController(MenuService svc, AvisoService avisos) {
        this.svc = svc;
        this.avisos = avisos;
    }

    @GetMapping
    public String panel(Model m) {
        m.addAttribute("categorias", svc.getMenu());
        AvisoService.Aviso aviso = avisos.get();
        m.addAttribute("avisoActivo", aviso.activo());
        m.addAttribute("avisoEtiqueta", aviso.etiqueta());
        m.addAttribute("avisoTextoBoton", aviso.textoBoton());
        m.addAttribute("avisoTitulo", aviso.titulo());
        m.addAttribute("avisoMensaje", aviso.mensaje());
        return "admin";
    }

    @PostMapping("/aviso")
    public String guardarAviso(@RequestParam(defaultValue = "false") boolean activo,
            @RequestParam String etiqueta, @RequestParam String textoBoton,
            @RequestParam String titulo, @RequestParam String mensaje) {
        avisos.guardar(activo, etiqueta.trim(), textoBoton.trim(), titulo.trim(), mensaje.trim());
        return "redirect:/admin?avisoActualizado";
    }

    @PostMapping("/categoria/nueva")
    public String nuevaCategoria(@RequestParam String nombre) {
        Categoria c = new Categoria();
        c.setNombre(nombre.trim());
        svc.getMenu().add(c);
        return fin("");
    }

    @PostMapping("/categoria/{c}/editar")
    public String editarCategoria(@PathVariable int c, @RequestParam String nombre,
            @RequestParam(required = false) String sub,
            @RequestParam(defaultValue = "false") boolean consultar) {
        Categoria cat = svc.getMenu().get(c);
        cat.setNombre(nombre.trim());
        cat.setSub(vacio(sub));
        cat.setConsultar(consultar);
        return fin("c" + c);
    }

    @PostMapping("/categoria/{c}/borrar")
    public String borrarCategoria(@PathVariable int c) {
        svc.getMenu().remove(c);
        return fin("");
    }

    @PostMapping("/categoria/{c}/item/nuevo")
    public String nuevoItem(@PathVariable int c, Item item) {
        normalizar(item);
        svc.getMenu().get(c).getItems().add(item);
        return fin("c" + c);
    }

    @PostMapping("/categoria/{c}/item/{i}/editar")
    public String editarItem(@PathVariable int c, @PathVariable int i, Item item) {
        normalizar(item);
        svc.getMenu().get(c).getItems().set(i, item);
        return fin("c" + c);
    }

    @PostMapping("/categoria/{c}/item/{i}/borrar")
    public String borrarItem(@PathVariable int c, @PathVariable int i) {
        svc.getMenu().get(c).getItems().remove(i);
        return fin("c" + c);
    }

    private void normalizar(Item it) {
        it.setNombre(it.getNombre().trim());
        it.setDescripcion(vacio(it.getDescripcion()));
        it.setFoto(vacio(it.getFoto()));
    }

    private String vacio(String s) { return s == null || s.isBlank() ? null : s.trim(); }

    private String fin(String ancla) {
        svc.guardar();
        return "redirect:/admin" + (ancla.isEmpty() ? "" : "#" + ancla);
    }
}
