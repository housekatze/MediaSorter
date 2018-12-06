package de.housekatze.media.helper;

import static de.housekatze.media.helper.FileInfoHolderFactory.PICTURE_INFO;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

/**
 * The Class PictureInfoClassic.
 * This is a implementation of <code>IFileInfoHolder</code> defined with type <code>PICTURE_INFO_CLASSIC</code> to determine picture file information like GIF and JPEG.
 * Stores file type, file size, picture size and picture compression.
 * 
 * @author  housekatze
 */
public class PictureInfoHolder  extends AbstractInfoHolder {
    
    /** The Constant type. */
    private final static int type = PICTURE_INFO;  
	
	/** The Constant UNKNOWN. */
	public final static int UNKNOWN      = -1;
	
	/** The Constant DIRECTORY. */
	public final static int DIRECTORY    =  0;
	
	/** The Constant GIF_PICTURE. */
	public final static int GIF_PICTURE  =  1;
    
    /** The Constant JPEG_PICTURE. */
    public final static int JPEG_PICTURE =  2;

	/** The file type. */
	private int   fileType = UNKNOWN;
	
	/** The file size. */
	private long  fileSize = 0;
	
	/** The picture size. */
	private int[] pictureSize = new int[]{0, 0};
	
	/** The picture compression. */
	private double  pictureCompression = 0;
	
    protected long[] sum = new long[]{0,0,0};

    private String[] nameMask = null;
	    
	public PictureInfoHolder(String[] nameMask) {
	   this.nameMask = nameMask;
	}

	/**
	 * Gets the picture size.
	 * 
	 * @return the picture size
	 */
	public int[] getPictureSize() {
		return pictureSize;
	}

