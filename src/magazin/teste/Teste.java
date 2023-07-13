package magazin.teste;

import magazin.domain.Producator;
import magazin.domain.Produs;
import magazin.domain.validatori.ProducatorValidator;
import magazin.domain.validatori.ProdusValidator;
import magazin.domain.validatori.ValidationException;
import magazin.repository.ProducatorRepository;
import magazin.repository.ProdusRepository;
import magazin.service.Service;
import magazin.service.ServiceException;

import java.lang.ref.SoftReference;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Aceasta clasa se ocupa cu testarea tuturor claselor si metodelor aferente acestora
 */
public class Teste {

    /**
     * Functie ce se ocupa cu testarea clasei Produs si a validatorului pentru Produs
     */
    private static void produsTest(){

        SortedMap<LocalDateTime, Integer> map = new TreeMap<>();
        map.put(LocalDateTime.now(), 1);
        Produs produs = new Produs(1, "Orez", 1,"Carbo",500, 5, 7D, map);

        assert(produs.getId() == 1);
        assert(produs.getNume().equals("Orez"));
        assert(produs.getProducator() == 1);
        assert(produs.getCategorie().equals("Carbo"));
        assert(produs.getGramaj() == 500);
        assert(produs.getStoc() == 5);
        assert(produs.getPret().equals(7D));
        assert(produs.getDataExpr() == map);

        produs.setId(2);
        produs.setNume("Paste");
        produs.setProducator(2);
        produs.setCategorie("Fainoase");
        produs.setGramaj(300);
        produs.setStoc(1);
        produs.setPret(10D);
        SortedMap<LocalDateTime, Integer> date1 = new TreeMap<>();
        date1.put(LocalDateTime.of(2021, 11, 23, 10, 11), 1);
        produs.setDataExpr(date1);

        assert(produs.getId() == 2);
        assert(produs.getNume().equals("Paste"));
        assert(produs.getProducator() == 2);
        assert(produs.getCategorie().equals("Fainoase"));
        assert(produs.getGramaj() == 300);
        assert(produs.getStoc() == 1);
        assert(produs.getPret().equals(10D));
        assert(produs.getDataExpr() == date1);
        assert(produs.toString().equals("Produs{id=2, nume='Paste', producator=2, categorie='Fainoase', gramaj=300, stoc=1, pret=10.0, dataExpr={2021-11-23T10:11=1}}"));

        // testam validatorul
        try{

            ProdusValidator produsValidator = new ProdusValidator();
            SortedMap<LocalDateTime, Integer> listt = new TreeMap<>();
            produsValidator.validate(new Produs(-1, "", -11, "", -1, -1, -1D,listt));
            assert(false);
        }catch (ValidationException err){
            assert (true);
        }

        try {

            ProdusValidator produsValidator = new ProdusValidator();
            SortedMap<LocalDateTime, Integer> listt = new TreeMap<>();
            listt.put(LocalDateTime.now(), 5);
            listt.put(LocalDateTime.of(2020,10,10,10,10,10), 5);
            produsValidator.validate(new Produs(1, "da", 1, "da", 1, 5, 1D, listt));
            assert(false);
        }catch (ValidationException err){

            assert(true);
        }
    }

    /**
     * Aceasta metoda se ocupa cu testarea clasei producator si cu validatorul acesteia
     */
    private static void producatorTest(){

        Producator producator = new Producator(1, "Barilla", "Italia");

        assert(producator.toString().equals("Producator{id=1, nume='Barilla', taraOrigine='Italia'}"));
        assert(producator.getId() == 1);
        assert(producator.getNume().equals("Barilla"));
        assert(producator.getTaraOrigine().equals("Italia"));

        producator.setId(2);
        producator.setNume("Olympia");
        producator.setTaraOrigine("Turcia");

        assert(producator.getId() == 2);
        assert(producator.getNume().equals("Olympia"));
        assert(producator.getTaraOrigine().equals("Turcia"));

        // testam validatorul
        try{

            ProducatorValidator producatorValidator = new ProducatorValidator();
            producatorValidator.validate(new Producator(-1, "", ""));
            assert(false);
        }catch (ValidationException err){
            assert(true);
        }
    }

