package dominio.scontistica;
import dominio.Biglietto;

public class Multa implements PrezzoFinale {
    @Override
    public float calcolaPrezzo(Object o) {
        Biglietto b = (Biglietto) o;
        float costo, cf;
        costo = b.getCostoFinale();
        cf = (costo*1.5f)-costo;
        return cf;
    }
}