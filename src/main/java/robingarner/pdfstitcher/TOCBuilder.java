package robingarner.pdfstitcher;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TOCBuilder {

  private static final float TITLE_FONT_SIZE = 16.0f;

  private static final float ENTRY_FONT_SIZE = 12.0f;

  private static final String TITLE = "Table Of Contents";

  private final ProjectFile project;

  private final PDDocument outDoc;

  public TOCBuilder(ProjectFile project, PDDocument outDoc) {
    this.project = project;
    this.outDoc = outDoc;
  }

  public PDPage getTOCPage() throws IOException {
    PDPage toc = new PDPage(PDRectangle.A4);

    PDRectangle pageSize = toc.getMediaBox();
    PDFont font = PDType1Font.HELVETICA;
    final float entryIndent = 2 * ENTRY_FONT_SIZE;
    int tocLines = project.getVisibleInputs().size() + 2;
    System.err.printf("tocLines=%d%n",tocLines);

    float stringWidth = font.getStringWidth( TITLE );
    float xPos = (pageSize.getWidth() - stringWidth/1000f*TITLE_FONT_SIZE)/2f;
    float yPos = (pageSize.getHeight() + tocLines*ENTRY_FONT_SIZE)/2f;


    try (PDPageContentStream contentStream = new PDPageContentStream(outDoc, toc);) {
      contentStream.setFont( font, TITLE_FONT_SIZE );
      contentStream.beginText();
      contentStream.newLineAtOffset(xPos, yPos);
      contentStream.showText("Table Of Contents");
      contentStream.endText();
      yPos -= 2 * TITLE_FONT_SIZE;
      xPos += entryIndent;
      int i = 1;
      System.err.printf("tocEntries=%d%n", tocEntries().size());
      contentStream.setFont( font, ENTRY_FONT_SIZE );
      for (String entry : tocEntries()) {
        contentStream.beginText();
        contentStream.newLineAtOffset(xPos, yPos);
        contentStream.showText(String.format("%d. %s", i, entry));
        contentStream.endText();
        yPos -= ENTRY_FONT_SIZE * 1.3;
        i++;
      }
    }
    return toc;
  }

  private List<String> tocEntries() {
    return project.getVisibleInputs().stream().map(InputFile::getToc).collect(toList());
  }

}