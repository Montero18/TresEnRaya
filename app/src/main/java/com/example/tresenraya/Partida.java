package com.example.tresenraya;

import java.util.Random;

public class Partida {


    //definimos una constante de clase para guardar la dificultad
    public final int dificultad;
    public int jugador;
    //array donde guardaremos las casillas que están ocupadas: 0-libre; numJugador-Ocupada
    private int[] ocupadas;
    //declaramos un array de dos dimensiones para guardar las posibles combinaciones ganadoras
    private final int[][] COMBINACIONES={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public Partida(int dificultad) {
        this.dificultad=dificultad;
        //hacemos que se comience por el jugador 1 que jugará con los círculos
        jugador=1;
        //iniciamos el array al comenzar la partida dandole valor 0 a todas sus posiciones
        ocupadas=new int[9];

        for (int i = 0; i < 9; i++) {
            ocupadas[i]=0;
        }
    }

    /***
     * Método que comprobará si la casilla que está intentando marcar el jugador está libre,
     * si está ocupada devolverá false y si está libre se marcará con el jugador actual
     * @param casilla
     * @return
     */
    public boolean casillaLibre(int casilla){

        if (ocupadas[casilla]!=0){
            return false;
        }
        else{
            //marcamos esa casilla con el jugador que la ha marcado
            ocupadas[casilla]=jugador;
        }
        return true;
    }

    /***
     * Método que comprobará si ha ganado alguien, cambiará el turno de juego y devolverá el resultado si ha finalizado
     * @return
     *         0 - no ha ganado nadie aún
     *         1 - gana jugador 1
     *         2 - gana jugador 2
     *         3 - empate
     */
    public int turnoJuego(){

        boolean empate = true;//la iniciamos a true
        boolean combinacion_ganadora = true; //variable donde comprobaremos si tenemos alguna combinación ganadora

        //comprobamos las casillas marcadas
        for (int i = 0; i < COMBINACIONES.length ; i++) {
            for (int pos:COMBINACIONES[i]) {

                System.out.println("Valor en posicion " + pos + " " + ocupadas[pos]);

                if (ocupadas[pos]!=jugador) combinacion_ganadora=false; //sólo se pondrá a false si alguna de las posiciones no es igual al jugador que ha tirado

                if (ocupadas[pos]==0){ //si queda alguna casilla por marcar no hay empate
                    empate = false;
                }
            }
            System.out.println("------------------------------------");

            if (combinacion_ganadora) return jugador; //si se cumple esta condición es que se ha cumplido que una de las combinaciones ganadoras están rellenas por el mismo jugador

            combinacion_ganadora=true; //si no ha pasado por el if anterior lo ponemos a true para la siguiente iteración del for donde comprobaremos la siguiente combinación ganadora
        }

        if (empate){
            return 3; //el juego está empatado
        }

        //cambiamos el turno del juego
        jugador++;

        if (jugador>2){
            jugador=1;
        }



        return 0; //el juego continúa, aún no ha ganado nadie
    }

    /***
     * En este método se determinará si alguno de los dos jugadores está a una tirada de conseguir ganar y determina dónde debería marcar para ganar
     * @param jugadorTurno
     * @return
     */
    public int dosEnRaya(int jugadorTurno){

        int casillaClave = -1; //casilla clave para ganar
        int casillasEnRaya = 0; //se guardarán cuántas casillas lleva de una combinación ganadora

        //recorremos el array de combinaciones

        for (int i = 0; i < COMBINACIONES.length ; i++) {
            for (int pos:COMBINACIONES[i]) {
                if (ocupadas[pos]==jugadorTurno){
                    casillasEnRaya++;
                }
                //nos guardamos la casilla que está igual a 0 por si es la clave
                if (ocupadas[pos]==0){
                    casillaClave=pos;
                }
            }

            //evaluamos si tenemos ya una posible combinacion con 2 en raya para ganar
            if (casillasEnRaya==2 && casillaClave!=-1){
                return casillaClave;
            }
            //iniciamos las variables para la siguiente iteración
            casillaClave=-1;
            casillasEnRaya=0;
        }

        //si no hemos encontrado una casilla para ganar devolvemos -1
        return -1;
    }

    /***
     * Método que devolverá una casilla al azar donde la máquina pondrá un aspa
     * @return
     * para el nivel fácil, se irán marcando las casillas al azar pero también comprobará si tiene posibilidad de ganarnos
     * para el nivel dificil la máquina intentará además evitar que nosotros ganemos
     */
    public int ia(){
        int casilla;

        casilla=dosEnRaya(2); //compruebo si la máquina que es el jugador 2 tiene posibilidad para hacer tres en raya

        if(casilla!= -1) return casilla; //si hay posibilidad de ganar devuelvo la casilla clave para hacer tres en raya

        //si aún no tiene posibilidad de ganarnos pero estamos en los niveles difícil o muy difícil comprueba si puede evitar que ganemos nostros
        if (dificultad>0){
            casilla=dosEnRaya(1);//se comprueba si el jugador 1 tiene posibilidad de ganar
            if(casilla!=-1) return casilla; //la máquina marcará una casilla para evitar que nosotros ganemos
        }

        //en caso de no tener posibilidad de ganar ni de evitarlo se genera una casilla al azar
        Random sig_casilla=new Random();
        casilla=sig_casilla.nextInt(9);//generamos un número aleatorio entre 0 y 8

        return casilla;
    }
}
