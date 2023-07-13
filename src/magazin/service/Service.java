package magazin.service;

import magazin.domain.Producator;
import magazin.domain.Produs;
import magazin.domain.validatori.ProducatorValidator;
import magazin.domain.validatori.ProdusValidator;
import magazin.domain.validatori.ValidationException;
import magazin.repository.ProducatorRepository;
import magazin.repository.ProdusRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Rolul acestei clase este de a realiza legaturile intre UI si cele 2 Repository-uri
 */
public class Service {

    /**
     * Acesta este campul ce realizeaza legatura catre repoistory-ul ce se ocupa cu gestionare producatorilor
     */
    private ProducatorRepository producatorRepository;

    /**
     * Acesta este campul ce se ocupa cu validarea producatorilor
     */
    private ProducatorValidator producatorValidator;

    /**
     * Acesta este campul ce realizeaza legatura catre repoistory-ul ce se ocupa cu gestionare produselor
     */
    private ProdusRepository produsRepository;

    /**
     * Acesta este campul ce se ocupa cu validarea produselor
     */
    private ProdusValidator produsValidator;

    /**
     * Constructorul clasei noastre
     * @param producatorRepository - repository-ul de producatori
     * @param producatorValidator - validatorul pentru producatori
     * @param produsRepository - repository-ul pentru produse
     * @param produsValidator - validatorul pentru produse
     */
    public Service(ProducatorRepository producatorRepository, ProducatorValidator producatorValidator, ProdusRepository produsRepository, ProdusValidator produsValidator) {

        this.producatorRepository = producatorRepository;
        this.producatorValidator = producatorValidator;
        this.produsRepository = produsRepository;
        this.produsValidator = produsValidator;
    }

    /**
     * Aceasta metoda se ocupa cu returnarea tuturor producatorilor
     * @return lista de producatori
     */
    public List<Producator> getProducatori(){

        return this.producatorRepository.getAll();
    }

    /**
     * Aceasta metoda se ocupa cu returnarea tuturor produselor
     * @return lista de produse
     */
    public List<Produs> getProduse(){

        return this.produsRepository.getAll();
    }

    /**
     * Metoda ce se ocupa cu adaugarea unui producator in baza de date
     * @param id - id-ul producatorului
     * @param nume - numele producatorului
     * @param tara_origine - tara de origine a producatorului
     * @throws ValidationException - in cazul in care nu este valid producatorul
     * @throws ServiceException - in cazul in care mai exista deja un producator cu acelasi id
     */
    public void adaugaProducator(Integer id, String nume, String tara_origine) throws ValidationException, ServiceException {

        Producator producator = new Producator(id, nume, tara_origine);

        this.producatorValidator.validate(producator);  // stratul de validare

        if(this.producatorRepository.cauta(id) != null) // stratul de bussiness

            throw new ServiceException("Mai exista un producator cu acest id!\n");

        this.producatorRepository.adauga(producator); // adaugam la final
    }

    /**
     * Aceasta metoda se ocupa cu adaugarea unui produs
     * @param id - id-ul produsului
     * @param nume - numele produsului
     * @param producator - producatorul produsului
     * @param categorie - categoria produsului
     * @param gramaj - gramajul produsului
     * @param stoc - stocul produsului
     * @param pret - pretul produsului
     * @param dataExpr - data expirarii produsului
     * @throws ValidationException - daca nu este valid produsul nostru
     * @throws ServiceException - daca mai exista un produs cu acest id sau daca nu exista un producator cu id-ul introdus
     */
    public void adaugaProdus(Integer id, String nume, Integer producator, String categorie, Integer gramaj, Integer stoc, Double pret, SortedMap<LocalDateTime, Integer> dataExpr) throws ValidationException, ServiceException {

        Produs produs = new Produs(id, nume, producator, categorie, gramaj, stoc, pret, dataExpr);

        this.produsValidator.validate(produs);

        if(this.producatorRepository.cauta(producator) == null)

            throw new ServiceException("Nu exista un producator cu acest id!\n");

        if(this.produsRepository.cauta(id) != null)

            throw new ServiceException("Mai exista un produs cu acest id!\n");

        this.produsRepository.adauga(produs);
    }

    /**
     * Aceasta metoda sterge producatorul cu id-ul primit ca si parametru
     * @param id - id-ul producatorului
     * @throws ValidationException - in cazul in care nu este valid id-ul primit ca si parametru
     * @throws ServiceException - in cazul in care nu exista un producator cu id-ul primit ca si parametru
     */
    public void stergeProducator(Integer id) throws ValidationException, ServiceException {

        this.producatorValidator.validate(new Producator(id, "Valid", "Valid"));

        if(this.producatorRepository.cauta(id) == null)

            throw new ServiceException("Nu exista un producator cu id-ul introdus!\n");

        this.producatorRepository.delete(id);
    }

