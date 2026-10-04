# language: es
Característica: Compra de productos de categorías aleatorias

  Como cliente de OpenCart
  Quiero seleccionar productos de diferentes categorías de forma dinámica
  Para agregarlos al carrito y finalizar una compra exitosa

  Esquema del escenario: Comprar productos de categorías seleccionadas aleatoriamente
    Dado que el cliente ingresa a la página de OpenCart
    Cuando selecciona aleatoriamente <cantidad> categorías disponibles y agrega un producto de cada una al carrito
    Cuando finaliza la compra
    Entonces debe visualizar el mensaje de confirmación de compra exitosa

    Ejemplos:
      | cantidad |
      | 2        |
     | 3        |
      | 4        |
