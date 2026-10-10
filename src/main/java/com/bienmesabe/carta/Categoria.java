package com.bienmesabe.carta;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String nombre, sub;
    private boolean consultar = true; // mostrar "Consultar" cuando no hay precio
    private List<Item> items = new ArrayList<>();

    public String getNombre() { return nombre; }
    public void setNombre(String v) { nombre = v; }
    public String getSub() { return sub; }
    public void setSub(String v) { sub = v; }
    public boolean isConsultar() { return consultar; }
    public void setConsultar(boolean v) { consultar = v; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> v) { items = v; }
}
