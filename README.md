# Spring WebFlux Application

## 📋 Descripción

Aplicación reactiva construida con **Spring WebFlux** que sigue una arquitectura limpia (Clean Architecture) con
separación clara de capas: dominio, aplicación e infraestructura.

## 🛠️ Tecnologías

- **Java SDK 25**
- **Spring WebFlux** - Framework reactivo para aplicaciones web
- **Jakarta EE** - Especificaciones empresariales con imports jakarta
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias y construcción del proyecto
- **Docker Compose** - Orquestación de contenedores

## 🚀 Requisitos Previos

- **Java 25** o superior
- **Maven 3.8+**
- **Docker** y **Docker Compose** (opcional, para contenedores)

## ⚙️ Instalación y Ejecución

### Usando Docker Compose

```bash
 docker-compose -f compose.yml up
```

### Compilar el proyecto

```bash
 ./mvnw clean package
 ``` 

## 🧪 Testing

### Ejecutar tests

```bash
 ./mvnw test
 ```

### Colección de Postman

El proyecto incluye una colección de Postman (`postman_collection.json`) para probar los endpoints de la API.

## 📖 Arquitectura

El proyecto implementa **Arquitectura Hexagonal/Clean Architecture**:

- **Domain**: Contiene las entidades de negocio y reglas de dominio
- **Application**: Implementa los casos de uso y lógica de aplicación
- **Infrastructure**: Adaptadores externos (controladores REST, repositorios, configuraciones)

## 🔄 Programación Reactiva

Este proyecto utiliza **Spring WebFlux** para proporcionar:

- Procesamiento no bloqueante
- Backpressure handling
- Alta concurrencia con recursos mínimos
- Streams reactivos con `Mono` y `Flux`

---

Desarrollado con ❤️ por DavinchiCoder