    /**
     * Aceasta metoda se ocupa cu testarea repository-ului de producatori
     */
    private static void producatorRepoTest(){

        String url = "jdbc:postgresql://localhost:5432/magazintest";
        String username = "postgres";
        String password = "postgres";

        ProducatorRepository producatorRepository = new ProducatorRepository(url, username, password);

        try{
            producatorRepository.adauga(null);
            assert(false);
        }catch (IllegalArgumentException err){
            assert (true);
        }

        try{
            producatorRepository.cauta(null);
            assert(false);
        }catch (IllegalArgumentException err){
            assert (true);
        }

        try{
            producatorRepository.delete(null);
            assert(false);
        }catch (IllegalArgumentException err){
            assert (true);
        }

        try{
            producatorRepository.update(null);
            assert(false);
        }catch (IllegalArgumentException err){
            assert (true);
        }

        producatorRepository.adauga(new Producator(1, "Barilla", "Italia"));

        assert (producatorRepository.getAll().size() == 1);

        assert(producatorRepository.cauta(1).getNume().equals("Barilla"));

        producatorRepository.update(new Producator(1, "Test", "Test"));

        assert (producatorRepository.cauta(1).getNume().equals("Test"));

        producatorRepository.delete(1);

        assert(producatorRepository.getAll().size() == 0);

    }

    /**
     * Metoda ce se ocupa cu rularea testelor pentru produse repo
     */
    private static void produseRepoTest(){

        String url = "jdbc:postgresql://localhost:5432/magazintest";
        String username = "postgres";
        String password = "postgres";

        ProdusRepository produsRepository = new ProdusRepository(url, username, password);

        ProducatorRepository producatorRepository = new ProducatorRepository(url, username, password);

        producatorRepository.adauga(new Producator(1, "da", "da"));
        producatorRepository.adauga(new Producator(2, "nu", "nu"));

        LocalDateTime date = LocalDateTime.now();
        SortedMap<LocalDateTime, Integer> map = new TreeMap<>();
        map.put(date, 2);
        Produs produs = new Produs(1, "da", 1, "da", 1, 2, 1D, map);
        produsRepository.adauga(produs);

        LocalDateTime date1 = LocalDateTime.of(2020, 10, 10, 10, 10, 10);
        SortedMap<LocalDateTime, Integer> map1 = new TreeMap<>();
        map1.put(date1, 2);
        Produs produs1 = new Produs(2, "da", 2, "da", 1, 2, 1D, map);
        produsRepository.adauga(produs1);

        assert(produsRepository.getAll().size() == 2);
        assert(produsRepository.cauta(1).getProducator() == 1);

        SortedMap<LocalDateTime, Integer> map2 = new TreeMap<>();
        map2.put(LocalDateTime.now(), 3);
        produsRepository.adauga(new Produs(3, "test", 2, "test", 1, 1, 1D, map2));

        assert(produsRepository.getAll().size() == 3);
        produsRepository.delete(3);
        assert(produsRepository.getAll().size() == 2);

        produsRepository.adauga(new Produs(3, "test", 2, "test", 1, 1, 1D, map2));

        SortedMap<LocalDateTime, Integer> map3 = new TreeMap<>();
        map3.put(LocalDateTime.now(), 5);
        produsRepository.update(new Produs(3, "test", 2, "test", 1, 5, 1D, map3));

        assert(produsRepository.cauta(3).getDataExpr().size() == 1);
        assert(produsRepository.cauta(3).getStoc() == 5);

        producatorRepository.delete(1);
        producatorRepository.delete(2);

        try{
            produsRepository.cauta(null);
            assert(false);
        }catch (IllegalArgumentException err){
            assert(true);
        }

        try{
            produsRepository.adauga(null);
            assert(false);
        }catch (IllegalArgumentException err){
            assert(true);
        }

        try{
            produsRepository.delete(null);
            assert(false);
        }catch (IllegalArgumentException err){
            assert(true);
        }
    }

