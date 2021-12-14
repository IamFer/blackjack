package Cartas;

public class Carta {
    private int valor;
    private final String carta;
    private final String simbolo;

    public Carta(int valor, String carta, String simbolo) {
        this.valor = valor;
        this.carta = carta;
        this.simbolo = simbolo;
    }

    public int getValor() {
        return valor;
    }

    public String getCarta() {
        return carta;
    }

    public String getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return getCarta() + " de " + getSimbolo();
    }
}
