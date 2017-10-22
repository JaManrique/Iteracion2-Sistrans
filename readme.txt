Preparación de la aplicación:

1. En SQL Developer hacer una conexión a una instancia de Oracle SQL con los datos disponibles en el archivo conexion.properties (dentro del directorio: WebContent\WEB-INF\ConnectionData)
2. En esa conexión ejecutar el archivo SQL\BorrarCrearPoblar.sql (Está en el directorio SQL) Éste archivo borra todas las tablas existentes relacionadas con el proyecto, las crea de nuevo con las restricciones y finalmente la pobla con datos de prueba (Se hace esto con el fin de minimizar fuentes de error)

Ejecución de la aplicación:

1. Abrir el proyecto Java en Eclipse (Debe tener JBoss/Wildfly instalado)
2. Dentro del paquete rest, ejecutar en el servidor (run as -> run on server) el archivo RotondAndesServices.java. Debe estar en un servidor Wildfly 10.x.

Pruebas de la aplicación:

-Pruebas unitarias:
   -1. Verifique que la aplicación NO esté corriendo (para evitar errores)
   -2. Dentro del paquete test, ejecute como pruebas de JUnit el archivo RotondAndesSQLTest.java

-Pruebas de servicios:
   -1. Verifique que la aplicación esté corriendo
   -2. Dentro de postman, importe el archivo RotondAndes.postman_collection
   -3. Ejecute las pruebas importadas
