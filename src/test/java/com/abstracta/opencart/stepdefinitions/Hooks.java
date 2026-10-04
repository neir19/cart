package com.abstracta.opencart.stepdefinitions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before(order = 0)
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }


    @After(order = 1000)
    public void limpiarDriver(Scenario scenario) {
        try {
            OnStage.drawTheCurtain();

        } finally {
            try {
                OnStage.drawTheCurtain();
            } catch (Exception ignored) {
                // Garantiza limpieza del Stage para el siguiente escenario
            }
        }
    }
}
