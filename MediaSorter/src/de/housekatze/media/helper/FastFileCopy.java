package de.housekatze.media.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;

public class FastFileCopy  {
    /**
     * @param args
     */
    public static void main(String[] args) {
      copy(file(args[0]), file(args[1]));
    }
   
   
    private static File file(String path) {
      return new File(path);
    }
   
   
    public static void copy(File source, File destination) {
        new FastFileCopy().copy(source, destination, false);
    }
    
    public void copy(File source, File destination, boolean createFolder) {
      try {
        if (createFolder &&(destination.getParentFile() != null))
          destination.getParentFile().mkdirs();
        
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(destination);
   
        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();
   
        transfer(inputChannel, outputChannel, source.length(), 1024 * 1024 * 32 /* 32 MB */, true);
   
        fileInputStream.close();
        fileOutputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
   
   
    private void transfer(FileChannel inputChannel, ByteChannel outputChannel, long lengthInBytes, long chunckSizeInBytes, boolean verbose) throws IOException {
      long overallBytesTransfered = 0L;
      long time = -System.currentTimeMillis();
      while (overallBytesTransfered < lengthInBytes) {
        long bytesToTransfer = Math.min(chunckSizeInBytes, lengthInBytes - overallBytesTransfered);
        long bytesTransfered = inputChannel.transferTo(overallBytesTransfered, bytesToTransfer, outputChannel);
   
        overallBytesTransfered += bytesTransfered;
   
        if (verbose) {
          long percentageOfOverallBytesTransfered = Math.round(overallBytesTransfered / ((double) lengthInBytes) * 100.0);
          setProgress(lengthInBytes, 100, overallBytesTransfered, percentageOfOverallBytesTransfered);
//          try {
//            Thread.sleep(200);
//          } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//          }
        }
   
      }
      time += System.currentTimeMillis();
   
      if (verbose) {
        double kiloBytesPerSecond = (overallBytesTransfered / 1024.0) / (time / 1000.0);
        System.out.printf("Transfered: %s bytes in: %s s -> %s kbytes/s\n", overallBytesTransfered, time / 1000, kiloBytesPerSecond);
      }
    }
    protected void setProgress(long overallBytes, long overallPercentage, long overallBytesTransfered, long percentageOfOverallBytesTransfered) {
      System.out.printf("please override!");
    }
    
}
