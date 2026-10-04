package com.abstracta.opencart.questions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class ProductoAgregadoExitosamente implements Question<Boolean> {

    public static ProductoAgregadoExitosamente enLaCategoria() {
        return new ProductoAgregadoExitosamente();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).findAll(PaginaOpenCart.ALERTA_PRODUCTO_AGREGADO).stream()
                .anyMatch(alerta -> alerta.isDisplayed());
    }
}
