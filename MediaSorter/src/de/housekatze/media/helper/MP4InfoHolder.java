package de.housekatze.media.helper;

import static de.housekatze.media.helper.FileInfoHolderFactory.MP4_INFO;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import mp4.util.Mp4Parser;
import mp4.util.atom.MoovAtom;
import mp4.util.atom.MvhdAtom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The Class MP4InfoHolder.
 * This is a implementation of <code>IFileInfoHolder</code> defined with type <code>MP4_INFO</code> to determine MP4 file information.
 * Stores file type, file encoding date.
 * 
 * @author  housekatze
 */
public class MP4InfoHolder  extends AbstractInfoHolder {
    private static Logger log = LoggerFactory.getLogger(MP4InfoHolder.class);
    
	/** The file type. */
	private int   fileType = MP4_FILE;
	
	/** The file size. */
	private long  fileSize = 0;
	
	private String[] nameMask = null;
	
	private MoovAtom moov = null;

//    private Calendar cal = Calendar.getInstance();
    
    private static long L1904 = -2082851999179L; // 01.01.1904 00:00:00

    public MP4InfoHolder(String[] nameMask) {
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
	    
		fileSize = file.length();
        modifiedDate = new Date(file.lastModified());
		
		try {
          Mp4Parser parser = new Mp4Parser(new DataInputStream(new FileInputStream(file)));

          System.out.println(file.getAbsolutePath());
          parser.parseMp4();
          moov = parser.getMoov();

          exposureDate = extractExposureDate();
          
          return;
		} catch (Exception e) {
          e.printStackTrace();
        } 
	}

	private Date extractExposureDate() {
	  try {
    	if (moov == null)
          return null;
    	  
    	  MvhdAtom mvhd = moov.getMvhd();
        if (mvhd == null)
          return null;
        
  //      cal.set(1904, 0, 1, -1, 0, 0); // -1 wegen der +1 der Zeitzone
  //      long m =  (mvhd.getCreationTime() * 1000) + cal.getTime().getTime();
  
        long m =  (mvhd.getCreationTime() * 1000) + L1904;
        return new Date(m);
	  }
	  catch (Exception e) {
	    e.printStackTrace();
	  }
	  return null;
	}
	@Override
	public void appendFileInfo(IFileInfoHolder infoHolder) {
		if (!infoHolder.getClass().getName().equals(this.getClass().getName())) 
			return;
		MP4InfoHolder holder = (MP4InfoHolder)infoHolder;
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
    return MP4_INFO;
  }

  @Override
  public String[] getNameMask() {
    return nameMask;
  }


}
