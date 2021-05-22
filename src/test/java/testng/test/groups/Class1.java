package testng.test.groups;

import org.testng.annotations.Test;

/**
 * The purpose of this class is to know how to work with groups
 * @author Jose Sanjuan Gonzalez
 *
 */
public class Class1 {
	
	@Test(groups = { "regression" })
	public void regressionTestsForClass1() {
		System.out.println("*******************");
		System.out.println("Class: 1");
		System.out.println("Group: regression");
		System.out.println("*******************");
	}

	@Test(groups = { "progression" })
	public void progressionTestsForClass1() {
		System.out.println("*******************");
		System.out.println("Class: 1");
		System.out.println("Group: progression");
		System.out.println("*******************");
	}

	@Test(groups = { "regression", "other" })
	public void regressionAndOtherTestsForClass1() {
		System.out.println("*******************");
		System.out.println("Class: 1");
		System.out.println("Group: regression,other");
		System.out.println("*******************");
	}

}
