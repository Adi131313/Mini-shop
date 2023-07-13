package magazin.repository;

import magazin.domain.Producator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa se ocupa cu gestionarea bazei de date pentru producatori
 */
public class ProducatorRepository {

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
    public ProducatorRepository(String url, String username, String password){

        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Aceasta metoda extrage un producator din DB si il returneaza
     * @param resultSet - resultSet-ul de unde extragem date
     * @return producatorul extras
     */
    public Producator extrageProducator(ResultSet resultSet){

        try{

            return new Producator(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        }
        catch (SQLException err){

            err.printStackTrace();
        }

        return null;
    }

    /**
     * Aceasta metoda se ocupa cu returnarea producatorului cu id-ul egal cu cel primit ca si parametru
     * @param id - id-ul produsului pe care-l cautam
     * @return producator daca-l gasim
     *         null daca nu-l gasim
     * @throws IllegalArgumentException daca id-ul e null
     */
    public Producator cauta(Integer id){

        if(id == null)

            throw new IllegalArgumentException("Id-ul nu trebuie sa fie null!\n");

        String sql = "SELECT * FROM producator WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())

                return extrageProducator(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Aceasta functie se ocupa cu returnarea listei de producatori
     * @return lista de producatori
     */
    public List<Producator> getAll(){

        List<Producator> list = new ArrayList<>();

        String sql = "SELECT * FROM producator";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();) {

            while(resultSet.next()){

                list.add(extrageProducator(resultSet));
            }

            return list;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } ;

        return list;
    }

    /**
     * Aceasta metoda se ocupa cu adaugarea unui producator in baza de date
     * @param p - Producatorul pe care-l adaugam
     * @throws IllegalArgumentException - daca producatorul primit e null
     */
    public void adauga(Producator p){

        if(p == null)

            throw new IllegalArgumentException("Producatorul nu poate sa fie null!\n");

        String sql = "INSERT INTO producator(id, nume, tara_origine) VALUES (?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, p.getId());
            statement.setString(2, p.getNume());
            statement.setString(3, p.getTaraOrigine());

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Aceasta metoda se ocupa cu stergerea unui producator dupa id
     * @param id - id-ul produsului pe care-l stergem
     * @throws IllegalArgumentException daca id-ul primit ca si parametru este null
     */
    public void delete(Integer id){

        if(id == null)

            throw new IllegalArgumentException("Id-ul nu trebuie sa fie null!\n");

        String sql = "DELETE FROM producator WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Metoda folosita pentru actualizarea numelui si a tarii de origine a producatorului
     * @param producator - contine id-ul vechi al producatorului, numele nou si tara de origine noua
     * @throws IllegalArgumentException daca producator este null
     */
    public void update(Producator producator){

        if(producator == null)

            throw new IllegalArgumentException("Producatorul nu poate sa fie null!\n");

        String sql = "UPDATE producator SET nume = ?, tara_origine = ? WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, producator.getNume());
            statement.setString(2, producator.getTaraOrigine());
            statement.setInt(3, producator.getId());

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
