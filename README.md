# TP_SENSOR_IOT
Trabajo Practico - Sensores e Internet de las Cosas
Se cargan en el servidor gratuito los margenes de intensidades y intensidades detectadas por la placa galileo.
La detección se realiza traves de un componente LDR (resistor dependiente de la luz), usando el cliente basico que ofrece Arduino.
La parte de android actúa como cliente para realizar una grafica de detección y enviar notificaciones si se detecta la presencia
de un objeto muy cercano.

Request para intensidad:

https://dweet.io/dweet/for/lightsensor_sounlam_com_grupo_soa_light?intensidad=100

https://dweet.io/get/dweets/for/lightsensor_sounlam_com_grupo_soa_light

Request para margenes maximos y minimos:

https://dweet.io/dweet/for/lightsensor_sounlam_com_grupo_soa_config?minimo=yMin&maximo=yMax

https://dweet.io/get/dweets/for/lightsensor_sounlam_com_grupo_soa_config

compilador y tools:
```
CompileSdkVersion 24
buildToolsVersion "24.0.0"
```

gradle para consumir la API REST:
```
compile 'com.squareup.retrofit:retrofit:1.9.0'

compile 'com.google.code.gson:gson:2.6.2'
```

```
gradle para grafica:
```
compile 'com.androidplot:androidplot-core:0.6.1'
```

gradle para coordinator:

compile 'com.android.support:design:24.0.0'
```

La aplicacioón consume una API REST de dweet con valores cargados desde una placa galileo.
