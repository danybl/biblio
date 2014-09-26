
package ca.qc.collegeahuntsic.bibliotheque.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ConnexionException;

//import java.sql.DatabaseMetaData;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.
 *
 * Ce programme ouvrir une connexion avec une BD via JDBC.
 * La méthode serveursSupportes() indique les serveurs support�s.
 *
 * Pré-condition
 *   le driver JDBC approprié doit être accessible.
 *
 * Post-condition
 *   la connexion est ouverte en mode autocommit false et sérialisable,
 *   (s'il est supporté par le serveur).
 * </pre>
 */
public class Connexion {

    private Connection connection;

    private static final String TYPE_SERVEUR_LOCAL = "local";

    private static final String TYPE_SERVEUR_DISTANT = "distant";

    private static final String TYPE_SERVEUR_POSTGRES = "postgres";

    private static final String TYPE_SERVEUR_ACCESS = "access";

    private static final String SERVEUR_LOCAL_CLASS = "com.mysql.jdbc.Driver";

    private static final String SERVEUR_DISTANT_CLASS = "oracle.jdbc.driver.OracleDriver";

    private static final String SERVEUR_POSTGRES_CLASS = "org.postgresql.Driver";

    private static final String SERVEUR_ACCESS_CLASS = "org.postgresql.Driver";

    private static final String SERVEUR_LOCAL_URL = "jdbc:mysql://localhost:3306/";

    private static final String SERVEUR_DISTANT_URL = "jdbc:oracle:thin:@localhost:1521:";

    //  private static final String SERVEUR_DISTANT_URL = "jdbc:oracle:thin:@collegeahunstic.info:1521:";

    private static final String SERVEUR_POSTGRES_URL = "jdbc:postgresql:";

    private static final String SERVEUR_ACCESS_URL = "jdbc:postgresql:";

    /**
     * Ouverture d'une connexion en mode autocommit false et sérialisable (si supporté)
     * @param serveur serveur SQL de la BD
     * @bd nom de la base de données
     * @user userid sur le serveur SQL
     * @pass mot de passe sur le serveur SQL
     */
    public Connexion(String typeServeur,
        String schema,
        String nomUtilisateur,
        String motPasse) throws ConnexionException {
        String urlBD = null;

        try {
            if(typeServeur.equals(Connexion.TYPE_SERVEUR_LOCAL)) {
                Class.forName(Connexion.SERVEUR_LOCAL_CLASS);
                urlBD = Connexion.SERVEUR_LOCAL_URL
                    + schema;
            } else if(typeServeur.equals(Connexion.TYPE_SERVEUR_DISTANT)) {
                Class.forName(Connexion.SERVEUR_DISTANT_CLASS);
                urlBD = Connexion.SERVEUR_DISTANT_URL
                    + schema;
            } else if(typeServeur.equals(Connexion.TYPE_SERVEUR_POSTGRES)) {
                Class.forName(Connexion.SERVEUR_POSTGRES_CLASS);
                urlBD = Connexion.SERVEUR_POSTGRES_URL
                    + schema;
            } else if(typeServeur.equals(Connexion.TYPE_SERVEUR_ACCESS)) {
                Class.forName(Connexion.SERVEUR_ACCESS_CLASS);
                urlBD = Connexion.SERVEUR_ACCESS_URL
                    + schema;
            } else {
                throw new IllegalArgumentException("Type de serveur "
                    + typeServeur
                    + "n'est pas valide.");
            }
            setConnection(DriverManager.getConnection(urlBD,
                nomUtilisateur,
                motPasse));

            // Mettre en mode de commit manuel
            getConnection().setAutoCommit(false);

            System.out.println("Ouverture de la connexion en mode read committed (default) :\n"
                + "Heure "
                + System.currentTimeMillis()
                + " "
                + getConnection());
        } catch(ClassNotFoundException classNotFoundException) {
            throw new ConnexionException(classNotFoundException);
        } catch(SQLException sqlException) {
            throw new ConnexionException(sqlException);
        } catch(IllegalArgumentException illegalArgumentException) {
            throw new ConnexionException(illegalArgumentException);
        }
    }

    private void setConnection(Connection connection2) {
        this.connection = connection2;
    }

    /**
     *fermeture d'une connexion
     */
    public void fermer() throws SQLException {
        this.connection.rollback();
        this.connection.close();
        System.out.println("Connexion fermée"
            + " "
            + this.connection);
    }

    /**
     *commit
     */
    public void commit() throws SQLException {
        this.connection.commit();
    }

    /**
     *rollback
     */
    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    /**
     *retourne la Connection jdbc
     */
    public Connection getConnection() {
        return this.connection;
    }

    public void close() throws Exception {
        getConnection().close();
        System.out.println("\nConnexion fermée "
            + getConnection());
    }

    /**
     * Retourne la liste des serveurs supportés par ce gestionnaire de connexions
     */
    public static String serveursSupportes() {
        return "local : MySQL installé localement\n"
            + "distant : Oracle installé au Département d'Informatique du Collège Ahuntsic\n"
            + "postgres : Postgres installé localement\n"
            + "access : Microsoft Access installé localement et inscrit dans ODBC";
    }
}// Classe Connexion
