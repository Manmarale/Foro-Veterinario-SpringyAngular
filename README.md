# 🐾 Foro Veterinario - Despliegue Full Stack

Aplicación completa de Foro Veterinario desplegada con Docker Compose.

## Stack Tecnológico

| Servicio | Tecnología | Puerto |
|----------|-----------|--------|
| Frontend | Angular 15 + Nginx | :80 |
| Backend  | Spring Boot 3.1.5  | :8080 |
| Database | MySQL 8.0          | :3307 |

## Despliegue Rápido

```bash
cd /home/manmarale/tfg
sudo docker-compose up --build -d
```

## Verificar estado

```bash
sudo docker ps
sudo docker logs foro_vet_backend
sudo docker logs foro_vet_frontend
sudo docker logs foro_vet_mysql
```

## Accesos

- **Frontend**: http://localhost
- **Backend API**: http://localhost:8080/api
- **Swagger**: http://localhost:8080/documentacion

## Configuración

- **Database**: foro_veterinario
- **DB User**: root
- **DB Password**: ""

## Estructura del Proyecto

```
tfg/
├── docker-compose.yml
├── Angular/
│   └── foro_veterinario_front/    # Código frontend
├── Spring/
│   └── foro_veterinario_back/       # Código backend
└── README.md
```

## Comandos Útiles

```bash
# Reconstruir
sudo docker-compose down && sudo docker-compose up --build -d

# Acceder al backend
docker exec -it foro_vet_backend bash

# Acceder a MySQL
docker exec -it foro_vet_mysql mysql -u root -pSOYELAMO

# Ver logs
docker logs foro_vet_backend
docker logs foro_vet_frontend
docker logs foro_vet_mysql
```

## Autor

**manmarale** — Trabajo de Fin de Grado (TFG)
