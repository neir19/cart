package com.abstracta.opencart.questions;

import com.abstracta.opencart.model.CategoriaDisponible;
import com.abstracta.opencart.ui.PaginaOpenCart;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class SubcategoriasDisponibles implements Question<List<CategoriaDisponible>> {

    // OpenCart rotula las categorías sin productos como "Nombre (0)". Descartarlas aquí
    // evita navegar a listados vacíos, donde ninguna Interaction puede agregar un producto.
    private static final String ROTULO_SIN_PRODUCTOS = "(0)";

    private final CategoriaDisponible categoriaPadre;

    private SubcategoriasDisponibles(CategoriaDisponible categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
    }

    public static SubcategoriasDisponibles de(CategoriaDisponible categoriaPadre) {
        return new SubcategoriasDisponibles(categoriaPadre);
    }

    @Override
    public List<CategoriaDisponible> answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor)
                .findAll(PaginaOpenCart.subcategoriasDe(categoriaPadre.url()))
                .stream()
                .filter(enlace -> enlace.isDisplayed() && !enlace.getText().trim().isEmpty())
                .map(enlace -> new CategoriaDisponible(
                        enlace.getText().trim(),
                        enlace.getAttribute("href"),
                        false,
                        categoriaPadre.url()))
                .filter(categoria -> categoria.url() != null && !categoria.url().isBlank())
                .filter(categoria -> !categoria.nombre().endsWith(ROTULO_SIN_PRODUCTOS))
                .toList();
    }
}
