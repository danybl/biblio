
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

public class DAO implements Serializable {

    protected Connexion connexion;

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

    protected long getPrimaryKey(String createPrimaryKeyRequest) throws DAOException {
        try(
            PreparedStatement createPreparedStatement = getConnection().prepareStatement(createPrimaryKeyRequest)) {
            try(
                ResultSet resultSet = createPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new DAOException("Impossible de lire la séquence");
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

}
