Preparaci�n de la aplicaci�n:

1. En SQL Developer hacer una conexi�n a una instancia de Oracle SQL con los datos disponibles en el archivo conexion.properties (dentro del directorio: WebContent\WEB-INF\ConnectionData)
2. En esa conexi�n ejecutar el archivo SQL\BorrarCrearPoblar.sql (Est� en el directorio SQL) �ste archivo borra todas las tablas existentes relacionadas con el proyecto, las crea de nuevo con las restricciones y finalmente la pobla con datos de prueba (Se hace esto con el fin de minimizar fuentes de error)

Ejecuci�n de la aplicaci�n:

1. Abrir el proyecto Java en Eclipse (Debe tener JBoss/Wildfly instalado)
2. Dentro del paquete rest, ejecutar en el servidor (run as -> run on server) el archivo RotondAndesServices.java. Debe estar en un servidor Wildfly 10.x.

Pruebas de la aplicaci�n:

-Pruebas unitarias:
   -1. Verifique que la aplicaci�n NO est� corriendo (para evitar errores)
   -2. Dentro del paquete test, ejecute como pruebas de JUnit el archivo RotondAndesSQLTest.java

-Pruebas de servicios:
   -1. Verifique que la aplicaci�n est� corriendo
   -2. Dentro de postman, importe el archivo RotondAndes.postman_collection
   -3. Ejecute las pruebas importadas
