package de.housekatze.media.gui;

import java.util.Date;

import org.eclipse.swt.graphics.Color;

import de.housekatze.media.helper.IFileInfoHolder;

public class EditableTableItem {
  public boolean checked;
  public String sourceName;
  public String destName;
  public String destPath;
  public Date fileDate;
  public boolean rotate;
  public String sourcePath;
  public IFileInfoHolder holder;
  public Color background;
  public Color foreground;

  public EditableTableItem() {
  }
}

