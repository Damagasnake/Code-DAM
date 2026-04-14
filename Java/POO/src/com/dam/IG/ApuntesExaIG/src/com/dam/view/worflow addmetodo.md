# Como añadir otra opcion / boton

### Archivo usado > opcion{x}.java (view)

1. Creo boton y label desde createComp()
2. Con metodo setCtrl le damos un actionListener para conectar con controlador
3. Creas metodo para poder modificar el texto que recibe el label (IMPORTANTE: ESTE METODO RECIBE STR)
4. Creas un getter del boton
---

### Archivo usado > lstEncuesta.java (model)
1. Haces el metodo (DEVUELVE UNA ENCUESTA)

---

### Archivo usado > control.java (control)
`
@Override
public void actionPerformed(ActionEvent e) {

    // Si resulta que lo que han pulsado es un botón de la pantalla
    else if(e.getSource() instanceof JButton) {
        
        // PREGUNTA: ¿Es exactamente el botón de "Ver resultado" de OPCION2?
        if(e.getSource().equals(op2.getBtnVer())) {
            
            // 1. Llamas al paquete MODELO para pedir el cálculo
            Encuesta ganadora = lstE.getMasVotada();
            
            // 2. Llamas al paquete VIEW para que lo pinte en el recuadro
            op2.mostrarResultado("La ganadora es: " + ganadora.toString());
        }
    }
}
`