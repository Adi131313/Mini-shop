package magazin;

import magazin.domain.validatori.ProducatorValidator;
import magazin.domain.validatori.ProdusValidator;
import magazin.repository.ProducatorRepository;
import magazin.repository.ProdusRepository;
import magazin.service.Service;
import magazin.teste.Teste;
import magazin.ui.UI;

/**
 * Clasa main are o singura metoda si anume main, ce initializeaza partile componente ale aplicatiei si porneste aplicatia
 */
public class Main {

    /**
     * Aceasta este metoda main ce initializeaza si porneste aplicatia noastra
     * @param args - argumentele din linia de comanda daca acestea sunt date
     */
    public static void main(String[] args) {

        ProdusRepository produsRepository = new ProdusRepository("jdbc:postgresql://localhost:5432/magazin", "postgres", "postgres");
        ProdusValidator produsValidator = new ProdusValidator();

        ProducatorRepository producatorRepository = new ProducatorRepository("jdbc:postgresql://localhost:5432/magazin", "postgres", "postgres");
        ProducatorValidator producatorValidator = new ProducatorValidator();

        Service service = new Service(producatorRepository, producatorValidator, produsRepository, produsValidator);

        UI console = new UI(service);

        Teste.ruleazaTeste();

        console.run();
    }
}
