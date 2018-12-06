package de.housekatze.media.helper;

import static de.housekatze.media.helper.FileInfoHolderFactory.FILE_INFO;

import java.io.File;
import java.util.Date;

import mp4.util.atom.MoovAtom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The Class MP4InfoHolder.
 * This is a implementation of <code>IFileInfoHolder</code> defined with type <code>MP4_INFO</code> to determine MP4 file information.
 * Stores file type, file encoding date.
 * 
 * @author  housekatze
 */
public class FileInfoHolder  extends AbstractInfoHolder {
    private static Logger log = LoggerFactory.getLogger(FileInfoHolder.class);
    
    private IFileInfoHolder   _infoholder = this;
    private IFileInfoHolder[] infoholders = null;
    
    public IFileInfoHolder[] getInfoholders() {
      return infoholders;
    }

    public void setInfoholders(IFileInfoHolder[] infoholders) {
      this.infoholders = infoholders;
    }

    /** The Constant type. */
    private final static int type = FILE_INFO;  
	
	/** The file type. */
	private int   fileType = FILE;
	
	/** The file size. */
	private long  fileSize = 0;
	
	private String[] nameMask = null;
	
	private MoovAtom moov = null;

    private static long L1904 = -2082851999179L; // 01.01.1904 00:00:00

    public FileInfoHolder(String[] nameMask) {
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
		case MP4_FILE: return "MP4_FILE";
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
	    
		for (IFileInfoHolder holder : infoholders) {
		  if ( matchNameMask(file.getName(), holder.getNameMask()) ) {
            _infoholder = holder;
            _infoholder.scanFileInfo(file);
            return;
		  }
		}
	}

	@Override
	public void appendFileInfo(IFileInfoHolder infoHolder) {
		if (!infoHolder.getClass().getName().equals(this.getClass().getName())) 
			return;
		FileInfoHolder holder = (FileInfoHolder)infoHolder;
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
	  
	  if (infoholders != null) {
	    for (IFileInfoHolder holder : infoholders) {
	      if (matchNameMask(name, holder.getNameMask())) 
	        return true;
	    }
	  }
	  else	  
	   return matchNameMask(name, nameMask);
	  
	  return false;
    }

    @Override
    public String[] getNameMask() {
      return null;
    }

    @Override
    public int[] getTypes() {
      int size = 1;
      if (infoholders != null) {
        size = size + infoholders.length;
      }
      int[] types = new int[size];

      types[0] = getType();
      if (infoholders != null) {
        for (int i=0; i<infoholders.length; i++) {
          types[i+1] = infoholders[i].getType();
        }
      }
      return types;
    }
    
    @Override
    public String getCameraModel() {
      if (_infoholder != this)
        return _infoholder.getCameraModel();
      
      return super.cameraModel;
    }
    @Override
    public int getOrientation() {
      if (_infoholder != this)
        return _infoholder.getOrientation();
      
      return super.orientation;
    }
    @Override
    public Date getExposureDate() {
      if (_infoholder != this)
        return _infoholder.getExposureDate();
      
      return super.exposureDate;
    }
    @Override
    public Date getModifiedDate() {
      if (_infoholder != this)
        return _infoholder.getModifiedDate();
      
      return super.modifiedDate;
    }

    @Override
    public int getType() {
      return type;
    }
}
