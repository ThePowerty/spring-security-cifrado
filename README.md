## API REST

Aplicación API REST con Spring Boot con conexión en base de datos PostgreSQL 
con cifrado de contraseñas y seguridad mediante JSON Web Token

----------------------------------------------------------------------------------------------------
## Cifrado

Es el proceso de codificar la información de su representación original (texto plano) 
a texto cifrado, de manera que solamente pueda ser descifrado utilizando una clave.

Historia de cifrado:

1. Almacenar contraseñas en texto plano
2. Almacenar contraseñas cifradas con una función hash
3. Almacenar contraseñas cifradas con una función hash + salt
4. Almacenar contraseñas cifradas con una función adaptativa + factor de trabajo

La seguridad se gana haciendo que la validación de contraseñas sea costosa computacionalmente.

## Algoritmos en Spring Security

* Bcrypt
* PBKDF2
* scrypt
* argon2
----------------------------------------------------------------------------------------------------
## JWT (JSON Web Token)

https://jwt.io/introduction

Es un estándar abierto que permite transmitir información entre dos partes:

## Funcionamiento Session

1. Cliente envía una petición a un servidor (/api/login)
2. Servidor valida username y password, si no son válidos devolverá una respuesta 401 unauthorized
3. Servidor valida username y password, si son válidos entonces se almacena el usuario en session
4. Se genera una cooki en el Cliente
5. En las próximas peticiones se comprueba que el cliente está en session

Desventajas:
* La información de la sesión se almacena en el servidor, lo cual consume recursos.

## Funcionamiento JWT

1. Cliente envía una petición a un servidor (/api/login)
2. Servidor valida username y password, si no son válidos devolverá una respuesta 401 unauthorized
3. Servidor valida username y password, si son válidos entonces genera un token JWT utilizando una secret key
4. Servidor devuelve el token JWT generado al Cliente
5. Cliente envía peticiones a los endpoint REST del servidor utilizando el token JWT en las cabeceras
6. Servidor valida el token JWT en cada petición y si es correcto permite el acceso a los datos

Ventajas:
* El token se almacena en el Cliente, de manera que consume menos recursos en el Servidor, lo cual permite mejor escalabilidad.

Desventajas:
* El token está en el navegador, no podríamos invalidarlo antes de la fecha de expiración asignada condo se creó
  * Lo que se realiza es dar la opción de logout, lo cual simplemente borra el token

## Estructura del token JWT

3 partes separadas por un punto {.} y codificadas en base 64 cada una:

1. Header
``` json
{
    "alg": "HS512,
    "typ": "JWT"    
}
```
2. Payload (información, datos del usuario, no sensibles)

``` json
{
  "name": "John Doe",
  "admin": true
}
```
3. Asignatura

```
HMACKSHA256(
    base64UrlEncode(header) + "." 
    + base64UrlEncode(payload), 
    secret)
```

El User-Agent envía el token JWT en las cabeceras:

```
Authorization: Bearer <token>
```
----------------------------------------------------------------------------------------------------

## Configuración Spring

Crear proyecto Spring Boot con:
* Spring Security
* Spring Web
* Spring boot devtools
* Spring Data JPA
* PostgreSQL
* Dependencia jwt (se añade manualmente en pom.xml)

``` xml
    <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
    <dependency>
          <groupId>io.jsonwebtoken</groupId>
          <artifactId>jjwt-api</artifactId>
          <version>0.12.5</version>
      </dependency>
      <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl -->
      <dependency>
          <groupId>io.jsonwebtoken</groupId>
          <artifactId>jjwt-impl</artifactId>
          <version>0.12.5</version>
          <scope>runtime</scope>
      </dependency>
      <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson -->
      <dependency>
          <groupId>io.jsonwebtoken</groupId>
          <artifactId>jjwt-jackson</artifactId>
          <version>0.12.5</version>
          <scope>runtime</scope>
      </dependency>
      <!-- https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api -->
      <dependency>
          <groupId>jakarta.servlet</groupId>
          <artifactId>jakarta.servlet-api</artifactId>
          <version>6.0.0</version>
          <scope>provided</scope>
      </dependency>
```