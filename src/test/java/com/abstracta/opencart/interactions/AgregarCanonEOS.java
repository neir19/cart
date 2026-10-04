package com.abstracta.opencart.interactions;


import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;

public class AgregarCanonEOS  implements Interaction {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PaginaOpenCart.SELECT),
                SeleccionarElemento.seleccionar(PaginaOpenCart.COLOR),
                Click.on(PaginaOpenCart.ADDCARRITO));

    }
    public static Instrumented.InstrumentedBuilder<AgregarCanonEOS> agregar() {
        return Instrumented.instanceOf(AgregarCanonEOS.class);
    }

}
