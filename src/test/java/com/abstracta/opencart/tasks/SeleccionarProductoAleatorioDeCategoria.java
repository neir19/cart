package com.abstracta.opencart.tasks;

import com.abstracta.opencart.constants.MemoriaCompraClave;
import com.abstracta.opencart.model.ProductoDisponible;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class SeleccionarProductoAleatorioDeCategoria implements Task {

    private final List<ProductoDisponible> productos;

    public SeleccionarProductoAleatorioDeCategoria(List<ProductoDisponible> productos) {
        if (productos.isEmpty()) {
            throw new IllegalArgumentException("Debe existir al menos un producto para seleccionar");
        }
        this.productos = List.copyOf(productos);
    }

    public static SeleccionarProductoAleatorioDeCategoria entre(
            List<ProductoDisponible> productos) {
        return Instrumented.instanceOf(SeleccionarProductoAleatorioDeCategoria.class)
                .withProperties(productos);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        int indiceAleatorio = ThreadLocalRandom.current().nextInt(productos.size());
        actor.remember(MemoriaCompraClave.PRODUCTO_SELECCIONADO, productos.get(indiceAleatorio));
    }
}
