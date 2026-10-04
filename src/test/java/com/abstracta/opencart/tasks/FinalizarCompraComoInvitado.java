package com.abstracta.opencart.tasks;

import com.abstracta.opencart.interactions.MarcarCasillaSiNoEstaMarcada;
import com.abstracta.opencart.interactions.SeleccionarOpcionDesplegable;
import com.abstracta.opencart.interactions.SeleccionarPrimerRadio;
import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class FinalizarCompraComoInvitado implements Task {

    private static final String NOMBRE = "QA";
    private static final String APELLIDO = "Prueba";
    private static final String CORREO = "qa.opencart@example.test";
    private static final String TELEFONO = "2025550199";
    private static final String DIRECCION = "100 Test Avenue";
    private static final String CIUDAD = "San Francisco";
    private static final String CODIGO_POSTAL = "94105";
    private static final String PAIS = "United States";
    private static final String REGION = "California";

    public static FinalizarCompraComoInvitado ahora() {
        return Instrumented.instanceOf(FinalizarCompraComoInvitado.class).withProperties();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PaginaOpenCart.BOTON_CARRITO_FINAL));
        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.CANTIDADES_CARRITO_PAGINA, isVisible()));

        actor.attemptsTo(
                Click.on(PaginaOpenCart.ENLACE_CHECKOUT_DESDE_CARRITO)
        );

        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.OPCION_COMPRA_INVITADO, isVisible())
        );

        actor.attemptsTo(
                Click.on(PaginaOpenCart.OPCION_COMPRA_INVITADO)
        );

        actor.attemptsTo(
                Click.on(PaginaOpenCart.BOTON_CONTINUAR_CUENTA)
        );

        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.BOTON_CONTINUAR_DATOS_INVITADO, isVisible())
        );

        actor.attemptsTo(
                Enter.theValue(NOMBRE).into(PaginaOpenCart.NOMBRE_CLIENTE),
                Enter.theValue(APELLIDO).into(PaginaOpenCart.APELLIDO_CLIENTE),
                Enter.theValue(CORREO).into(PaginaOpenCart.CORREO_CLIENTE),
                Enter.theValue(TELEFONO).into(PaginaOpenCart.TELEFONO_CLIENTE),
                Enter.theValue(DIRECCION).into(PaginaOpenCart.DIRECCION_CLIENTE),
                Enter.theValue(CIUDAD).into(PaginaOpenCart.CIUDAD_CLIENTE),
                Enter.theValue(CODIGO_POSTAL).into(PaginaOpenCart.CODIGO_POSTAL_CLIENTE)
        );

        actor.attemptsTo(
                SelectFromOptions.byVisibleText(PAIS)
                        .from(PaginaOpenCart.PAIS_CLIENTE)
        );

        actor.attemptsTo(
                SeleccionarOpcionDesplegable.visible(
                        PaginaOpenCart.REGION_CLIENTE,
                        REGION
                )
        );

        actor.attemptsTo(
                Click.on(PaginaOpenCart.BOTON_CONTINUAR_DATOS_INVITADO)
        );

        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.METODOS_ENVIO, isVisible())
        );

        actor.attemptsTo(
                SeleccionarPrimerRadio.de(PaginaOpenCart.METODOS_ENVIO)
        );

        actor.attemptsTo(
                Click.on(PaginaOpenCart.BOTON_CONTINUAR_ENVIO)
        );

        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.METODOS_PAGO, isVisible())
        );

        actor.attemptsTo(
                SeleccionarPrimerRadio.de(PaginaOpenCart.METODOS_PAGO)
        );

        actor.attemptsTo(
                MarcarCasillaSiNoEstaMarcada.la(PaginaOpenCart.ACEPTAR_TERMINOS)
        );

        actor.attemptsTo(
                Click.on(PaginaOpenCart.BOTON_CONTINUAR_PAGO)
        );

        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.BOTON_CONFIRMAR_PEDIDO, isVisible())
        );

        actor.attemptsTo(
                Click.on(PaginaOpenCart.BOTON_CONFIRMAR_PEDIDO)
        );

        actor.attemptsTo(
                WaitUntil.the(PaginaOpenCart.MENSAJE_CONFIRMACION_COMPRA, isVisible())
        );


    }
}
