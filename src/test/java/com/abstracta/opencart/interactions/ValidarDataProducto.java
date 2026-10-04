package com.abstracta.opencart.interactions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ValidarDataProducto  implements Interaction {

    @Override
    public <T extends Actor> void performAs(T actor) {

        if (PaginaOpenCart.TITULO_PRODUCTO.of("Apple Cinema 30")
                .resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible)) {
            actor.attemptsTo(LlenarFormularioCinema.ahora());
            actor.attemptsTo(Click.on(PaginaOpenCart.ADDCARRITO));
        } else if (PaginaOpenCart.TITULO_PRODUCTO.of("Canon EOS 5D")
                .resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible)) {
            actor.attemptsTo(AgregarCanonEOS.agregar().newInstance());
        } else if (PaginaOpenCart.TITULO_PRODUCTO.of("Product 8")
                .resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible)) {
            actor.attemptsTo(AgregarProduct8.agregar().newInstance());
        } else if (PaginaOpenCart.TITULO_PRODUCTO.of("Nikon D300")
                .resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible)) {
            actor.attemptsTo(AgregarNikinD.agregar().newInstance());
        } else if (PaginaOpenCart.TITULO_PRODUCTO.of("HP LP3065")
                .resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible)) {
            actor.attemptsTo(AgregarNikinD.agregar().newInstance());
        } else {
            actor.attemptsTo(Click.on(PaginaOpenCart.ADDCARRITO));
        }
    }

    public static Instrumented.InstrumentedBuilder<ValidarDataProducto> agregar() {
        return Instrumented.instanceOf(ValidarDataProducto.class);
    }
}