	/**
	 * Sets the picture size.
	 * 
	 * @param pictureSize the new picture size
	 */
	public void setPictureSize(int[] pictureSize) {
		this.pictureSize = pictureSize;
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
		case GIF_PICTURE: return "GIF_PICTURE";
		case JPEG_PICTURE: return "JPEG_PICTURE";
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

	/**
	 * Gets the picture compression.
	 * 
	 * @return the picture compression
	 */
	public double getPictureCompression() {
		return pictureCompression;
	}

	/**
	 * Sets the picture compression.
	 * 
	 * @param pictureCompression the new picture compression
	 */
	public void setPictureCompression(long pictureCompression) {
		this.pictureCompression = pictureCompression;
	}

	/* (non-Javadoc)
	 * @see de.lineas.tools.helper.AbstractInfoHolder#getAsString(java.lang.String)
	 */
	@Override
	public String getAsString(String prefix) {
		String fmtStr1 = String.format("%stype: %s, name: '%s'", prefix, getFileTypeAsString(), file);
		String fmtStr2 = String.format("\n"+prefix+"length:      %d byte\n"+
						                    prefix+"size:        %d px x %d px\n"+
						                    prefix+"compression: %.2f %%", 
						                       fileSize, pictureSize[0], pictureSize[1], pictureCompression);
		String fmtStr3 = "\n"+prefix+"-----------------";
		
		return fmtStr1 + ((fileType == UNKNOWN)?"":fmtStr2)+fmtStr3;	
	}

	/* (non-Javadoc)
	 * @see de.lineas.tools.helper.AbstractInfoHolder#scanFileInfo(java.io.File)
	 */
	@Override
	public void scanFileInfo(File file) {
        this.file = file;
		if (file.isDirectory()) {
			fileType = DIRECTORY;
			return;
		}
	    
		fileSize = file.length();
        modifiedDate = new Date(file.lastModified());
        
	    // nat. nur die Sachen, die das Dateiformat auch hat
        switch (fileType = getFileType(file)) {
	      case GIF_PICTURE:   pictureSize = getGIFSize(file); break;
	      case JPEG_PICTURE:  
	        try {
	          // -1, wenn UNKNOWN
	          orientation = extractPictureOrientation();
	          exposureDate = extractExposureDate();
              pictureSize = getJPEGSize(file);
              cameraModel = extractCameraModel();
	        } catch (Exception e) {
	          e.printStackTrace();
	        } 
	      break;
	    }
	    if ((pictureSize[0] > 0) && (pictureSize[1] > 0))
	    	pictureCompression = (((double)fileSize * 100) / ((double)pictureSize[0] * (double)pictureSize[1] * 3));
	}

    private String extractCameraModel() {
      String result = null;
      try {
          Metadata metadata = JpegMetadataReader.readMetadata(file);
          boolean tagsValid = false;

          ExifDirectory edir = (ExifDirectory)metadata.getDirectory(ExifDirectory.class);
          if (edir != null)
          {
              tagsValid = edir.containsTag(ExifDirectory.TAG_MODEL);
          }
          if (tagsValid) {
              result = edir.getString(ExifDirectory.TAG_MODEL);
              return result;
          }
      } catch (JpegProcessingException e) {
        e.printStackTrace();
      }
      return result;
    }

    private int extractPictureOrientation() {
      int result = -1;
      try {
          Metadata metadata = JpegMetadataReader.readMetadata(file);
          boolean tagsValid = false;

          ExifDirectory edir = (ExifDirectory)metadata.getDirectory(ExifDirectory.class);
          if (edir != null)
          {
              tagsValid = edir.containsTag(ExifDirectory.TAG_ORIENTATION);
          }
          if (tagsValid) {
              result = edir.getInt(ExifDirectory.TAG_ORIENTATION);
              return result;
          }
      } catch (MetadataException e) {
          e.printStackTrace();
      } catch (JpegProcessingException e) {
        e.printStackTrace();
      }
      return result;
    }

    private Date extractExposureDate() {
      Date result = null;
      try {
          Metadata metadata = JpegMetadataReader.readMetadata(file);
          boolean tagsValid = false;

//       // iterate through metadata directories
//          Iterator directories = metadata.getDirectoryIterator();
//          if(directories != null) {
//              while (directories.hasNext()) {
//                  Directory directory = (Directory)directories.next();
//                  // iterate through tags and print to System.out
//                  Iterator tags = directory.getTagIterator();
//                  while (tags.hasNext()) {
//                      Tag tag = (Tag)tags.next();
//                      // use Tag.toString()
//                      System.out.println(tag+"<br>");
//                      
//                  }           
//              }
//          }          
          
          ExifDirectory edir = (ExifDirectory)metadata.getDirectory(ExifDirectory.class);
          if (edir != null)
          {
              tagsValid = edir.containsTag(ExifDirectory.TAG_DATETIME_ORIGINAL);
          }
          if (tagsValid) {
              result = edir.getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
              return result;
          }
      } catch (MetadataException e) {
          e.printStackTrace();
      } catch (JpegProcessingException e) {
        e.printStackTrace();
      }
      return result;
    }

	/**
	 * Gets the file type.
	 * 
	 * @param file the <code>File</code> instance
	 * 
	 * @return the file type
	 */
	private int getFileType(File file) {
        try {
			String mime = file.toURI().toURL().openConnection().getContentType();
			if ((mime.toLowerCase().endsWith("jpeg") || (mime.toLowerCase().endsWith("jpg"))))
				return JPEG_PICTURE;	
			
			if (mime.toLowerCase().endsWith("gif"))
				return GIF_PICTURE;	

        } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return UNKNOWN;
	}

	/**
	 * Gets the GIF picture size.
	 * 
	 * @param file the <code>File</code> instance
	 * 
	 * @return the GIF picture size
	 */
	private int [] getGIFSize (File file)
	{
	    int [] result = new int []{0, 0};
	    FileInputStream       fis = null;
	    ByteArrayInputStream  bin = null;
	    BufferedInputStream   bis = null;
	    DataInputStream       dis = null;
	    try {
          fis = new FileInputStream (file);
	      bis = new BufferedInputStream (fis);
	      dis = new DataInputStream (bis);

	      dis.skip (6);
	      int wLow  = dis.readUnsignedByte ();
	      int wHigh = dis.readUnsignedByte ();
	      int hLow  = dis.readUnsignedByte ();
	      int hHigh = dis.readUnsignedByte ();

	      result [0] = (wHigh << 8) | (wLow & 0xFF);
	      result [1] = (hHigh << 8) | (hLow & 0xFF);
	      return result; 
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	      try {
	        if (dis != null) dis.close ();
	        if (bis != null) bis.close ();
	        if (bin != null) bin.close ();
	      } catch (Exception e) {
	      }
	    }
	    return result;
	}
    
	/**
	 * Gets the JPEG size.
	 * 
	 * @param file the <code>File</code> instance
	 * 
	 * @return the JPEG picture size
	 */
	private int[] getJPEGSize(File file)
	{
        int [] result = new int []{0, 0};
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(file);
			boolean tagsValid = false;
			JpegDirectory jdir = (JpegDirectory)metadata.getDirectory(JpegDirectory.class);
			if (jdir != null)
			{
			  tagsValid = (jdir.containsTag(JpegDirectory.TAG_JPEG_IMAGE_HEIGHT) && jdir.containsTag(JpegDirectory.TAG_JPEG_IMAGE_WIDTH));
			}
			if (tagsValid) {
		        result [0] = jdir.getImageWidth();
			    result [1] = jdir.getImageHeight();
			    return result;
			}
			
			ExifDirectory edir = (ExifDirectory)metadata.getDirectory(ExifDirectory.class);
			if (edir != null)
			{
			  tagsValid = (edir.containsTag(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT) && edir.containsTag(ExifDirectory.TAG_EXIF_IMAGE_WIDTH));
			}
			if (tagsValid) {
		        result [0] = edir.getInt(ExifDirectory.TAG_EXIF_IMAGE_WIDTH);
			    result [1] = edir.getInt(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT);
			    return result;
			}
		} catch (JpegProcessingException e1) {
			e1.printStackTrace();
		} catch (MetadataException e) {
			e.printStackTrace();
		}

		FileInputStream     	 fin = null;
	    BufferedInputStream      bis = null;
	    DataInputStream          dis = null;
	    try {
          fin = new FileInputStream (file);
	      bis = new BufferedInputStream (fin);
	      dis = new DataInputStream (bis);

	      if (dis.readUnsignedByte () != 0xFF) return result;
	      if (dis.readUnsignedByte () != 0xD8) return result;
	      while (true)
	      {
	        int value = dis.readUnsignedByte ();
	        while (value == 0xFF)
	        {
	          value = dis.readUnsignedByte ();
	        }
	        int size = dis.readUnsignedShort ();
	        if ((value >= 0xC0) && (value <= 0xC3))
	        {
	          dis.readByte ();
	          int s1 = dis.readUnsignedShort ();
	          int s2 = dis.readUnsignedShort ();

		      result [0] = s2;
		      result [1] = s1;
		      return result; 
	        }
	        else
	        {
	        	dis.skip (size - 2);
	        }
	      }
	    } catch (Exception e) {
	      e.printStackTrace ();
	    } finally {
	      try {
	        if (dis != null) dis.close ();
	        if (bis != null) bis.close ();
	        if (fin != null) fin.close ();
	      } catch (Exception e) {
	      }
	    }
	    return result;
	}

