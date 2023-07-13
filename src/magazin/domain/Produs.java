package magazin.domain;

import java.time.LocalDateTime;
import java.util.SortedMap;

/**
 * Aceasta este clasa ce retine detaliile unui produs si metodele aferente acestuia
 */
public class Produs {

    /**
     * Id-ul intern al unui produs
     */
    private Integer id;

    /**
     * Numele produsului
     */
    private String nume;

    /**
     * Producatorul produsului nostru
     */
    private Integer producator;

    /**
     * Categoria din care apartine produsul
     */
    private String categorie;

    /**
     * Gramajul produsului nostru
     */
    private Integer gramaj;

    /**
     * Numarul de bucati pe stoc disponibile
     */
    private Integer stoc;

    /**
     * Pretul pe bucata al produsului nostru
     */
    private Double pret;

    /**
     * Data cand expira produsul nostru
     */
    private SortedMap<LocalDateTime, Integer> dataExpr;

    /**
     * Constructorul clasei produs
     * @param id - id-ul produsului
     * @param nume - numele produsului
     * @param producator - producatorul produsului
     * @param categorie - categoria produsului
     * @param gramaj - gramajul produsului
     * @param stoc - stoc-ul produsului
     * @param pret - pretul produsului
     * @param dataExpr - data expirarii produsului
     */
    public Produs(Integer id, String nume, Integer producator, String categorie, Integer gramaj, Integer stoc, Double pret, SortedMap<LocalDateTime, Integer> dataExpr) {
        this.id = id;
        this.nume = nume;
        this.producator = producator;
        this.categorie = categorie;
        this.gramaj = gramaj;
        this.stoc = stoc;
        this.pret = pret;
        this.dataExpr = dataExpr;
    }

    /**
     * Get method pentru id
     * @return id-ul produsului
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set method pentru id
     * @param id - noul id al produslui
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get method pentru nume
     * @return numele produsului
     */
    public String getNume() {
        return nume;
    }

    /**
     * Set method pentru nume
     * @param nume - noul nume al produsului
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Get method pentru producator
     * @return producatorul produsului
     */
    public Integer getProducator() {
        return producator;
    }

    /**
     * Set method pentru producator
     * @param producator - noul producator al produsului
     */
    public void setProducator(Integer producator) {
        this.producator = producator;
    }

    /**
     * Get method pentru categorie
     * @return categoria produsului
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Set method pentru categorie
     * @param categorie - noua categorie a produsului
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Get method pentru gramaj
     * @return gramajul produsului
     */
    public Integer getGramaj() {
        return gramaj;
    }

    /**
     * Set method pentru gramaj
     * @param gramaj - noul gramaj al produsului
     */
    public void setGramaj(Integer gramaj) {
        this.gramaj = gramaj;
    }

    /**
     * Get method pentru stoc
     * @return stoc-ul produsului
     */
    public Integer getStoc() {
        return stoc;
    }

    /**
     * Set method pentru stoc
     * @param stoc - noul stoc al produsului
     */
    public void setStoc(Integer stoc) {
        this.stoc = stoc;
    }

    /**
     * Get method pentru pret
     * @return pretul produsului
     */
    public Double getPret() {
        return pret;
    }

    /**
     * Set method pentru pret
     * @param pret - noul pret al produsului
     */
    public void setPret(Double pret) {
        this.pret = pret;
    }

    /**
     * Get method pentru data expirarii
     * @return data expirarii a produsului
     */
    public SortedMap<LocalDateTime, Integer> getDataExpr() {
        return dataExpr;
    }

    /**
     * Set method pentru data expirarii
     * @param dataExpr - noua data de expirare a produsului
     */
    public void setDataExpr(SortedMap<LocalDateTime, Integer> dataExpr) {
        this.dataExpr = dataExpr;
    }

    /**
     * Suprascriem metoda toString pentru a afisa mai frumos
     * @return obiectul in convertit in string
     */
    @Override
    public String toString() {
        return "Produs{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", producator=" + producator +
                ", categorie='" + categorie + '\'' +
                ", gramaj=" + gramaj +
                ", stoc=" + stoc +
                ", pret=" + pret +
                ", dataExpr=" + dataExpr +
                '}';
    }
}
