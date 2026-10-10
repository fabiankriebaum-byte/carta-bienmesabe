package com.bienmesabe.carta;

public class Item {
    private String nombre, descripcion, foto;
    private Integer precio; // null = "Consultar"

    public String getNombre() { return nombre; }
    public void setNombre(String v) { nombre = v; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String v) { descripcion = v; }
    public String getFoto() { return foto; }
    public void setFoto(String v) { foto = v; }
    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer v) { precio = v; }
}
