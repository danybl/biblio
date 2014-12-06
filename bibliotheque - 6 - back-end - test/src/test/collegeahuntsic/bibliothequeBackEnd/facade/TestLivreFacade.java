
package test.collegeahuntsic.bibliothequeBackEnd.facade;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.collegeahuntsic.bibliothequeBackEnd.exception.TestCaseFailedException;

/**
 *
 * TODO Test case for
 *
 * @author Dany Benoit-Lafond
 */
public class TestLivreFacade extends TestCase {

    //    private final static Log LOGGER = LogFactory.getLog(TestLivreFacade.class);
    //
    private final static String TEST_CASE_TITLE = "Livre facade test case"; //$NON-NLS-1$

    //
    //    private final static String TITRE = "Titre "; //$NON-NLS-1$
    //
    //    private final static String AUTEUR = "Auteur "; //$NON-NLS-1$
    //
    //    private static int sequence = 1;

    /**
     *
     * Default constructor
     *
     * @param The name of the test case
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

    public static Test suite() {
        final TestSuite suite = new TestSuite(TestLivreFacade.TEST_CASE_TITLE);
        suite.addTestSuite(TestCase.class);
        suite.addTestSuite(TestLivreFacade.class);
        return suite;
    }

}
