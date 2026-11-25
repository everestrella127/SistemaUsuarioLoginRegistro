Buenas! Esta es mi tarea 4, en este readme explicare rapidamente como utilizar el programa 
Primero, consta de varios requisitos como:

* JDK 17 o superior
  
* MySQL Server 8 (o XAMPP con MySQL) y MySQL Workbench
  
* IntelliJ IDEA (u otro IDE con soporte para Maven)

Ahora, para ejecutar el programa debes de:

1- clonar el repositorio 

2- crear la base de datos abriendo mysql workbench y ejecutando el script presente en la carpeta sql

3- abrir el proyecto en IntelliJ IDEA (o IDE de preferencia con soporte para Maven) y ejecute el main 

Para usar la aplicacion es de esta forma:

1-La primera vez no hay usuarios, entonces, pulsa Crear cuenta nueva y completa el formulario.

2- Vuelve al login, entra con tu usuario y contraseña y verás la lista de usuarios registrados.

3-Usa Nuevo usuario para agregar más. Selecciona una fila y pulsa Actualizar o Eliminar para modificarla.

4-Al actualizar, si dejas las contraseñas vacías se conserva la actual.

5-Pulsa Cerrar sesión para volver al login.

Tiene cumple con varias funcionalidades como:

1-Login con usuario y contraseña. La contraseña se oculta mientras se escribe.

2-Si falta el usuario o la contraseña, muestra el mensaje: "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse".

3-Registro con 7 campos obligatorios: nombre de usuario, nombre, apellido, teléfono, correo electrónico, contraseña y confirmar contraseña.

4-Si falta algún campo, un mensaje indica cuáles faltan.

5-Si la contraseña y la confirmación no coinciden, muestra un error.

6-Valida que el teléfono tenga 10 números, que el correo tenga un formato válido y que el nombre de usuario no esté repetido.

7-Al iniciar sesión correctamente, se cierra el login y se abre la pantalla principal con la lista de usuarios (nombre, apellido, teléfono, correo y usuario).

8-Nuevo usuario, Actualizar y Eliminar usuarios. Los cambios se ven automáticamente en la tabla.

9-Botón Cerrar sesión, que vuelve a mostrar el login.

10-Las contraseñas se guardan encriptadas (SHA-256), nunca en texto plano para mantener la seguridad

Ojo: la contraseña de MySQL que aparece en ConexionBD.java es solo un texto de ejemplo, cada persona debe de poner la suya antes de ejecutar el programa

Esta es mi primera vez trabajando con MySQL y github, espero que mi tarea cumpla de manera satisfactoria con la rubrica! Muchas Gracias!

