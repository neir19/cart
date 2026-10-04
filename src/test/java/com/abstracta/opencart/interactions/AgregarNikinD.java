package com.abstracta.opencart.interactions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.ClickAndHold;

public class AgregarNikinD  implements Interaction {
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(PaginaOpenCart.ADDCARRITO));

    }
    public static Instrumented.InstrumentedBuilder<AgregarNikinD> agregar() {
        return Instrumented.instanceOf(AgregarNikinD.class);
    }
}