	/* (non-Javadoc)
	 * @see de.lineas.tools.helper.AbstractInfoHolder#appendFileInfo(de.lineas.tools.helper.IFileInfoHolder)
	 */
	@Override
	public void appendFileInfo(IFileInfoHolder infoHolder) {
		if (!infoHolder.getClass().getName().equals(this.getClass().getName())) 
			return;
		PictureInfoHolder holder = (PictureInfoHolder)infoHolder;
		if (holder.fileType == UNKNOWN)
			return;
		
		if (!(holder.getFileType() == DIRECTORY)) {
		    sum[0] += (long)holder.getPictureSize()[0];
			sum[1] += (long)holder.getPictureSize()[1];
			sum[2] += 1;
			fileSize += holder.getFileSize();
			
		}
		
		pictureSize[0] = (int)(sum[0] / sum[2]);
		pictureSize[1] = (int)(sum[1] / sum[2]);
		
	    if ((pictureSize[0] > 0) && (pictureSize[1] > 0))
	    	pictureCompression = (((double)fileSize * 100) / ((double)pictureSize[0] * (double)pictureSize[1] * 3)) / (double)sum[2];
	}

	/* (non-Javadoc)
	 * @see de.lineas.tools.helper.AbstractInfoHolder#getType()
	 */
	public int getType() {
		return type;
	}
 
  @Override
  public String[] getNameMask() {
    return nameMask;
  }

  public void setPictureOrientation(int pictureOrientation) {
    this.orientation = pictureOrientation;
  }

  public int getPictureOrientation() {
    return orientation;
  }


}
