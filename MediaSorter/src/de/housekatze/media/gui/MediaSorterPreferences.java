package de.housekatze.media.gui;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import de.housekatze.media.MediaSorter;


public class MediaSorterPreferences extends Object {
  private Preferences prefs;
  private MediaSorter sorter;
  
  public MediaSorterPreferences(MediaSorter sorter) {
    this.sorter = sorter;
    try {
      prefs = Preferences.userNodeForPackage(MediaSorter.class).node("MediaSorter");
      prefs.flush();
    } catch (BackingStoreException e) {
      e.printStackTrace();
    }
  }

  public void storeFilterValues() {
    prefs.node("pattern").put("filePattern", sorter.getFilePattern());
  }
  
}
