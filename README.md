# Taller – Estilos Call-Return (ARSW 2026-i)

> **Curso:** Arquitecturas y Redes de Software  
> **Institución:** Escuela Colombiana de Ingeniería  
> **Fecha:** 04 de Junio de 2026  
> **Autores:** Rodrigo Humberto Gualtero
 
---
## Descripción general

Este taller introduce los conceptos fundamentales de comunicación en red usando Java. Se abordan
los protocolos **TCP** y **UDP**, el trabajo con URLs, sockets cliente-servidor, datagramas y,
finalmente, invocación remota de métodos con **RMI**.

A lo largo del taller se implementan clientes y servidores progresivamente más complejos,
pasando de lecturas simples de URLs hasta un chat distribuido con objetos remotos.


## Estructura del proyecto

```
EstilosCall-Return/
├── src/
│   ├── Punto1/
│   │   └── URLmetodos.java            # Lectura de componentes de URL
│   ├── Punto2/
│   │   └── lecturaPaginaInternet.java # Browser por consola
│   ├── Punto3/
│   │   ├── Cliente.java               # Cliente TCP – cuadrados
│   │   └── Servidor.java              # Servidor TCP – cuadrados
│   ├── Punto4/
│   │   ├── Cliente.java               # Cliente TCP – trigonométrico
│   │   └── Servidor.java              # Servidor TCP – trigonométrico
│   ├── Punto5/
│   │   └── ServidorWeb.java           # Servidor HTTP multi-solicitud
│   ├── Punto6/
│   │   ├── ClienteHora.java           # Cliente UDP – hora
│   │   └── ServidorHora.java          # Servidor UDP – hora
│   └── Punto7/
│       ├── chatClient.java            # Cliente del chat RMI
│       ├── ChatService.java           # Interfaz remota
│       └── ChatServiceImpl.java       # Implementación del objeto remoto
├── www/
│   ├── index.html                     # Página estática servida por Punto5
│   └── servidorWeb.png                # Imagen estática servida por Punto5
├── imagenes/                          # Capturas de pantalla del taller
├── lectura.html                       # Archivo generado por Punto2
├── .gitignore
└── README.md
```

---
EJERCICIO 1
---
### Objetivo
Crear un objeto **URL** en Java e imprimir los valores retornados por sus ocho métodos
de inspección: **getProtocol**, **getAuthority**, **getHost**, **getPort**, **getPath**,
**getQuery**, **getFile** y **getRef**.

### ¿Qué se hace?
Se instancia la clase **java.net.URL** con una dirección concreta y se llama a cada uno
de sus métodos para descomponer la URL en sus partes constitutivas (protocolo, host,
puerto, ruta, query string, etc.).

### Evidencia fotográfica

URL: https://teams.microsoft.com/v2/

![img.png](imagenes%2Fimg.png)

URL: https://myaccount.microsoft.com/?ref=MeControl

![imagQuery.png](imagenes%2FimagQuery.png)

EJERCICIO 2
---

### Objetivo
Construir una aplicación que solicite una dirección URL al usuario, descargue su
contenido usando un **BufferedReader** sobre **URL.openStream()** y lo guarde en un
archivo **resultado.html**.

### ¿Qué se hace?
Se abre un flujo de entrada (**InputStream**) sobre el objeto **URL**, se lee línea a
línea con un **BufferedReader** y se escribe el contenido en disco. El archivo
resultante puede abrirse directamente en un navegador.

### Evidencia fotográfica

URL: https://www.instagram.com/

![imghtml.png](imagenes%2Fimghtml.png)

url: https://developer.mozilla.org/es/docs/Web/HTTP

![imghtml1.png](imagenes%2Fimghtml1.png)



EJERCICIO 3
---
### Objetivo
Implementar un par cliente-servidor con sockets TCP donde el cliente envía un
número entero y el servidor responde con su cuadrado.

### ¿Qué se hace?
- **Servidor:** abre un **ServerSocket** en el puerto 35000, acepta una conexión, lee
  el número recibido, calcula el cuadrado y lo devuelve como texto.
- **Cliente:** crea un **Socket** hacia **127.0.0.1:35000**, lee un número desde
  la consola, lo envía y muestra la respuesta.
