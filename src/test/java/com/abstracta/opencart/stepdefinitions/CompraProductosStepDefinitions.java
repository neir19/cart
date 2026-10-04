package com.abstracta.opencart.stepdefinitions;

i
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class CompraProductosStepDefinitions {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("cliente");
    }

    @Dado("que el cliente ingresa a la página de OpenCart")
    public void elClienteIngresaAOpenCart() {
        OnStage.theActorInTheSpotlight().attemptsTo(IngresarAOpenCart.ahora());

    }

    @Cuando("selecciona aleatoriamente {int} categorías disponibles y agrega un producto de cada una al carrito")
    public void agregaProductosDeCategoriasAleatorias(int cantidad) {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(
                        AgregarProductosDeCategoriasAleatorias.cantidad(cantidad),
                        DesplegarCarrito.desdeLaCabecera());
    }


    @Cuando("finaliza la compra")
    public void finalizaLaCompra() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(FinalizarCompraComoInvitado.ahora());
    }

    @Entonces("debe visualizar el mensaje de confirmación de compra exitosa")
    public void visualizaConfirmacionDeCompraExitosa() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(CompraConfirmada.exitosamente(), is(true)));
    }
}
