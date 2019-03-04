package robingarner.pdfstitcher;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import robingarner.pdfstitcher.InputFile.PageAlign;

public class JSONParserTest {

  @DataProvider
  Object[][] inputs() {
    return new Object[][] {
      { "{ }" , emptyList() },
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\", \"align\" : \"AUTO\" } ] }",
        asList(new InputFile(new File("input.txt"), PageAlign.AUTO))},
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\" } ] }",
          asList(new InputFile(new File("input.txt"), PageAlign.AUTO))},
      { "{ \"baseDir\" : \"/tmp\","
          + "\"inputs\" : [ { \"file\" : \"input.txt\" } ] }",
            asList(new InputFile(new File("input.txt"), PageAlign.AUTO))},
      { "{ \"inputs\" : [ { \"file\" : \"input.txt\", \"range\" : \"1-3\" } ] }",
            asList(new InputFile(new File("input.txt"), PageAlign.AUTO).setRange(new PageRange(1,3)))},
    };
  }

  @Test(dataProvider="inputs")
  public void parse(String input, List<InputFile> expected) throws Exception {
    ProjectFile p = new JSONParser(new ByteArrayInputStream(input.getBytes())).parse();
    Assert.assertEquals(p.getInputs(), expected);
  }
}
