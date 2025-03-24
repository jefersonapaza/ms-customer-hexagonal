# 🎯 Capitole - Price Checker Service

## 📝 Desafío Técnico - Inditex (Capitole)

Este proyecto es un microservicio REST desarrollado en **Spring Boot** para resolver el siguiente desafío técnico propuesto por **Capitole - Inditex**.

### 🎯 Objetivo
Diseñar un servicio REST que permita consultar el precio final aplicable a un producto específico de una marca, según un rango de fechas.

### 🔍 Requisitos principales:
- `applicationDate`: fecha en la que se desea aplicar la tarifa.
- `productId`: ID del producto.
- `brandId`: ID de la marca.
- El servicio devuelve la tarifa correspondiente, con prioridad y moneda.

---

## 🏗️ Metodología de desarrollo

Para fines de simular un entorno **Scrum**, la gestión de tareas y sprints se realizó en un tablero Kanban de **Jira**, que puedes consultar aquí:  
👉 [Tablero Jira - Price Checker](https://jefersonapaza.atlassian.net/jira/software/projects/SDPPT/boards/34)

![Captura de pantalla 2025-03-20 210257](https://github.com/user-attachments/assets/e5b1627e-a8bd-4865-b80d-e352cca7f6c7)


> Cada funcionalidad fue desarrollada siguiendo buenas prácticas de ingeniería de software.

---

## ⚙️ Stack y dependencias clave

- **Spring Boot Starter Web** - Desarrollo de APIs REST.
- **Spring Boot Starter Data JPA** - Persistencia en base de datos.
- **H2 Database** - Base de datos embebida.
- **Flyway** - Migraciones automáticas de la base de datos.
- **Lombok** - Reducción de código repetitivo.
- **Springdoc OpenAPI** - Documentación Swagger.
- **Spring Boot Starter Test** - Pruebas unitarias e integración.

---

## 🧩 Arquitectura y diseño

- Arquitectura Hexagonal (Ports & Adapters).
- Principios **SOLID**.
- Separación en capas: `domain`, `application`, `infrastructure`.

---

## 🗄️ Base de datos y migraciones

La base de datos fue refactorizada creando dos nuevas tablas para mejorar el desacoplamiento:

- **BRANDS** (ID, NAME, STATE)
- **PRODUCTS** (ID, NAME, STATE)

La tabla **PRICES** ahora referencia a estas nuevas entidades.  
Las migraciones fueron gestionadas mediante **Flyway**.

---

## ✅ Pruebas cubiertas

El microservicio incluye pruebas de integración para los siguientes casos:

- ✔️ Respuesta exitosa (`200 OK`)
- ⚠️ Sin contenido (`204 No Content`)
- ❌ Parámetros inválidos (`400 Bad Request`)
- 💥 Error interno (`500 Internal Server Error`)

---

## 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/tu-usuario/price-checker.git
cd price-checker

---

## ✨ Autor

- [Jeferson Apaza](https://github.com/jefersonapaza)
