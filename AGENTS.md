# AGENTS.md

## 1. Propósito

Este proyecto tiene como objetivo desarrollar una automatización de pruebas para **OpenCart** utilizando:

* Java 17
* Gradle
* Serenity BDD
* Screenplay Pattern
* Cucumber / Gherkin
* Selenium WebDriver

La automatización debe construirse siguiendo principios de:

* SOLID
* Clean Code
* separación de responsabilidades
* reutilización
* mantenibilidad
* estabilidad
* escalabilidad

El objetivo no es únicamente crear pruebas que pasen, sino construir una automatización mantenible y fácil de extender.

---

# 2. Reglas generales para el agente

Antes de implementar cualquier funcionalidad, el agente debe:

1. Leer este `AGENTS.md`.
2. Inspeccionar la estructura actual del proyecto.
3. Revisar las clases existentes relacionadas con la funcionalidad.
4. Buscar componentes reutilizables antes de crear nuevos.
5. Respetar la arquitectura Screenplay definida en este documento.
6. No asumir elementos, locators o comportamientos de OpenCart.
7. Inspeccionar el DOM real cuando sea necesario crear o modificar un locator.
8. Mantener las convenciones existentes del proyecto.
9. Evitar modificaciones que no sean necesarias para cumplir la solicitud.
10. Validar que el código compile después de realizar cambios.

Si existe una ambigüedad funcional que pueda cambiar el comportamiento esperado, solicitar aclaración antes de implementar una solución arbitraria.

---

# 3. Lenguaje y convenciones

El lenguaje principal del proyecto es **Java**.

Los siguientes elementos deben utilizar nombres en español:

* clases
* métodos
* variables
* constantes
* features
* escenarios
* steps
* comentarios

Ejemplos:

```text
PaginaInicioSesion
IniciarSesion
AgregarProductoAlCarrito
FinalizarCompra
ProductoSeleccionado
UsuarioEstaAutenticado
```

Constantes:

```text
CAMPO_USUARIO
BOTON_INICIAR_SESION
PRODUCTO_SELECCIONADO
```

Mantener una convención consistente durante todo el proyecto.

No mezclar innecesariamente español e inglés:

```text
LoginPage
PaginaInicioSesion
AddProduct
AgregarProducto
```

Si el proyecto utiliza español, mantener español.

---

# 4. Arquitectura

La automatización debe utilizar **Screenplay Pattern**.

Flujo principal:

```text
Gherkin
   ↓
Step Definition
   ↓
Task
   ↓
Interaction
   ↓
Target
   ↓
Aplicación
```

Para las validaciones:

```text
Aplicación
   ↓
Question
   ↓
Assertion
```

La estructura esperada es:

```text
src/
└── test/
    ├── java/
    │   └── org/example/
    │       ├── userinterfaces/
    │       ├── tasks/
    │       ├── interactions/
    │       ├── questions/
    │       ├── stepdefinitions/
    │       ├── runners/
    │       ├── constants/
    │       ├── models/
    │       └── utils/
    │
    └── resources/
        ├── features/
        └── serenity.conf
```

La estructura puede evolucionar cuando el proyecto crezca, pero cualquier cambio debe tener una razón técnica.

---

# 5. Responsabilidades de cada componente

## 5.1 User Interfaces / Targets

Contienen los elementos de la aplicación y sus locators.

Ejemplo:

```java
public static final Target BOTON_COMPRAR =
        Target.the("botón comprar")
                .located(By.xpath("..."));
```

Los locators deben estar centralizados.

No colocar locators directamente dentro de:

* Step Definitions
* Tasks
* Questions
* Features

---

## 5.2 Tasks

Una Task representa una **acción de negocio realizada por el Actor**.

Ejemplos:

```text
IniciarSesion
RegistrarUsuario
AgregarProductoAlCarrito
SeleccionarProducto
FinalizarCompra
```

Una Task debe representar una responsabilidad funcional coherente.

No existe una regla de "una Task = un método".

La regla correcta es:

> Una Task debe representar una acción de negocio coherente y tener una responsabilidad clara.

Una Task puede utilizar varias Interactions cuando sea necesario.

Ejemplo conceptual:

```java
actor.attemptsTo(
    IniciarSesion.con(usuario, password)
);
```

