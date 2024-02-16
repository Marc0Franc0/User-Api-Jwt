# [User-Api-Jwt](https://github.com/Marc0Franc0/User-Api-Jwt#user-api-jwt)
Proyecto de muestra sobre cómo implementar la seguridad con autenticación JWT basada en Spring boot 3 y Spring security 6

## Características
- Registro de usuario e inicio de sesión con autenticación JWT
- Cifrado de contraseña usando BCrypt
- Autorización basada en roles con Spring Security

## Tecnologías
- Spring Boot 3.1.4
- Spring Security
- JSON Web Tokens (JWT)
- BCrypt
- Maven
- MySQL

## Ejecución
1. Clonar repositorio: git clone https://github.com/Marc0Franc0/User-Api-Jwt.git
2. Ir al directorio del proyecto: cd User-Api-Jwt
3. Seguir pasos para ejecución con Maven

## Requerimientos para ejecutar con Maven

Para construir y ejecutar la aplicación necesita:

- [JDK 17+](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3+](https://maven.apache.org)

Configurar perfil (producción o desarrollo):[application.properties](https://github.com/Marc0Franc0/User-Api-Jwt/blob/main/src/main/resources/application.properties)

Configurar variables de entorno para producción: [application-prod.properties](https://github.com/Marc0Franc0/User-Api-Jwt/blob/main/src/main/resources/application-prod.properties)

Configurar variables de entorno para desarrollo: [application-dev.properties](https://github.com/Marc0Franc0/User-Api-Jwt/blob/main/src/main/resources/application-dev.properties)

Ejecutar localmente

```shell
mvn clean install
```
```shell
mvn spring-boot:run
```

Dirigirse a:
- [Documentación Swagger](http://localhost:8080/swagger-ui/index.html)