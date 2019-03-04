package robingarner.pdfstitcher;

import com.google.inject.Guice;

public class Main {

	public static void main(String[] args) {
	    System.setProperty("java.security.egd","file:///dev/./urandom");

	    Guice.createInjector(new MainModule(Main.class, args)).getInstance(PdfStitcher.class).run();

	}

}
