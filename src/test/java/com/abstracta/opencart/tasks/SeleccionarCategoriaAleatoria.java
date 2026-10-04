package com.abstracta.opencart.tasks;

import com.abstracta.opencart.constants.MemoriaCompraClave;
import com.abstracta.opencart.interactions.AbrirMenuDeCategoria;
import com.abstracta.opencart.interactions.SeleccionarCategoriaEnMenu;
import com.abstracta.opencart.model.CategoriaDisponible;
import com.abstracta.opencart.questions.SubcategoriasDisponibles;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class SeleccionarCategoriaAleatoria implements Task {

    private final List<CategoriaDisponible> categorias;

    public SeleccionarCategoriaAleatoria(List<CategoriaDisponible> categorias) {
        if (categorias.isEmpty()) {
            throw new IllegalArgumentException("Debe existir al menos una categoría para seleccionar");
        }
        this.categorias = List.copyOf(categorias);
    }

    public static SeleccionarCategoriaAleatoria entre(List<CategoriaDisponible> categorias) {
        return Instrumented.instanceOf(SeleccionarCategoriaAleatoria.class)
                .withProperties(categorias);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        int indiceAleatorio = ThreadLocalRandom.current().nextInt(categorias.size());
        CategoriaDisponible categoria = categorias.get(indiceAleatorio);
        if (categoria.desplegable()) {
            actor.attemptsTo(AbrirMenuDeCategoria.de(categoria));
            List<CategoriaDisponible> subcategorias =
                    actor.asksFor(SubcategoriasDisponibles.de(categoria));
            if (subcategorias.isEmpty()) {
                throw new IllegalStateException(
                        "No se encontraron subcategorías visibles para " + categoria.nombre());
            }
            categoria = subcategorias.get(ThreadLocalRandom.current().nextInt(subcategorias.size()));
            actor.attemptsTo(SeleccionarCategoriaEnMenu.subcategoriaConMenuAbierto(categoria));
        } else {
            actor.attemptsTo(SeleccionarCategoriaEnMenu.categoria(categoria));
        }
        actor.remember(MemoriaCompraClave.CATEGORIA_SELECCIONADA, categoria);
    }
}
