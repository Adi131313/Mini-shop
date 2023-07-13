package magazin.domain.validatori;

/**
 * Aceasta este clasa ce retine erorile ce pot aparea la nivel de domain, mai exact la validari
 */
public class ValidationException extends Exception{

    /**
     * Constructorul clasei noastre
     * @param error - codul de eroare
     */
    ValidationException(String error){

        super(error);
    }
}
