# Constitution — OpenCart Automation

## 1. Propósito

Este proyecto contiene la automatización funcional de la aplicación web **OpenCart**, disponible en:

`https://opencart.abstracta.us/`

La automatización utiliza un enfoque orientado a comportamiento, mantenibilidad y reutilización.

Debe permitir validar los principales flujos funcionales de la aplicación de manera confiable, legible y sostenible.

---

# 2. Stack tecnológico

El proyecto utiliza:

* Java 17.
* Gradle.
* Serenity BDD.
* Serenity Screenplay.
* Cucumber / Gherkin.
* Selenium WebDriver.
* JUnit, cuando corresponda según la configuración del proyecto.

La arquitectura debe respetar las responsabilidades propias de cada tecnología.

---

# 3. Principio fundamental

La automatización debe representar **comportamientos funcionales del negocio**, no detalles técnicos de implementación.

El código debe responder principalmente:

> ¿Qué comportamiento del usuario estamos validando?

y no:

> ¿Cómo interactúa Selenium con el elemento?

Los detalles técnicos deben permanecer encapsulados dentro de los componentes correspondientes de Screenplay.

---

# 4. Arquitectura Screenplay

El proyecto debe seguir la separación conceptual:

```text
Feature
   ↓
Scenario
   ↓
Step Definition
   ↓
Task / Interaction / Question
   ↓
Target
   ↓
Application
```

Cada capa debe tener una responsabilidad clara.

---

## 4.1 Feature

Los archivos `.feature` representan comportamiento funcional.

Deben estar escritos utilizando lenguaje de negocio comprensible para una persona que conozca el sistema, aunque no conozca Java, Selenium o Serenity.

No deben contener detalles de implementación.

### No permitido

```gherkin
When hago click en el XPath //button[@id='login']
```

### Permitido

```gherkin
When el usuario inicia sesión con sus credenciales
```

---

# 5. Gherkin

Los escenarios deben utilizar:

```gherkin
Given
When
Then
And
But
```

de acuerdo con su significado semántico.

## Given

Representa el contexto o estado inicial.

## When

Representa una acción realizada por el actor.

## Then

Representa una condición o resultado esperado.

## And / But

Se utilizan para complementar el paso anterior sin introducir ambigüedad.

---

# 6. Calidad de los escenarios

Cada escenario debe validar un comportamiento específico.

Debe evitarse:

* escenarios excesivamente largos;
* múltiples funcionalidades dentro del mismo escenario;
* pasos técnicos;
* duplicación innecesaria;
* dependencias entre escenarios;
* datos hardcodeados cuando puedan parametrizarse correctamente.

Los escenarios deben poder entenderse sin revisar el código Java.

---

# 7. Independencia de escenarios

Cada escenario debe ser independiente.

Un escenario no debe depender del resultado o estado generado por otro escenario.

Si un escenario necesita:

* usuario;
* producto;
* sesión;
* dirección;
* carrito;
* configuración;

debe preparar explícitamente las condiciones necesarias o utilizar mecanismos de preparación apropiados.

---

# 8. Step Definitions

Las Step Definitions funcionan como puente entre Gherkin y Screenplay.

Su responsabilidad debe mantenerse pequeña.

Una Step Definition debe principalmente:

1. recibir los parámetros del escenario;
2. obtener o preparar los datos necesarios;
3. invocar Tasks, Interactions o Questions;
4. delegar la lógica de automatización.

No debe contener grandes cantidades de lógica Selenium.

### Evitar

```java
@When("el usuario inicia sesión")
public void iniciarSesion() {
    driver.findElement(...).click();
    driver.findElement(...).sendKeys(...);
    driver.findElement(...).click();
}
```

### Preferir

```java
@When("el usuario inicia sesión")
public void iniciarSesion() {
    actor.attemptsTo(
        IniciarSesion.conLasCredenciales(...)
    );
}
```

---

# 9. Tasks

Las Tasks representan acciones de negocio realizadas por el Actor.

Ejemplos:

```text
IniciarSesion
RegistrarUsuario
AgregarProductoAlCarrito
ActualizarDatosUsuario
FinalizarCompra
```

Una Task debe representar una intención funcional y no un conjunto arbitrario de comandos Selenium.

---

# 10. Interactions

Las Interactions encapsulan interacciones técnicas reutilizables que no representan por sí mismas una acción completa de negocio.

