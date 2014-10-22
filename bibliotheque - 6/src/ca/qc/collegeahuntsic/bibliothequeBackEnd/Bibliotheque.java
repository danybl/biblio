
package ca.qc.collegeahuntsic.bibliothequeBackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.StringTokenizer;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.util.BibliothequeCreateur;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.util.FormatteurDate;
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
 * </pre>
 */
public class Bibliotheque {

    private static BibliothequeCreateur bibliothequeCreateur;

    private final static Logger LOGGER = Logger.getLogger(Bibliotheque.class);

    /**
     * Ouverture de la BD,
     * traitement des transactions et
     * fermeture de la BD.
     */
    public static void main(String argv[]) throws Exception {
        // validation du nombre de paramètres
        if(argv.length < 1) {
            Bibliotheque.LOGGER.info("Usage: java Biblio <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }

        try {
            // ouverture du fichier de transactions
            InputStream sourceTransaction = Bibliotheque.class.getResourceAsStream("/"
                + argv[0]);
            try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {

                bibliothequeCreateur = new BibliothequeCreateur();
                traiterTransactions(reader);
            }
        } catch(Exception e) {
            //bibliothequeCreateur.rollbackTransaction();
            e.printStackTrace(System.out);
        }
    }

    /**
     * Traitement des transactions de la biblioth�que
     */
    static void traiterTransactions(BufferedReader reader) throws Exception {
        afficherAide();
        System.out.println("\n\n\n");
        String transaction = lireTransaction(reader);
        while(!finTransaction(transaction)) {
            /* découpage de la transaction en mots*/
            StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            if(tokenizer.hasMoreTokens()) {
                executerTransaction(tokenizer);
            }
            transaction = lireTransaction(reader);
        }
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        String transaction = reader.readLine();
        if(transaction != null) {
            System.out.println(transaction);
        }
        /* echo si lecture dans un fichier */
        return transaction;
    }

    /**
     * Décodage et traitement d'une transaction
     */
    static void executerTransaction(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            String command = tokenizer.nextToken();

            /* ******************* */
            /*         HELP        */
            /* ******************* */
            if("aide".startsWith(command)) {
                afficherAide();
            } else if("acquerir".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setTitre(readString(tokenizer));
                livreDTO.setAuteur(readString(tokenizer));
                livreDTO.setDateAcquisition(readDate(tokenizer));
                bibliothequeCreateur.getLivreFacade().acquerir(bibliothequeCreateur.getSession(),
                    livreDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("vendre".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                bibliothequeCreateur.getLivreFacade().vendre(bibliothequeCreateur.getSession(),
                    livreDTO);

                bibliothequeCreateur.commitTransaction();
            } else if("preter".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readString(tokenizer));
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                PretDTO pretDTO = new PretDTO();
                pretDTO.setLivreDTO(livreDTO);
                pretDTO.setMembreDTO(membreDTO);
                bibliothequeCreateur.getPretFacade().emprunter(bibliothequeCreateur.getSession(),
                    pretDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("renouveler".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                PretDTO pretDTO = new PretDTO();
                pretDTO.setIdPret(readString(tokenizer));
                bibliothequeCreateur.getPretFacade().renouveler(bibliothequeCreateur.getSession(),
                    pretDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("retourner".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                PretDTO pretDTO = new PretDTO();
                pretDTO.setLivreDTO(livreDTO);
                bibliothequeCreateur.getPretFacade().retourner(bibliothequeCreateur.getSession(),
                    pretDTO);
                //La méthode du prof retourne un pret
                //et bibliocreateur.terminer
                bibliothequeCreateur.commitTransaction();
            } else if("inscrire".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setNom(readString(tokenizer));
                membreDTO.setTelephone(readString(tokenizer));
                membreDTO.setLimitePret(readString(tokenizer));
                bibliothequeCreateur.getMembreFacade().inscrireMembre(bibliothequeCreateur.getSession(),
                    membreDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("desinscrire".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readString(tokenizer));
                bibliothequeCreateur.getMembreFacade().desinscrireMembre(bibliothequeCreateur.getSession(),
                    membreDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("reserver".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                // Juste pour éviter deux dates de réservation strictement identiques
                Thread.sleep(1);
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readString(tokenizer));
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setLivreDTO(livreDTO);
                reservationDTO.setMembreDTO(membreDTO);
                bibliothequeCreateur.getReservationFacade().placer(bibliothequeCreateur.getSession(),
                    reservationDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("utiliser".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readString(tokenizer));
                bibliothequeCreateur.getReservationFacade().utiliser(bibliothequeCreateur.getSession(),
                    reservationDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("annuler".startsWith(command)) {
                bibliothequeCreateur.beginTransaction();
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readString(tokenizer));
                bibliothequeCreateur.getReservationFacade().annuler(bibliothequeCreateur.getSession(),
                    reservationDTO);
                bibliothequeCreateur.commitTransaction();
            } else if("--".startsWith(command)) {
                // ne rien faire; c'est un commentaire
            } else {
                System.out.println("  Transactions non reconnue.  Essayer \"aide\"");
            }
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | MissingDTOException
            | InvalidDTOClassException
            | InvalidCriterionException
            | InvalidSortByPropertyException
            | ExistingReservationException
            | ExistingLoanException
            | InvalidLoanLimitException
            | MissingLoanException
            | FacadeException
            | InvalidCriterionValueException
        /*| DuplicateDTOException*/exception) {
            Bibliotheque.LOGGER.error("**** "
                + exception.getMessage());
            bibliothequeCreateur.rollbackTransaction();
        } catch(InterruptedException interruptedException) {
            Bibliotheque.LOGGER.error("**** "
                + interruptedException.toString());
            bibliothequeCreateur.rollbackTransaction();
        }
    }

    /** Affiche le menu des transactions acceptées par le système */
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

    /**
     * Vérifie si la fin du traitement des transactions est
     * atteinte.
     */
    static boolean finTransaction(String transaction) {
        /* fin de fichier atteinte */
        if(transaction == null) {
            return true;
        }

        StringTokenizer tokenizer = new StringTokenizer(transaction,
            " ");

        //NOTE : CODE CI-DESSOUS A REVOIR!!

        /* ligne ne contenant que des espaces */
        if(!tokenizer.hasMoreTokens()) {
            return false;
        }

        /* commande "exit" */
        String commande = tokenizer.nextToken();
        if(commande.equals("exit")) {
            return true;
        }
        return false;

    }

    /** lecture d'une chaine de caractères de la transaction entrée à l'écran */
    static String readString(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BibliothequeException("autre paramètre attendu");

    }

    /**
     * lecture d'un int java de la transaction entrée à l'écran
     */
    //    static int readInt(StringTokenizer tokenizer) throws BibliothequeException {
    //        if(tokenizer.hasMoreElements()) {
    //            String token = tokenizer.nextToken();
    //            try {
    //                return Integer.valueOf(token).intValue();
    //            } catch(NumberFormatException e) {
    //                throw new BibliothequeException("Nombre attendu à la place de \""
    //                    + token
    //                    + "\"");
    //            }
    //        }
    //        throw new BibliothequeException("autre paramètre attendu");
    //
    //    }

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
            String token = tokenizer.nextToken();
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
