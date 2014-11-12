// Fichier IPretFacade.java
// Auteur : Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
// Date de création : 2014-11-04

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les prêts dans la base de données.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 */

public interface IPretFacade extends IFacade {

    /**
     * Emprunte un livre.
     *
     * @param session la session à utiliser
     * @param pretDTO Le prêt à emprunter
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     * @throws ExistingLoanException Si prêt existe déjà
     * @throws ExistingReservationException Si réservation existe déjà
     * @throws InvalidLoanLimitException Si limite de prêts atteinte
     */

    void emprunter(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException,
        InvalidLoanLimitException,
        ExistingReservationException,
        ExistingLoanException;

    /**
     * Retourne un livre.
     *
     * @param session la session à utiliser
     * @param pretDTO Le prêt du livre à retourner
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     * @throws MissingLoanException Si prêt est <code>null</code>
     */
    void retourner(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException,
        MissingLoanException;

    /**
     * Renouvele un prêt.
     *
     * @param session la session à utiliser
     * @param pretDTO Le prêt à renouveler
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     * @throws MissingLoanException Si prêt est <code>null</code>
     * @throws ExistingReservationException Si prêt existant
     */

    void renouveler(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException,
        ExistingReservationException,
        MissingLoanException;

    /**
     * Lit un prêt.
     *
     * @param session La session à utiliser
     * @param idPret L'ID du prêt à lire
     * @return Le prêt
     * @throws InvalidHibernateSessionException Si la sesssion est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    PretDTO getPret(Session session,
        String idPret) throws InvalidHibernateSessionException,
        FacadeException;
}
