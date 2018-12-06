package de.housekatze.media.helper;

import static de.housekatze.media.helper.FileInfoHolderFactory.MOV_INFO;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The Class MP4InfoHolder.
 * This is a implementation of <code>IFileInfoHolder</code> defined with type <code>MP4_INFO</code> to determine MP4 file information.
 * Stores file type, file encoding date.
 * 
 * @author  housekatze
 */
public class MOVInfoHolder  extends AbstractInfoHolder {
    private static Logger log = LoggerFactory.getLogger(MOVInfoHolder.class);
    
	/** The file type. */
	private int   fileType = MOV_FILE;
	
	/** The file size. */
	private long  fileSize = 0;
	
	private String[] nameMask = null;
	
    public MOVInfoHolder(String[] nameMask) {
	    this.nameMask = nameMask;
	}
	
	/**
	 * Gets the file type.
	 * 
	 * @return the file type
	 */
	public int getFileType() {
		return fileType;
	}
	
	/**
	 * Gets the file type as string.
	 * 
	 * @return the file type as string
	 */
	public String getFileTypeAsString() {
	   switch (fileType) {
		case DIRECTORY: return "DIRECTORY";
		case MOV_FILE: return "MOV_FILE";
		default: return "UNKNOWN";
		}
	}

	/**
	 * Sets the file type.
	 * 
	 * @param fileType the new file type
	 */
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	/**
	 * Gets the file size.
	 * 
	 * @return the file size
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * Sets the file size.
	 * 
	 * @param fileSize the new file size
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public void scanFileInfo(File file) {
	    this.file = file;
		if (file.isDirectory()) {
			fileType = DIRECTORY;
			return;
		}
	    
		fileSize = file.length();
        modifiedDate = new Date(file.lastModified());
		
	}

	@Override
	public void appendFileInfo(IFileInfoHolder infoHolder) {
		if (!infoHolder.getClass().getName().equals(this.getClass().getName())) 
			return;
		MOVInfoHolder holder = (MOVInfoHolder)infoHolder;
		if (holder.fileType == UNKNOWN)
			return;
	}

	
	@Override
     /**
     * Tests if a specified file should be included in a file list.
     * 
     * @param dir the path
     * @param name the file name
     * 
     * @return true, if successful
     */
    public boolean accept(File dir, String name) {
	  if (new File(dir, name).isDirectory())
	    return true;
	  
	  if (nameMask == null)
        return false;
	  
      for (int i = 0; i< nameMask.length; i++) {
        if (name.toLowerCase().endsWith(nameMask[i]))
          return true;
      }
	  
	  
	  return false;
    }

  @Override
  public int getType() {
    return MOV_INFO;
  }

  @Override
  public String[] getNameMask() {
    return nameMask;
  }


}
