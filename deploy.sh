#!/bin/bash

echo "Iniciando despliegue de Foro Veterinario..."

# Verificar que Docker esté corriendo
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker no está corriendo. Por favor inicia Docker primero."
    exit 1
fi

# Verificar que docker-compose esté instalado
if ! command -v docker-compose &> /dev/null; then
    echo "Error: docker-compose no está instalado."
    exit 1
fi

echo "Construyendo y desplegando contenedores..."

# Detener contenedores existentes si están corriendo
echo "Deteniendo contenedores existentes..."
docker-compose down --remove-orphans

# Construir y levantar todos los servicios
echo "Construyendo imágenes y levantando servicios..."
docker-compose up --build -d

echo "Esperando que los servicios estén listos..."

# Función para verificar si un servicio está listo
wait_for_service() {
    local service_name=$1
    local url=$2
    local max_attempts=30
    local attempt=1
    
    echo "Verificando $service_name..."
    
    while [ $attempt -le $max_attempts ]; do
        if curl -f -s "$url" > /dev/null; then
            echo "$service_name está listo!"
            return 0
        fi
    echo "Intento $attempt/$max_attempts - Esperando que $service_name esté listo..."
        sleep 10
        ((attempt++))
    done
    
    echo "$service_name no respondió después de $max_attempts intentos"
    return 1
}

# Esperar a que MySQL esté listo
echo "Verificando base de datos MySQL..."
sleep 20

# Esperar a que el backend esté listo
wait_for_service "Backend (Spring Boot)" "http://localhost:8080/api/auth/status" || {
    echo "El backend no respondió, pero continuamos..."
}

# Esperar a que el frontend esté listo
wait_for_service "Frontend (Angular)" "http://localhost" || {
    echo "Error: El frontend no está disponible"
    exit 1
}

echo ""
echo "¡Despliegue completado!"
echo ""
echo "URLs disponibles:"
echo "   Frontend (Angular): http://localhost"
echo "   Backend (Spring Boot): http://localhost:8080"
echo "   Documentación API (Swagger): http://localhost:8080/documentacion"
echo "   MySQL: localhost:3306"
echo ""
echo "Comandos útiles:"
echo "   Ver logs: docker-compose logs -f [servicio]"
echo "   Parar todo: docker-compose down"
echo "   Reiniciar: docker-compose restart [servicio]"
echo "   Estado: docker-compose ps"
echo ""
echo "Credenciales de la base de datos:"
echo "   Host: localhost:3306"
echo "   Database: foro_veterinario"
echo "   User: root"
echo "   Password: SOYELAMO"
echo ""