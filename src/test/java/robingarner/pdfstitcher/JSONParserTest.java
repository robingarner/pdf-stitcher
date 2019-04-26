package robingarner.pdfstitcher;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JSONParserTest {

  @DataProvider(indices=0)
  Object[][] inputs() {
    return new Object[][] {
      { "{ }" , new ProjectFile() },
      // Test the defaults for all optional values
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\" } ] }",
        new ProjectFile(new InputFile(new File("input.txt"))
            .align(PageAlign.AUTO)
            .range(PageRange.ALL)
            .include(true))},

      { "{ \"inputs\" : [ { \"file\" : \"input.txt\", \"align\" : \"ODD\" } ] }",
              new ProjectFile(new InputFile(new File("input.txt")).align(PageAlign.ODD))},
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\" } ] }",
          new ProjectFile(new InputFile(new File("input.txt")).align(PageAlign.AUTO))},
      { "{ \"baseDir\" : \"/tmp\","
          + "\"inputs\" : [ { \"file\" : \"input.txt\" } ] }",
          new ProjectFile(new InputFile(new File("input.txt")))},
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\", \"range\" : \"1-3\" } ] }",
              new ProjectFile(new InputFile(new File("input.txt")).range(new PageRange(1,3)))},
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\", \"include\" : \" false\" } ] }",
              new ProjectFile(new InputFile(new File("input.txt")).include(false))},
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\", \"include\" : \" false\" } ] }",
                new ProjectFile(new InputFile(new File("input.txt")).include(false))},
      { "{ \"toc\" : \"true\" }" , new ProjectFile().setToc(true) },
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\", \"toc\" : \"TOC Entry\" } ] }",
              new ProjectFile(new InputFile(new File("input.txt"))
                  .setToc("TOC Entry")) },
    };
  }

  @Test(dataProvider="inputs")
  public void parse(String input, ProjectFile expected) throws Exception {
    ProjectFile p = new JSONParser(new ByteArrayInputStream(input.getBytes())).parse();
    Assert.assertEquals(p, expected);
  }
}
