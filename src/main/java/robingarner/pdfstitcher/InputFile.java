package robingarner.pdfstitcher;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of an input file, parsed from the project file.
 *
 * Includes Jackson annotations so that we can parse it directly.
 */
public class InputFile {

  /** The input file itself. */
  private File file;

  /** The alignment requirements for this file */
  private PageAlign align = PageAlign.AUTO;

  /** Range of pages from the source */
  private PageRange range = PageRange.ALL;

  /** Include or exclude this file altogether. */
  @JsonProperty("include")
  private boolean include = true;

  @JsonProperty("toc")
  private String tocEntry = null;

  @JsonProperty("composer")
  private String composer = null;

  @JsonProperty("title")
  private String title = null;

  /** Is this a real file or just blank pages for including a spacer. */
  @JsonProperty("spacer")
  private boolean spacer = false;

  /** The page number in the resulting document on which this file starts */
  private int firstPage;

  public InputFile() {

  }

  /**
   * Public constructor.
   * @param file The input file
   */
  public InputFile(File file) {
    this.file = file;
  }

  /**
   * @return The input file
   */
  public File getFile() {
    return file;
  }

  /** @return the alignment */
  public PageAlign getAlign() {
    return align;
  }

  /** Fluent setter for alignment
   *
   * @param align Alignment to use
   * @return {@code this}
   */
  public InputFile align(PageAlign align) {
    this.align = align;
    return this;
  }

  public InputFile setToc(String value) {
    this.tocEntry = value;
    return this;
  }

  public String getToc() {
    return tocEntry == null ? file.getName() : tocEntry;
  }

  public boolean isSpacer() {
    return spacer;
  }

  public boolean isNotSpacer() {
    return !spacer;
  }

  public void setSpacer(boolean spacer) {
    this.spacer = spacer;
  }

  public PageRange getRange() {
    return range;
  }

  public InputFile range(PageRange range) {
    this.range = range;
    return this;
  }

  public boolean isIncluded() {
    return include;
  }

  public InputFile include(boolean include) {
    this.include = include;
    return this;
  }

  public int getFirstPage() {
    return firstPage;
  }

  public void setFirstPage(int firstPage) {
    this.firstPage = firstPage;
  }

  public String getComposer() {
    return composer;
  }

  public void setComposer(String composer) {
    this.composer = composer;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }



  @Override
  public String toString() {
    return "InputFile [file=" + file + ", align=" + align + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((align == null) ? 0 : align.hashCode());
    result = prime * result + ((composer == null) ? 0 : composer.hashCode());
    result = prime * result + ((file == null) ? 0 : file.hashCode());
    result = prime * result + (include ? 1231 : 1237);
    result = prime * result + ((range == null) ? 0 : range.hashCode());
    result = prime * result + (spacer ? 1231 : 1237);
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((tocEntry == null) ? 0 : tocEntry.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    InputFile other = (InputFile) obj;
    if (align != other.align)
      return false;
    if (composer == null) {
      if (other.composer != null)
        return false;
    } else if (!composer.equals(other.composer))
      return false;
    if (file == null) {
      if (other.file != null)
        return false;
    } else if (!file.equals(other.file))
      return false;
    if (include != other.include)
      return false;
    if (range == null) {
      if (other.range != null)
        return false;
    } else if (!range.equals(other.range))
      return false;
    if (spacer != other.spacer)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (tocEntry == null) {
      if (other.tocEntry != null)
        return false;
    } else if (!tocEntry.equals(other.tocEntry))
      return false;
    return true;
  }

}