Evitar crear Tasks que solamente encapsulen acciones técnicas simples si esas acciones pertenecen realmente a una Interaction.

---

# 6. Interactions

Las Interactions representan acciones técnicas reutilizables.

Ejemplos:

```text
ScrollAlInicio
CambiarVentana
CambiarFrame
EjecutarJavaScript
```

Una Interaction debe responder:

> ¿Qué operación técnica necesito realizar?

No debe contener reglas de negocio.

Evitar utilizar una Interaction para ocultar lógica de negocio que debería pertenecer a una Task.

---

# 7. Questions

Las Questions consultan información o estado de la aplicación.

Ejemplos:

```text
UsuarioEstaAutenticado
ProductoEstaEnElCarrito
PrecioDelProducto
TituloDeLaPagina
MensajeDeCompra
```

Una Question puede:

* consultar elementos;
* obtener información;
* transformar información;
* devolver un valor;
* comprobar un estado.

Una Question no debe modificar el estado de la aplicación.

No realizar dentro de una Question acciones como:

```text
click
sendKeys
submit
navigate
```

Las Questions deben utilizarse para separar la consulta de la validación.

---

# 8. Assertions

Las acciones y las validaciones deben permanecer separadas.

Preferir:

```java
actor.attemptsTo(
    IniciarSesion.con(usuario, password)
);

actor.should(
    seeThat(
        UsuarioEstaAutenticado.es(),
        equalTo(true)
    )
);
```

Evitar colocar assertions de negocio dentro de una Task.

La responsabilidad debe mantenerse:

```text
Task       → realiza una acción
Question   → obtiene o consulta información
Assertion  → valida el resultado
```

---

# 9. Step Definitions

Los Step Definitions deben ser delgados.

Su responsabilidad es traducir el lenguaje Gherkin a acciones Screenplay.

Ejemplo:

```java
@Cuando("el usuario inicia sesión")
public void iniciarSesion() {
    actor.attemptsTo(
        IniciarSesion.con(usuario, password)
    );
}
```

No colocar dentro de Step Definitions:

* XPath
* CSS
* Selenium directo
* WebDriver
* JavascriptExecutor
* `Thread.sleep`
* lógica de negocio
* lógica compleja
* implementación de Tasks
* locators

Si un Step Definition comienza a crecer, evaluar mover la responsabilidad a:

```text
Task
Interaction
Question
```

---

# 10. Gherkin

Gherkin debe describir **comportamiento de negocio**.

Debe ser comprensible sin conocer Java, Selenium o Screenplay.

Incorrecto:

```gherkin
Cuando hago click en el botón con XPath "//button[3]"
```

Correcto:

```gherkin
Cuando agrego el producto al carrito
```

No incluir detalles de implementación dentro de los escenarios.

---

# 11. Scenario y Scenario Outline

Utilizar `Scenario` cuando se describe un flujo específico.

Utilizar `Scenario Outline` cuando la misma regla de negocio necesita ejecutarse con diferentes conjuntos de datos.

No utilizar `Scenario Outline` únicamente para evitar duplicar texto.

Los escenarios deben representar comportamientos funcionales, no pruebas de implementación.

---

# 12. Locators

Utilizar esta prioridad:

1. `id`
2. `data-testid`
3. atributos funcionales estables
4. CSS estable
5. XPath

Evitar locators frágiles basados en posición:

```text
div[3]
tr[2]/td[4]
```

Evitar:

* clases dinámicas;
* estructuras excesivamente profundas;
* selectores dependientes de posiciones;
* XPath innecesariamente complejo.

Antes de crear un locator:

1. Inspeccionar el DOM.
2. Identificar el elemento real.
3. Buscar atributos estables.
4. Verificar que el locator sea suficientemente específico.
5. Preferir el locator más simple que identifique correctamente el elemento.

Nunca inventar locators.

---

# 13. OpenCart

OpenCart es la aplicación bajo prueba.

No asumir que su estructura HTML, nombres de elementos o comportamiento coincide con otras aplicaciones.

Antes de implementar interacciones:

1. Analizar la página real.
2. Inspeccionar el DOM.
3. Identificar los elementos necesarios.
4. Determinar el comportamiento real.
5. Implementar los locators basándose en esa información.

No reutilizar automáticamente locators de otros sitios o proyectos.

---

