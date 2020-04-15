# HTTP SERVER | JAVA 
El objetivo general del proyecto consiste en programar un servidor HTTP capaz de responder a solicitudes HTTP en Java.

El servidor debe ser programado con sockets. 

No se puede utilizar ninguna biblioteca HTTP.

Deberá utilizar programación concurrente.

Se debe manejar una lista de Mime Types (puede utilizar esta lista en XML de Mime Types).

El servidor mantendrá una bitácora de conexiones.
# HTTP METODOS DE PETICIÓN
**HEAD:** El método `GET` solicita una representación de un recurso específico. Las peticiones que usan el método `GET` sólo deben recuperar datos.
**GET:** El método `HEAD` pide una respuesta idéntica a la de una petición GET, pero sin el cuerpo de la respuesta.
**POST:**   El método `POST` se utiliza para enviar una entidad a un recurso en específico, causando a menudo un cambio en el estado o efectos secundarios en el servidor.

# HTTP HEADERS  
En este proyecto se utilizan los siguientes encabezados para las solicitudes HTTP.

 - 	Accept 	
 - Content-type 
 - Content-length 
 - 	Date 
 - 	Host 
 - 	Referer     
 - Server

# CÓDIGOS DE RETORNO

***200 : Ok***
La solicitud ha tenido éxito. El significado de un éxito varía dependiendo del método HTTP:  
GET: El recurso se ha obtenido y se transmite en el cuerpo del mensaje.  
HEAD: Los encabezados de entidad están en el cuerpo del mensaje.  
PUT o POST: El recurso que describe el resultado de la acción se transmite en el cuerpo del mensaje.  
TRACE: El cuerpo del mensaje contiene el mensaje de solicitud recibido por el servidor.

***404: Not Found***
El servidor no pudo encontrar el contenido solicitado. Este código de respuesta es uno de los más famosos dada su alta ocurrencia en la web.

***406:Not Acceptable***
Esta respuesta es enviada cuando el servidor, despues de aplicar una negociación de contenido servidor-impulsado, no encuentra ningún contenido seguido por la criteria dada por el usuario.

***501: Not Implemented***
El método solicitado no esta soportado por el servidor y no puede ser manejada. Los únicos métodos que los servidores requieren soporte (y por lo tanto no deben retornar este código) son GET y HEAD.

# Referencias


-	 SSaurel's Blog :   https://www.ssaurel.com/blog/create-a-simple-http-web-server-in
- HTTP Server: Everything you need to know to Build a simple HTTP server from scratch-java:  https://medium.com/from-the-scratch/http-server-what-do-you-need-to-know-to-build-a-simple-http-server-from-scratchd1ef8945e4fawww.w3.org/Protocols/rfc2616/rfc2616.html.
- Códigos de estado de respuesta HTTP - HTTP | MDN: 
https://developer.mozilla.org/es/docs/Web/HTTP/Status 
- Hypertext Transfer Protocol -- HTTP/1.1: http://www.w3.org/Protocols/rfc2616/rfc2616.html. 

# Autor

Propiedad de Mayquely Salmerón y Felipe Almanza. Desarrollado para el curso CI-0137: Desarrollo de Aplicaciones Web de la Universidad de Costa Rica.

Licencia

Copyright (c) 2020 Mayquely Salmerón  maysjmnz@gmail.com, Felipe Almanza falmanza4@gmail.com.

Se concede permiso, de forma gratuita, a cualquier persona que obtenga una copia de este software y los archivos de documentación asociados, para tratar el Software sin restricciones, incluyendo sin limitación, los derechos de uso, copia, modificación, fusión, publicación, distribuir, sublicenciar y / o vender copias del Software, y permite a las personas a quienes se les proporciona el Software hacerlo, sujeto a las siguientes condiciones:

El aviso de copyright anterior y este aviso de permiso serán incluido en todas las copias o porciones sustanciales del software.

EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESO O IMPLICITO, INCLUIDAS, ENTRE OTRAS, LAS GARANTÍAS DE COMERCIABILIDAD, APTITUD PARA UN PROPÓSITO PARTICULAR Y NO INFRACCIÓN. EN NINGÚN CASO EL AUTOR SERÁ RESPONSABLE DE NINGÚN TIPO RECLAMO, DAÑOS U OTRA RESPONSABILIDAD, YA SEA EN UNA ACCIÓN DE CONTRATO, AGRAVIO O DE OTRA ÍNDOLE, DERIVADO DE, FUERA DE O EN RELACIÓN CON EL SOFTWARE O EL USO U OTROS TRATOS EN EL SOFTWARE.
