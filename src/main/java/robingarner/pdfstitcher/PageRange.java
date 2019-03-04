package robingarner.pdfstitcher;

import java.util.function.Predicate;

public class PageRange implements Predicate<Integer>{

  public static final PageRange ALL = new PageRange();

  int low = 1;

  int high = Integer.MAX_VALUE;

  public PageRange() {
  }

  public PageRange(int low, int high) {
    this.low = low;
    this.high = high;
  }

  public PageRange(String range) {
    setRange(range);
  }

  @Override
  public boolean test(Integer t) {
    if (!(low <= t && t <= high)) {
      System.out.printf("Omitting page %d%n", t);
    }
    return low <= t && t <= high;
  }

  public void setRange(String range) {
    if (range.contains("-")) {
      String[] parts = range.split("-");
      if (parts[0].length() > 0) {
        low = Integer.valueOf(parts[0]);
      } else {
        low = 1;
      }
      if (parts.length > 1 && parts[1].length() > 0) {
        high = Integer.valueOf(parts[1]);
      } else {
        high = Integer.MAX_VALUE;
      }
    } else {
      low = high = Integer.valueOf(range);
    }
  }

  public String getRange() {
    if (high == Integer.MAX_VALUE) {
      return String.format("%d-", low);
    }
    return String.format("%d-%d", low, high);
  }

  public int pages() {
    return high - low + 1;
  }

  public int pages(int docPages) {
    int result = docPages;
    if (low > 1) {
      result -= (low - 1);
    }
    if (high < docPages) {
      result -= (docPages - high);
    }
    return result >= 0 ? result : 0;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + high;
    result = prime * result + low;
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
    PageRange other = (PageRange) obj;
    if (high != other.high)
      return false;
    if (low != other.low)
      return false;
    return true;
  }


}
