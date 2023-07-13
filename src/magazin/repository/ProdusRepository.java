package magazin.repository;

import magazin.domain.Produs;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Aceasta clasa se ocupa cu gestionarea bazei de date pentru produse
 */
public class ProdusRepository {

    /**
     * The url of the connection
     */
    private String url;

    /**
     * The username of the db account
     */
    private String username;

    /**
     * The password of the db account
     */
    private String password;

    /**
     * The constructor of the class
     * @param url
     * @param username
     * @param password
     */
    public ProdusRepository(String url, String username, String password){

        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Aceasta metoda se ocupa cu extragerea unui Produs din baza de date
     * @param resultSet - resultSet
     * @return produsul nostru
     */
    public Produs extragePrdous(ResultSet resultSet){

        try{

            SortedMap<LocalDateTime, Integer> map = new TreeMap<>();

            Integer id = resultSet.getInt(1);

            String sql = "SELECT * FROM dataexp WHERE id_prod = ?";

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet1 = statement.executeQuery();

            while(resultSet1.next()){

                LocalDateTime localDateTime = resultSet1.getTimestamp(2).toLocalDateTime();

                if(map.containsKey(localDateTime)){

                    Integer val = map.get(localDateTime);
                    map.put(localDateTime, val + 1);
                }
                else
                    map.put(localDateTime, 1);
            }

            String nume = resultSet.getString(2);
            Integer producator = resultSet.getInt(3);
            String categorie = resultSet.getString(4);
            Integer gramaj = resultSet.getInt(5);
            Integer stoc = resultSet.getInt(6);
            Double pret = resultSet.getDouble(7);
            return new Produs(id, nume, producator, categorie, gramaj, stoc, pret, map);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Aceasta functie se ocupa cu cautarea unui produs dupa id
     * @param id - id-ul produsului pe care-l cautam
     * @return produsul cautat daca-l gasim sau nu daca nu-l gasim
     * @throws IllegalArgumentException daca id-ul este null
     */
    public Produs cauta(Integer id){

        if(id == null)

            throw new IllegalArgumentException("Id-ul nu trebuie sa fie null!\n");

        String sql = "SELECT * FROM produs WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())

                return extragePrdous(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Aceasta metoda se ocupa cu returnarea listei de produse din baza de date
     * @return lista ce contine produsele noastre
     */
    public List<Produs> getAll(){

        List<Produs> list = new ArrayList<>();

        String  sql = "SELECT * FROM produs";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();) {

            while(resultSet.next())

                list.add(extragePrdous(resultSet));

            return list;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    /**
     * Aceasta metoda se ocupa cu adaugarea unui produs in baza noastra de date
     * @param p - produsul pe care dorim sa-l adaugam
     * @throws IllegalArgumentException daca produsul nostru este null
     */
    public void adauga(Produs p){

        if(p == null)

            throw new IllegalArgumentException("Produsul nu poate sa fie null!\n");

        String sql = "INSERT INTO produs(id, nume, id_producator, categorie, gramaj, stoc, pret) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, p.getId());
            statement.setString(2, p.getNume());
            statement.setInt(3, p.getProducator());
            statement.setString(4, p.getCategorie());
            statement.setInt(5, p.getGramaj());
            statement.setInt(6, p.getStoc());
            statement.setDouble(7, p.getPret());

            statement.executeUpdate();

            String sql_data = "INSERT INTO dataexp(id_prod, data_expr) VALUES (?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql_data);

            SortedMap<LocalDateTime, Integer> map = p.getDataExpr();

            statement1.setInt(1, p.getId());

            map.forEach((x, y) -> {
                for(int i = 0; i < y; i++){

                    try {
                        statement1.setTimestamp(2, Timestamp.valueOf(x));
                        statement1.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Aceasta functie se ocupa cu stergerea unui produs dupa id
     * @param id - id-ul produsului pe care-l stergem
     * @throws IllegalArgumentException daca id-ul este null
     */
    public void delete(Integer id){

        if(id == null)

            throw new IllegalArgumentException("Id-ul nu trebuie sa fie null!\n");

        String sql = "DELETE FROM produs WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Aceasta metoda se ocupa cu actualizarea stocului unui produs
     * @param produs - produsul pe care vrem sa-l actualizam contiannd toate detaliile noi, anume stocul si datele de expirare
     * @throws IllegalArgumentException - daca produsul primit este null
     */
    public void update(Produs produs){

        if(produs == null)

            throw new IllegalArgumentException("Produsul nu poate sa fie null!\n");

        String sql = "DELETE FROM dataexp WHERE id_prod = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, produs.getId());
            statement.executeUpdate();

            String sql1 = "UPDATE produs SET stoc = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1, produs.getStoc());
            preparedStatement.setInt(2, produs.getId());
            preparedStatement.executeUpdate();

            String sql2 = "INSERT INTO dataexp (id_prod, data_expr) VALUES (?, ?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);

            preparedStatement1.setInt(1, produs.getId());

            produs.getDataExpr().forEach((x, y) ->{

                for(int i = 0; i < y; i++){

                    try {
                        preparedStatement1.setTimestamp(2, Timestamp.valueOf(x));
                        preparedStatement1.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