Ejemplos:

```text
ScrollAlInicio
ScrollAlElemento
AceptarCookies
Esperar
```

Deben utilizarse cuando una operación técnica tenga sentido como componente reutilizable.

No se deben crear Interactions innecesarias para acciones simples que puedan pertenecer naturalmente a una Task.

---

# 11. Questions

Las Questions representan consultas al estado de la aplicación.

Deben utilizarse para obtener información o verificar condiciones.

Ejemplos:

```text
CuentaAutenticada
ProductoSeleccionado
PrecioProducto
CarritoActual
UsuarioAutenticado
```

Las Questions deben separar la consulta del estado de la aplicación de las acciones realizadas por el Actor.

---

# 12. Assertions

Las validaciones deben mantenerse separadas de las acciones siempre que sea posible.

Preferir:

```java
actor.should(
    seeThat(CuentaAutenticada.es(), equalTo(true))
);
```

en lugar de incorporar assertions directamente dentro de Tasks o Interactions.

Las Tasks realizan acciones.

Las Questions consultan información.

Las assertions determinan si el resultado cumple la expectativa.

---

# 13. Targets

Los Targets representan elementos de la interfaz.

Deben estar centralizados y tener nombres semánticos.

Ejemplo:

```java
public static final Target BOTON_INICIAR_SESION =
    Target.the("botón para iniciar sesión")
        .located(By.xpath(...));
```

El nombre del Target debe explicar su propósito funcional.

Evitar nombres como:

```text
BUTTON_1
ELEMENTO
XPATH_LOGIN
BOTON_X
```

Preferir:

```text
BOTON_INICIAR_SESION
CAMPO_CORREO
CAMPO_CONTRASENA
ENLACE_CERRAR_SESION
BOTON_AGREGAR_AL_CARRITO
```

---

# 14. Locators

Los locators deben ser:

* estables;
* legibles;
* específicos;
* resistentes a cambios visuales;
* orientados a atributos funcionales cuando sea posible.

Orden de preferencia:

1. `id` estable;
2. atributos `data-*`;
3. atributos funcionales estables;
4. roles o propiedades semánticas;
5. CSS;
6. XPath cuando sea necesario.

Evitar XPath excesivamente largos o dependientes de la estructura completa del DOM.

---

# 15. XPath

Cuando XPath sea necesario, debe mantenerse simple.

Preferir:

```xpath
//a[contains(@href, 'account')]
```

sobre XPath excesivamente dependientes de múltiples niveles:

```xpath
//div/div/div/nav/div/ul/li[1]/a
```

Cuando se utilice `contains`, debe existir una razón funcional o estructural clara.

---

# 16. Esperas

No utilizar:

```java
Thread.sleep()
```

como mecanismo habitual de sincronización.

Preferir las esperas proporcionadas por Serenity, Selenium o Screenplay.

Las esperas deben utilizarse para sincronizar el estado esperado de la aplicación, no para ocultar problemas de sincronización.

---

# 17. Actor Memory

La memoria del Actor puede utilizarse para conservar información necesaria durante un flujo.

Ejemplo:

```java
actor.remember(
    Constantes.PRODUCTO_SELECCIONADO,
    producto
);
```

La información almacenada debe tener un propósito funcional claro.

Las constantes utilizadas como claves deben estar centralizadas cuando sean reutilizadas.

---

# 18. SOLID

El proyecto debe aplicar principios SOLID, especialmente:

## Single Responsibility Principle

Cada clase debe tener una responsabilidad clara.

Una clase no debería encargarse simultáneamente de:

* localizar elementos;
* ejecutar acciones;
* realizar assertions;
* gestionar datos;
* controlar navegación.

---

## Open/Closed Principle

Los componentes existentes deberían poder reutilizarse y extenderse sin modificar constantemente código estable.

---

## Dependency Inversion Principle

Las clases deben depender de abstracciones apropiadas proporcionadas por Screenplay y Serenity cuando corresponda.

---

# 19. Clean Code

El código debe priorizar:

* nombres descriptivos;
* métodos pequeños;
* responsabilidades claras;
* baja duplicación;
* bajo acoplamiento;
* alta cohesión;
* ausencia de código muerto;
* ausencia de comentarios innecesarios.

Un buen nombre debe reducir la necesidad de explicar qué hace el código.

---

# 20. Reutilización

