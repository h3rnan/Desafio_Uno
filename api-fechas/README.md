# Api Solución Desafío Previred
La solución obedece al [desafío de previred](https://github.com/previred/Desafio_Uno), este proyecto se ha generado con la herramienta [swagger-codegen](https://github.com/swagger-api/swagger-codegen) bajo el plugin de maven.

### Requerimientos De Programación
- Java 8
- Maven 3
- Spring-Boot 1.5.9.RELEASE
- Swagger Codegen 2.4.7 (OpenApi 2.0)

### Compilación y Ejecución De La Solución
Para copilar y ejecutar el proyecto se requiere Java 8 y Maven instalado. 
1. Ingresar al directorio del proyecto **api-fechas**.
2. Ingresar el siguiente comando *maven* para compilar el proyecto:
   ```bash
   mvn package
   ```
3. Ingresar el siguiente comando *java* para ejecutar el proyecto:
   ```bash
   java -jar target/api-fechas-1.0.0.jar
   ```
4. Ejecutar el servicio [GDD](https://github.com/previred/Generador_Datos_Desafio_Uno) (Opcional).

*Nota*: Debe estar disponible el puerto 8180 y 8080 (*si desea utilizar el servicio [GDD](https://github.com/previred/Generador_Datos_Desafio_Uno)*) del PC donde se ejecute el proyecto.

### Uso De La Solución

1. Ingresar a la interfaz de usuario *swagger* [http://localhost:8180/api-fechas](http://localhost:8180/api-fechas)
2. Ingresar periodo en formato `JSON` en servicio de `/fechas`:
    ```json
    {
      "id": 6,
      "fechaCreacion": "1968-08-01",
      "fechaFin": "1971-06-01",
      "fechas": [
        "1969-03-01",
        "1969-05-01",
        "1969-09-01",
        "1971-05-01"]
    }
    ```
3. La salida del servicio es la siguiente:
    ```json
    {
      "id": 6,
      "fechaCreacion": "1969-03-01",
      "fechaFin": "1970-01-01",
      "fechas": [
        "1969-03-01",
        "1969-05-01",
        "1969-09-01",
        "1970-01-01"
      ],
      "fechasFaltantes": [
        "1969-04-01",
        "1969-06-01",
        "1969-07-01",
        "1969-08-01",
        "1969-10-01",
        "1969-11-01",
        "1969-12-01"
      ]
    }
    ```
4. Opcionalmente, si no desea ingresar el periodo entonces la solución invocará el servicio [GDD](https://github.com/previred/Generador_Datos_Desafio_Uno).
