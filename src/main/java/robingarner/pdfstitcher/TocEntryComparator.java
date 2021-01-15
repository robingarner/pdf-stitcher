package robingarner.pdfstitcher;

import java.util.Comparator;

public class TocEntryComparator implements Comparator<InputFile> {

  private final Comparator<InputFile> byComposer =
      Comparator.<InputFile, String>comparing(InputFile::getComposer, Comparator.nullsLast(Comparator.naturalOrder()));

  private final Comparator<InputFile> byTitle =
      Comparator.comparing(InputFile::getTitle, Comparator.nullsLast(Comparator.naturalOrder()));

  private final Comparator<InputFile> byToc =
      Comparator.comparing(InputFile::getToc, Comparator.nullsLast(Comparator.naturalOrder()));

  private final Comparator<InputFile> c = byComposer
      .thenComparing(byTitle)
      .thenComparing(byToc);


  @Override
  public int compare(InputFile o1, InputFile o2) {
    return c.compare(o1, o2);
  }

}
