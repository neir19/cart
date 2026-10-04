package com.abstracta.opencart.questions;

import com.abstracta.opencart.model.CategoriaDisponible;
import com.abstracta.opencart.ui.PaginaOpenCart;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class CategoriasDisponibles implements Question<List<CategoriaDisponible>> {

    public static CategoriasDisponibles enOpenCart() {
        return new CategoriasDisponibles();
    }

    @Override
    public List<CategoriaDisponible> answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).findAll(PaginaOpenCart.CATEGORIAS).stream()
                .map(enlace -> {
                    String nombre = enlace.getText().trim();
                    String clases = enlace.getAttribute("class");
                    String url = enlace.getAttribute("href");
                    return new CategoriaDisponible(
                            nombre,
                            url,
                            tieneClase(clases, "dropdown-toggle"),
                            null);
                })
                .filter(categoria -> !categoria.nombre().isEmpty()
                        && categoria.url() != null
                        && !categoria.url().isBlank()
                        && !categoria.nombre().equalsIgnoreCase("Software")
                        && !categoria.nombre().equalsIgnoreCase("Components")
                        && !categoria.nombre().endsWith("(0)"))
                .toList();
    }

    private boolean tieneClase(String clases, String clase) {
        return clases != null && List.of(clases.split("\\s+")).contains(clase);
    }
}
