package robingarner.pdfstitcher;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MainTest {

  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }


  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

  @DataProvider
  public Object[][] tests() {
    return new Object[][] {
      new Object[] { "src/test/resources/test1/partfile.json" },
      { "src/test/resources/test3/partfile.json" },
      { "src/test/resources/test4/partfile.json" },
      { "src/test/resources/testTOC1/partfile.json" },
      { "src/test/resources/testTOC2/partfile.json" },
      { "src/test/resources/testTOC3/partfile.json" },
    };
  }

  @Test(dataProvider="tests")
  public void main(String partfile) {
    Main.main(new String[] { partfile });
  }

  @DataProvider
  public Object[][] badTests() {
    return new Object[][] {
      new Object[] { "src/test/resources/test2/partfile.json" },
    };
  }

  @Test(dataProvider="badTests", expectedExceptions=RuntimeException.class)
  public void failingTests(String partfile) {
    Main.main(new String[] { partfile });
  }
}
