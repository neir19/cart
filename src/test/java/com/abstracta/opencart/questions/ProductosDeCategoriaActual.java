package com.abstracta.opencart.questions;

import com.abstracta.opencart.model.ProductoDisponible;
import com.abstracta.opencart.ui.PaginaOpenCart;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class ProductosDeCategoriaActual implements Question<List<ProductoDisponible>> {

    private static final Pattern IDENTIFICADOR_PRODUCTO =
            Pattern.compile("[?&]product_id=(\\d+)");

    public static ProductosDeCategoriaActual disponibles() {
        return new ProductosDeCategoriaActual();
    }

    @Override
    public List<ProductoDisponible> answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).findAll(PaginaOpenCart.ENLACES_PRODUCTO_CATEGORIA).stream()
                .map(enlace -> productoDesde(enlace.getText().trim(),
                        enlace.getAttribute("href")))
                .toList();
    }

    private ProductoDisponible productoDesde(String nombre, String url) {
        Matcher coincidencia = IDENTIFICADOR_PRODUCTO.matcher(url);
        if (!coincidencia.find()) {
            throw new IllegalStateException(
                    "No se pudo obtener el identificador del producto: " + url);
        }
        return new ProductoDisponible(nombre, coincidencia.group(1));
    }
}
