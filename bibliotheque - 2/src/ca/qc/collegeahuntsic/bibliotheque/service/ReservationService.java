
package ca.qc.collegeahuntsic.bibliotheque.service;

import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;

public class ReservationService extends Service {

    /**
     * TODO Auto-generated field javadoc
     */

    private static final long serialVersionUID = 1L;

    private ReservationDAO reservationDAO;

    public ReservationService() {
        // TODO Auto-generated constructor stub
    }

    public ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

}
