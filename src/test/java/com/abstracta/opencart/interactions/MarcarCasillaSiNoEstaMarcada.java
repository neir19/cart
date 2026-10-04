package com.abstracta.opencart.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;

public class MarcarCasillaSiNoEstaMarcada implements Interaction {

    private final Target casilla;

    public MarcarCasillaSiNoEstaMarcada(Target casilla) {
        this.casilla = casilla;
    }

    public static MarcarCasillaSiNoEstaMarcada la(Target casilla) {
        return Instrumented.instanceOf(MarcarCasillaSiNoEstaMarcada.class)
                .withProperties(casilla);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var elemento = BrowseTheWeb.as(actor).find(casilla);
        if (!elemento.isSelected()) {
            elemento.click();
        }
    }
}
