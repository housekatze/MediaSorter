package de.housekatze.media.helper;


/**
 * A factory for creating IFileInfoHolder objects.
 */
public class FileInfoHolderFactory {

    /** The Constant FILE. */
    public final static int FILE_INFO = 0;

    /** The Constant UNKNOWN. */
	public final static int UNKNOWN = -1;
	
	/** The Constant PICTURE_INFO. */
	public final static int PICTURE_INFO = 1;
    public final static String[] PICTURE_NAME_MASK = {".jpeg",".jpg", ".gif"};
	
	/** The Constant MP4_INFO. */
	public final static int MP4_INFO = 10;
	public final static String[] MP4_NAME_MASK = {".mp4",".m4v"};
	
    /** The Constant MTS_INFO. */
    public final static int MTS_INFO = 11;
    public final static String[] MTS_NAME_MASK = {".mts",".m2ts"};
    
    /** The Constant MOV_INFO. */
    public final static int MOV_INFO = 12;
    public final static String[] MOV_NAME_MASK = {".mov",".qt"};

    /** The Constant AVI_INFO. */
    public final static int AVI_INFO = 13;
    public final static String[] AVI_NAME_MASK = {".avi"};
    
    /** The Constant MKV_INFO. */
    public final static int MKV_INFO = 14;
    public final static String[] MKV_NAME_MASK = {".mkv"};
	/**
	 * Gets a single instance of a IFileInfoHolder object.
	 * 
	 * @param type the info holder type
	 * 
	 * @return single instance of IFileInfoHolder
	 */
	public static IFileInfoHolder getInstance(int[] types) {
		if (types == null)
		  return null;
		 
	    switch (types[0]) {
        case FILE_INFO: {
          FileInfoHolder holder = new FileInfoHolder(null);
          
          IFileInfoHolder[] holders = new IFileInfoHolder[types.length-1]; 
          for (int i=1; i<types.length; i++)
          {
             holders[i-1] = getInstance(new int[]{types[i]});  
          }
          holder.setInfoholders(holders);
          
          return holder;
        }
		case PICTURE_INFO:
		  return new PictureInfoHolder(PICTURE_NAME_MASK);
		case MP4_INFO:
		  return new MP4InfoHolder(MP4_NAME_MASK);
        case MTS_INFO:
          return new MTSInfoHolder(MTS_NAME_MASK);
        case MOV_INFO:
          return new MOVInfoHolder(MOV_NAME_MASK);
        case AVI_INFO:
          return new AVIInfoHolder(AVI_NAME_MASK);
        case MKV_INFO:
          return new MKVInfoHolder(MKV_NAME_MASK);
		
		default:
			return null;
		}
	}
}
