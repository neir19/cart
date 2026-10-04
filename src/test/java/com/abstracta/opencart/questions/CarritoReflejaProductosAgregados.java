package com.abstracta.opencart.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CarritoReflejaProductosAgregados implements Question<Boolean> {

    public static CarritoReflejaProductosAgregados correctamente() {
        return new CarritoReflejaProductosAgregados();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return actor.asksFor(CantidadProductosEnCarrito.actual())
                .equals(actor.asksFor(CantidadProductosAgregados.enElEscenario()));
    }
}
