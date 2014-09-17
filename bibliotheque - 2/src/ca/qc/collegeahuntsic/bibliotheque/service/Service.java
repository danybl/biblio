
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.io.Serializable;
import java.sql.Connection;

public class Service implements Serializable {

    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    private Connection connexion;

    public Service() {
        // TODO Auto-generated constructor stub
    }

    protected Connection getConnexion() {
        return this.connexion;
    }

    public void setConnexion(Connection newConnexion) {
        this.connexion = newConnexion;
    }

}