# 14. Esperas y sincronización

Está prohibido utilizar:

```java
Thread.sleep(...)
```

como mecanismo normal de sincronización.

Preferir esperas condicionadas a estados reales de la aplicación:

```text
elemento visible
elemento presente
elemento habilitado
elemento seleccionado
URL esperada
texto esperado
estado esperado
```

Evitar esperas fijas cuando exista una condición que pueda esperar correctamente el estado requerido.

Una espera fija solo debe utilizarse cuando exista una justificación técnica concreta.

---

# 15. Datos de prueba

Nunca almacenar credenciales reales, tokens, claves API o secretos directamente en:

```text
Java
features
serenity.conf
build.gradle
```

Utilizar mecanismos externos como variables de entorno o configuración segura.

Los datos de prueba deben estar separados de la lógica de automatización siempre que sea necesario.

No duplicar datos sensibles en múltiples clases.

---

# 16. Estado de las pruebas

Cada escenario debe poder ejecutarse de manera independiente.

Evitar compartir estado mediante variables globales o `static`.

No utilizar variables estáticas para almacenar:

* usuarios;
* productos;
* precios;
* resultados;
* información del carrito;
* información específica de un escenario.

Cuando corresponda, utilizar la memoria del Actor:

```java
actor.remember(CLAVE, valor);
```

y posteriormente:

```java
actor.recall(CLAVE);
```

La información específica de un escenario debe permanecer aislada.

---

# 17. SOLID

Aplicar SOLID de manera práctica.

### Single Responsibility

Cada clase debe tener una responsabilidad clara.

### Open/Closed

Evitar modificar código estable cuando pueda extenderse de forma segura.

### Liskov Substitution

Las implementaciones deben respetar los contratos de sus abstracciones.

### Interface Segregation

Evitar interfaces grandes con responsabilidades no relacionadas.

### Dependency Inversion

Evitar acoplamientos innecesarios entre lógica de negocio e implementación técnica.

No aplicar SOLID de manera dogmática.

La simplicidad y la claridad tienen prioridad sobre una arquitectura excesivamente compleja.

---

# 18. Clean Code

El código debe priorizar:

* nombres descriptivos;
* métodos pequeños;
* responsabilidades claras;
* bajo acoplamiento;
* alta cohesión;
* reutilización;
* ausencia de código muerto;
* ausencia de duplicación innecesaria;
* eliminación de números mágicos;
* constantes para valores reutilizados.

Evitar código excesivamente complejo cuando una solución sencilla resuelva correctamente el problema.

---

# 19. No crear abstracciones innecesarias

No crear clases o interfaces únicamente "por si algún día se necesitan".

Evitar clases genéricas sin una responsabilidad clara:

```text
BaseTask
BaseQuestion
BaseInteraction
CommonUtils
GenericUtils
HelperUtils
```

Crear una abstracción únicamente cuando exista una necesidad real.

Antes de crear una clase nueva, comprobar:

1. ¿Ya existe una clase que tenga esta responsabilidad?
2. ¿Puede reutilizarse una Task existente?
3. ¿Puede reutilizarse una Question?
4. ¿Puede reutilizarse una Interaction?
5. ¿Existe un Target reutilizable?
6. ¿La nueva clase tiene una responsabilidad única?

---

# 20. Regla crítica: no modificar código existente sin autorización

El agente **NO debe modificar, renombrar, mover ni eliminar código existente sin autorización explícita del usuario**.

Esto incluye:

* clases;
* métodos;
* locators;
* constantes;
* firmas;
* comportamiento;
* estructura de paquetes.

Antes de modificar una clase existente:

1. Identificar la clase.
2. Explicar por qué es necesario modificarla.
3. Explicar qué comportamiento podría verse afectado.
4. Evaluar si puede solucionarse creando una nueva clase.
5. Solicitar autorización cuando el cambio no haya sido solicitado explícitamente.

Ejemplos de autorización explícita:

```text
modifica X.java
corrige X.java
refactoriza X.java
actualiza X.java
cambia el locator de X.java
```

Una solicitud como:

```text
agrega la funcionalidad X
```

no autoriza automáticamente a modificar cualquier clase existente.

Preferir agregar nuevos componentes antes que modificar componentes existentes cuando sea técnicamente viable.

---