- 
### Cómo ejecutar
1. Compilar el proyecto.
2. Lanzar primero el servidor; quedará en espera.
3. Lanzar el cliente en otra terminal, ingresar un número y observar la respuesta.

Primero ejecuetamos el servidor, este se queda esperando y luego corremos el cliente,
aqui nos pide el numero y el servidor nos devuelve el cuadrado del número.

### Evidencia fotográfica

Número: 2


| Cliente | Servidor |
|---------|----------|
|![img.png](imagenes/cliente.png)| ![img.png](imagenes/Servidor.png)|


Número: 485

| Cliente | Servidor |
|---------|----------|
|![img.png](imagenes/cliente2.png)|![img.png](imagenes/servidor2.png)|


---
## EJERCICIO 4

---

### Objetivo
Ampliar el servidor TCP para que calcule seno, coseno o tangente según
la función activa. La función puede cambiarse en tiempo de ejecución
enviando un mensaje con prefijo **fun:** (p. ej. **fun:sin**, **fun:cos**, **fun:tan**). Por defecto inicia calculando **coseno**.

### ¿Qué se hace?
El servidor mantiene un estado interno (**funcionActual**) que cambia cuando
recibe un mensaje que comienza por **fun:**. Si recibe un número, aplica la
función activa y retorna el resultado. El cliente puede mezclar cambios de
función y consultas numéricas en la misma sesión.

### Evidencia fotográfica

![img.png](imagenes/funciones.png)

## EJERCICIO 5

---

### Objetivo
Implementar un servidor HTTP capaz de atender múltiples solicitudes
consecutivas (no concurrentes) y retornar archivos estáticos: páginas
HTML e imágenes.

### ¿Qué se hace?
El servidor escucha en el puerto 6565. Por cada solicitud GET: lee la ruta
del recurso, localiza el archivo en el sistema de archivos local, determina
el **Content-Type** adecuado (**text/html**, **image/png**, etc.) y lo envía
precedido de las cabeceras HTTP correctas. Si el recurso no existe,
responde **404 Not Found**.

### Evidencia fotográfica

**Solicitud de imagen:** http://localhost:6565/servidorWeb.png

| Explorador | Terminal |
|------------|----------|
|![img.png](imagenes/urlImagenServidorWeb.png)|![img.png](imagenes/TerminalServidorWeb.png)|


**Solicitud de página HTML:** http://localhost:6565/index.html


| Explorador | Terminal |
|------------|----------| 
|![img.png](imagenes/exploradorServidorrIndex.png)|![img.png](imagenes/terminalIndex.png)|

## EJERCICIO 6

---

### Objetivo
Usar el protocolo UDP para implementar un cliente que consulta la hora actual a un servidor cada 5 segundos. Si el servidor no responde, el cliente conserva la última hora recibida y se resincroniza automáticamente en cuanto el servidor vuelve a estar disponible.

### ¿Qué se hace?
- **Servidor (**DatagramTimeServer**):** escucha en el puerto 4445. Al recibir cualquier datagrama, responde con la hora actual en formato **String**.
- **Cliente (**DatagramTimeClient**):** envía un datagrama vacío cada 5 segundos y muestra la hora de la respuesta. Usa un **setSoTimeout** en el socket para no bloquearse indefinidamente si el servidor está caído.

### Evidencia fotográfica

![hora.png](imagenes/hora.png)

Cuando apago el servidor

![servidorApagado.png](imagenes/servidorApagado.png)

## EJERCICIO 7

---
### Objetivo
Implementar un chat entre dos instancias de la misma aplicación utilizando **Java RMI**. Cada instancia actúa simultáneamente como cliente (invoca métodos remotos) y como servidor (publica un objeto remoto en el **rmiregistry**).

### ¿Qué se hace?
- Se define una interfaz remota (**ChatService extends Remote**) con un método **sendMessage(String msg)**.
- Cada instancia publica su objeto en un **rmiregistry** local y se conecta al registry del otro participante para invocar su método remoto.
- La aplicación solicita IP y puerto al iniciar para descubrir al interlocutor.

Instancia persona 1

![img.png](imagenes/Instania1L.png)

instancia persona 2

![img.png](imagenes/instanciaA.png)

