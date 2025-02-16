package dominio.scontistica;
import dominio.Corsa;

public class LastMinute implements PrezzoFinale{
    @Override
    public float calcolaPrezzo(Object o) {
        Corsa c = (Corsa) o;
        float cb,cf;
        cb = c.getCostoBase();
        cf = cb-(cb*0.9f);
    return cf;
    }
}
