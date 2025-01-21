package dominio;

public class Cliente {
    private String nome;
    private String cognome;
    private String CF;
    private String codPersonale;

    public Cliente(String nome, String cognome, String CF, String codPersonale) {
        this.nome = nome;
        this.cognome = cognome;
        this.CF = CF;
        this.codPersonale = codPersonale;
    }
}