    /**
     * Aceasta metoda se ocupa cu stergerea produsului cu id-ul egal cu cel primit ca si parametru
     * @param id - id-ul produsului pe care dorim sa-l stergem
     * @throws ValidationException - in cazul in care id-ul primit ca si parametru nu este valid
     * @throws ServiceException - in cazul in care nu exista un produs cu id-ul introdus ca si parametru
     */
    public void stergeProdus(Integer id) throws ValidationException, ServiceException {

        SortedMap<LocalDateTime, Integer> map = new TreeMap<>();
        map.put(LocalDateTime.now(), 1);
        this.produsValidator.validate(new Produs(id, "Valid", 1, "Valid", 1, 1, 1D, map));

        if(this.produsRepository.cauta(id) == null)

            throw new ServiceException("Nu exista un produs cu id-ul introdus!\n");

        this.produsRepository.delete(id);
    }

    /**
     * Rolul acestei functii este de a updata nnumele si tara de origine a unui producator
     * @param id - id-ul producatorului
     * @param nume_nou - noul nume al producatorului
     * @param tara_noua - tara noua a producatorului
     * @throws ValidationException - in cazul in care datele introduse sunt invalide
     * @throws ServiceException - in cazul in care nu exista un producator cu id-ul introdus
     */
    public void updateProducator(Integer id, String nume_nou, String tara_noua) throws ValidationException, ServiceException {

        Producator producator = new Producator(id, nume_nou, tara_noua);

        this.producatorValidator.validate(producator);

        if(this.producatorRepository.cauta(id) == null)

            throw new ServiceException("Nu exista un producator cu id-ul introdus!\n");

        this.producatorRepository.update(producator);
    }

    /**
     * Aceasta metoda se ocupa cu actualizarea stocului unui produs si cu datele de expirare ale acestora
     * @param id - id-ul produsului
     * @param nume - numele produsului
     * @param producator - id-ul producatorului
     * @param categorie - categoria produsului
     * @param gramaj - gramajul produsului
     * @param stoc_nou - noul stoc al produsului
     * @param pret - pretul produslui
     * @param dataExpr_nou - noua data de expirare a stocului
     * @throws ValidationException - in cazul in care datele introduse nu sunt valide
     * @throws ServiceException - in cazul in care nu exista produsul pe care dorim sa-l actualizam
     */
    public void updateProdus(Integer id, String nume, Integer producator, String categorie, Integer gramaj, Integer stoc_nou, Double pret, SortedMap<LocalDateTime, Integer> dataExpr_nou) throws ValidationException, ServiceException {

        Produs produs = new Produs(id, nume, producator, categorie, gramaj, stoc_nou, pret, dataExpr_nou);

        this.produsValidator.validate(produs);

        if(this.produsRepository.cauta(id) == null)

            throw new ServiceException("Nu exista un produs cu id-ul introdus!\n");

        this.produsRepository.update(produs);
    }

    /**
     * Aceasta functie se ocupa cu cautarea unui producator dupa id
     * @param id - id-ul producatorului pe care-l cautam
     * @return producatorul daca l gasim
     * @throws ValidationException daca id-ul introdus nu e valid
     * @throws ServiceException daca nu gasim producatorul
     */
    public Producator cautaProducator(Integer id) throws ValidationException, ServiceException {

        this.producatorValidator.validate(new Producator(id, "Valid", "Valid"));

        Producator producator = this.producatorRepository.cauta(id);

        if(producator == null)

            throw new ServiceException("Nu exista un producator cu acest id!\n");

        return producator;
    }

    /**
     * Aceasta metoda se ocupa cu cautarea unui produs dupa id
     * @param id - id-ul produsului pe care-l cautam
     * @return produsul daca-l gasim
     * @throws ValidationException - daca id-ul nu e valid
     * @throws ServiceException - daca nu gasim produsul
     */
    public Produs cautaProdus(Integer id) throws ValidationException, ServiceException {

        SortedMap<LocalDateTime, Integer> map = new TreeMap<>();
        map.put(LocalDateTime.now(), 1);
        this.produsValidator.validate(new Produs(id, "Valid", 1, "Valid", 1, 1, 1D, map));

        Produs produs = this.produsRepository.cauta(id);

        if(produs == null)

            throw new ServiceException("Nu exista un produs cu acest id!\n");

        return produs;
    }

    /**
     * Aceasta functie se ocupa cu returnarea listei de producatori ordonate alfabetic
     * @return lista de producatori ordonata crescator
     */
    public List<Producator> sortareaCrescatoareProducatori(){

        return this.producatorRepository.getAll().stream().sorted(Comparator.comparing(Producator::getNume)).collect(Collectors.toList());
    }

    /**
     * Aceasta functie se ocupa cu returnarea listei de produse in ordine crescatoare dupa nume
     * @return lista de produse ordonata crescator
     */
    public List<Produs> sortareCrescatoareProduse(){

        return this.produsRepository.getAll().stream().sorted(Comparator.comparing(Produs::getNume)).collect(Collectors.toList());
    }
}