package de.housekatze.media.gui;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import static de.housekatze.media.MediaSorter.ITEM_INDEX_CHECKED;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_DESTNAME;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_DESTPATH;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_FILEDATE;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_ROTATE;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_SRCNAME;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_SRCPATH;

import java.text.SimpleDateFormat;

import de.housekatze.media.helper.IFileInfoHolder;

public class TableLabelProvider 
//extends LabelProvider 
implements ITableLabelProvider, IColorProvider 
{

  @Override
  public Image getColumnImage(Object element, int columnIndex) {
    return null;
  }

  @Override
  public String getColumnText(Object element, int columnIndex) {
    IFileInfoHolder holder = ((EditableTableItem) element).holder;
    switch (columnIndex) {
    case ITEM_INDEX_CHECKED:
      return ((EditableTableItem) element).checked ? "\u2714":"";
    case ITEM_INDEX_SRCNAME:
      return ((EditableTableItem) element).sourceName;
    case ITEM_INDEX_DESTNAME:
      return ((EditableTableItem) element).destName;
    case ITEM_INDEX_DESTPATH:
      return ((EditableTableItem) element).destPath;
    case ITEM_INDEX_FILEDATE:
      String strDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                               .format(((EditableTableItem) element).fileDate);
      return strDate;
    case ITEM_INDEX_ROTATE:
      return ((EditableTableItem) element).rotate ? "\u2714":"";
    case ITEM_INDEX_SRCPATH:
      return ((EditableTableItem) element).sourcePath ;
    default:
      return "Invalid column: " + columnIndex;
    }
  }

  @Override
  public Color getBackground(Object element) {
    return ((EditableTableItem) element).background;
  }

  @Override
  public Color getForeground(Object element) {
    return ((EditableTableItem) element).foreground;
  }

  @Override
  public void addListener(ILabelProviderListener listener) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isLabelProperty(Object element, String property) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void removeListener(ILabelProviderListener listener) {
    // TODO Auto-generated method stub
    
  }
  
}
