package dominio.scontistica;
import dominio.Corsa;

public class PrezzoDomenica implements PrezzoFinale {
    @Override
    public float calcolaPrezzo(Corsa c) {
        float cb,cf;
        cb = c.getCostoBase();
        cf = (cb*1.33f)-cb;
        return cf;
    }
}
