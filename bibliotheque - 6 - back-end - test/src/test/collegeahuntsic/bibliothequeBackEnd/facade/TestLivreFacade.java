
package test.collegeahuntsic.bibliothequeBackEnd.facade;

import java.sql.Timestamp;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestSuite;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import test.collegeahuntsic.bibliothequeBackEnd.exception.TestCaseFailedException;

/**
 * Test case for {@link ca.qc.collegeahuntsic.bibliothqueBackEnd.facade.interfaces.ILivreFacade}.
 *
 * @author Dany Benoit-Lafond,Cedric Soumpholphakdy, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 */
public class TestLivreFacade extends TestCase {

    private static final Log LOGGER = LogFactory.getLog(TestLivreFacade.class);

    private static final String TEST_CASE_TITLE = "Livre facade test case"; //$NON-NLS-1$

    private static final String TITLE = "Titre "; //$NON-NLS-1$

    private static final String AUTEUR = "Auteur "; //$NON-NLS-1$

    private static int sequence = 1;

    /**
     * Default constructor.
     *
     * @param name The name of the test case
     * @throws TestCaseFailedException If an error occurs
     */
    public TestLivreFacade(String name) throws TestCaseFailedException {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Configures the tests to be executed in this test case. This suite is now visible for a {@link junit.awtui.TestRunner}.<br /><br />
     * The suite contains all test cases for:{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade}<br />
     *
     * <ul>
     * <li>{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade#testAcquerirLivre()}
     * <li>{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade#testGetAllLivres()}
     * <li>{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade#testGetLivre()}
     * <li>{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade#testUpdateLivre()}
     * <li>{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade#testVendreLivre()}
     * </ul>
     *
     * @return Test The tests to be executed in this test case
     */

    public static Test suite() {
        final TestSuite suite = new TestSuite(TestLivreFacade.TEST_CASE_TITLE);
        suite.addTestSuite(TestCase.class);
        suite.addTestSuite(TestLivreFacade.class);
        return suite;
    }

    /**
     * Tests if {@link ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade#acquerirLivre
     * (org.hibernate.Session, ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO)}.
     *
     * @throws TestCaseFailedException If an error occurs
     */
    public void testAcquerirLivre() throws TestCaseFailedException {
        try {
            beginTransaction();
            final LivreDTO livreDTO = new LivreDTO();
            livreDTO.setTitre(TestLivreFacade.TITLE
                + TestLivreFacade.sequence);
            livreDTO.setAuteur(TestLivreFacade.AUTEUR
                + TestLivreFacade.sequence);
            livreDTO.setDateAcquisition(new Timestamp(System.currentTimeMillis()));
            TestLivreFacade.sequence = TestLivreFacade.sequence + 1;
            getLivreFacade().acquerirLivre(getSession(),
                livreDTO);
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFailedException) {
                TestLivreFacade.LOGGER.error(testCaseFailedException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }

    /**
     * Tests if {@link ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade#getLivre(org.hibernate.Session, String)}.
     *
     * @throws TestCaseFailedException If an error occurs
     */

    public void testGetLivre() throws TestCaseFailedException {
        try {
            testAcquerirLivre();

            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getDateAcquisition());
            final String idLivre = livreDTO.getIdLivre();
            final String titre = livreDTO.getTitre();
            final String auteur = livreDTO.getAuteur();
            final Timestamp dateAcquisition = livreDTO.getDateAcquisition();

            commitTransaction();

            beginTransaction();

            livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getDateAcquisition());

            assertEquals(idLivre,
                livreDTO.getIdLivre());
            assertEquals(titre,
                livreDTO.getTitre());
            assertEquals(auteur,
                livreDTO.getAuteur());
            assertEquals(dateAcquisition,
                livreDTO.getDateAcquisition());

            commitTransaction();

            beginTransaction();

            livreDTO = getLivreFacade().getLivre(getSession(),
                "-1");
            assertNull(livreDTO);

            commitTransaction();

        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | InvalidPrimaryKeyException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFailedException) {
                TestLivreFacade.LOGGER.error(testCaseFailedException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }

    /**
     *
     * Tests if {@link ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade#vendreLivre
     * (org.hibernate.Session, ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO)}.
     *
     * @throws TestCaseFailedException If an error occurs
     */
    public void testVendreLivre() throws TestCaseFailedException {
        try {
            testAcquerirLivre();
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getDateAcquisition());
            final String idLivre = livreDTO.getIdLivre();
            livreDTO.getIdLivre();
            getLivreFacade().vendreLivre(getSession(),
                livreDTO);
            commitTransaction();

            beginTransaction();
            livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            assertNull(livreDTO);
            commitTransaction();
        } catch(
            FacadeException
            | InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | InvalidPrimaryKeyException
            | InvalidDTOException
            | ExistingLoanException
            | ExistingReservationException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFailedException) {
                TestLivreFacade.LOGGER.error(testCaseFailedException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }
}
