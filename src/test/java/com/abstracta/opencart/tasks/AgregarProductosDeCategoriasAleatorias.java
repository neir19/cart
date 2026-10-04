package com.abstracta.opencart.tasks;

import com.abstracta.opencart.constants.MemoriaCompraClave;
import com.abstracta.opencart.model.CategoriaDisponible;
import com.abstracta.opencart.questions.CategoriasDisponibles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class AgregarProductosDeCategoriasAleatorias implements Task {

    private final int cantidadSolicitada;

    public AgregarProductosDeCategoriasAleatorias(int cantidadSolicitada) {
        if (cantidadSolicitada < 1) {
            throw new IllegalArgumentException("La cantidad solicitada debe ser mayor que cero");
        }
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public static AgregarProductosDeCategoriasAleatorias cantidad(int cantidad) {
        return Instrumented.instanceOf(AgregarProductosDeCategoriasAleatorias.class)
                .withProperties(cantidad);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        List<CategoriaDisponible> disponibles = new ArrayList<>(actor.asksFor(CategoriasDisponibles.enOpenCart()));
        if (disponibles.size() < cantidadSolicitada) {
            throw new IllegalStateException(
                    "No hay suficientes categorías disponibles con productos. Solicitadas: "
                            + cantidadSolicitada + ", disponibles: " + disponibles.size());
        }

        Collections.shuffle(disponibles);
        List<CategoriaDisponible> seleccionadas = disponibles.subList(0, cantidadSolicitada);

        int totalAgregados = 0;
        for (CategoriaDisponible categoria : seleccionadas) {
            actor.attemptsTo(AgregarProductoAleatorioDeCategoria.en(categoria));
            totalAgregados++;
            actor.remember(MemoriaCompraClave.CANTIDAD_PRODUCTOS_AGREGADOS, totalAgregados);
        }
    }
}
