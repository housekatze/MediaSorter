package de.housekatze.media.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * This class stores the information of a folder or file node. It stores lists of all subfolders and files beneath the current one.
 * It holds the computed informations of subfolders and files by an info holder instance. 
 * 
 * @author  housekatze
 */
public class FileInfoNode {

  /** The parent node. */
  private FileInfoNode         parent        = null;

  /** The info holder. */
  private IFileInfoHolder      infoHolder    = null;

  /** The files list. */
  private Vector<FileInfoNode> files         = new Vector<FileInfoNode>();

  /** The folders list. */
  private Vector<FileInfoNode> folders       = new Vector<FileInfoNode>();

  private boolean              scanSubFolder = true;
  
  public boolean isScanSubFolder() {
    return scanSubFolder;
  }

  /**
   * Instantiates a new file info node.
   * 
   * @param parent the owner of this node
   */
  public FileInfoNode(FileInfoNode parent) {
    this.parent = parent;
  }

  /**
   * Gets the parent node.
   * 
   * @return the parent node
   */
  public FileInfoNode getParent() {
    return parent;
  }

  /**
   * Sets the parent node.
   * 
   * @param parent the new parent node
   */
  public void setParent(FileInfoNode parent) {
    this.parent = parent;
  }

  /**
   * Gets the info holder.
   * 
   * @return the info holder
   */
  public IFileInfoHolder getInfoHolder() {
    return infoHolder;
  }

  /**
   * Sets the info holder.
   * 
   * @param infoHolder the new info holder
   */
  public void setInfoHolder(IFileInfoHolder infoHolder) {
    this.infoHolder = infoHolder;
  }

  /**
   * Gets the holder information as string.
   * 
   * @param prefix the prefix
   * 
   * @return the holder info as string
   */
  public String getInfoAsString(String prefix) {
    return infoHolder == null ? "no info available!" : infoHolder
        .getAsString(prefix);
  }

  /**
   * Gets the files list.
   * 
   * @return the files list as <code>Vector</code>
   */
  public Vector<FileInfoNode> getFiles() {
    return files;
  }

  /**
   * Gets the subfolders list.
   * 
   * @return the folders list as <code>Vector</code>
   */
  public Vector<FileInfoNode> getFolders() {
    return folders;
  }

  /**
   * Append file info holder to the instance info holder instance
   * and to all parent nodes above the current node.
   * 
   * @param childHolder the childinfo  holder
   */
  public void appendFileInfo(IFileInfoHolder childHolder) {
    infoHolder.appendFileInfo(childHolder);
    if (parent != null)
      parent.appendFileInfo(childHolder);
  }

  /**
   * Scans folder and subfolders defined by instance of <code>File</code> and stores it to the info holder instance and appends this information to all nodes above.
   * 
   * @param file the file
   */
  public void scan(File file) {
    infoHolder.scanFileInfo(file);

    if (!file.isDirectory())
      return;

    System.out.print(".");

    String[] filesArray = file.list((FilenameFilter) infoHolder); // accept sorgt für die richtigen Dateien in der Liste
    if (filesArray != null) {
      for (String filename : filesArray) {
        File childFile = new File(file, filename);
        FileInfoNode child = new FileInfoNode(this);
        IFileInfoHolder childHolder = FileInfoHolderFactory
            .getInstance(infoHolder.getTypes());

        if (childFile.isDirectory() && (!scanSubFolder))
          continue;

        child.setInfoHolder(childHolder);
        child.scan(childFile);

        if (childFile.isDirectory())
          folders.add(child);
        else
          files.add(child);

        appendFileInfo(childHolder);
      }
    }
  }

  public void setScanSubFolder(boolean scanSubFolder) {
    this.scanSubFolder = scanSubFolder;
  }

  public List<IFileInfoHolder> getInfoHolderList() {
    List<IFileInfoHolder> list = Collections
        .synchronizedList(new ArrayList<IFileInfoHolder>());

    if (scanSubFolder)
      for (FileInfoNode node : folders)
        list.addAll(node.getInfoHolderList());
    
    for (FileInfoNode node : files)
      list.add(node.getInfoHolder());
    
    return list;
  }
}
