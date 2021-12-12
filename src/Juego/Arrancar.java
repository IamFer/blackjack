package Juego;

import Cartas.Carta;
import Cartas.Paquete;

import java.util.Scanner;

public class Arrancar {
    public static int numeroCartas = 0;

    public static int puntaje_croupier = 0;
    public static boolean victoria_croupier;
    public static Carta[] cartas_croupier = new Carta[50];

    public static boolean blackjack = false;

    public static int puntaje_jugador = 0;
    public static boolean victoria_jugador;
    public static Carta[] cartas_jugador = new Carta[50]; // TODO: Convertir en bidimensional para dividir la mano

    public static int turno = 0;

    public static void main(String[] MACF) {
        Paquete.cartas();

        // TODO: Una interfaz grafica
        // TODO: Limpiar un poco el código XD
        iniciar_juego();
        declarar_victoria();
    }

    // INICIAR JUEGO: Repartir las primeras 2 cartas a cada jugador en la mesa
    public static void iniciar_juego() {
        tomar_cartas(2, cartas_croupier);

        numeroCartas = 0; // Total de cartas que tiene el jugador en turno

        tomar_cartas(2, cartas_jugador);
        evaluar_puntaje();
    }

    // TURNO JUGADOR: Inicia el juego del jugador
    public static void turno_jugador() {
        Scanner entrada = new Scanner(System.in);

        System.out.println("\nEscriba una opción deseada: ");
        //System.out.println("1) Doble");
        System.out.println("2) Pedir");
        System.out.println("3) Plantarse");
        //System.out.println("4) Dividir");

        int opcion = entrada.nextInt();

        switch (opcion) {
            //case 1 -> doble();
            case 2 -> pedir();
            case 3 -> plantarse();
            //case 4 -> dividir_juego();
        }
    }

    // TODO: DOBLE: Duplicar nuestra apuesta y terminar turno

    // PEDIR: Pide una carta más
    public static void pedir() {
        tomar_cartas(1, cartas_jugador);
        evaluar_puntaje();
    }

    // TODO: DIVIDIR: Divide tu mano en 2 o 3 manos mas

    // EVALUAR PUNTAJE: Calcula el valor de las cartas del jugador, y determina su situación (si continua o no)
    public static void evaluar_puntaje() {
        puntaje_jugador = 0;

        limpiar_consola();
        mostrar_mesa();

        // Saber el numero de Ases con valor 11 que tiene el jugador
        int asesJugador = 0;

        // Comienza el conteo de las cartas
        for (int i = 0; i < numeroCartas; i++) {
            if(((cartas_jugador[i].getCarta().equals("As")) && (asesJugador == 0)) && (puntaje_jugador + 11 <= 21)) {
                puntaje_jugador += 11;
                asesJugador++;
            } else {
                puntaje_jugador = puntaje_jugador + cartas_jugador[i].getValor();
            }

            // Si hay un As con valor de 11 y ya se llegó al límite, regresarle el valor de 1 a dicho As
            if((asesJugador == 1) && (puntaje_jugador > 21)) {
                puntaje_jugador -= 10;
                asesJugador--;
            }
        }

        System.out.println("Valor de tu mano: " + puntaje_jugador);
        turno++;

        if(puntaje_jugador < 21) {
            turno_jugador();
        } else if(puntaje_jugador == 21) {
            if(turno == 1) {
                System.out.println("\n¡¡BLACKJACK!!\n");
                blackjack = true;
            } else {
                System.out.println("\n¡Lograste 21!\n");
            }

            victoria_jugador = true;
            turno_croupier();
        } else {
            System.out.println("\n¡Te pasaste!\n");

            victoria_jugador = false;
            turno_croupier();
        }
    }

    // PLANTARSE: Terminar turno del jugador y pasar al turno de la banca
    public static void plantarse() {
        System.out.println("\n¡Ahora sigue el turno del Croupier!\n");

        turno_croupier();
    }

