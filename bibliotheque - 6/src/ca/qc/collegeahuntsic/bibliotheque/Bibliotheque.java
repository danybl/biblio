
package ca.qc.collegeahuntsic.bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.StringTokenizer;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.util.BibliothequeCreateur;
import ca.qc.collegeahuntsic.bibliotheque.util.FormatteurDate;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import org.apache.log4j.Logger;

/**
 *
 * Interface du système de gestion d'une bibliothèque
 *
 * Ce programme permet d'appeler les transactions de base d'une
 * bibliothèque.  Il gère des livres, des membres et des
 * réservations. Les données sont conservées dans une base de
 * données relationnelles accédée avec JDBC. Pour une liste des
 * transactions traitées, voir la méthode afficherAide().
 *
 * Paramètres
 * 0- site du serveur SQL ("local", "distant" ou "postgres")
 * 1- nom de la BD
 * 2- user id pour établir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa.
 */
public class Bibliotheque {

    private static BibliothequeCreateur bibliothequeCreateur;

    private static final Logger LOGGER = Logger.getLogger(Bibliotheque.class);

    /**
     * Constructeur par défaut.
     *
     */
    private Bibliotheque() {
        //Constructeur par défaut
    }

    /**
     * Ouverture de la BD,
     * traitement des transactions et
     * fermeture de la BD.
     * @param argv arguments lors du démarage de l'application
     * @throws Exception Si erreur au démarrage
     */
    public static void main(String[] argv) throws Exception {
        // validation du nombre de paramètres
        if(argv.length < 1) {
            Bibliotheque.LOGGER.info("Usage: java Biblio <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }

        try {
            // ouverture du fichier de transactions
            final InputStream sourceTransaction = Bibliotheque.class.getResourceAsStream("/"
                + argv[0]);
            try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {

                bibliothequeCreateur = new BibliothequeCreateur();
                traiterTransactions(reader);
            }
        } catch(BibliothequeException exception) {
            exception.printStackTrace(System.out);
        }
    }

    /** Traitement des transactions de la bibliothèque.
     * @param reader Buffer pour la lecture d'une transaction
     * @throws IOException Si erreur de lecture de la transaction
     * @throws BibliothequeException
     *      exception lancée s'il y a un problème avec l'exécution de la commande
     */
    static void traiterTransactions(BufferedReader reader) throws BibliothequeException,
    IOException {
        afficherAide();
        Bibliotheque.LOGGER.info("\n\n");
        String transaction = lireTransaction(reader);
        while(!finTransaction(transaction)) {
            // découpage de la transaction en mots
            final StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            if(tokenizer.hasMoreTokens()) {
                executerTransaction(tokenizer);
            }
            transaction = lireTransaction(reader);
        }
    }

    /** Lecture d'une transaction.
     * @param reader Buffer pour la lecture d'une transaction
     * @throws IOException Si erreur de lecture de la transaction
     * @return transanction Retourne le résultat de la lecture
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        final String transaction = reader.readLine();
        if(transaction != null) {
            Bibliotheque.LOGGER.info(transaction);
        }
        return transaction;
    }

    /** Décodage et traitement d'une transaction.
     * @param tokenizer StringTokenizer pour découper les entrées pour la transaction
     * @throws BibliothequeException Si erreur dans la transaction
     */
    static void executerTransaction(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            final String command = tokenizer.nextToken();

            switch(command) {
                case "aide":
                    Bibliotheque.afficherAide();
                    break;
                case "acquerir":
                    Bibliotheque.acquerirLivre(tokenizer);
                    break;
                case "vendre":
                    Bibliotheque.vendreLivre(tokenizer);
                    break;
                case "preter":
                    Bibliotheque.preterLivre(tokenizer);
                    break;
                case "renouveler":
                    Bibliotheque.renouvelerPret(tokenizer);
                    break;
                case "retourner":
                    Bibliotheque.retournerLivre(tokenizer);
                    break;
                case "inscrire":
                    Bibliotheque.inscrireMembre(tokenizer);
                    break;
                case "desinscrire":
                    Bibliotheque.desinscrireMembre(tokenizer);
                    break;
                case "reserver":
                    Bibliotheque.reserverLivre(tokenizer);
                    break;
                case "utiliser":
                    Bibliotheque.utiliserReservation(tokenizer);
                    break;
                case "annuler":
                    Bibliotheque.annulerReservation(tokenizer);
                    break;
                case "--":
                    // ne rien faire; c'est un commentaire
                    break;
                default:
                    Bibliotheque.LOGGER.info("  Transactions non reconnue.  Essayer \"aide\"");
                    break;
            }
        } catch(BibliothequeException exception) {
            Bibliotheque.LOGGER.error("**** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
            //        } catch(InterruptedException interruptedException) {
            //            Bibliotheque.LOGGER.error("**** "
            //                + interruptedException.toString());
            //            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /**
     * Affiche le menu des transactions acceptées par le système.
     * */
    static void afficherAide() {
        Bibliotheque.LOGGER.info("\n");
        Bibliotheque.LOGGER.info("Chaque transaction comporte un nom et une liste d'arguments");
        Bibliotheque.LOGGER.info("separes par des espaces. La liste peut etre vide.");
        Bibliotheque.LOGGER.info(" Les dates sont en format yyyy-mm-dd.");
        Bibliotheque.LOGGER.info("");
        Bibliotheque.LOGGER.info("Les transactions sont:");
        Bibliotheque.LOGGER.info("  aide");
        Bibliotheque.LOGGER.info("  exit");
        Bibliotheque.LOGGER.info("  acquerir <idLivre> <titre> <auteur> <dateAcquisition>");
        Bibliotheque.LOGGER.info("  emprunter <idLivre> <idMembre>");
        Bibliotheque.LOGGER.info("  renouveler <idLivre>");
        Bibliotheque.LOGGER.info("  retourner <idLivre>");
        Bibliotheque.LOGGER.info("  vendre <idLivre>");
        Bibliotheque.LOGGER.info("  inscrire <nom> <telephone> <limitePret>");
        Bibliotheque.LOGGER.info("  desinscrire <idMembre>");
        Bibliotheque.LOGGER.info("  reserver <idLivre> <idMembre>");
        Bibliotheque.LOGGER.info("  utiliser <idReservation>");
        Bibliotheque.LOGGER.info("  annulerRes <idReservation>");
    }

    /** Permet d'acquerir un livre.
     * @param tokenizer StringTokenizer pour découper les entrées pour la transaction
     * @throws BibliothequeException Si erreur dans les transaction
     */
    static void acquerirLivre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final LivreDTO livreDTO = new LivreDTO();
            livreDTO.setTitre(readString(tokenizer));
            livreDTO.setAuteur(readString(tokenizer));
            livreDTO.setDateAcquisition(readDate(tokenizer));
            bibliothequeCreateur.getLivreFacade().acquerir(bibliothequeCreateur.getSession(),
                livreDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /** Permet de vendre un livre.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    static void vendreLivre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = bibliothequeCreateur.getLivreFacade().getLivre(bibliothequeCreateur.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            bibliothequeCreateur.getLivreFacade().vendre(bibliothequeCreateur.getSession(),
                livreDTO);

            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidCriterionValueException
            | InvalidSortByPropertyException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /** Permet de preter un livre.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    static void preterLivre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = bibliothequeCreateur.getMembreFacade().getMembre(bibliothequeCreateur.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = bibliothequeCreateur.getLivreFacade().getLivre(bibliothequeCreateur.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            final PretDTO pretDTO = new PretDTO();
            pretDTO.setMembreDTO(membreDTO);
            pretDTO.setLivreDTO(livreDTO);
            bibliothequeCreateur.getPretFacade().emprunter(bibliothequeCreateur.getSession(),
                pretDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidSortByPropertyException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /** Permet de renouveler un pret.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    static void renouvelerPret(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final String idPret = readString(tokenizer);
            final PretDTO pretDTO = bibliothequeCreateur.getPretFacade().getPret(bibliothequeCreateur.getSession(),
                idPret);
            if(pretDTO == null) {
                throw new MissingDTOException("Le pret "
                    + idPret
                    + " n'existe pas");
            }
            bibliothequeCreateur.getPretFacade().renouveler(bibliothequeCreateur.getSession(),
                pretDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidSortByPropertyException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /** Permet de retourner un livre.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    static void retournerLivre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();

            final String idPret = readString(tokenizer);
            final PretDTO pretDTO = bibliothequeCreateur.getPretFacade().getPret(bibliothequeCreateur.getSession(),
                idPret);
            if(pretDTO == null) {
                throw new MissingDTOException("Le pret "
                    + idPret
                    + " n'existe pas");
            }
            bibliothequeCreateur.getPretFacade().retourner(bibliothequeCreateur.getSession(),
                pretDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | BibliothequeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidSortByPropertyException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /** Permet d'inscrire d'un membre.
     * @param tokenizer StringTokenizer pour séparer les entrées
     * @throws BibliothequeException Si exception dans les transaction
     */
    static void inscrireMembre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final MembreDTO membreDTO = new MembreDTO();
            membreDTO.setNom(readString(tokenizer));
            membreDTO.setTelephone(readString(tokenizer));
            membreDTO.setNbPret("0");
            membreDTO.setLimitePret(readString(tokenizer));
            bibliothequeCreateur.getMembreFacade().inscrireMembre(bibliothequeCreateur.getSession(),
                membreDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidSortByPropertyException
            | FacadeException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /**
     * Permet de desinscrire d'un membre.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans la transaction
     */
    static void desinscrireMembre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = bibliothequeCreateur.getMembreFacade().getMembre(bibliothequeCreateur.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            bibliothequeCreateur.getMembreFacade().desinscrireMembre(bibliothequeCreateur.getSession(),
                membreDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidSortByPropertyException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /**
     * Permet de reserver d'un livre.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans la transaction
     */
    static void reserverLivre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            // Juste pour éviter deux dates de réservation strictement identiques
            Thread.sleep(1);
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = bibliothequeCreateur.getMembreFacade().getMembre(bibliothequeCreateur.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = bibliothequeCreateur.getLivreFacade().getLivre(bibliothequeCreateur.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            final ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setLivreDTO(livreDTO);
            reservationDTO.setMembreDTO(membreDTO);
            bibliothequeCreateur.getReservationFacade().placer(bibliothequeCreateur.getSession(),
                reservationDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InterruptedException
            | InvalidHibernateSessionException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidCriterionValueException
            | InvalidSortByPropertyException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /** Permet de utiliser d'une reservation.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans la transaction
     */
    static void utiliserReservation(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final String idReservation = readString(tokenizer);
            final ReservationDTO reservationDTO = bibliothequeCreateur.getReservationFacade().getReservation(bibliothequeCreateur.getSession(),
                idReservation);
            if(reservationDTO == null) {
                throw new MissingDTOException("La réservation "
                    + idReservation
                    + " n'existe pas");
            }
            bibliothequeCreateur.getReservationFacade().utiliser(bibliothequeCreateur.getSession(),
                reservationDTO);
            bibliothequeCreateur.getReservationFacade().annuler(bibliothequeCreateur.getSession(),
                reservationDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidCriterionException
            | InvalidCriterionValueException
            | InvalidSortByPropertyException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /** Permet d'annuler une reservation.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans transaction
     */
    static void annulerReservation(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            bibliothequeCreateur.beginTransaction();
            final String idReservation = readString(tokenizer);
            final ReservationDTO reservationDTO = bibliothequeCreateur.getReservationFacade().getReservation(bibliothequeCreateur.getSession(),
                idReservation);
            if(reservationDTO == null) {
                throw new MissingDTOException("La réservation "
                    + idReservation
                    + " n'existe pas");
            }
            bibliothequeCreateur.getReservationFacade().annuler(bibliothequeCreateur.getSession(),
                reservationDTO);
            bibliothequeCreateur.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException exception) {
            throw new BibliothequeException(exception.getMessage());
        }
    }

    /**
     * Vérifie si la fin du traitement des transactions est
     * atteinte.
     * @param transaction String pour l'entrée de la transaction
     * @return True si fin du fichier atteint, sinon false
     */
    static boolean finTransaction(String transaction) {
        // fin de fichier atteinte
        if(transaction == null) {
            return true;
        }
        StringTokenizer tokenizer = new StringTokenizer(transaction,
            " ");
        //NOTE : CODE CI-DESSOUS A REVOIR!!

        // ligne ne contenant que des espaces
        if(!tokenizer.hasMoreTokens()) {
            return false;
        }

        // commande "exit"
        String commande = tokenizer.nextToken();
        if(commande.equals("exit")) {
            return true;
        }
        return false;

    }

    /** lecture d'une chaine de caractères de la transaction entrée à l'écran.
     * @param tokenizer
     */
    static String readString(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BibliothequeException("autre paramètre attendu");

    }

    /**
     * lecture d'un int java de la transaction entrée à l'écran
     */

    /**
     * lecture d'un long java de la transaction entrée à l'écran
     */
    static long readLong(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Long.valueOf(token).longValue();
            } catch(NumberFormatException e) {
                throw new BibliothequeException("Nombre attendu à la place de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**
     * lecture d'une date en format YYYY-MM-DD
     */
    static Timestamp readDate(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return FormatteurDate.timestampValue(token);
            } catch(ParseException e) {
                throw new BibliothequeException("Date en format YYYY-MM-DD attendue à la place  de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }
}//class
