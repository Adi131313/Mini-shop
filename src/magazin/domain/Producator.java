package magazin.domain;

/**
 * Aceasta clasa se ocupa cu retinerea detaliilor despre un producator si a metodelor aferenete acestuia
 */
public class Producator {

    /**
     * Acesta este id-ul intern al unui producator
     */
    private int id;

    /**
     * Campul nume, reprezinta numele producatorului
     */
    private String nume;

    /**
     * Acesta este campul ce retine tara de origine a producatorului
     */
    private String taraOrigine;

    /**
     * Acesta este constructorul clasei Producator
     * @param id - id-ul producatorului
     * @param nume - numele producatorului
     * @param taraOrigine - tara de origine a producatorului
     */
    public Producator(int id, String nume, String taraOrigine) {
        this.id = id;
        this.nume = nume;
        this.taraOrigine = taraOrigine;
    }

    /**
     * Get method pentru id
     * @return id-ul producatorului
     */
    public int getId() {
        return id;
    }

    /**
     * Set method pentru id
     * @param id - noul id al producatorului
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get method pentru nume
     * @return numele producatorului
     */
    public String getNume() {
        return nume;
    }

    /**
     * Set method pentru nume
     * @param nume - noul nume al producatorului
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Get method pentru tara de origine
     * @return tara de origine a producatorului
     */
    public String getTaraOrigine() {
        return taraOrigine;
    }

    /**
     * Set method pentru tara de origine
     * @param taraOrigine - noua tara de origine a producatorului
     */
    public void setTaraOrigine(String taraOrigine) {
        this.taraOrigine = taraOrigine;
    }

    /**
     * Suprascriem metoda toString pentru a afisa mai frumos
     * @return obiectul in convertit in string
     */
    @Override
    public String toString() {
        return "Producator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", taraOrigine='" + taraOrigine + '\'' +
                '}';
    }
}
