package com.abstracta.opencart.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import com.abstracta.opencart.questions.CompraConfirmada;
import com.abstracta.opencart.tasks.AgregarProductosDeCategoriasAleatorias;
import com.abstracta.opencart.tasks.DesplegarCarrito;
import com.abstracta.opencart.tasks.FinalizarCompraComoInvitado;
import com.abstracta.opencart.tasks.IngresarAOpenCart;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.is;

public class CompraProductosStepDefinitions {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("cliente");
    }

    @Dado("que el cliente ingresa a la página de OpenCart")
    public void elClienteIngresaAOpenCart() {

        theActorCalled("Actor").wasAbleTo(
                IngresarAOpenCart.ahora()
        );
        //OnStage.theActorInTheSpotlight().attemptsTo(IngresarAOpenCart.ahora());

    }

    @Cuando("selecciona aleatoriamente {int} categorías disponibles y agrega un producto de cada una al carrito")
    public void agregaProductosDeCategoriasAleatorias(int cantidad) {
        theActorInTheSpotlight()
                .attemptsTo(
                        AgregarProductosDeCategoriasAleatorias.cantidad(cantidad),
                        DesplegarCarrito.desdeLaCabecera());
    }


    @Cuando("finaliza la compra")
    public void finalizaLaCompra() {
        theActorInTheSpotlight()
                .attemptsTo(FinalizarCompraComoInvitado.ahora());
    }

    @Entonces("debe visualizar el mensaje de confirmación de compra exitosa")
    public void visualizaConfirmacionDeCompraExitosa() {
        theActorInTheSpotlight().should(
                seeThat(CompraConfirmada.exitosamente(), is(true)));
    }
}
