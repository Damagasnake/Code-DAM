# Nvim Cheatsheet

## Modos

- `i`: insertar antes del cursor
- `a`: insertar despues del cursor
- `o`: nueva linea debajo
- `O`: nueva linea encima
- `Esc`: volver a modo normal

## Movimiento

- `h j k l`: izquierda, abajo, arriba, derecha
- `w`: siguiente palabra
- `b`: palabra anterior
- `e`: fin de palabra
- `0`: inicio de linea
- `^`: primer caracter no vacio
- `$`: fin de linea
- `gg`: inicio del archivo
- `G`: fin del archivo
- `:{n}`: ir a la linea `n`

## Edicion

- `x`: borrar caracter
- `dd`: borrar linea
- `dw`: borrar palabra
- `D`: borrar hasta fin de linea
- `yy`: copiar linea
- `p`: pegar despues
- `P`: pegar antes
- `u`: deshacer
- `Ctrl-r`: rehacer
- `r`: reemplazar un caracter
- `cw`: cambiar palabra
- `cc`: cambiar linea

## Seleccion visual

- `v`: visual por caracteres
- `V`: visual por lineas
- `Ctrl-v`: bloque visual
- `y`: copiar seleccion
- `d`: borrar seleccion
- `>`: indentar
- `<`: desindentar

## Buscar y reemplazar

- `/texto`: buscar hacia delante
- `?texto`: buscar hacia atras
- `n`: siguiente coincidencia
- `N`: coincidencia anterior
- `:%s/viejo/nuevo/g`: reemplazar todo
- `:%s/viejo/nuevo/gc`: reemplazar con confirmacion

## Archivos

- `:w`: guardar
- `:q`: salir
- `:wq`: guardar y salir
- `:q!`: salir sin guardar
- `:e archivo`: abrir archivo
- `:Ex`: explorador de archivos
- `:so %`: recargar archivo actual

## Buffers

- `:bnext`: siguiente buffer
- `:bprev`: buffer anterior
- `:bd`: cerrar buffer
- `:ls`: listar buffers

## Ventanas

- `:split`: division horizontal
- `:vsplit`: division vertical
- `Ctrl-w h/j/k/l`: moverse entre ventanas
- `Ctrl-w c`: cerrar ventana
- `Ctrl-w o`: dejar solo una

## Pestanas

- `:tabnew`: nueva pestana
- `:tabnext`: siguiente pestana
- `:tabprev`: pestana anterior
- `:tabclose`: cerrar pestana

## Registros y portapapeles

- `"+y`: copiar al portapapeles del sistema
- `"+p`: pegar desde el portapapeles
- `"ayy`: copiar en el registro `a`
- `"ap`: pegar desde el registro `a`

## Macros

- `qa`: grabar macro en el registro `a`
- `q`: detener grabacion
- `@a`: ejecutar macro `a`
- `@@`: repetir la ultima macro

## Ayuda

- `:help`
- `:help comando`
- `:checkhealth`
