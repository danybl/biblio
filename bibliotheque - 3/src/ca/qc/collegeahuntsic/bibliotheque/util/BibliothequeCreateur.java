
package ca.qc.collegeahuntsic.bibliotheque.util;

import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.service.LivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.MembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.ReservationService;

public class BibliothequeCreateur {

    private LivreDAO livreDAO;

    private MembreDAO membreDAO;

    private ReservationDAO reservationDAO;

    private LivreService livreService;

    private MembreService membreService;

    private ReservationService reservationService;

    public BibliothequeCreateur(Connexion connexion) {
        this.livreDAO = new LivreDAO(connexion);
        this.membreDAO = new MembreDAO(connexion);
        this.reservationDAO = new ReservationDAO(connexion);
        this.livreService = new LivreService(this.livreDAO,
            this.membreDAO,
            this.reservationDAO);
        this.membreService = new MembreService(this.membreDAO);
        this.reservationService = new ReservationService(this.reservationDAO,
            this.membreDAO,
            this.livreDAO);

    }

    public LivreService getLivreService() {
        return this.livreService;
    }

    public MembreService getMembreService() {
        return this.membreService;
    }

    public ReservationService getReservationService() {
        return this.reservationService;
    }

}
