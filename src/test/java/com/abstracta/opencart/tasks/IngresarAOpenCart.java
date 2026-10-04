package com.abstracta.opencart.tasks;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class IngresarAOpenCart implements Task {

    public static IngresarAOpenCart ahora() {
        return Instrumented.instanceOf(IngresarAOpenCart.class).withProperties();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url("https://opencart.abstracta.us/"),
                WaitUntil.the(PaginaOpenCart.LOGO, isVisible()));
    }
}
