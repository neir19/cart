package com.abstracta.opencart.questions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

public class CompraConfirmada implements Question<Boolean> {

    public static CompraConfirmada exitosamente() {
        return new CompraConfirmada();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return Visibility.of(PaginaOpenCart.MENSAJE_CONFIRMACION_COMPRA)
                .answeredBy(actor);
    }
}
