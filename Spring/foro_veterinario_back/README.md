# 🐾 Foro Veterinario - Backend (Spring Boot)

API REST para el Foro Veterinario, una comunidad donde veterinarios, estudiantes y propietarios de mascotas pueden compartir conocimiento sobre salud animal.

## Tecnologías

- **Java 17** + **Spring Boot 3.1.5**
- **Spring Security** + **JWT**
- **Spring Data JPA** + **Hibernate**
- **MySQL 8.0**
- **Spring Mail** (recordatorios de vacunas)
- **Swagger/OpenAPI** (documentación)

## Funcionalidades

- Gestión de usuarios con roles (ADMIN, VETERINARIO, ESTUDIANTE, PROPIETARIO)
- Foro de discusión con temas veterinarios
- Gestión de mascotas (CRUD)
- Calendario de vacunas con recordatorios por email
- Subida de imágenes de perfil y mascotas
- Autenticación y autorización con JWT

## Estructura de Paquetes

```
com.manmarale.foro_veterinario/
├── configuracion/       # Seguridad, CORS, OpenAPI
├── controllers/         # REST Controllers
├── exceptions/          # Excepciones personalizadas
├── handler/             # Manejador global de errores
├── models/              # Entidades JPA y DTOs
├── repository/          # Repositorios JPA
├── security/jwt/        # Filtros y proveedores JWT
└── services/            # Lógica de negocio
```

## Autor

**manmarale** — Trabajo de Fin de Grado (TFG)
