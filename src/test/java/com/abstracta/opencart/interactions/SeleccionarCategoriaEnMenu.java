package com.abstracta.opencart.interactions;

import com.abstracta.opencart.model.CategoriaDisponible;
import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SeleccionarCategoriaEnMenu implements Interaction {

    private final CategoriaDisponible categoria;
    private final boolean subcategoriaConMenuAbierto;

    public SeleccionarCategoriaEnMenu(
            CategoriaDisponible categoria, boolean subcategoriaConMenuAbierto) {
        this.categoria = categoria;
        this.subcategoriaConMenuAbierto = subcategoriaConMenuAbierto;
    }

    public static SeleccionarCategoriaEnMenu categoria(CategoriaDisponible categoria) {
        return Instrumented.instanceOf(SeleccionarCategoriaEnMenu.class)
                .withProperties(categoria, false);
    }

    public static SeleccionarCategoriaEnMenu subcategoriaConMenuAbierto(
            CategoriaDisponible categoria) {
        return Instrumented.instanceOf(SeleccionarCategoriaEnMenu.class)
                .withProperties(categoria, true);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (categoria.urlCategoriaPadre() != null) {
            if (subcategoriaConMenuAbierto) {
                actor.attemptsTo(Click.on(PaginaOpenCart.enlaceCategoria(categoria.url())));
                return;
            }
            actor.attemptsTo(
                    Click.on(PaginaOpenCart.enlaceCategoriaPadre(categoria.urlCategoriaPadre())),
                    WaitUntil.the(PaginaOpenCart.enlaceCategoria(categoria.url()), isVisible()),
                    Click.on(PaginaOpenCart.enlaceCategoria(categoria.url())));
            return;
        }
        if (categoria.desplegable()) {
            actor.attemptsTo(
                    Click.on(PaginaOpenCart.enlaceCategoriaPorNombre(categoria.nombre())),
                    WaitUntil.the(PaginaOpenCart.enlaceVerTodoPorNombre(categoria.nombre()), isVisible()),
                    Click.on(PaginaOpenCart.enlaceVerTodoPorNombre(categoria.nombre())));
            return;
        }
        actor.attemptsTo(Click.on(PaginaOpenCart.enlaceCategoriaPorNombre(categoria.nombre())));
    }
}
