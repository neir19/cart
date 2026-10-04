package com.abstracta.opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class PaginaOpenCart {

    public static final String URL = "https://opencart.abstracta.us/";
    public static final Target LOGO =
            Target.the("logo de OpenCart")
                    .located(By.id("logo"));
    public static final Target CATEGORIAS =
            Target.the("categorías disponibles en el menú")
                    .located(By.xpath(
                            "//nav[@id='menu']//ul[contains(@class,'nav')]/li/a"));
    public static final Target SUBCATEGORIAS =
            Target.the("subcategorías visibles en el menú")
                    .located(By.xpath(
                            "//a[@aria-expanded='true']/following-sibling::div//a[@class='see-all']"));
    public static final By ENLACE_CATEGORIA_PADRE =
            By.xpath("./ancestor::li[contains(@class,'dropdown')][1]/a[contains(@class,'dropdown-toggle')]");

    public static Target enlaceCategoriaPorNombre(String nombre) {
        return Target.the("enlace de categoría " + nombre)
                .located(By.xpath("//nav[@id='menu']//ul[contains(@class,'nav')]/li/a[normalize-space()='" + nombre + "']"));
    }

    public static Target enlaceVerTodoPorNombre(String nombre) {
        return Target.the("enlace ver todo de " + nombre)
                .located(By.xpath("//nav[@id='menu']//li[a[normalize-space()='" + nombre + "']]//a[contains(@class,'see-all')]"));
    }

    public static Target enlaceCategoriaPadre(String url) {
        return Target.the("enlace de categoría en el menú principal")
                .located(By.xpath("//nav[@id='menu']//ul[contains(@class,'nav')]/li/a[@href='" + url + "']"));
    }

    public static Target enlaceVerTodoDe(String urlCategoria) {
        return Target.the("enlace ver todo de " + urlCategoria)
                .located(By.xpath("//nav[@id='menu']//a[contains(@class,'see-all') and @href='" + urlCategoria + "']"));
    }

    public static Target enlaceCategoria(String url) {
        return Target.the("enlace de la categoría")
                .located(By.xpath("//nav[@id='menu']//a[@href='" + url + "']"));
    }

    public static Target subcategoriasDe(String urlCategoria) {
        return Target.the("subcategorías de " + urlCategoria)
                .located(By.xpath("//nav[@id='menu']//ul[contains(@class,'nav')]/li"
                        + "[a[@href='" + urlCategoria + "']]"
                        + "//div[contains(@class,'dropdown-menu')]"
                        + "//a[not(contains(@class,'see-all'))]"));
    }
    public static final Target AGREGAR_CARRITO =
            Target.the("botón para agregar el producto al carrito")
                    .locatedBy(
                            "//span[text()='Add to Cart']");
    public static final Target TITULO_CATEGORIA =
            Target.the("título de la categoría")
                    .located(By.cssSelector("#content h2"));
    public static final Target ENLACES_PRODUCTO_CATEGORIA =
            Target.the("productos disponibles en la categoría")
                    .located(By.cssSelector(".product-thumb .caption h4 a"));
    public static final Target ALERTA_PRODUCTO_AGREGADO =
            Target.the("confirmación de producto agregado")
                    .located(By.cssSelector(".alert-success"));
    public static final Target BOTON_CABECERA_CARRITO =
            Target.the("botón desplegable del carrito en la cabecera")
                    .located(By.xpath("//button[@class='btn btn-inverse btn-block btn-lg dropdown-toggle']"));
    public static final Target BOTON_CARRITO_FINAL =
            Target.the("botón desplegable del carrito en la cabecera")
                    .located(By.xpath("//strong[normalize-space()='View Cart']"));
    public static final Target MENU_CARRITO_DESPLEGABLE =
            Target.the("contenido desplegable del carrito")
                    .located(By.cssSelector("#cart .dropdown-menu"));
    public static final Target CANTIDADES_CARRITO_DESPLEGABLE =
            Target.the("cantidades de los productos en el carrito desplegable")
                    .located(By.cssSelector(
                            "#cart .dropdown-menu table.table-striped td.text-right"));
    public static final Target ENLACE_VER_CARRITO =
            Target.the("enlace para ver el carrito")
                    .located(By.cssSelector(
                            "#cart .dropdown-menu a[href*='route=checkout/cart']"));
    public static final Target CANTIDADES_CARRITO_PAGINA =
            Target.the("cantidades de productos en la página del carrito")
                    .located(By.xpath("//h2[normalize-space()='What would you like to do next?']"));
    public static final Target ENLACE_CHECKOUT_DESDE_CARRITO =
            Target.the("enlace para continuar al checkout desde el carrito")
                    .located(By.xpath("//a[contains(@href,'checkout/checkout') and contains(@class,'btn-primary')]"));
    public static final Target OPCION_COMPRA_INVITADO =
            Target.the("opción de compra como invitado")
                    .located(By.xpath("//input[@value='guest']"));
    public static final Target BOTON_CONTINUAR_CUENTA =
            Target.the("continuar con la compra como invitado")
                    .located(By.id("button-account"));
    public static final Target NOMBRE_CLIENTE =
            Target.the("nombre del cliente")
                    .located(By.id("input-payment-firstname"));
    public static final Target APELLIDO_CLIENTE =
            Target.the("apellido del cliente")
                    .located(By.id("input-payment-lastname"));
    public static final Target CORREO_CLIENTE =
            Target.the("correo de prueba del cliente")
                    .located(By.id("input-payment-email"));
    public static final Target TELEFONO_CLIENTE =
            Target.the("teléfono de prueba del cliente")
                    .located(By.id("input-payment-telephone"));
    public static final Target DIRECCION_CLIENTE =
            Target.the("dirección de prueba del cliente")
                    .located(By.id("input-payment-address-1"));
    public static final Target CIUDAD_CLIENTE =
            Target.the("ciudad de prueba del cliente")
                    .located(By.id("input-payment-city"));
    public static final Target CODIGO_POSTAL_CLIENTE =
            Target.the("código postal de prueba del cliente")
                    .located(By.id("input-payment-postcode"));
    public static final Target PAIS_CLIENTE =
            Target.the("país del cliente")
                    .located(By.id("input-payment-country"));
    public static final Target REGION_CLIENTE =
            Target.the("región del cliente")
                    .located(By.id("input-payment-zone"));
    public static final Target BOTON_CONTINUAR_DATOS_INVITADO =
            Target.the("continuar con los datos de compra como invitado")
                    .located(By.id("button-guest"));
    public static final Target METODOS_ENVIO =
            Target.the("métodos de envío disponibles")
                    .located(By.cssSelector(
                            "#collapse-shipping-method input[name='shipping_method']"));
    public static final Target BOTON_CONTINUAR_ENVIO =
            Target.the("continuar con el método de envío")
                    .located(By.id("button-shipping-method"));
    public static final Target METODOS_PAGO =
            Target.the("métodos de pago disponibles")
                    .located(By.cssSelector(
                            "#collapse-payment-method input[name='payment_method']"));
    public static final Target ACEPTAR_TERMINOS =
            Target.the("aceptar los términos y condiciones")
                    .located(By.cssSelector(
                            "#collapse-payment-method input[name='agree']"));
    public static final Target BOTON_CONTINUAR_PAGO =
            Target.the("continuar con el método de pago")
                    .located(By.id("button-payment-method"));
    public static final Target BOTON_CONFIRMAR_PEDIDO =
            Target.the("botón para confirmar el pedido")
                    .located(By.id("button-confirm"));
    public static final Target MENSAJE_CONFIRMACION_COMPRA =
            Target.the("mensaje de confirmación de compra exitosa")
                    .located(By.xpath("//div[@id='content']//h1[text()='Your order has been placed!']"));
    public static final Target TITULO_PRODUCTO =
            Target.the("título del producto")
                    .locatedBy("//h1[contains(normalize-space(),'{0}')]");
    public static final Target OPCIONES_RADIO_CINEMA =
            Target.the("opciones de radio de Apple Cinema 30")
                    .located(By.cssSelector("#product input[type='radio']"));
    public static final Target OPCIONES_CHECKBOX_CINEMA =
            Target.the("opciones de casilla de Apple Cinema 30")
                    .located(By.cssSelector("#product input[type='checkbox']"));
    public static final Target TEXTO_CINEMA =
            Target.the("texto requerido de Apple Cinema 30")
                    .located(By.id("input-option208"));
    public static final Target SELECCION_CINEMA =
            Target.the("opción desplegable de Apple Cinema 30")
                    .located(By.id("input-option217"));
    public static final Target AREA_TEXTO_CINEMA =
            Target.the("texto descriptivo de Apple Cinema 30")
                    .located(By.id("input-option209"));
    public static final Target BOTON_SUBIR_ARCHIVO_CINEMA =
            Target.the("botón para cargar el archivo de Apple Cinema 30")
                    .located(By.id("button-upload222"));
    public static final Target ARCHIVO_CINEMA_CARGADO =
            Target.the("archivo cargado para Apple Cinema 30")
                    .located(By.id("input-option222"));
    public static final Target CAMPO_ARCHIVO_SUBIDA =
            Target.the("selector del archivo para la opción de Apple Cinema 30")
                    .located(By.cssSelector("#form-upload input[name='file']"));
    public static final Target FECHA_CINEMA =
            Target.the("fecha requerida de Apple Cinema 30")
                    .located(By.id("input-option219"));
    public static final Target HORA_CINEMA =
            Target.the("hora requerida de Apple Cinema 30")
                    .located(By.id("input-option221"));
    public static final Target FECHA_HORA_CINEMA =
            Target.the("fecha y hora requeridas de Apple Cinema 30")
                    .located(By.id("input-option220"));
    public static final Target FECHA_ENTREGA =
            Target.the("fecha de entrega requerida")
                    .located(By.xpath("//label[normalize-space()='Delivery Date']/following-sibling::input[@type='text']"));
    public static final Target FECHHA_ENTREGA = FECHA_ENTREGA;
    public static final Target ADDCARRITO =
            Target.the("botón para agregar HPLP al carrito")
                    .located(By.id("button-cart"));

    public static Target botonAgregarProductoListado(String identificadorProducto) {
        return Target.the("botón para agregar el producto al carrito")
                .located(By.xpath(
                        "//div[contains(@class,'product-thumb')][.//a[contains(@href,'product_id="
                                + identificadorProducto
                                + "')]]//button[contains(@onclick,'cart.add')]"));
    }




    //CANONEOS
    public final static  Target SELECT= Target.the("select de opciones de Apple Cinema 30")
            .located(By.xpath("//select[@id='input-option226']"));

    public final static Target COLOR= Target.the("opciones de color de Apple Cinema 30")
            .located(By.xpath("//option[contains(@value,'1')]"));

    private PaginaOpenCart() {
    }
}
