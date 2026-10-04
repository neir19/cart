package com.abstracta.opencart.questions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class CompraConfirmada implements Question<Boolean> {

    public static CompraConfirmada exitosamente() {
        return new CompraConfirmada();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).findAll(PaginaOpenCart.MENSAJE_CONFIRMACION_COMPRA).stream()
                .anyMatch(mensaje -> mensaje.isDisplayed()
                        && mensaje.getText().trim().equalsIgnoreCase("Your order has been placed!"));
    }
}
