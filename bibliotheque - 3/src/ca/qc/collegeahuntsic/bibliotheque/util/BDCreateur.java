
package ca.qc.collegeahuntsic.bibliotheque.util;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.BDCreateurException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ConnexionException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *<pre>
 *
 *Permet de créer la BD utilisée par Biblio.java.
 *
 *Paramètres:0- serveur SQL
 *           1- bd nom de la BD
 *           2- user id pour �tablir une connexion avec le serveur SQL
 *           3- mot de passe pour le user id
 *</pre>
 */
class BDCreateur {
    public static void main(String args[]) throws BDCreateurException,
    ConnexionException {

        try {
            if(args.length < 3) {
                System.out.println("Usage: java CreerBD <serveur> <bd> <user> <password>");
                return;
            }

            Connexion cx = new Connexion(args[0],
                args[1],
                args[2],
                args[3]);

            try(
                Statement stmt = cx.getConnection().createStatement();) {
                //Commentaire
                stmt.executeUpdate("DROP TABLE membre CASCADE CONSTRAINTS");
                stmt.executeUpdate("CREATE TABLE membre (idMembre   NUMBER    CHECK (idMembre > 0), "
                    + " nom        VARCHAR(100) NOT NULL,"
                    + " telephone  NUMBER(10),"
                    + "limitePret NUMBER(2)    CHECK (limitePret > 0 AND limitePret <= 10),"
                    + "nbPret     NUMBER(2)    DEFAULT 0 CHECK (nbpret >= 0),"
                    + "CONSTRAINT cleMembre    PRIMARY KEY (idMembre),"
                    + "CONSTRAINT limiteNbPret CHECK (nbPret <= limitePret))");

                stmt.executeUpdate("DROP TABLE livre CASCADE CONSTRAINTS");
                stmt.executeUpdate("CREATE TABLE livre (idLivre         NUMBER(3)    CHECK (idLivre > 0),"
                    + "titre           VARCHAR(100) NOT NULL,"
                    + "auteur          VARCHAR(100) NOT NULL,"
                    + " dateAcquisition TIMESTAMP    NOT NULL,"
                    + " CONSTRAINT      cleLivre     PRIMARY KEY (idLivre))");

                stmt.executeUpdate("DROP TABLE pret CASCADE CONSTRAINTS");
                stmt.executeUpdate("CREATE TABLE pret (idPret     NUMBER(3)  CHECK (idPret > 0),"
                    + "idMembre   NUMBER  CHECK (idMembre > 0),"
                    + "idLivre    NUMBER  CHECK (idLivre > 0),"
                    + "datePret   TIMESTAMP,"
                    + "dateRetour TIMESTAMP,"
                    + "CONSTRAINT clePrimairePret PRIMARY KEY (idPret),"
                    + "CONSTRAINT refPretMembre   FOREIGN KEY (idMembre) REFERENCES membre (idMembre) ON DELETE CASCADE,"
                    + "CONSTRAINT refPretLivre    FOREIGN KEY (idLivre)  REFERENCES livre (idLivre)   ON DELETE CASCADE)");

                stmt.executeUpdate("DROP TABLE reservation CASCADE CONSTRAINTS");
                stmt.executeUpdate("CREATE TABLE reservation (idReservation   NUMBER(3)  CHECK (idReservation > 0),"
                    + " idMembre        NUMBER  CHECK (idMembre > 0),"
                    + "idLivre         NUMBER  CHECK (idLivre > 0),"
                    + "dateReservation TIMESTAMP,"
                    + " CONSTRAINT      clePrimaireReservation  PRIMARY KEY (idReservation),"
                    + " CONSTRAINT      cleEtrangereReservation UNIQUE (idMembre, idLivre),"
                    + " CONSTRAINT      refReservationMembre    FOREIGN KEY (idMembre) REFERENCES membre (idMembre) ON DELETE CASCADE,"
                    + "CONSTRAINT      refReservationLivre     FOREIGN KEY (idLivre)  REFERENCES livre (idLivre)   ON DELETE CASCADE)");
                stmt.executeUpdate("CREATE SEQUENCE SEQ_ID_MEMBRE      START WITH 1 INCREMENT BY 1");
                stmt.executeUpdate("CREATE SEQUENCE SEQ_ID_LIVRE       START WITH 1 INCREMENT BY 1");
                stmt.executeUpdate("CREATE SEQUENCE SEQ_ID_PRET        START WITH 1 INCREMENT BY 1");
                stmt.executeUpdate("CREATE SEQUENCE SEQ_ID_RESERVATION START WITH 1 INCREMENT BY 1");
                cx.fermer();
            }
        } catch(SQLException sqlex) {
            throw new BDCreateurException(sqlex);
        }

    }
}