    /**
     * Aceasta metoda se ocupa cu testarea tuturor metodelor din service
     */
    private static void serviceTest(){

        String url = "jdbc:postgresql://localhost:5432/magazintest";
        String username = "postgres";
        String password = "postgres";

        ProducatorRepository producatorRepository = new ProducatorRepository(url, username, password);
        ProducatorValidator producatorValidator = new ProducatorValidator();

        ProdusRepository produsRepository = new ProdusRepository(url, username, password);
        ProdusValidator produsValidator = new ProdusValidator();

        Service service = new Service(producatorRepository, producatorValidator, produsRepository, produsValidator);

        try {
            service.adaugaProducator(1, "Producator1", "Tara1");
            assert(true);
        } catch (ValidationException | ServiceException e) {
           assert(false);
        }

        try {
            service.adaugaProducator(1, "Producator1", "Tara1");
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        assert (service.getProducatori().size() == 1);

        try {
            service.adaugaProducator(2, "Aaa","aaa");
            assert (service.getProducatori().size() == 2);

            List<Producator> list = service.sortareaCrescatoareProducatori();
            assert(list.get(0).getId() == 2);
            assert(list.get(1).getId() == 1);

        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        try {
            service.stergeProducator(100);
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        SortedMap<LocalDateTime, Integer> map1 = new TreeMap<>();
        map1.put(LocalDateTime.now(), 1);
        try {
            service.adaugaProdus(1, "Nume1", 1, "Categorie1", 1, 1, 1D, map1);
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        try {
            service.adaugaProdus(1, "Nume1", 1, "Categorie1", 1, 1, 1D, map1);
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        try {
            service.adaugaProdus(2, "Nume1", 4, "Categorie1", 1, 1, 1D, map1);
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        assert (service.getProduse().size() == 1);

        try{
            SortedMap<LocalDateTime, Integer> mapp = new TreeMap<>();
            mapp.put(LocalDateTime.now(), 1);
            service.adaugaProdus(2, "Aaa", 2, "aaa", 100, 1, 10D, mapp);
            assert (service.getProduse().size() == 2);
            List<Produs> list = service.sortareCrescatoareProduse();
            assert(list.get(0).getId() == 2);
            assert(list.get(1).getId() == 1);
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        try {
            service.stergeProducator(2);
            assert (service.getProducatori().size() == 1);
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert (false);
        }

        try {
            Producator producator = service.cautaProducator(1);
            assert(producator.getNume().equals("Producator1"));
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        try{
            Producator producator = service.cautaProducator(6);
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        try{
            Produs produs = service.cautaProdus(1);
            assert(produs.getPret().equals(1D));
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        try{
            Produs produs = service.cautaProdus(8);
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        try{
            service.updateProducator(1, "NumeNou", "TaraNoua");
            assert(service.cautaProducator(1).getNume().equals("NumeNou"));
            assert(true);
        }catch (ValidationException | ServiceException e){
            assert(false);
        }

        try {
            service.updateProducator(9, "Nume", "Tara");
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        SortedMap<LocalDateTime, Integer> map2 = new TreeMap<>();
        map2.put(LocalDateTime.now(), 2);
        try{
            service.updateProdus(1, "nume", 1, "categorie", 1, 2, 1D, map2);
            assert(service.cautaProdus(1).getStoc() == 2);
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        try{
            service.updateProdus(9, "nume", 1, "categorie", 1, 2, 1D, map2);
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        try {
            service.stergeProdus(1);
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        assert (service.getProduse().size() == 0);

        try {
            service.stergeProdus(1);
            assert(false);
        } catch (ValidationException | ServiceException e) {
            assert(true);
        }

        try {
            service.stergeProducator(1);
            assert(true);
        } catch (ValidationException | ServiceException e) {
            assert(false);
        }

        assert (service.getProducatori().size() == 0);
        assert (service.getProduse().size() == 0);
    }

    /**
     * Aceasta metoda se ocupa cu apelarea tuturor functiilor de testare
     */
    public static void ruleazaTeste(){

        System.out.println("Se ruleaza testele...");
        produsTest();
        producatorTest();
        producatorRepoTest();
        produseRepoTest();
        serviceTest();
        System.out.println("Testele au fost rulate cu succes!\n");
    }
}