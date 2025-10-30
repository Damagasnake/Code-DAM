package com.dam.pojo;

public class punto {
    /**
     * Esta clase define un punto en un espacio de dos dimensiones.
     * 
     * @author Damaga
     *         @version1.0, 30 de Octubre de 2025
     */
    public class Punto {
        /** @param x Coordenada x del punto */
        protected float x;
        protected float y;

        /**
         * Constructor por defecto
         */
        public Punto() {
            x = 0.0f;
            y = 0.0f;
        }

        /**
         * Constructor con argumentos.
         * 
         * @param x La coordenada ’x’ del punto.
         * @param y La coordenada ’y’ del punto.
         */
        public Punto(float x, float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Esta función devuelve el valor de la coordenada ’x’
         * 
         * @return El valor de la coordenada ’x’
         */
        public float getX() {
            return x;
        }

        /**
         * Esta función cambia el valor de la coordenada ’x’
         * 
         * @param x El nuevo valor de la coordenada ’x’
         */
        public void setX(float x) {
            this.x = x;
        }
        /**
         * Esta función devuelve el valor de la coordenada 'y'
         * 
         * @return El valor de la coordenada 'y'
         */
        public float getY() {
            return y;
        }

        /**
         * Esta función cambia el valor de la coordenada 'y'
         * 
         * @param y El nuevo valor de la coordenada 'y'
         */
        public void setY(float y) {
            this.y = y;
        }
        /**
         * Devuelve una representación en cadena del punto
         * 
         * @return Una cadena con las coordenadas del punto
         */
        @Override
        public String toString() {
            return "Punto(" + x + ", " + y + ")";
        }
    }

}