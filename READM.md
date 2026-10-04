# Automatizacion de compras en OpenCart

## Descripcion

Proyecto de pruebas funcionales automatizadas para OpenCart. El escenario disponible selecciona al azar una cantidad de categorias, agrega un producto de cada una al carrito y trata de completar la compra como invitado.

Aplicacion bajo prueba: <https://opencart.abstracta.us/>

## Tecnologias

- Java 17
- Gradle 9.6.0 mediante Gradle Wrapper
- Serenity BDD 4.2.34
- Serenity Screenplay y Serenity Cucumber
- Cucumber 7.22.2 con Gherkin en espanol
- Selenium WebDriver con Chrome
- JUnit Platform

## Estructura del proyecto

```text
src/
├── main/java/                         # Codigo de ejemplo de Gradle
└── test/
    ├── java/com/abstracta/opencart/
    │   ├── constants/                 # Claves de memoria del Actor
    │   ├── interactions/              # Acciones tecnicas reutilizables
    │   ├── model/                     # Modelos de categorias y productos
    │   ├── questions/                 # Consultas al estado de OpenCart
    │   ├── runner/                    # Suite JUnit Platform/Cucumber
    │   ├── stepdefinitions/           # Enlace entre Gherkin y Screenplay
    │   ├── tasks/                     # Acciones del flujo de compra
    │   └── ui/                        # Targets y locators centralizados
    └── resources/
        ├── features/                  # Escenarios Cucumber
        └── serenity.conf              # Configuracion de Serenity y Chrome
```

## Flujo automatizado

El feature `src/test/resources/features/compra_productos.feature` define un Esquema del escenario para comprar productos de 2, 3 o 4 categorias elegidas aleatoriamente. El flujo navega a OpenCart, agrega un producto por categoria, abre el carrito, finaliza la compra como invitado y verifica el mensaje de confirmacion.

La automatizacion sigue Screenplay: los Step Definitions coordinan Tasks, las Tasks representan acciones del cliente, las Questions consultan el estado y `PaginaOpenCart` concentra los Targets de la interfaz.

## Requisitos

- JDK 17 instalado y disponible en `PATH` (`java -version`).
- Acceso a Internet para descargar dependencias y abrir OpenCart.
- Google Chrome instalado. Serenity esta configurado para usar Chrome y descargar automaticamente el driver cuando sea necesario.
- Permiso de ejecucion para `gradlew` en macOS/Linux.

## Ejecucion

Desde la raiz del repositorio, consulta `readme.txt` para seguir la preparacion, compilacion y ejecucion paso a paso.

Comandos principales:

```bash
./gradlew compileTestJava
./gradlew test
```

En Windows se puede utilizar `gradlew.bat` en lugar de `./gradlew`.

## Reportes

Al ejecutar `test`, Gradle genera los reportes de Serenity mediante la tarea `aggregate`. Revisa:

- `target/site/serenity/index.html`: reporte Serenity.
- `build/reports/tests/test/index.html`: reporte HTML de Gradle.

Los reportes reflejan la ultima ejecucion; no representan necesariamente el estado actual de la aplicacion si no se vuelven a generar.

## Estado conocido de los resultados disponibles

Los resultados que ya estaban en el repositorio al preparar esta documentacion registran tres ejemplos fallidos en la verificacion final de la compra. El XML de Gradle muestra que no se encontro el estado esperado de confirmacion; el resumen de Serenity, en cambio, informa cero escenarios. `conclusiones.txt` detalla este hallazgo y sus limites. Estos archivos son evidencia de una ejecucion anterior, no de una nueva ejecucion realizada al escribir esta documentacion.
