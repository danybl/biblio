
package ca.qc.collegeahuntsic.bibliotheque.db;

import java.sql.Connection;
//import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    private Connection conn;

    private final static String LOCAL = "local";

    private final static String LOCAL_DRIVER = "com.mysql.jdbc.Driver";

    private final static String LOCAL_URL = "jdbc:mysql://localhost:3306/";

    private final static String DISTANT = "distant";

    private final static String DISTANT_DRIVER = "oracle.jdbc.driver.OracleDriver";

    private final static String DISTANT_URL = "jdbc:oracle:thin:@localhost:1521:";

    private final static String POSTGRES = "postgres";

    private final static String POSTGRES_DRIVER = "org.postgresql.Driver";

    private final static String POSTGRES_URL = "jdbc:postgresql:";

    private final static String ACCESS_URL = "jdbc:odbc:";

    /**
     * Ouverture d'une connexion en mode autocommit false et sérialisable (si supporté)
     * @param serveur serveur SQL de la BD
     * @bd nom de la base de données
     * @user userid sur le serveur SQL
     * @pass mot de passe sur le serveur SQL
     */
    public Connexion(String serveur,
        String bd,
        String user,
        String pass) throws SQLException {
        Driver d;
        try {
            if(serveur.equals(LOCAL)) {
                d = (Driver) Class.forName(LOCAL_DRIVER).newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection(LOCAL_URL
                    + bd,
                    user,
                    pass);
            } else if(serveur.equals(DISTANT)) {
                d = (Driver) Class.forName(DISTANT_DRIVER).newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection(DISTANT_URL
                    + bd,
                    user,
                    pass);
            } else if(serveur.equals(POSTGRES)) {
                d = (Driver) Class.forName(POSTGRES_DRIVER).newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection(POSTGRES_URL
                    + bd,
                    user,
                    pass);
            } else // access
            {
                d = (Driver) Class.forName(POSTGRES_DRIVER).newInstance();
                DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
                this.conn = DriverManager.getConnection(ACCESS_URL
                    + bd,
                    "",
                    "");
            }

            // mettre en mode de commit manuel
            this.conn.setAutoCommit(false);

            // mettre en mode s�rialisable si possible
            // (plus haut niveau d'integrit� l'acc�s concurrent aux donn�es)
            //            DatabaseMetaData dbmd = this.conn.getMetaData();
            //            if(dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
            //                this.conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            //                System.out.println("Ouverture de la connexion en mode sérialisable :\n"
            //                    + "Estampille "
            //                    + System.currentTimeMillis()
            //                    + " "
            //                    + this.conn);
            //            } else {
            //                System.out.println("Ouverture de la connexion en mode read committed (default) :\n"
            //                    + "Heure "
            //                    + System.currentTimeMillis()
            //                    + " "
            //                    + this.conn);
            //            }
        }// try

        catch(SQLException e) {
            throw e;
        } catch(Exception e) {
            e.printStackTrace(System.out);
            throw new SQLException("JDBC Driver non instancié");
        }
    }

    /**
     *fermeture d'une connexion
     */
    public void fermer() throws SQLException {
        this.conn.rollback();
        this.conn.close();
        System.out.println("Connexion fermée"
            + " "
            + this.conn);
    }

    /**
     *commit
     */
    public void commit() throws SQLException {
        this.conn.commit();
    }

    /**
     *rollback
     */
    public void rollback() throws SQLException {
        this.conn.rollback();
    }

    /**
     *retourne la Connection jdbc
     */
    public Connection getConnection() {
        return this.conn;
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
