
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.io.Serializable;
import java.sql.Connection;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;

public class DAO implements Serializable {

    private Connexion connexion;

    private static final long serialVersionUID = 1L;

    public DAO(Connexion connexion) {
        super();
        setConnexion(connexion);
    }

    public Connexion getConnexion() {
        return this.connexion;
    }

    protected Connection getConnection() {
        return getConnexion().getConnection();
    }

    private void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }

}
