package com.abstracta.opencart.interactions;

import java.time.Duration;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleccionarOpcionDesplegable implements Interaction {

    private final Target lista;
    private final String opcion;

    public SeleccionarOpcionDesplegable(Target lista, String opcion) {
        this.lista = lista;
        this.opcion = opcion;
    }

    public static SeleccionarOpcionDesplegable visible(Target lista, String opcion) {
        return Instrumented.instanceOf(SeleccionarOpcionDesplegable.class)
                .withProperties(lista, opcion);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var navegador = BrowseTheWeb.as(actor).getDriver();
        WebDriverWait espera = new WebDriverWait(navegador, Duration.ofSeconds(10));
        espera.ignoring(StaleElementReferenceException.class);
        espera.until(driver -> {
            try {
                var elementos = BrowseTheWeb.as(actor).findAll(lista);
                if (elementos.isEmpty()) {
                    return false;
                }
                Select select = new Select(elementos.get(0));
                boolean tieneOpcion = select.getOptions().stream()
                        .anyMatch(opcionLista -> opcionLista.getText().trim().equals(opcion));
                if (tieneOpcion) {
                    select.selectByVisibleText(opcion);
                    return true;
                }
                return false;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }
}
