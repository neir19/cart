package com.abstracta.opencart.interactions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LlenarFormularioCinema implements Interaction {

    public static Interaction ahora() {
        return Instrumented.instanceOf(LlenarFormularioCinema.class).withProperties();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        seleccionarOpcionRadio(actor);
        seleccionarOpcionCasilla(actor);
        actor.attemptsTo(Enter.theValue("Prueba automatizada").into(PaginaOpenCart.TEXTO_CINEMA));
        seleccionarPrimeraOpcionDisponible(actor);
        actor.attemptsTo(Enter.theValue("Formulario de prueba").into(PaginaOpenCart.AREA_TEXTO_CINEMA));
        cargarArchivo(actor);
        actor.attemptsTo(
                Enter.theValue("2026-10-03").into(PaginaOpenCart.FECHA_CINEMA),
                Enter.theValue("11:44").into(PaginaOpenCart.HORA_CINEMA),
                Enter.theValue("2026-10-03 11:44").into(PaginaOpenCart.FECHA_HORA_CINEMA));
    }

    private void seleccionarOpcionRadio(Actor actor) {
        List<WebElementFacade> opciones =
                PaginaOpenCart.OPCIONES_RADIO_CINEMA.resolveAllFor(actor);
        if (!opciones.isEmpty()) {
            opciones.get(0).click();
        }
    }

    private void seleccionarOpcionCasilla(Actor actor) {
        List<WebElementFacade> opciones =
                PaginaOpenCart.OPCIONES_CHECKBOX_CINEMA.resolveAllFor(actor);
        if (!opciones.isEmpty() && !opciones.get(0).isSelected()) {
            opciones.get(0).click();
        }
    }

    private void seleccionarPrimeraOpcionDisponible(Actor actor) {
        Select lista = new Select(PaginaOpenCart.SELECCION_CINEMA.resolveFor(actor));
        lista.getOptions().stream()
                .filter(opcion -> !opcion.getAttribute("value").isBlank())
                .findFirst()
                .ifPresent(opcion -> lista.selectByValue(opcion.getAttribute("value")));
    }

    private void cargarArchivo(Actor actor) {
        Path archivo = obtenerArchivoPrueba();
        actor.attemptsTo(net.serenitybdd.screenplay.actions.Click.on(
                PaginaOpenCart.BOTON_SUBIR_ARCHIVO_CINEMA));
        var navegador = BrowseTheWeb.as(actor).getDriver();
        WebElementFacade campoArchivo = new WebDriverWait(navegador, Duration.ofSeconds(10))
                .until(driver -> {
                    List<WebElementFacade> campos =
                            PaginaOpenCart.CAMPO_ARCHIVO_SUBIDA.resolveAllFor(actor);
                    return campos.isEmpty() ? null : campos.get(0);
                });
        campoArchivo.sendKeys(archivo.toAbsolutePath().toString());
        new WebDriverWait(navegador, Duration.ofSeconds(20))
                .until(driver -> {
                    String valor = PaginaOpenCart.ARCHIVO_CINEMA_CARGADO
                            .resolveFor(actor)
                            .getAttribute("value");
                    return valor != null && !valor.isBlank();
                });
    }

    private Path obtenerArchivoPrueba() {
        try {
            return Path.of(LlenarFormularioCinema.class
                    .getResource("/archivos/archivo_prueba.txt")
                    .toURI());
        } catch (URISyntaxException | NullPointerException e) {
            throw new IllegalStateException(
                    "No se pudo localizar el archivo de prueba para Apple Cinema 30", e);
        }
    }
}
