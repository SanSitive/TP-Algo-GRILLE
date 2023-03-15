
import java.util.Objects;

public class Coord {
    private final int l;
    private final int c;

    public Coord(int ll, int cc) {
        l = ll;
        c = cc;
    }

    public Coord(Coord c){
        this(c.getL(),c.getC());
    }
    public int getL() {
        return l;
    }

    public int getC() {
        return c;
    }

    public boolean estDansPlateau(int nbL, int nbC) {
        return (0 <= l) && (l < nbL) && (0 <= c) && (c < nbC);
    }

    public boolean estADistanceUn(Coord cd) {//
        // retourne vrai ssi c a distance exactement 1 de this (c'est à dire peut être atteint  en exactement un pas depuis this)
        return ((cd.l == l) && (Math.abs(cd.c - c) == 1)) || ((cd.c == c) && (Math.abs(cd.l - l) == 1));
    }

    public int distanceFrom(Coord cd) {
        // retourne le nombre de pas nécessaires pour aller de this à cd
        return Math.abs(cd.l - l) + Math.abs(cd.c - c);
    }

    public String toString() {
        return "(" + l + "," + c + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return l == coord.l &&
                c == coord.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(l, c);
    }
}