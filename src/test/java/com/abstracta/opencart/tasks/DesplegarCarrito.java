package com.abstracta.opencart.tasks;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.Wait;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class DesplegarCarrito implements Task {

    public static DesplegarCarrito desdeLaCabecera() {
        return Instrumented.instanceOf(DesplegarCarrito.class).withProperties();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.BOTON_CABECERA_CARRITO, isVisible()),
                Click.on(PaginaOpenCart.BOTON_CABECERA_CARRITO),
                WaitUntil.the(PaginaOpenCart.MENU_CARRITO_DESPLEGABLE, isVisible())
        );
    }
}
