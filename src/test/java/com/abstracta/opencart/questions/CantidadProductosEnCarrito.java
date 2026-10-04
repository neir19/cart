package com.abstracta.opencart.questions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class CantidadProductosEnCarrito implements Question<Integer> {

    public static CantidadProductosEnCarrito actual() {
        return new CantidadProductosEnCarrito();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        // OpenCart agrega cada producto con su cantidad por defecto (cart.add('id','cantidad')),
        // por lo que el carrito se compara por productos y no por unidades.
        return (int) BrowseTheWeb.as(actor)
                .findAll(PaginaOpenCart.CANTIDADES_CARRITO_DESPLEGABLE)
                .stream()
                .filter(elemento -> elemento.isDisplayed())
                .map(elemento -> elemento.getText().trim())
                .filter(texto -> texto.matches("x \\d+"))
                .count();
    }
}
