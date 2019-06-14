package robingarner.pdfstitcher;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TOCBuilderTest {

  File srcDir = new File("src/test/resources/tocbuilder");
  File dstDir = new File("target/test/resources/tocbuilder");
  @BeforeMethod
  public void beforeMethod() {
  }

  @DataProvider
  public Object[][] tests() {
    return new Object[][] {
      new Object[] { "test1.json" },
      new Object[] { "test2.json" },
    };
  }

  @Test(dataProvider="tests")
  public void testBuildTOC(String partfile) throws Exception {
    ProjectFile project = ProjectFileParserFactory.parse(new File(srcDir, partfile));
    PDDocument outDoc = new PDDocument();
    outDoc.addPage(new TOCBuilder(project, outDoc).getTOCPage());
    outDoc.save(new File(dstDir,partfile+".pdf"));
  }
}
