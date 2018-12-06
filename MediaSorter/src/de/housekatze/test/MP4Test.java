package de.housekatze.test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import mp4.util.Mp4Parser;
import mp4.util.atom.AtomException;
import mp4.util.atom.MoovAtom;
import mp4.util.atom.MvhdAtom;


public class MP4Test {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    //Mp4Dump.main(new String[]{"-in", "v:\\test\\eee.mp4"});
//    private long m1904 = -2082848399378;
    
    Mp4Parser parser;
    try {
      parser = new Mp4Parser(new DataInputStream(new FileInputStream("v:\\test\\eee.mp4")));
      parser.parseMp4();
      MoovAtom moov = parser.getMoov();
      MvhdAtom mvhd = moov.getMvhd();

      System.out.println(mvhd.getDuration());
      System.out.println(mvhd.getCreationTime());
      
      Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));

      cal.set(1904, 0, 1, 0, 0, 0);
      
      
      
//      cal.add(Calendar.YEAR, 1904);
      System.out.println(cal.getTime());
      
      long m =  (mvhd.getCreationTime() * 1000) + cal.getTime().getTime();
      cal.setTimeInMillis(m);

      System.out.println(cal.getTime());
      parser.visit(mvhd);
      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
//    try {
//      long size = 0;;
//      while (size != -1) {
//        size = reader.printAtom();
//      }
//    } catch (AtomException e) {
//      MP4Log.log("Invalid atom descriptor");
//      e.printStackTrace();
//    }    
 catch (AtomException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
