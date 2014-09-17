
package ca.qc.collegeahuntsic.bibliotheque.dao;

public class MembreDAO {

    public static final String INSERT_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret, nbpret)"
        + "VALUES (?,?,?,?,?)";

    public static final String UPDATE_INFO_REQUEST = "UPDATE membre"
        + "SET nom = ?, telephone = ?, limitePret = ?, "
        + "WHERE idMembre = ?";

    public static final String DELETE_REQUEST = "DELETE FROM membre"
        + "WHERE idMembre = ?";

    public static final String GET_ALL_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbpret"
        + " FROM membre";

    //a completer
    public static final String FIND_BY_ID = "";

    public static final String FIND_BY_NOM = "SELECT idMembre, nom, telephone, limitePret, nbpret"
        + "FROM membre"
        + "where nom like ?";

    public static final String FIND_BY_TEL = "SELECT idMembre, nom, telephone, limitePret, nbpret"
        + "FROM membre"
        + "where telephone = ?";

    public MembreDAO() {
        // TODO Auto-generated constructor stub
    }

}
