
package ca.qc.collegeahuntsic.bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.StringTokenizer;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.DuplicateDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.util.BibliothequeCreateur;
import ca.qc.collegeahuntsic.bibliotheque.util.FormatteurDate;

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

    private static boolean lectureAuClavier = true;

    private static BibliothequeCreateur bibliothequeCreateur;

    /**
     * Ouverture de la BD,
     * traitement des transactions et
     * fermeture de la BD.
     */
    public static void main(String argv[]) throws Exception {
        // validation du nombre de paramètres
        if(argv.length < 4) {
            System.out.println("Usage: java Biblio <serveur> <bd> <user> <password> [<fichier-transactions>]");
            System.out.println(Connexion.getServeursSupportes());
            return;
        }
        //test
        try(
            InputStream sourceTransaction = (argv.length > 4 ? Bibliotheque.class.getResourceAsStream("/"
                + argv[4]) : System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction));) {
            if(argv.length > 4) {
                lectureAuClavier = false;
            } else {
                lectureAuClavier = true;
            }
            bibliothequeCreateur = new BibliothequeCreateur(argv[0],
                argv[1],
                argv[2],
                argv[3]);
            traiterTransactions(reader);
        } catch(Exception e) {
            bibliothequeCreateur.rollback();
            e.printStackTrace(System.out);
        } finally {
            bibliothequeCreateur.close();
        }
    }

    /**
     * Traitement des transactions de la biblioth�que
     */
    static void traiterTransactions(BufferedReader reader) throws Exception {
        afficherAide();
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
        System.out.print("> ");
        String transaction = reader.readLine();
        /* echo si lecture dans un fichier */
        if(!lectureAuClavier) {
            System.out.println(transaction);
        }
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
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setTitre(readString(tokenizer));
                livreDTO.setAuteur(readString(tokenizer));
                livreDTO.setDateAcquisition(readDate(tokenizer));
                bibliothequeCreateur.getLivreFacade().acquerir(bibliothequeCreateur.getConnexion(),
                    livreDTO);
                bibliothequeCreateur.commit();
            } else if("vendre".startsWith(command)) {
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                bibliothequeCreateur.getLivreFacade().vendre(bibliothequeCreateur.getConnexion(),
                    livreDTO);

                bibliothequeCreateur.commit();
            } else if("preter".startsWith(command)) {
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readString(tokenizer));
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                PretDTO pretDTO = new PretDTO();
                pretDTO.setLivreDTO(livreDTO);
                pretDTO.setMembreDTO(membreDTO);
                bibliothequeCreateur.getPretFacade().emprunter(bibliothequeCreateur.getConnexion(),
                    pretDTO);
                bibliothequeCreateur.commit();
            } else if("renouveler".startsWith(command)) {
                PretDTO pretDTO = new PretDTO();
                pretDTO.setIdPret(readString(tokenizer));
                bibliothequeCreateur.getPretFacade().renouveler(bibliothequeCreateur.getConnexion(),
                    pretDTO);
                bibliothequeCreateur.commit();
            } else if("retourner".startsWith(command)) {
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                PretDTO pretDTO = new PretDTO();
                pretDTO.setLivreDTO(livreDTO);
                bibliothequeCreateur.getPretFacade().retourner(bibliothequeCreateur.getConnexion(),
                    pretDTO);
                bibliothequeCreateur.commit();
                //La méthode du prof retourne un pret
                //et bibliocreateur.terminer

            } else if("inscrire".startsWith(command)) {
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setNom(readString(tokenizer));
                membreDTO.setTelephone(readString(tokenizer));
                membreDTO.setLimitePret(readString(tokenizer));
                bibliothequeCreateur.getMembreFacade().inscrire(bibliothequeCreateur.getConnexion(),
                    membreDTO);
                bibliothequeCreateur.commit();
            } else if("desinscrire".startsWith(command)) {
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readString(tokenizer));
                bibliothequeCreateur.getMembreFacade().desinscrire(bibliothequeCreateur.getConnexion(),
                    membreDTO);
                bibliothequeCreateur.commit();
            } else if("reserver".startsWith(command)) {
                // Juste pour éviter deux dates de réservation strictement identiques
                Thread.sleep(1);
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readString(tokenizer));
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setLivreDTO(livreDTO);
                reservationDTO.setMembreDTO(membreDTO);
                bibliothequeCreateur.getReservationFacade().placer(bibliothequeCreateur.getConnexion(),
                    reservationDTO);
                bibliothequeCreateur.commit();
            } else if("utiliser".startsWith(command)) {
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readString(tokenizer));
                bibliothequeCreateur.getReservationFacade().utiliser(bibliothequeCreateur.getConnexion(),
                    reservationDTO);
                bibliothequeCreateur.commit();
            } else if("annuler".startsWith(command)) {
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readString(tokenizer));
                bibliothequeCreateur.getReservationFacade().annuler(bibliothequeCreateur.getConnexion(),
                    reservationDTO);
                bibliothequeCreateur.commit();
            } else if("--".startsWith(command)) {
                // ne rien faire; c'est un commentaire
            } else {
                System.out.println("  Transactions non reconnue.  Essayer \"aide\"");
            }
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | InvalidPrimaryKeyException
            | MissingDTOException
            | InvalidDTOClassException
            | InvalidCriterionException
            | InvalidSortByPropertyException
            | ExistingReservationException
            | ExistingLoanException
            | InvalidLoanLimitException
            | InvalidPrimaryKeyRequestException
            | MissingLoanException
            | FacadeException
            | DuplicateDTOException exception) {
            System.out.println("**** "
                + exception.getMessage());
            bibliothequeCreateur.rollback();
        } catch(InterruptedException interruptedException) {
            System.out.println("**** "
                + interruptedException.toString());
            bibliothequeCreateur.rollback();
        }
    }

    /** Affiche le menu des transactions acceptées par le système */
    static void afficherAide() {
        System.out.println();
        System.out.println("Chaque transaction comporte un nom et une liste d'arguments");
        System.out.println("separes par des espaces. La liste peut etre vide.");
        System.out.println(" Les dates sont en format yyyy-mm-dd.");
        System.out.println("");
        System.out.println("Les transactions sont:");
        System.out.println("  aide");
        System.out.println("  exit");
        System.out.println("  acquerir <idLivre> <titre> <auteur> <dateAcquisition>");
        System.out.println("  emprunter <idLivre> <idMembre>");
        System.out.println("  renouveler <idLivre>");
        System.out.println("  retourner <idLivre>");
        System.out.println("  vendre <idLivre>");
        System.out.println("  inscrire <nom> <telephone> <limitePret>");
        System.out.println("  desinscrire <idMembre>");
        System.out.println("  reserver <idLivre> <idMembre>");
        System.out.println("  utiliser <idReservation>");
        System.out.println("  annulerRes <idReservation>");
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
