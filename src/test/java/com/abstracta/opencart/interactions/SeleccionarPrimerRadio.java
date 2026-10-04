package com.abstracta.opencart.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebElement;

public class SeleccionarPrimerRadio implements Interaction {

    private final Target opciones;

    public SeleccionarPrimerRadio(Target opciones) {
        this.opciones = opciones;
    }

    public static SeleccionarPrimerRadio de(Target opciones) {
        return Instrumented.instanceOf(SeleccionarPrimerRadio.class)
                .withProperties(opciones);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var opcionesDisponibles = BrowseTheWeb.as(actor).findAll(opciones).stream()
                .filter(WebElement::isEnabled)
                .toList();

        WebElement opcionSeleccionada = opcionesDisponibles.stream()
                .filter(WebElement::isSelected)
                .findFirst()
                .orElseGet(() -> opcionesDisponibles.stream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(
                                "No hay opciones de selección disponibles")));

        if (!opcionSeleccionada.isSelected()) {
            opcionSeleccionada.click();
        }
    }
}
