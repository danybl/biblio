
package ca.qc.collegeahuntsic.bibliotheque.dao;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;

public class ReservationDAO extends DAO {
    private static final long serialVersionUID = 1L;

    //Region private final members
    //        private static final String FIND_RESERVATION_BY_ID = "select idReservation, idLivre, idMembre, dateReservation "
    //            + "from reservation "
    //            + "where idReservation = ?";
    //    
    //        private static final String FIND_LIVRE_BY_ID = "select idReservation, idLivre, idMembre, dateReservation "
    //            + "from reservation "
    //            + "where idLivre = ? ";
    //    
    //        private static final String FIND_MEMBRE_BY_ID = "select idReservation, idLivre, idMembre, dateReservation "
    //            + "from reservation "
    //            + "where idMembre = ? ";
    //    
    //        private static final String INSERT_REQUEST = "insert into reservation (idReservation, idlivre, idMembre, dateReservation) "
    //            + "values (?,?,?,to_date(?,'YYYY-MM-DD'))";
    //    
    //        private static final String DELETE_REQUEST = "delete from reservation "
    //            + "where idReservation = ?";
    //EndRegion
    public ReservationDAO(Connexion connexion) {
        super(connexion);
    }

}
