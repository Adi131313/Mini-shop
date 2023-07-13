package magazin.domain.validatori;

import magazin.domain.Producator;

/**
 * Aceasta clasa se ocupa cu validatarea unui producator
 */
public class ProducatorValidator {

    /**
     * Metoda validate valideaza fiecare atribut al unui producator pentru a verifica daca acesta este corect sau nu
     * @param producator - Producatorul pe care-l validam
     * @throws ValidationException - in cazul in care producatorul este invalid
     */
    public void validate(Producator producator) throws ValidationException{

        String erori = "";

        if(producator.getId() <= 0)

            erori += "Id-ul nu poate sa fie mai sau egal decat 0!\n";

        if(producator.getNume().equals(""))

            erori += "Numele nu poate sa fie vid!\n";

        // verificam doar daca e diferita de vid pentru ca am putea avea 2 variante la tara de origine
        // 1) Numele tarii: Romania
        // 2) Sau ISO 3166-2: ISO 3166-2:RO
        if(producator.getTaraOrigine().equals(""))

            erori += "Tara de origine nu poate sa fie vida!\n";

        if(!erori.equals(""))

            throw new ValidationException(erori);
    }
}
