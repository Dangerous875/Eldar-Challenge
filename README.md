# Challenge document.
- :file_folder: **Mobile Challenge Android File**:

  [Ejercicio mobile V1.2.pdf](https://drive.google.com/file/d/1s0v6vGiDxebF5E71IUQxLIpOOkUuABLo/view?usp=drive_link)

#  Mobile challenge Eldar: Eldar Wallet. Billetera Virtual.
> [!NOTE]
>  Este proyecto utiliza una arquitectura robusta basada en Clean Architecture y el patrón MVVM, integrando tecnologías como Firebase, Mockk(UnitTest), Retrofit, Dagger Hilt, Room, Coil, Jetpack Compose y Kotlin Serialization 2.8.0 para argumentos de navegación seguros y funciones para cifrar y descifrar datos utilizando el algoritmo AES con el modo de operación GCM. La pantalla principal, MainScreen, es el corazón de la aplicación permite administrar las tarjetas de crédito del usuario, generar QR con sus datos, visualizar su saldo disponible, vincular nuevas tarjetas de crédito a su cuenta y realizar pagos con sus tarjetas de crédito disponibles.
________________________________________

# HomeScreen (Crear cuenta/login)

> [!TIP]
>En la pantalla HomeScreen de la aplicación, tenemos la opción de acceder a dos secciones: la de crear cuenta o la de iniciar sesión si ya tenemos una cuenta creada. El proceso de iniciar sesión está integrado con Firebase, permitiendo la autenticación del usuario mediante su ID único. Este ID se utiliza para vincular los datos del usuario almacenados en la base de datos local, garantizando que la información asociada a su cuenta se mantenga sincronizada de manera segura y eficiente.
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1EXcMYl0DbAUagRQc4VJO0Wi44QGqvsf_)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1LBBozGKFrSJ_nvnD2trufK656rWjRSUX)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1xuK4LHIia_QaXFAtLZA09bEl39qCMS4g)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1Th92oNl4l2pdHdXEfa4HXNFD0m8umGR1)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1A53EgUXHWqqo7STKncC4CKRsP88lqUS-)
>

# MainScreen 

> [!TIP]
>En la pantalla MainScreen podemos visualizar una topBar con un mensaje de bienvenida al usuario mostrando su nombre, un IconButton con la acción de acceder a la pantalla de generación de QR y un menú desplegable con las funciones de LogOut y ExitApp para salir de la aplicación. En el ContentView de la pantalla podemos ver la lista de las tarjetas de crédito vinculadas al usuario y en el caso de no tenerlas vemos un mensaje con una lista vacía. En la parte inferior de la pantalla tenemos un botón para agregar una nueva tarjeta de crédito.
>
![Descripción de la imagen](https://drive.google.com/uc?id=1PDaPEnp62h437g3hImWrr3ofdlAalmSy)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=15vEq5Oika0uJT3m6DFJ4jR0Vro95zNZq)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1Evzce-UHk3G_V-Uy2R0FNdv4ohRJ3Lfs)
>
# QRScreen 

> [!TIP]
>En la Pantalla de generación de QR ponemos visualizar el QR generado mediante el api https://rapidapi.com/zingzy/api/qrcode68 el cual al escanear se podrán visualizar nombre y apellido del usuario actual. En el caso de no estar conectado a internet nos saldra una pantalla con el mensaje sin conexión con un boton de "Try again" en caso de querer recargar la pantalla al recuperar la conexión
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1vQ04zQiKJ8NhFDPWpkBxbJAuISvuO0zf)
> 
![Descripción de la imagen](https://drive.google.com/uc?id=15oEbuOb0Q5CXq6wYfbK8OuJ0fOYRHl_6)
# AddNewCard 

> [!TIP]
>En la Pantalla para generar una nueva tarjeta de crédito podemos visualizar una imagen genérica de una tarjeta que muestra lo campos ingresados por el usuario en tiempo real autocompletando los datos básicos como el logo de la tarjeta y la marca (Según el número de tarjeta ingresado por el usuario). Una vez completado todos los campos solicitados el sistema solo permitirá agregar la nueva tarjeta si el nombre y apellido ingresados por el usuario coinciden con sus datos ingresados al crear su cuenta. Si los datos son correctos la tarjeta es almacenada en la base de datos local pero encriptada utilizando un algoritmo AES con el modo de operación GCM.
>Para el cifrado los datos se cifran con una clave secreta almacenada en el KeyStore. El IV (vector de inicialización) utilizado en el cifrado se genera de forma automática y se concatena con los datos cifrados. El resultado se codifica en Base64 para su almacenamiento. 
>Para el descifrado los datos cifrados en Base64 se decodifican y se extraen el IV y los datos cifrados. Luego, se usa el mismo algoritmo y la clave secreta para recuperar los datos originales.

>  
![Descripción de la imagen](https://drive.google.com/uc?id=19bhYXfc2kwfS9TkVWuy1M_ow65-Xinv5)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1DSwinNYwZK_oY7SpNVCMypUnW6FaP3Zi)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1dRRUZKJvOjqOpfq9O9n3j9potIq_nTcj)
>
# Generar un pago y funcionamiento PayScreen 

> [!TIP]
>En la Pantalla de MainScreen al visualizar las tarjetas ingresadas el usuario puede seleccionar la que desee y realizar un pago, en la pantalla de pagos podemos visualizar la tarjea seleccionada con mucho más detalle que en el MainScreen, donde al ingresar un monto válido se habilita el botón para pagar, al realizar el pago se simula una transacción, mostrando la transacción exitosa actualizando el saldo del usuario.
>
![Descripción de la imagen](https://drive.google.com/uc?id=1GwFg0Xma03IUfBbE699qiWpU1scYMgzW)
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1znyHonVRGGLHa9hiR5mtvuQ0IIlfzbIJ)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1J4tcwZLy8M14Hvn3z_ZXyfm_xGJs_NMY)
> 
![Descripción de la imagen](https://drive.google.com/uc?id=11exQcfhwmbDeWC-LV3c5Or5sPn5V7QWA)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1SL_l7-B9Jt66ticXryEScLbm4fhB2YXp)

> 
![Descripción de la imagen](https://drive.google.com/uc?id=1UT9i1CbE_XKx-hhW1zYQmq9mbG6T2od1)
>
> 
![Descripción de la imagen](https://drive.google.com/uc?id=1AjS5piFh_nosEv_McJA01eqZ1fswLWuF)
