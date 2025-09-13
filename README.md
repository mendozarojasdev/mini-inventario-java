# Mini Inventario 2
Sistema para control de productos alimenticios desarrollado en Java con CRUD completo y generación de reportes en PDF.

## Tabla de contenido
- [Características principales](#características-principales)
- [Screenshots](#screenshots)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Requerimientos](#requerimientos)
- [Instalación](#instalación)
- [Licencia](#licencia)

## Características principales
- CRUD de productos (Crear, Leer, Actualizar, Eliminar)
- Interfaz gráfica desarrollada con Java Swing.
- Visualización de registros en tabla dinámica.
- Generación de reportes PDF con logotipo, fecha actual y listado de productos (Jaspersoft).

## Screenshots

### Pantalla principal
![pantalla principal](screenshots/03-seleccionar-producto.png)
> Pantalla principal con datos de prueba

### Pantalla de reportes
![pantalla reportes](screenshots/07-previsualizar-reporte)
> Pantalla de previsualización de reportes

📂 Puedes ver más capturas en la carpeta [/screenshots](screenshots/).

## Tecnologías utilizadas
**Frontend**
- Java Swing
- Jasper Reports

**Backend**
- Java

**Base de datos**
- MariaDB 12.0

**Software**
- Eclipse IDE 2025-03
- Jaspersoft Studio 7.0.2

## Requerimientos
- [Java JDK 23](https://download.oracle.com/java/23/archive/jdk-23.0.1_windows-x64_bin.exe)
- [MariaDB 12.0.2](https://mariadb.org/download/)

## Instalación

### 1. Crear base de datos
- Abre la consola de MariaDB
- Ingresa con usuario: **root** (sin contraseña por defecto).
- Copia y ejecuta el [esquema](database/scheme.sql) que se encuentra en el repositorio.
- Crea al usuario administrador ejecutando la instrucción [seed](database/seed.sql).

### 2. Descargar el proyecto
Puedes descargar la versión más reciente de Mini Inventario 2 desde [GitHub Releases](https://github.com/mendozarojasdev/mini-inventario-java/releases/latest).

### 3. Instalar ejecutable
- Ejecutar el .exe para comenzar la instalación
- El programa se instalará por defecto en la ruta C:\Users\(Usuario)\AppData\Local\Inventario\, también se puede instalar en una ruta diferente como C:\Program Files\Mini Inventario 2, pero en este caso se debe ejecutar con privilegios de administrador.

## Desarrollo
Si deseas continuar con el desarrollo del proyecto:
- Asegúrate de tener instalado [Java JDK 23](https://download.oracle.com/java/23/archive/jdk-23.0.1_windows-x64_bin.exe) y [MariaDB 12.0.2](https://mariadb.org/download/)
- 
- También es necesario tener Eclipse IDE for Java Developers.
### 1. Clona este proyecto y copia la carpeta MiniInventario2 a tu eclipse-workspace


### 3. Configurar PHP en WampServer
Edita el archivo `php.ini` y ajusta las siguientes configuraciones:

```ini
date.timezone = America/Mexico_City
display_errors = Off
upload_max_filesize = 256M

# Habilita las siguientes extensiones
extension=mysqli
extension=gd
```

### 4. Configurar MariaDB en lugar de MySQL
En WampServer:
```ini
Right-click Wampmanager icon -> Tools -> Invert default DBMS MySQL to MariaDB
```

### 5. Crear la base de datos
- Abre phpMyAdmin desde localhost/phpmyadmin/
- Ingresa con usuario: **root** (sin contraseña por defecto).
- Copia y ejecuta el esquema que se encuentra en el [repositorio](database/biblioteca_asistencias.sql).

### 6. Desplegar el proyecto
Descomprime el archivo zip en:
```bash
C:\wamp\www\
```

### 7. Crear usuario principal
Ingresa a:
```bash
localhost/biblioteca-asistencias/admin/signup.php
```
En este aparado registra el primer usuario administrador.

### 8. Instalar la aplicación
- Una vez dentro, utiliza la opción “instalar” (aparece en la esquina derecha de la URL).
- Esto generará un acceso directo en tu escritorio para ingresar más fácilmente.

### 9. Instalar PhpSpreadsheet
Abre una terminal en la ruta:
```bash
C:/wamp/www/biblioteca-asistencias/componentes/phpspreadsheet
```
En esta ruta ejecuta el siguiente comando.
```bash
composer require phpoffice/phpspreadsheet
```
✅ Listo, el sistema debería estar funcionando en el entorno local.

## Licencia
Mini Inventario 2 está publicado bajo la licencia MIT. Consulta el archivo [MIT license](https://github.com/mendozarojasdev/mini-inventario-java/blob/master/LICENSE) para más información.
