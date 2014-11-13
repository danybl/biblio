// Fichier IReservationFacade.java
// Auteur : Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
// Date de création : 2014-11-04

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les réservations dans la base de données.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 */
public interface IReservationFacade extends IFacade {
    /**
     * Place une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à placer
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     * @throws ExistingLoanException Si prêt existe déjà
     * @throws ExistingReservationException Si réservation existe déjà
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void placer(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        FacadeException;

    /**
     * Utilise une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à utiliser
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws ExistingReservationException Si réservation existe déjà
     * @throws ExistingLoanException Si prêt existe déjà
     * @throws InvalidLoanLimitException Si limite de prêt atteinte
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void utiliser(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        FacadeException;

    /**
     * Annule une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO Le reservation à annuler
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws FacadeException Si la session est <code>null</code>, si la réservation est <code>null</code>, si la réservation n'existe pas
     *         ou s'il y a une erreur avec la base de données
     */
    void annuler(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException;

    /**
     * Lit une reservation.
     *
     * @param session La session à utiliser
     * @param idReservation L'ID de la reservation à lire
     * @return La reservation
     * @throws InvalidHibernateSessionException Si la sesssion est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    ReservationDTO getReservation(Session session,
        String idReservation) throws InvalidHibernateSessionException,
        FacadeException;
}
