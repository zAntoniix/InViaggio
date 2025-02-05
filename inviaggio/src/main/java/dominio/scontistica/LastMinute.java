package dominio.scontistica;
import dominio.Corsa;

public class LastMinute implements PrezzoFinale{
    @Override
    public float calcolaPrezzo(Corsa c) {
        float cb,cf;
        cb = c.getCostoBase();
        cf = cb-(cb*0.9f);
    return cf;
    }
}
