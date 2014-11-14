// Fichier Bibliotheque.java

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
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public final class Bibliotheque {

    private static BibliothequeCreateur bibliothequeCreateur;

    private static final Log LOGGER = LogFactory.getLog(Bibliotheque.class);

    /**
     *Constructeur privé pour empêcher toute instanciation.
     *
     */
    private Bibliotheque() {
        super();
    }

    /**
     * Ouverture de la BD,
     * traitement des transactions et
     * fermeture de la BD.
     * @param argv arguments lors du démarage de l'application
     */
    public static void main(String[] argv) {
        if(argv.length < 1) {
            Bibliotheque.LOGGER.info("Usage: java Bibliotheque <fichier-transactions>");
            return;
        }
        try {
            final InputStream sourceTransaction = Bibliotheque.class.getResourceAsStream("/"
                + argv[0]);
            try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {
                Bibliotheque.bibliothequeCreateur = new BibliothequeCreateur();
                Bibliotheque.traiterTransactions(reader);
            }
        } catch(IOException ioException) {
            Bibliotheque.LOGGER.error(" **** "
                + ioException.getMessage());
        } catch(BibliothequeException bibliothequeException) {
            Bibliotheque.LOGGER.error(" **** "
                + bibliothequeException.getMessage());
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
    private static String lireTransaction(BufferedReader reader) throws IOException {
        final String transaction = reader.readLine();
        if(transaction != null) {
            Bibliotheque.LOGGER.info(transaction);
        }
        return transaction;
    }

    /**
     * Décodage et traitement d'une transaction.
     *
     * @param tokenizer Le tokenizer à utiliser
     * @throws BibliothequeException S'il y a une erreur d'exécution
     */
    private static void executerTransaction(StringTokenizer tokenizer) throws BibliothequeException {
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
        }
    }

    /**
     * Affiche le menu des transactions acceptées par le système.
     * */
    private static void afficherAide() {
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

    /**
     * Vérifie si la fin du traitement des transactions est
     * atteinte.
     * @param transaction String pour l'entrée de la transaction
     * @return True si fin du fichier atteint, sinon false
     */
    private static boolean finTransaction(String transaction) {
        boolean resultat = true;
        if(transaction != null) {
            final StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            resultat = tokenizer.hasMoreTokens();
            if(resultat) {
                final String commande = tokenizer.nextToken();
                resultat = "exit".equals(commande);
            }
        }
        return resultat;
    }

    /**
     * Lit une chaîne de caractères de la transaction.
     *
     * @param tokenizer Le tokenizer à utiliser
     * @return La chaîne de caractères de la transaction
     * @throws BibliothequeException S'il y a un problème de lecture
     */
    private static String readString(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**
     * Lit une une date en format YYYY-MM-DD.
     *
     * @param tokenizer Le tokenizer à utiliser
     * @return La date en format YYYY-MM-DD
     * @throws BibliothequeException S'il y a un problème de lecture
     */
    private static Timestamp readDate(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return FormatteurDate.timestampValue(token);
            } catch(ParseException parseException) {
                throw new BibliothequeException("Date en format YYYY-MM-DD attendue à la place  de \""
                    + token
                    + "\"",
                    parseException);
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /** Permet d'inscrire d'un membre.
     * @param tokenizer StringTokenizer pour séparer les entrées
     * @throws BibliothequeException Si exception dans les transaction
     */
    private static void inscrireMembre(StringTokenizer tokenizer) throws BibliothequeException {
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
            | FacadeException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /**
     * Permet de desinscrire d'un membre.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans la transaction
     */
    private static void desinscrireMembre(StringTokenizer tokenizer) throws BibliothequeException {
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
            | InvalidDTOException
            | MissingDTOException
            | FacadeException
            | ExistingLoanException
            | ExistingReservationException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Permet d'acquerir un livre.
     * @param tokenizer StringTokenizer pour découper les entrées pour la transaction
     * @throws BibliothequeException Si erreur dans les transaction
     */
    private static void acquerirLivre(StringTokenizer tokenizer) throws BibliothequeException {
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
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Permet de vendre un livre.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    private static void vendreLivre(StringTokenizer tokenizer) throws BibliothequeException {
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
            | InvalidDTOException
            | FacadeException
            | MissingDTOException
            | ExistingLoanException
            | ExistingReservationException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Permet de preter un livre.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    private static void preterLivre(StringTokenizer tokenizer) throws BibliothequeException {
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
            | InvalidDTOException
            | FacadeException
            | MissingDTOException
            | InvalidLoanLimitException
            | ExistingReservationException
            | ExistingLoanException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Permet de renouveler un pret.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    private static void renouvelerPret(StringTokenizer tokenizer) throws BibliothequeException {
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
            | InvalidDTOException
            | FacadeException
            | MissingDTOException
            | ExistingReservationException
            | MissingLoanException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Permet de retourner un livre.
     * @param tokenizer StringTokenizer pour séparer les entrées de la commande
     * @throws BibliothequeException Si erreur dans les transaction
     */
    private static void retournerLivre(StringTokenizer tokenizer) throws BibliothequeException {
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
            | InvalidDTOException
            | FacadeException
            | BibliothequeException
            | MissingDTOException
            | MissingLoanException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /**
     * Permet de reserver d'un livre.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans la transaction
     */
    private static void reserverLivre(StringTokenizer tokenizer) throws BibliothequeException {
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
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException
            | InterruptedException
            | MissingDTOException
            | ExistingReservationException
            | ExistingLoanException
            | MissingLoanException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Permet de utiliser d'une reservation.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans la transaction
     */
    private static void utiliserReservation(StringTokenizer tokenizer) throws BibliothequeException {
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
            | InvalidDTOException
            | FacadeException
            | MissingDTOException
            | ExistingLoanException
            | ExistingReservationException
            | InvalidLoanLimitException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Permet d'annuler une reservation.
     * @param tokenizer StringTokenizer pour découper les entrées
     * @throws BibliothequeException Si erreur dans transaction
     */
    private static void annulerReservation(StringTokenizer tokenizer) throws BibliothequeException {
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
            | InvalidDTOException
            | FacadeException
            | MissingDTOException exception) {
            Bibliotheque.LOGGER.error(" **** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

} //class

