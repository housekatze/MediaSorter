package de.housekatze.media.helper;

import java.io.File;
import java.util.Date;

/**
 * The Interface IFileInfoHolder for access on instances of AbstractInfoHolder sub classes.
 */
public interface IFileInfoHolder {
	
	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public int getType();
	
    public int[] getTypes();

    /**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public File getFile();

    public Date getExposureDate(); 
  
    public Date getModifiedDate();
  
    public int getOrientation();
    
    public void setEstimatedDate(Date estimatedDate);

    public String getCameraModel();

    public Date getEstimatedDate();
    /**
	 * Scan file info by instance of <code>File</code>.
	 * 
	 * @param file the file
	 */
	public void   scanFileInfo(File file);
	
	/**
	 * Appends info holder.
	 * Merges existing informations with the new one (generates summaries, averages and calculations).<br>
	 * Helpful for "folder" nodes. 
	 * 
	 * @param infoHolder the new <code>IFileInfoHolder</code> instance
	 */
	public void   appendFileInfo(IFileInfoHolder infoHolder);
	
	/**
	 * Gets the holder information formatted as string.
	 * 
	 * @param prefix the prefix
	 * 
	 * @return the holder info as string
	 */
	public String getAsString(String prefix);

  public boolean accept(File parentFile, String name);

  public String[] getNameMask();
}
