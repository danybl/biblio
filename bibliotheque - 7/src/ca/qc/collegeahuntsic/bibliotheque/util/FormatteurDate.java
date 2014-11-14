
package ca.qc.collegeahuntsic.bibliotheque.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Permet de valider le format d'une date en YYYY-MM-DD et de la convertir en un
 * objet Date.
 *@author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa.
 */
public final class FormatteurDate {
    private static final String FORMAT_DATE = "yyyy-MM-dd";

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(FormatteurDate.FORMAT_DATE);

    static {
        FormatteurDate.SIMPLE_DATE_FORMAT.setLenient(false);
    }

    /**
     * Constructeur par défaut.
     */
    private FormatteurDate() {
        //Constructeur par défaut
    }

    /**
     * Convertit une chaîne de caractères en {@link java.sql.Timestamp} selon le format <code>yyyy-MM-dd</code>.
     *
     * @param date La chaîne de caractères
     * @return Le {@link java.sql.Timestamp} issu de la conversion
     * @throws ParseException Si la chaîne de caractères n'est pas formatée correctement
     */
    public static Timestamp timestampValue(String date) throws ParseException {
        final Date dateFormatee = FormatteurDate.SIMPLE_DATE_FORMAT.parse(date);
        final Timestamp timestamp = new Timestamp(dateFormatee.getTime());
        return timestamp;
    }

    /**
     * Convertit un {@link java.sql.Timestamp} en une chaîne de caractères selon le format <code>yyyy-MM-dd</code>.
     *
     * @param timestamp Le {@link java.sql.Timestamp}
     * @return La chaîne de caractères issue de la conversion
     * @throws ParseException Si le {@link java.sql.Timestamp} n'est pas formaté correctement
     */
    public static String stringValue(Timestamp timestamp) throws ParseException {
        final Date date = new Date(timestamp.getTime());
        final String dateFormatee = FormatteurDate.SIMPLE_DATE_FORMAT.format(date);
        return dateFormatee;
    }
}
