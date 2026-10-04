package com.abstracta.opencart.tasks;

import com.abstracta.opencart.constants.MemoriaCompraClave;
import com.abstracta.opencart.interactions.IntentarAgregarProductoDesdeCategoria;
import com.abstracta.opencart.interactions.SeleccionarCategoriaEnMenu;
import com.abstracta.opencart.model.CategoriaDisponible;
import com.abstracta.opencart.model.ProductoDisponible;
import com.abstracta.opencart.questions.ProductoAgregadoExitosamente;
import com.abstracta.opencart.questions.ProductosDeCategoriaActual;
import com.abstracta.opencart.ui.PaginaOpenCart;
import java.util.ArrayList;
import java.util.List;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AgregarProductoAleatorioDeCategoria implements Task {

    private final CategoriaDisponible categoria;

    public AgregarProductoAleatorioDeCategoria(CategoriaDisponible categoria) {
        this.categoria = categoria;
    }

    public static AgregarProductoAleatorioDeCategoria en(CategoriaDisponible categoria) {
        return Instrumented.instanceOf(AgregarProductoAleatorioDeCategoria.class)
                .withProperties(categoria);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        List<ProductoDisponible> productos = obtenerProductos(actor);
        if (productos.isEmpty()) {
            throw new IllegalStateException(
                    "No se encontraron productos en la categoría " + categoria.nombre());
        }

        while (!productos.isEmpty()) {
            actor.attemptsTo(SeleccionarProductoAleatorioDeCategoria.entre(productos));
            ProductoDisponible producto =
                    actor.recall(MemoriaCompraClave.PRODUCTO_SELECCIONADO);
            productos.remove(producto);
            actor.attemptsTo(
                    SeleccionarCategoriaEnMenu.categoria(categoria),
                    WaitUntil.the(PaginaOpenCart.TITULO_CATEGORIA, isVisible()),
                    IntentarAgregarProductoDesdeCategoria.producto(producto.identificador()));
            
            org.openqa.selenium.WebDriver driver = net.serenitybdd.screenplay.abilities.BrowseTheWeb.as(actor).getDriver();
            if (driver.getCurrentUrl().contains("route=product/product")) {
                actor.attemptsTo(com.abstracta.opencart.interactions.ValidarDataProducto.agregar().newInstance());
                try {
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(navegador ->
                            net.serenitybdd.screenplay.abilities.BrowseTheWeb.as(actor)
                                    .findAll(PaginaOpenCart.ALERTA_PRODUCTO_AGREGADO).stream()
                                    .anyMatch(org.openqa.selenium.WebElement::isDisplayed));
                } catch (Exception e) {
                    // Ignore, let ProductoAgregadoExitosamente handle it
                }
            }

            if (actor.asksFor(ProductoAgregadoExitosamente.enLaCategoria())) {
                return;
            }
        }
        throw new IllegalStateException(
                "No se pudo agregar ningún producto de la categoría " + categoria.nombre());
    }

    private List<ProductoDisponible> obtenerProductos(Actor actor) {
        actor.attemptsTo(
                SeleccionarCategoriaEnMenu.categoria(categoria),
                WaitUntil.the(PaginaOpenCart.TITULO_CATEGORIA, isVisible()));
        return new ArrayList<>(actor.asksFor(ProductosDeCategoriaActual.disponibles()));
    }
}
