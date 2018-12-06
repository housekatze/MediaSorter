package de.housekatze.media.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

/**
 * The Class AbstractInfoHolder defines abstract methods and basic functionality for 
 * classes defining an holder to store and compute file specific information.<br>
 * <br>
 * @see FilenameFilter
 * 
 * @author  housekatze
 */
public abstract class AbstractInfoHolder  implements FilenameFilter, IFileInfoHolder {
	
  /** The Constant UNKNOWN. */
  public final static int UNKNOWN      = -1;
  
  public final static int FILE = 0;
  
  /** The Constant DIRECTORY. */
  public final static int DIRECTORY    =  1;

  /** The Constant MP4_FILE. */
  public final static int MP4_FILE  =  2;

  /** The Constant MTS_FILE. */
  public final static int MTS_FILE  =  3;

  /** The Constant MOV_FILE. */
  public final static int MOV_FILE  =  4;

  /** The Constant MOV_FILE. */
  public final static int AVI_FILE  =  5;

  /** The Constant MKV_FILE. */
  public final static int MKV_FILE  =  6;

  /** The file name. */
	protected File file = null;
	
	protected Date modifiedDate = null;
	protected Date exposureDate = null; 
    protected Date estimatedDate = null; 
    protected String cameraModel = null;
    public String getCameraModel() {
      return cameraModel;
    }

    public Date getEstimatedDate() {
      return estimatedDate;
    }

    public void setEstimatedDate(Date estimatedDate) {
      this.estimatedDate = estimatedDate;
    }

  protected int orientation = 1;
	
    public int getOrientation() {
      return orientation;
    }
  
	public Date getExposureDate() {
	  return exposureDate;
    }
  
    public Date getModifiedDate() {
      return modifiedDate;
    }
  

  /**
	 * Gets the info holder type.
	 * 
	 * @return the type
	 */
	public abstract int getType();
	
	/**
	 * Scan info from file.
	 * 
	 * @param file the <code>File</code> instance
	 */
	public abstract void scanFileInfo(File file);
	
	/**
	 * Appends info holder.
	 * Merges existing informations with the new one (generates summaries, averages and calculations).<br>
	 * Helpful for "folder" nodes. 
	 * 
	 * @param infoHolder the new <code>IFileInfoHolder</code> instance
	 */
	public abstract void appendFileInfo(IFileInfoHolder infoHolder);
	
	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Sets the file name.
	 * 
	 * @param fileName the new file name
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * Tests if a specified file should be included in a file list.
	 * 
	 * @param dir the path
	 * @param name the file name
	 * 
	 * @return true, if successful
	 */
	public boolean accept(File dir, String name) {
		return true;
	}
	
	
	/**
	 * Gets the informations as formated string.
	 * 
	 * @param prefix the line prefix
	 * 
	 * @return the information string
	 */
	public String getAsString(String prefix) {
		return null;
	}
	
    protected boolean matchNameMask(String name, String[] nameMask) {
	      if (nameMask == null)
	        return false;
	      
	      for (int i = 0; i< nameMask.length; i++) {
	        if (name.toLowerCase().endsWith(nameMask[i]))
	          return true;
	      }
	      
	      return false;
    }

    public int[] getTypes() {
      // TODO Auto-generated method stub
      return null;
    }
}
