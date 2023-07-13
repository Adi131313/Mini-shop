package magazin.service;

/**
 * Aceasta este clasa ce stocheaza toate erorile ce pot aparea la nivelul service-ului
 */
public class ServiceException extends Exception{

    /**
     * Constructorul clasei noastre
     * @param error - mesajul de eroare
     */
    public ServiceException(String error){

        super(error);
    }
}
