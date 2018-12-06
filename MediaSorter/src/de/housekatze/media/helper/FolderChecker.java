package de.housekatze.media.helper;

import static de.housekatze.media.helper.FileInfoHolderFactory.MP4_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.PICTURE_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.UNKNOWN;
import static de.housekatze.media.helper.FileInfoHolderFactory.FILE_INFO;

import java.io.File;

import de.housekatze.media.helper.FileInfoHolderFactory;
import de.housekatze.media.helper.FileInfoNode;

/**
 * Class FolderChecker.<br>
 * <br>
 * Die Klasse erlaubt es ein Dateisystem-Verzeichnis anhand einer Dateityp-Definition<br> 
 * zu durchsuchen und als Baumliste von <code>FileInfoNode</code> Klassen darzustellen.<br>
 * <br>
 * Jeder Knoten enthält eine entsprechend der Dateityp-Definition festgelegte Info-Struktur (<code>IFileInfoHolder</code>).<br>
 * Jede Info-Struktur-Instanz enthält die kumulierten Informationen aller Unterknoten (Unterverzeichnisse und Dateien).<br>
 * <br>
 * @author  housekatze
 */
public class FolderChecker {

  /** The info holder type. */
  private int[]        infoHolderTypes = {UNKNOWN};

  /** The root node */
  private FileInfoNode root           = new FileInfoNode(null);

  /** The prefix for printing formatted information string */
  private String       prefix         = "";

  private boolean      scanSubFolder  = true;

  public boolean isScanSubFolder() {
    return scanSubFolder;
  }

  public void setScanSubFolder(boolean scanSubFolder) {
    this.scanSubFolder = scanSubFolder;
  }

  /**
   * Gets the root node.
   * 
   * @return the root node
   */
  public FileInfoNode getRootNode() {
    return root;
  }

  /**
   * Gets the info holder type.
   * 
   * @return the info holder type
   */
  public int[] getInfoHolderTypes() {
    return infoHolderTypes;
  }

  /**
   * Sets the info holder type.
   * 
   * @param infoHolderType the new info holder type
   */
  public void setInfoHolderTypes(int[] infoHolderTypes) {
    this.infoHolderTypes     = infoHolderTypes;
  }

  /**
   * Scan folder and sub-folders defined by folder name.
   * 
   * @param folderName the folder name
   */
  public void scan(String folderName) {
    scan(new File(folderName));
  }

  /**
   * Scan folder and sub-folders defined by instance of <code>File</code>.
   * 
   * @param file the file
   */
  public void scan(File file) {
    root.setInfoHolder(FileInfoHolderFactory.getInstance(infoHolderTypes));
    root.setScanSubFolder(scanSubFolder);
    root.scan(file);
  }

  /**
   * Prints all node infos starting by specified node.
   * 
   * @param node the starting <code>FileInfoNode</code> instance
   */
  private void printNodeInfos(FileInfoNode node) {
    String oldtab = prefix;
    prefix += "  ";
    for (FileInfoNode child : node.getFolders()) {
      System.out.println(child.getInfoAsString(prefix));
      printNodeInfos(child);
    }
    for (FileInfoNode child : node.getFiles()) {
      System.out.println(child.getInfoAsString(prefix));
    }
    prefix = oldtab;
  }

  /**
   * The main method.<br>
   * <br>
     *  FolderChecker usage<br>
   *	de.lineas.tools.FolderChecker --directory|-d [startdir] [--type|-t [filetype]]<br>
   *	   --directory or -d   : directory to scan for files including all subdirectories<br>
   *	   --type      or -t   : type of files to scan (default is PICTURE)<br>
   *	                 values: PICTURE (default) | PICTURE_CLASSIC)<br>
   * <br>
   * @param args the args
   */
  public static void main(String[] args) {
    String startFolder = null;
    String type = "MP4";
    int iType = -1;
    for (int aCnt = 0; aCnt < args.length; aCnt++) {
      if (args[aCnt].startsWith("--directory") || (args[aCnt].startsWith("-d"))) {
        aCnt++;
        startFolder = args[aCnt];
      }
      if (args[aCnt].startsWith("--type") || (args[aCnt].startsWith("-t"))) {
        aCnt++;
        type = args[aCnt];
      }
    }

    FolderChecker checker = new FolderChecker();
    if (type.equalsIgnoreCase("PICTURE"))
      iType = PICTURE_INFO;
    else if (type.equalsIgnoreCase("MP4"))
      iType = MP4_INFO;
    else {
      usage();
      System.exit(0);
    }

    File startFile = null;
    if ((startFolder == null) || ((startFile = new File(startFolder)) == null)
        || (!startFile.exists())) {
      usage();
      System.exit(0);
    }
    System.out.println("");
    System.out.print("scan running...");
    checker.setInfoHolderTypes(new int[]{FILE_INFO, iType});
    checker.scan(startFile);
    System.out.println("");
    System.out.println("...finished");

    System.out.println("printing...");
    System.out.println(checker.getRootNode().getInfoAsString(""));
    checker.printNodeInfos(checker.getRootNode());
    System.out.println("...end");
  }

  /**
   * Outputs the decription using this class
   */
  private static void usage() {
    System.out.println("FolderChecker usage:");
    System.out
        .println("\tde.lineas.tools.FolderChecker --directory|-d [startdir] [--type|-t [filetype]]");
    System.out
        .println("\t\t--directory or -d   : directory to scan for files including all subdirectories");
    System.out
        .println("\t\t--type      or -t   : type of files to scan (default is MP4)");
    System.out.println("\t\t              values: MP4 (default) | PICTURE)");
  }
}
