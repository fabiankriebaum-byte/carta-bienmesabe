# Carta Bienmesabe (Spring Boot)

Requisitos: Java 17+ y Maven.

    mvn spring-boot:run

- Carta para clientes:  http://localhost:8080/
- Panel para editar:    http://localhost:8080/admin   (usuario/contraseña en application.properties)

Los cambios se guardan en `data/menu.json`. Las fotos van en la carpeta `img/`
(ej: `img/frente-local.jpg`) y en el panel se escriben como `/img/archivo.jpg`.
Datos del local (teléfono, WhatsApp, dirección) y la contraseña: `src/main/resources/application.properties`.
