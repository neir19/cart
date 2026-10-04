INSTRUCCIONES DE EJECUCION - AUTOMATIZACION OPENCART
====================================================

REQUISITOS
----------
1. Instalar JDK 17.
2. Confirmar que Java esta disponible:
       java -version
   La version principal debe ser 17.
3. Tener acceso a Internet para descargar dependencias y acceder a:
       https://opencart.abstracta.us/
4. Instalar Google Chrome. Serenity esta configurado para utilizar Chrome y
   descargar el driver automaticamente si hace falta.

PREPARACION (macOS / Linux)
---------------------------
1. Abrir una terminal en la carpeta raiz del proyecto (donde esta `build.gradle`).
2. Dar permiso de ejecucion al wrapper de Gradle, si el sistema lo requiere:
       chmod +x gradlew
3. Confirmar que el wrapper puede ejecutarse:
       ./gradlew --version
   La primera ejecucion puede descargar Gradle y las dependencias del proyecto.

PREPARACION (Windows)
---------------------
1. Abrir una terminal en la carpeta raiz del proyecto.
2. Ejecutar:
       gradlew.bat --version

COMPILAR Y EJECUTAR
-------------------
1. Compilar el codigo de pruebas:
       macOS / Linux: ./gradlew compileTestJava
       Windows:      gradlew.bat compileTestJava
2. Ejecutar la suite Cucumber:
       macOS / Linux: ./gradlew test
       Windows:      gradlew.bat test
3. La suite ejecuta el Esquema del escenario de compra, con ejemplos que
   seleccionan 2, 3 y 4 categorias. La seleccion es aleatoria.
4. Esperar a que Gradle finalice. La tarea `test` esta configurada para generar
   tambien el agregado de reportes Serenity.
5. El feature actual no declara tags; la ejecucion normal corre los tres
   ejemplos del escenario.

CONSULTAR LOS REPORTES
----------------------
1. Abrir el reporte Serenity:
       target/site/serenity/index.html
2. Para el reporte HTML de Gradle, abrir:
       build/reports/tests/test/index.html
3. Revisar el resultado y los pasos fallidos antes de interpretar una ejecucion
   como exitosa. Los archivos de reportes existentes pueden corresponder a una
   ejecucion anterior.

COMANDO COMPLETO (macOS / Linux)
--------------------------------
       ./gradlew compileTestJava test