    // TURNO CROUPIER: Inicia el juego de la banca
    public static void turno_croupier() {
        numeroCartas = 2;
        turno = 0;

        do {
            puntaje_croupier = 0;
            limpiar_consola();


            System.out.println("=====================");
            System.out.println("Cartas del Croupier: ");
            System.out.println("=====================");
            mostrar_cartas(cartas_croupier);
            System.out.println("=====================\n");

            // Saber el numero de Ases con valor 11 que tiene el Croupier
            int asesCroupier = 0;

            // Comienza el conteo de las cartas
            for(int i = 0; i < numeroCartas; i++) {
                if(((cartas_croupier[i].getCarta().equals("As")) && (asesCroupier == 0)) && (puntaje_croupier + 11 <= 21)) {
                    puntaje_croupier += 11;
                    asesCroupier++;
                } else {
                    puntaje_croupier = puntaje_croupier + cartas_croupier[i].getValor();
                }

                // Si hay un As con valor de 11 y ya se llegó al límite, regresarle el valor de 1 a dicho As
                if((asesCroupier == 1) && (puntaje_croupier > 21)) {
                    puntaje_croupier -= 10;
                    asesCroupier--;
                }
            }

            System.out.println("Valor de la mano del Croupier: " + puntaje_croupier);
            turno++;

            System.out.println("\n=====================");
            System.out.println("Tus Cartas:\nValor: " + puntaje_jugador);
            System.out.println("=====================");
            mostrar_cartas(cartas_jugador);
            System.out.println("=====================");

            // TODO: Tratar de eliminar esas pausas
            pausar(3500);

            System.out.println("\nCroupier pensando...");
            pausar(3500);

            if(puntaje_jugador > 21) {
                victoria_croupier = true;
                break;
            }

            tomar_cartas(1, cartas_croupier);

        } while (puntaje_croupier < 17);

        if(puntaje_croupier > 21) {
            victoria_croupier = false;
        } else if(puntaje_croupier == 21) {
            if(turno == 1) {
                System.out.println("\n¡¡BLACKJACK!!\n");

                if(!(blackjack)) {
                    victoria_jugador = false;
                    blackjack = true;
                }
            }

            victoria_croupier = true;
        }
    }

    // DECLARAR VICTORIA: Determina quien gano el juego, y reparte los premios
    public static void declarar_victoria() {
        if((puntaje_jugador <= 21 && puntaje_croupier >= 21) ||
           ((puntaje_jugador > puntaje_croupier) && (puntaje_jugador <= 21))) {
            victoria_jugador = true;
        } else if ((puntaje_croupier <= 21 && puntaje_jugador >= 21) ||
                   ((puntaje_croupier > puntaje_jugador) && (puntaje_croupier <= 21))) {
            victoria_croupier = true;
        }

        if(victoria_croupier) {
            System.out.println("\n¡Perdiste!\n");
        } else if(victoria_jugador) {
            System.out.println("\n¡¡HAS GANADO!!\n");
        } else if(puntaje_croupier == puntaje_jugador) {
            System.out.println("\n¡Ha ocurrido un EMPATE!\n");
        } else {
            System.out.println("\n¡Ha ocurrido un EMPATE!\n");
        }
    }

    // MESA: Muestra su mesa al Jugador
    public static void mostrar_mesa() {
        System.out.println("=====================");
        System.out.println("Cartas del Croupier: ");
        System.out.println("=====================");
        System.out.println(cartas_croupier[0]);
        System.out.println("[CARTA OCULTA]");  // Únicamente muestra la primer carta del Croupier
        System.out.println("=====================\n");

        System.out.println("=====================");
        System.out.println("Tus Cartas: ");
        System.out.println("=====================");
        mostrar_cartas(cartas_jugador); // Muestra tu mano
        System.out.println("=====================");
    }

    // CARTAS: Muestra las cartas de cada jugador
    public static void mostrar_cartas(Carta[] jugador) {
        for (Carta cartas: jugador) {
            if(cartas != null) {
                System.out.println(" - " + cartas);
            } else {
                return;
            }
        }
    }

    // TOMAR CARTAS: Reparte las cartas a los jugadores del paquete de cartas
    public static void tomar_cartas(int cuantasCartas, Carta[] jugador) {
        for (int i = 0; i < cuantasCartas; i++) {
            jugador[numeroCartas] = Paquete.sacar_carta();
            numeroCartas++;
        }
    }

    // UTILIDADES
    // Limpia la pantalla de impresión (dependiendo donde se ejecute, podría no funcionar)
    public static void limpiar_consola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Pausa la ejecución del programa momentáneamente
    public static void pausar(int tiempo) {
        try {
            Thread.sleep(tiempo);
        }  catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
