package com.abstracta.opencart.interactions;

import com.abstracta.opencart.ui.PaginaOpenCart;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.support.ui.Select;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AgregarProduct8 implements Interaction {

    public static final Target SIZE_SELECT = Target.the("selección de tamaño de Product 8")
            .located(By.id("input-option224"));

    @Override
    public <T extends Actor> void performAs(T actor) {
        Select select = new Select(SIZE_SELECT.resolveFor(actor));
        select.getOptions().stream()
                .filter(o -> !o.getAttribute("value").isBlank())
                .findFirst()
                .ifPresent(o -> select.selectByValue(o.getAttribute("value")));
        actor.attemptsTo(Click.on(PaginaOpenCart.ADDCARRITO));
    }

    public static Instrumented.InstrumentedBuilder<AgregarProduct8> agregar() {
        return Instrumented.instanceOf(AgregarProduct8.class);
    }
}
