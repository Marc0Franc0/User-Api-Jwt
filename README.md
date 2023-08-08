# [User-Api-Jwt](https://github.com/Marc0Franc0/User-Api-Jwt#user-api-jwt)
Proyecto de muestra sobre cómo implementar la seguridad con autenticación JWT basada en Spring boot 3 y Spring security 6

## Características
- Registro de usuario e inicio de sesión con autenticación JWT
- Cifrado de contraseña usando BCrypt
- Autorización basada en roles con Spring Security

## Tecnologías
- Spring Boot 3.0
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

Configurar datos de la base de datos MySQL: [application.properties](https://github.com/Marc0Franc0/School-Management/blob/main/src/main/resources/application.properties)

Configurar JWT: [application.properties](https://github.com/Marc0Franc0/School-Management/blob/main/src/main/resources/application.properties)
- jwt.secret.key = esYG4cI3/jsQ1f3b+25mesq9nXsN7kIU45hPr27FyRxMd7WteShs/5VnXv1YfgMR
  (Se utiliza para firmar los tokens)
- jwt.time.expiration = 86400000 (equivalente a un día)

Configurar usuario admin:
- user.admin.username = nombre_de_usuario_admin
- user.admin.password = contraseña_de_usuario_admin

Ejecutar localmente

```shell
mvn clean install
```
```shell
mvn spring-boot:run
```

Dirigirse a: [http://localhost:8080/](http://localhost:8080/)