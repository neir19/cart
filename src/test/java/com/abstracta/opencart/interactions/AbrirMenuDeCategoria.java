package com.abstracta.opencart.interactions;

import com.abstracta.opencart.model.CategoriaDisponible;
import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AbrirMenuDeCategoria implements Interaction {

    private final CategoriaDisponible categoria;

    public AbrirMenuDeCategoria(CategoriaDisponible categoria) {
        this.categoria = categoria;
    }

    public static AbrirMenuDeCategoria de(CategoriaDisponible categoria) {
        return Instrumented.instanceOf(AbrirMenuDeCategoria.class)
                .withProperties(categoria);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PaginaOpenCart.enlaceCategoria(categoria.url())),
                WaitUntil.the(PaginaOpenCart.subcategoriasDe(categoria.url()), isVisible()));
    }
}
