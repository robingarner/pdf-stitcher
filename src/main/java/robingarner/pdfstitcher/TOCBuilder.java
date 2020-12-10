package robingarner.pdfstitcher;

import static java.util.stream.Collectors.toList;
import static robingarner.pdfstitcher.Constants.*;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class TOCBuilder {

  private static final String TOC = "Table Of Contents";

  private final ProjectFile project;

  private final PDDocument outDoc;

  private final PDPage toc;

  public TOCBuilder(ProjectFile project, PDDocument outDoc) {
    this.project = project;
    this.outDoc = outDoc;
    this.toc = new PDPage(PDRectangle.A4);
  }

  public PDPage getTOCPage() {
    return toc;
  }

  public PDPage finishTOCPage() throws IOException {
    PDRectangle pageSize = toc.getMediaBox();
    final float entryIndent = 2 * ENTRY_FONT_SIZE;
    int tocLines = project.getVisibleInputs().size();

    float stringWidth = TOC_FONT.getStringWidth( TOC );
    float xPos = LEFT_MARGIN_OFFSET;
    final float pageNoPos = pageSize.getWidth() - PAGE_NUMBER_OFFSET;
    final float tocPos = (pageSize.getHeight() + tocLines*ENTRY_FONT_SIZE + 2*TITLE_FONT_SIZE)/2f;

    final float titlePos = tocPos + (pageSize.getHeight() - tocPos)/2;

    // Width of the index (ie 1., 2. ...)
    final float indexWidth = TOC_FONT.getStringWidth( "88. " )/1000f * ENTRY_FONT_SIZE;

    float yPos = tocPos;

    try (PDPageContentStream contentStream = new PDPageContentStream(outDoc, toc);) {
      if (project.hasTitle()) {
        contentStream.setFont( TOC_FONT, TITLE_FONT_SIZE );
        contentStream.beginText();
        contentStream.newLineAtOffset(xPos, titlePos);
        contentStream.showText(project.getTitle());
        contentStream.endText();
      }
      contentStream.setFont( TOC_FONT, TITLE_FONT_SIZE );
      contentStream.beginText();
      contentStream.newLineAtOffset(xPos, yPos);
      contentStream.showText("Table Of Contents");
      contentStream.endText();
      yPos -= 2 * TITLE_FONT_SIZE;
      xPos += entryIndent;
      int i = 1;
      contentStream.setFont( TOC_FONT, ENTRY_FONT_SIZE );
      for (InputFile input : project.getVisibleInputs()) {
        String entry = input.getToc();
        contentStream.beginText();
        contentStream.newLineAtOffset(xPos, yPos);
        contentStream.showText(String.format("%2d.", i));
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(xPos+indexWidth, yPos);
        contentStream.showText(String.format("%s", entry));
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(pageNoPos, yPos);
        contentStream.showText(String.format("%3d", input.getFirstPage()));
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