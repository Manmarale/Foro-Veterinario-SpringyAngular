#!/bin/bash

echo "🧹 Limpiando despliegue de Foro Veterinario..."

# Parar y eliminar contenedores
echo "🛑 Deteniendo y eliminando contenedores..."
docker-compose down --remove-orphans

# Eliminar volúmenes (opcional, descomenta si quieres eliminar la base de datos)
# echo "🗑️  Eliminando volúmenes..."
# docker-compose down --volumes

# Eliminar imágenes construidas (opcional)
read -p "¿Eliminar también las imágenes construidas? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🗑️  Eliminando imágenes..."
    docker image rm tfg_backend tfg_frontend 2>/dev/null || true
    docker system prune -f
fi

echo "✅ Limpieza completada!"