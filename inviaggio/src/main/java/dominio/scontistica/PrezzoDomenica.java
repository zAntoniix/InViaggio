package dominio.scontistica;
import dominio.Corsa;

public class PrezzoDomenica implements PrezzoFinale {
    @Override
    public float calcolaPrezzo(Object o) {
        Corsa c = (Corsa) o;
        float cb,cf;
        cb = c.getCostoBase();
        cf = (cb*1.33f)-cb;
        return cf;
    }
}
