
package ca.qc.collegeahuntsic.bibliotheque.dao;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    protected BigDecimal getPrimaryKey(String createPrimaryKeyRequest) throws DAOException {
        try(
            PreparedStatement createPreparedStatement = getConnection().prepareStatement(createPrimaryKeyRequest)) {
            try(
                ResultSet resultSet = createPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                }
                throw new DAOException("Impossible de lire la séquence");
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

}
