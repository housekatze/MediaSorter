package de.housekatze.media.gui;

import static de.housekatze.media.MediaSorter.PROPERTY_CHECKED;
import static de.housekatze.media.MediaSorter.PROPERTY_DESTNAME;
import static de.housekatze.media.MediaSorter.PROPERTY_DESTPATH;
import static de.housekatze.media.MediaSorter.PROPERTY_ROTATE;
import static de.housekatze.media.MediaSorter.PROPERTY_SRCPATH;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

public class TableCellModifier implements ICellModifier {
  TableViewer viewer = null;
  
  public TableCellModifier(TableViewer viewer) {
     this.viewer = viewer;
  }
  
  public boolean canModify(Object element, String property) {
    return true;
  }

  public Object getValue(Object element, String property) {
    if (PROPERTY_CHECKED.equals(property))
      return ((EditableTableItem) element).checked;
    else if ((PROPERTY_DESTNAME.equals(property)))
      return ((EditableTableItem) element).destName;
    else if ((PROPERTY_DESTPATH.equals(property)))
      return ((EditableTableItem) element).destPath;
    else if ((PROPERTY_SRCPATH.equals(property)))
      return ((EditableTableItem) element).sourcePath;
    else if (PROPERTY_ROTATE.equals(property))
      return ((EditableTableItem) element).rotate;
    
    return "";
  }

  public void modify(Object element, String property, Object value) {
    TableItem tableItem = (TableItem) element;
    EditableTableItem item = (EditableTableItem) tableItem.getData();
    if (PROPERTY_CHECKED.equals(property))
      item.checked = (Boolean)value;
    else if ((PROPERTY_DESTNAME.equals(property)))
      item.destName = (String)value;
    else if ((PROPERTY_DESTPATH.equals(property)))
      item.destPath = (String)value;
    else if ((PROPERTY_ROTATE.equals(property)))
      item.rotate = (Boolean)value;
    
    viewer.refresh();
  }
}
