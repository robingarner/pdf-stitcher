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

  private static final String TOC = "Table Of Contents";

  private final ProjectFile project;

  private final PDDocument outDoc;

  public TOCBuilder(ProjectFile project, PDDocument outDoc) {
    this.project = project;
    this.outDoc = outDoc;
  }

  public PDPage getTOCPage() throws IOException {
    PDPage toc = new PDPage(PDRectangle.A4);
    PDRectangle pageSize = toc.getMediaBox();
    PDFont font = PDType1Font.TIMES_ROMAN;
    final float entryIndent = 2 * ENTRY_FONT_SIZE;
    int tocLines = project.getVisibleInputs().size();

    float stringWidth = font.getStringWidth( TOC );
    float xPos = (pageSize.getWidth() - stringWidth/1000f*TITLE_FONT_SIZE)/3f;
    final float tocPos = (pageSize.getHeight() + tocLines*ENTRY_FONT_SIZE + 2*TITLE_FONT_SIZE)/2f;

    final float titlePos = tocPos + (pageSize.getHeight() - tocPos)/2;

    // Width of the index (ie 1., 2. ...)
    final float indexWidth = font.getStringWidth( "88. " )/1000f * ENTRY_FONT_SIZE;

    float yPos = tocPos;

    try (PDPageContentStream contentStream = new PDPageContentStream(outDoc, toc);) {
      if (project.hasTitle()) {
        contentStream.setFont( font, TITLE_FONT_SIZE );
        contentStream.beginText();
        contentStream.newLineAtOffset(xPos, titlePos);
        contentStream.showText(project.getTitle());
        contentStream.endText();
      }
      contentStream.setFont( font, TITLE_FONT_SIZE );
      contentStream.beginText();
      contentStream.newLineAtOffset(xPos, yPos);
      contentStream.showText("Table Of Contents");
      contentStream.endText();
      yPos -= 2 * TITLE_FONT_SIZE;
      xPos += entryIndent;
      int i = 1;
      contentStream.setFont( font, ENTRY_FONT_SIZE );
      for (String entry : tocEntries()) {
        contentStream.beginText();
        contentStream.newLineAtOffset(xPos, yPos);
        float thisIndexWidth = font.getStringWidth( "88. " )/1000f * ENTRY_FONT_SIZE;
        contentStream.showText(String.format("%2d.", i));
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(xPos+indexWidth, yPos);
        contentStream.showText(String.format("%s", entry));
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