# 21. Cambios mínimos

Para una funcionalidad nueva:

* modificar únicamente lo necesario;
* reutilizar componentes existentes;
* evitar refactors no solicitados;
* evitar cambios cosméticos;
* no reorganizar paquetes innecesariamente;
* no cambiar nombres existentes sin autorización;
* no introducir dependencias innecesarias.

El agente debe evitar realizar trabajo adicional que no forme parte de la solicitud.

---

# 22. Manejo de errores

No ocultar excepciones.

Evitar:

```java
try {
    ...
} catch (Exception e) {
}
```

No utilizar `catch` para hacer que una prueba continúe silenciosamente después de un error.

Cuando sea necesario capturar una excepción:

1. Debe existir una razón concreta.
2. Debe conservarse información útil.
3. Debe mantenerse la trazabilidad del error.
4. El fallo no debe ocultarse.

---

# 23. Features

Organizar los features por funcionalidad de negocio.

Ejemplo:

```text
features/
├── autenticacion/
│   ├── inicio_sesion.feature
│   └── registro_usuario.feature
│
├── productos/
│   ├── busqueda_producto.feature
│   └── seleccion_producto.feature
│
└── compras/
    └── compra_producto.feature
```

La estructura puede evolucionar conforme crezca el proyecto.

---

# 24. Tags

Utilizar tags para clasificar y ejecutar escenarios.

Ejemplos:

```gherkin
@smoke
@login
@compra
@regression
```

Un escenario puede utilizar varios tags:

```gherkin
@smoke @login
Scenario: Usuario inicia sesión correctamente
```

No crear tags innecesarios.

Los tags deben representar una clasificación útil del escenario.

---

# 25. Verificación

Después de implementar una funcionalidad, verificar como mínimo la compilación:

```bash
./gradlew compileTestJava
```

Cuando corresponda, ejecutar:

```bash
./gradlew test
```

Para ejecutar escenarios específicos mediante tags:

```bash
./gradlew test -Dcucumber.filter.tags="@tag"
```

Si una ejecución falla, identificar primero la categoría del problema:

```text
compilación
configuración
locator
sincronización
datos
lógica funcional
```

No solucionar un fallo modificando código existente sin autorización.

---

# 26. Reportes

La automatización debe generar los reportes correspondientes de Serenity.

El agente debe indicar al usuario dónde encontrar el reporte después de una ejecución exitosa.

No afirmar que una prueba fue ejecutada si realmente no se ejecutó.

Si no fue posible ejecutar la prueba, indicarlo claramente.

---

# 27. Documentación del código

Los comentarios deben utilizarse únicamente cuando aporten información que no sea evidente.

Evitar:

```java
// Hacer click en el botón
boton.click();
```

Preferir comentarios que expliquen:

* decisiones técnicas;
* workarounds;
* limitaciones de la aplicación;
* comportamientos particulares de Serenity;
* razones de una implementación no evidente.

---

# 28. Principio de diseño

La automatización debe mantener esta separación:

```text
Gherkin
   ↓
Comportamiento
   ↓
Step Definition
   ↓
Orquestación
   ↓
Task
   ↓
Acción de negocio
   ↓
Interaction
   ↓
Acción técnica
   ↓
Target
   ↓
Elemento de UI
```

Y para las validaciones:

```text
Question
   ↓
Consulta del estado
   ↓
Assertion
   ↓
Resultado esperado
```

Cada capa debe conocer únicamente lo que necesita para cumplir su responsabilidad.

---

# 29. Regla de oro

Antes de crear código, el agente debe preguntarse:

```text
¿Existe ya algo que pueda reutilizar?
        ↓
¿Dónde pertenece esta responsabilidad?
        ↓
¿Estoy mezclando lógica de negocio con lógica técnica?
        ↓
¿Estoy colocando una assertion en el lugar correcto?
        ↓
¿El locator está basado en el DOM real?
        ↓
¿Estoy creando una abstracción innecesaria?
        ↓
¿Estoy modificando código existente sin autorización?
        ↓
¿La solución es simple y mantenible?
```

El resultado esperado es una automatización:

```text
legible
reutilizable
estable
mantenible
escalable
```

construida sobre:

```text
Java
Serenity BDD
Screenplay
Cucumber
Selenium
SOLID
Clean Code
```
