package robingarner.pdfstitcher;

import java.util.function.BiPredicate;

/**
 * Various alignment options that can be applied to an input file.
 */
public enum PageAlign {
  ODD((n, p) -> PageAlign.isEven(n)),
  EVEN((n, p) -> PageAlign.isOdd(n)),
  AUTO((n, p) -> PageAlign.isEven(p) ? PageAlign.isOdd(n) : false),
  NONE((n, p) -> false);

  private PageAlign(BiPredicate<Integer, Integer> pred) {
    this.pred = pred;
  }

  private final BiPredicate<Integer, Integer> pred;

  /**
   * Do we need to insert an alignment page, given that the next page in
   * the output is 'current' and the document to be merged is 'pages' pages long ?
   * @param current Current page number
   * @param length Document length (in pages)
   * @return {@code true} if an alignment page is required.
   */
  public boolean isRequired(int current, int length) {
    return pred.test(current, length);
  }

  /**
   * Test whether a number is odd.
   * @param n The number to test
   * @return {@code true} if n is odd
   */
  static final boolean isOdd(int n) {
    return (n & 1) == 1;
  }

  /**
   * Test whether a number is odd.
   * @param n The number to test
   * @return {@code true} if n is odd
   */
  static final boolean isEven(int n) {
    return !isOdd(n);
  }
}