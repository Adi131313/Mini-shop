package magazin.domain.validatori;

import magazin.domain.Produs;

/**
 * Aceasta clasa se ocupa cu validaraea unui produs
 */
public class ProdusValidator {

    /**
     * Metoda validate valideaza fiecare atribut al unui produs pentru a verifica daca acesta este corect sau nu
     * @param produs - Produsul pe care-l verificam
     * @throws ValidationException - in cazul in care produsul nu este valid
     */
    public void validate(Produs produs) throws ValidationException{

        String erori = "";

        if(produs.getId() <= 0)

            erori += "Id-ul nu poate sa fie mai sau egal decat 0!\n";

        if(produs.getNume().equals(""))

            erori += "Numele nu poate sa fie vid!\n";

        if(produs.getProducator() <= 0)

            erori += "Id-ul producatorului nu poate sa fie mai mic sau egal decat 0!\n";

        if(produs.getCategorie().equals(""))

            erori += "Produsul nu poate sa aiba categoria vida!\n";

        if(produs.getGramaj() <= 0)

            erori += "Produsul nu poate sa aiba gramajul mai mic sau egal decat 0!\n";

        if(produs.getStoc() <= 0)

            erori += "Produsul nu poate sa aiba stocul mai mic sau egal decat 0!\n";

        else if (produs.getStoc() != produs.getDataExpr().values().stream().mapToInt(Integer::intValue).sum())

                erori += "Stocul nu corespunde cu numarul de date de expirare introduse!\n";

        if(produs.getPret() <= 0)

            erori += "Produsul nu poate sa aiba pretul mai mic sau egal decat 0!\n";

        if(!erori.equals(""))

            throw new ValidationException(erori);
    }
}
