package magazin.ui;

import magazin.domain.validatori.ValidationException;
import magazin.service.Service;
import magazin.service.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Aceasta este clasa ce se ocupa cu realizarea interfetei in consola pentru aplicatia noastra
 */
public class UI {

    /**
     * Acesta este campul ce conecteaza interfata de codul din spatele aplicatiei
     */
    private Service service;

    /**
     * Acesta este constructorul clasei
     * @param service - Service
     */
    public UI(Service service) {

        this.service = service;
    }

    /**
     * Aceasta metoda se ocupa cu printarea meniului
     */
    private void afisareMeniu(){

        System.out.println("===============Magazin===============");
        System.out.println();
        System.out.println("1) Adauga un producator");
        System.out.println("2) Sterge un producator");
        System.out.println("3) Actualizeaza un producator");
        System.out.println("4) Cauta un producator");
        System.out.println("5) Afiseaza toti producatorii");
        System.out.println("6) Afiseaza producatorii in ordine alfabetic");
        System.out.println("7) Adauga un produs");
        System.out.println("8) Sterge un produs");
        System.out.println("9) Actualizeaza stocul unui produs");
        System.out.println("10) Cauta un produs");
        System.out.println("11) Afiseaza toate produsele");
        System.out.println("12) Afiseaza produsele in ordine alfabetica");
        System.out.println("13) Iesire");
        System.out.println();
    }

