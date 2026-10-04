package com.abstracta.opencart.interactions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import java.time.Duration;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IntentarAgregarProductoDesdeCategoria implements Interaction {

    private final String identificadorProducto;

    public IntentarAgregarProductoDesdeCategoria(String identificadorProducto) {
        this.identificadorProducto = identificadorProducto;
    }

    public static IntentarAgregarProductoDesdeCategoria producto(String identificadorProducto) {
        return Instrumented.instanceOf(IntentarAgregarProductoDesdeCategoria.class)
                .withProperties(identificadorProducto);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        actor.attemptsTo(
                Click.on(PaginaOpenCart.botonAgregarProductoListado(identificadorProducto))
        );

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(navegador ->
                BrowseTheWeb.as(actor)
                                .findAll(PaginaOpenCart.ALERTA_PRODUCTO_AGREGADO).stream()
                                .anyMatch(WebElement::isDisplayed)
                        || (navegador.getCurrentUrl().contains("route=product/product")
                                && navegador.getCurrentUrl().contains(
                                        "product_id=" + identificadorProducto)));
    }
}
