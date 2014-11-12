// Fichier ILivreFacade.java
// Auteur : Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
// Date de création : 2014-11-04

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les livres dans la base de données.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 *
 */
public interface ILivreFacade extends IFacade {
    /**
     * Acquiert un livre.
     *
     * @param session La session à utiliser
     * @param livreDTO Le livre à acquérir
     * @throws InvalidHibernateSessionException Si la sesssion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */

    void acquerir(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException;

    /**
     * Vend un livre.
     *
     * @param session La session à utiliser
     * @param livreDTO Le livre à vendre
     * @throws InvalidHibernateSessionException Si la sesssion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */

    void vendre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException;

    /**
     * Lit un livre.
     *
     * @param session La session à utiliser
     * @param idLivre L'ID du livre à lire
     * @return Le livre
     * @throws InvalidHibernateSessionException Si la sesssion est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    LivreDTO getLivre(Session session,
        String idLivre) throws InvalidHibernateSessionException,
        FacadeException;
}
