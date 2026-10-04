package com.abstracta.opencart.model;

public record CategoriaDisponible(
        String nombre,
        String url,
        boolean desplegable,
        String urlCategoriaPadre) {
}
