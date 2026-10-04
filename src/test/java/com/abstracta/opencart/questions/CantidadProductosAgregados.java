package com.abstracta.opencart.questions;

import com.abstracta.opencart.constants.MemoriaCompraClave;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CantidadProductosAgregados implements Question<Integer> {

    public static CantidadProductosAgregados enElEscenario() {
        return new CantidadProductosAgregados();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        Integer cantidad = actor.recall(MemoriaCompraClave.CANTIDAD_PRODUCTOS_AGREGADOS);
        return cantidad == null ? 0 : cantidad;
    }
}