    /**
     * Aceasta metoda se ocupa cu citirea tututror datelor unui producator
     */
    private void adaugaProducator() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul producatorului: ");
        String id = reader.readLine();
        System.out.print("Introduceti numele producatorului: ");
        String nume = reader.readLine();
        System.out.print("Introduceti tara de origine a producatorului: ");
        String tara = reader.readLine();
        Integer idInt;
        try{
            idInt = Integer.valueOf(id);
            this.service.adaugaProducator(idInt, nume, tara);
            System.out.println("Producatorul a fost adaugat cu succes!\n");
        }catch (NumberFormatException err){

            System.out.println("Id-ul introdus nu este un numar!\n");
        }
    }

    /**
     * Aceasta metoda se ocupa cu citirea si adaugarea unui produs
     */
    private void adaugaProdus() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul produsului: ");
        String id = reader.readLine();
        System.out.print("Introduceti numele produsului: ");
        String nume = reader.readLine();
        System.out.print("Introduceti id-ul producatorului: ");
        String id_p = reader.readLine();
        System.out.print("Introduceti categoria produsului: ");
        String categorie = reader.readLine();
        System.out.print("Introduceti gramajul produsului: ");
        String gramaj = reader.readLine();
        System.out.print("Introduceti stocul produsului: ");
        String stoc = reader.readLine();
        System.out.print("Introduceti pretul produsului: ");
        String pret = reader.readLine();
        System.out.print("Introduceti una sau mai multe date separate prin | (ex: 2021-10-10 10:10|2021-10-10 10:10): ");
        String date = reader.readLine();

        try{
            String[] stringList = date.split("\\|");

            SortedMap<LocalDateTime, Integer> date_expr = new TreeMap<>();

            Arrays.stream(stringList).forEach(x -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                try{
                    LocalDateTime data = LocalDateTime.parse(x, formatter);
                    if(date_expr.containsKey(data)){

                        Integer val = date_expr.get(data);
                        date_expr.put(data, val + 1);
                    }
                    else
                        date_expr.put(data, 1);

                }catch (DateTimeParseException ignored){

                }
            });
            //System.out.println(date_expr);
            this.service.adaugaProdus(Integer.valueOf(id), nume, Integer.valueOf(id_p), categorie, Integer.valueOf(gramaj), Integer.valueOf(stoc), Double.valueOf(pret), date_expr);
            System.out.println("Produsul a fost adaugat cu succes!\n");
        }catch (NumberFormatException err){
            System.out.println("Datele introduse nu au fost corecte!\n");
        }
    }

    /**
     *  Aceasta metoda se ocupa cu apelarea metodei ce sterge un producator dupa id
     */
    private void stergeProducator() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul producatorului: ");
        String id = reader.readLine();

        try{
            this.service.stergeProducator(Integer.valueOf(id));
            System.out.println("Producatorul a fost sters cu succes!\n");
        }catch (NumberFormatException ignored){
            System.out.println("Id-ul introdus nu e un numar!\n");
        }
    }

    /**
     *  Aceasta metoda se ocupa cu stergerea unui produs
     */
    private void stergeProdus() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul produsului: ");
        String id = reader.readLine();

        try{
            this.service.stergeProdus(Integer.valueOf(id));
            System.out.println("Produsul a fost sters cu succes!\n");
        }catch (NumberFormatException ignored){
            System.out.println("Id-ul introdus nu e un numar!\n");
        }
    }

    /**
     * Aceasta metoda se ocupa cu actualizarea unui producator
     */
    private void updateProducator() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul producatorului: ");
        String id = reader.readLine();
        System.out.print("Introduceti numele producatorului: ");
        String nume = reader.readLine();
        System.out.print("Introduceti tara de origine a producatorului: ");
        String tara = reader.readLine();

        try{
            this.service.updateProducator(Integer.valueOf(id), nume, tara);
            System.out.println("Producatorul a fost actualizat cu succes!\n");
        }catch (NumberFormatException ignored){
            System.out.println("Id-ul introdus nu e un numar!\n");
        }
    }

    /**
     * Aceasta metoda se ocupa cu actualizarea unui produs
     */
    private void updateProdus() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul prodsului: ");
        String id = reader.readLine();
        System.out.print("Introduceti numele prodsului: ");
        String nume = reader.readLine();
        System.out.print("Introduceti id-ul producatorului: ");
        String id_p = reader.readLine();
        System.out.print("Introduceti categoria prodsului: ");
        String categorie = reader.readLine();
        System.out.print("Introduceti gramajul produsului: ");
        String gramaj = reader.readLine();
        System.out.print("Introduceti stocul nou al prodsului: ");
        String stoc = reader.readLine();
        System.out.print("Introduceti pretul prodsului: ");
        String pret = reader.readLine();
        System.out.print("Introduceti una sau mai mutlte date separate prin | (ex: 2021-10-10 10:10|2021-10-10 10:10): ");
        String date = reader.readLine();

        try{
            String[] stringList = date.split("\\|");

            SortedMap<LocalDateTime, Integer> date_expr = new TreeMap<>();

            Arrays.stream(stringList).forEach(x -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                try{
                    LocalDateTime data = LocalDateTime.parse(x, formatter);
                    if(date_expr.containsKey(data)){

                        Integer val = date_expr.get(data);
                        date_expr.put(data, val + 1);
                    }
                    else
                        date_expr.put(data, 1);

                }catch (DateTimeParseException ignored){

                }
            });
            this.service.updateProdus(Integer.valueOf(id), nume, Integer.valueOf(id_p), categorie, Integer.valueOf(gramaj), Integer.valueOf(stoc), Double.valueOf(pret), date_expr);
            System.out.println("Produsul a fost actualizat cu succes!\n");
        }catch (NumberFormatException err){
            System.out.println("Datele introduse nu au fost corecte!\n");
        }
    }

    /**
     *  Aceasta metoda se ocupa cu cautarea unui producator dupa id
     */
    private void cautaProducator() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul producatorului: ");
        String id = reader.readLine();

        try{
            System.out.println(this.service.cautaProducator(Integer.valueOf(id)));
        }catch (NumberFormatException ignored){
            System.out.println("Id-ul introdus nu e un numar!\n");
        }
    }

    /**
     *  Aceasta metoda se ocupa cu cautarea unui produs
     */
    private void cautaProdus() throws IOException, ValidationException, ServiceException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul produsului: ");
        String id = reader.readLine();

        try{
            System.out.println(this.service.cautaProdus(Integer.valueOf(id)));
        }catch (NumberFormatException ignored){
            System.out.println("Id-ul introdus nu e un numar!\n");
        }
    }

    /**
     * Metoda ce se ocupa cu afisarea producatorilor
     */
    private void afisareProducatori(){

        this.service.getProducatori().forEach(System.out::println);
    }

    /**
     * Metoda ce se ocupa cu afisare produselor
     */
    private void afisareProduse(){

        this.service.getProduse().forEach(System.out::println);
    }

    /**
     * Metoda ce se ocupa cu afisarea producatorilor alfabetic
     */
    private void afisareProducatoriAlfabetic(){

        this.service.sortareaCrescatoareProducatori().forEach(System.out::println);
    }

    /**
     * Metoda ce se ocupa cu afisarea produselor alfabetic
     */
    private void afisareProduseAlfabetic(){

        this.service.sortareCrescatoareProduse().forEach(System.out::println);
    }

    /**
     * Metoda ce porneste aplicatia
     */
    public void run(){

        while (true){

            afisareMeniu();
            System.out.print("Introduceti comanda: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                String optiune = reader.readLine();

                switch (optiune){
                    case "1" -> this.adaugaProducator();
                    case "2" -> this.stergeProducator();
                    case "3" -> this.updateProducator();
                    case "4" -> this.cautaProducator();
                    case "5" -> this.afisareProducatori();
                    case "6" -> this.afisareProducatoriAlfabetic();
                    case "7" -> this.adaugaProdus();
                    case "8" -> this.stergeProdus();
                    case "9" -> this.updateProdus();
                    case "10" -> this.cautaProdus();
                    case "11" -> this.afisareProduse();
                    case "12" -> this.afisareProduseAlfabetic();
                    case "13" -> {
                        System.out.println("La revedere!\n");
                        return;
                    }
                    default -> System.out.println("Comanda invalida!\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ValidationException | ServiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
/*
System.out.println("1) Adauga un producator");
        System.out.println("2) Sterge un producator");
        System.out.println("3) Actualizeaza un producator");
        System.out.println("4) Cauta un producator");
        System.out.println("5) Afiseaza toti producatorii");
        System.out.println("6) Afiseaza producatorii in ordine alfabetic");
        System.out.println("7) Adauga un produs");
        System.out.println("8) Sterge un produs");
        System.out.println("9) Actualizeaza stocul unui produs");
        System.out.println("10) Cauta un produs");
        System.out.println("11) Afiseaza toate produsele");
        System.out.println("12) Afiseaza produsele in ordine alfabetica");
        System.out.println("13) Iesire");
 */
