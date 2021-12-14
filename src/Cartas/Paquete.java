package Cartas;

import java.util.Random;

public class Paquete {
    public static int CARTAS_REPARTIDAS = -1;
    public static final int TOTAL_CARTAS = 52;
    public static final String[] SIMBOLOS = {"Tréboles", "Espadas", "Corazones", "Diamante"};
    
    public static final String[] CARTAS = {
        "As", "Dos", "Tres", "Cuatro",
        "Cinco", "Seis", "Siete",
        "Ocho", "Nueve", "Diez",
        "Joker", "Reina", "Rey"
    };

    public static Carta[] lasCartas = new Carta[TOTAL_CARTAS];

    public static int valor_carta(String carta) {
        switch (carta) {
            case "As" -> {return 1;}
            case "Dos" -> {return 2;}
            case "Tres" -> {return 3;}
            case "Cuatro" -> {return 4;}
            case "Cinco" -> {return 5;}
            case "Seis" -> {return 6;}
            case "Siete" -> {return 7;}
            case "Ocho" -> {return 8;}
            case "Nueve" -> {return 9;}
            case "Diez", "Joker", "Reina", "Rey" -> {return 10;}
        }

        return 0;
    }

    public static void abrir_paquete() {
        int count_c = 0;
        int count_s = 0;

        for(int i = 0; i < TOTAL_CARTAS; i++) {
            lasCartas[i] = new Carta(valor_carta(CARTAS[count_c]), CARTAS[count_c], SIMBOLOS[count_s]);
            count_c++;

            if(count_c == CARTAS.length && count_s < 3) {
                count_c = 0;
                count_s++;
            }
        }
    }

    public static void barajar_cartas() {
        Carta carta;
        int posicionElegida;

        Random nuevaPosicion = new Random();

        for (int i = 0; i < TOTAL_CARTAS; i++) {
            posicionElegida = nuevaPosicion.nextInt(TOTAL_CARTAS);  // Va desde 0 - 51

            carta = lasCartas[posicionElegida];
            lasCartas[posicionElegida] = lasCartas[i];
            lasCartas[i] = carta;
        }
    }

    public static Carta sacar_carta() {
        if(CARTAS_REPARTIDAS == TOTAL_CARTAS) {
            System.out.println("Ya no hay cartas en el paquete.");
            return null;
        }

        CARTAS_REPARTIDAS++;

        return lasCartas[CARTAS_REPARTIDAS];
    }

    public static void cartas() {
        abrir_paquete();
        barajar_cartas();
    }
}