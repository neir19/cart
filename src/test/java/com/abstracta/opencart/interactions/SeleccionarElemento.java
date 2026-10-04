package com.abstracta.opencart.interactions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.Random;

import java.util.List;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;


public class SeleccionarElemento implements Interaction {
 private final  Target elemento;

    public SeleccionarElemento(Target elemento) {
        this.elemento = elemento;
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        // Implement the interaction logic here
        actor.attemptsTo(WaitUntil.the(elemento, isVisible()).forNoMoreThan(7).seconds());
        List<WebElementFacade> elementos = elemento.resolveAllFor(actor);
        System.out.printf("Cantidad de elementos encontrados: %d%n", elementos.size());
           if (elementos.isEmpty() || elementos.size() == 0) {
               throw new IllegalStateException("No se encontraron elementos para seleccionar");
           }
           WebElementFacade elementoSeleccionado = elementos.get(new Random().nextInt(elementos.size()));
           String clases = elementoSeleccionado.getAttribute("class");
           boolean esDropdown = clases != null && clases.contains("dropdown-toggle");
           boolean esProducto = clases != null && clases.contains("product-thumb");
           actor.attemptsTo(
                   Scroll.to(elementoSeleccionado),
                   Click.on(elementoSeleccionado)
           );

           if (esDropdown) {
               actor.attemptsTo(Click.on(PaginaOpenCart.SUBCATEGORIAS));
           }
           else if(esProducto)
           {
                actor.attemptsTo(ValidarDataProducto.agregar().newInstance());
           }



    }
    public static Performable seleccionar(Target elemento) {
        return Instrumented.instanceOf(SeleccionarElemento.class)
                .withProperties(elemento);
    }
}
