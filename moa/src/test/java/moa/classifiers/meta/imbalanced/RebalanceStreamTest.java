/*
 *   RebalanceStreamTest.java
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 */
package moa.classifiers.meta.imbalanced;

import junit.framework.Test;
import junit.framework.TestSuite;
import moa.classifiers.AbstractMultipleClassifierTestCase;
import moa.classifiers.Classifier;

/**
 * Tests the RebalanceStream classifier.
 * 
 * @author Alessio Bernardo (alessio dot bernardo at polimi dot com) 
 * @version $Revision$
 */
public class RebalanceStreamTest
  extends AbstractMultipleClassifierTestCase {

  /**
   * Constructs the test case. Called by subclasses.
   *
   * @param name 	the name of the test
   */
  public RebalanceStreamTest(String name) {
    super(name);
    this.setNumberTests(1);
  }

  /**
   * Returns the classifier setups to use in the regression test.
   *
   * @return		the setups
   */
  @Override
  protected Classifier[] getRegressionClassifierSetups() {
	  RebalanceStream RBTest = new RebalanceStream();
	  RBTest.minInstanceLimitBatchOption.setValue(10);
	  RBTest.maxInstanceLimitBatchOption.setValue(10);
	  RBTest.minInstanceLimitResetBatchOption.setValue(-1);
	  RBTest.maxInstanceLimitResetBatchOption.setValue(-1);

    return new Classifier[]{
    		RBTest,
    };
  }
  
  /**
   * Returns a test suite.
   *
   * @return		the test suite
   */
  public static Test suite() {
    return new TestSuite(RebalanceStreamTest.class);
  }

  /**
   * Runs the test from commandline.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    runTest(suite());
  }
}