Antes de crear una nueva clase, Task, Question, Interaction o Target, se debe revisar si ya existe un componente reutilizable.

No crear duplicados funcionalmente equivalentes.

Por ejemplo, antes de crear una nueva validación de sesión:

```text
ValidarSesion
```

se debe revisar si ya existe:

```text
CuentaAutenticada
```

y determinar si puede reutilizarse.

---

# 21. Modificación de código existente

Las clases existentes deben considerarse código estable.

**No se debe modificar automáticamente una clase existente.**

Si una nueva funcionalidad requiere modificar una clase existente:

1. identificar la clase;
2. explicar por qué es necesario modificarla;
3. explicar qué cambio se propone;
4. explicar el impacto potencial;
5. solicitar aprobación explícita antes de modificarla.

No se debe realizar la modificación hasta recibir autorización.

Esta regla tiene prioridad sobre la conveniencia de implementar rápidamente una funcionalidad.

---

# 22. Nuevas funcionalidades

Antes de implementar una nueva funcionalidad se debe analizar:

1. Feature existente.
2. Scenarios existentes.
3. Step Definitions existentes.
4. Tasks existentes.
5. Questions existentes.
6. Interactions existentes.
7. Targets existentes.
8. utilidades existentes.
9. constantes existentes.
10. estructura actual del proyecto.

Primero se debe reutilizar.

Después se debe extender.

Finalmente, si no existe una solución apropiada, se debe crear un nuevo componente.

---

# 23. Datos de prueba

Los datos deben mantenerse separados de la lógica cuando sea apropiado.

Evitar hardcodear información repetitiva dentro de Tasks o Step Definitions.

Los datos sensibles nunca deben almacenarse directamente en:

* Features;
* código fuente;
* repositorios Git;
* logs.

---

# 24. Registro y debugging

Cuando un escenario falle, el análisis debe comenzar por determinar:

1. qué paso Gherkin falló;
2. qué Step Definition lo ejecutó;
3. qué Task/Interaction/Question se utilizó;
4. qué Target estaba involucrado;
5. cuál era el estado esperado;
6. cuál fue el estado real;
7. si el problema corresponde a locator;
8. sincronización;
9. datos;
10. aplicación.

No modificar código simplemente para hacer desaparecer un error sin comprender primero su causa.

---

# 25. Navegación y estado

La automatización debe controlar explícitamente estados importantes como:

* usuario autenticado;
* usuario no autenticado;
* carrito;
* producto seleccionado;
* página actual;
* datos registrados.

No asumir que el navegador se encuentra en un estado determinado sin haberlo establecido o validado.

---

# 26. Reportes

Serenity debe utilizarse para generar evidencia útil del comportamiento automatizado.

Los nombres de Tasks, Questions, Interactions y escenarios deben ser suficientemente descriptivos para que los reportes sean comprensibles.

---

# 27. Criterio de implementación

Antes de escribir código se debe responder:

> ¿Existe ya un componente que pueda reutilizarse?

Si la respuesta es sí:

```text
REUTILIZAR
```

Si la respuesta es no:

```text
CREAR NUEVO COMPONENTE
```

Si la solución requiere modificar una clase existente:

```text
DETENER
→ EXPLICAR
→ SOLICITAR APROBACIÓN
→ ESPERAR AUTORIZACIÓN
```

---

# 28. Principio de diseño

La automatización debe buscar esta relación:

```text
Gherkin
   ↓
Comportamiento de negocio
   ↓
Screenplay
   ↓
Abstracción
   ↓
Interacción técnica
   ↓
Aplicación
```

Los detalles técnicos no deben filtrarse hacia los escenarios funcionales.

---

# 29. URL objetivo

La aplicación bajo prueba principal es:

```text
https://opencart.abstracta.us/
```

La URL debe centralizarse mediante la configuración de Serenity/Gradle correspondiente y no repetirse innecesariamente dentro de Features, Tasks, Step Definitions o Targets.

---

# 30. Regla final

La prioridad del proyecto es:

1. Correctitud funcional.
2. Mantenibilidad.
3. Reutilización.
4. Legibilidad.
5. Estabilidad de la automatización.
6. Bajo acoplamiento.
7. Separación de responsabilidades.
8. Evidencia clara en Serenity.

La automatización debe evolucionar como un producto de software mantenible, no como una colección de scripts independientes.
