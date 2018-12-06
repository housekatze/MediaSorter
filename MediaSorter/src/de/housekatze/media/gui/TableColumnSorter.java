package de.housekatze.media.gui;

import java.util.Date;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_CHECKED;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_SRCNAME;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_DESTNAME;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_DESTPATH;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_FILEDATE;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_ROTATE;
import static de.housekatze.media.MediaSorter.ITEM_INDEX_SRCPATH;

public class TableColumnSorter extends ViewerComparator {
  public static final int ASC = 1;
  public static final int NONE = 0; 
  public static final int DESC = -1;

  private int direction = 0;

  private TableColumn column;

  private TableViewer viewer;
  private int columnIndex; 

  public TableColumnSorter(TableViewer viewer, TableColumn column, int columnIndex) {
          this.column = column;
          this.viewer = viewer;
          this.columnIndex = columnIndex;
          this.column.addSelectionListener(new SelectionAdapter() {

                  public void widgetSelected(SelectionEvent e) {
                          if (TableColumnSorter.this.viewer.getComparator() != null) {
                                  if (TableColumnSorter.this.viewer.getComparator() == TableColumnSorter.this) {
                                          int tdirection = TableColumnSorter.this.direction;

                                          if (tdirection == ASC) {
                                                  setSorter(TableColumnSorter.this, DESC);
                                          } else if (tdirection == DESC) {
                                                  setSorter(TableColumnSorter.this, ASC);
                                          }
                                  } else {
                                    if (!(TableColumnSorter.this.column.getParent().getSortColumn() == TableColumnSorter.this.column) ) 
                                        setSorter(TableColumnSorter.this, ASC);
                                     else if (TableColumnSorter.this.column.getParent().getSortDirection() == SWT.UP) 
                                        setSorter(TableColumnSorter.this, DESC);
                                     else
                                        setSorter(TableColumnSorter.this, ASC);
                                  }
                          } else {
                                  setSorter(TableColumnSorter.this, ASC);
                          }
                  }
          });
  }

  public void setSorter(TableColumnSorter sorter, int direction) {
          if (direction == NONE) {
                  column.getParent().setSortColumn(null);
                  column.getParent().setSortDirection(SWT.NONE);
                  viewer.setComparator(null);
          } else {
                  column.getParent().setSortColumn(column);
                  sorter.direction = direction;

                  if (direction == ASC) {
                          column.getParent().setSortDirection(SWT.UP);
                  } else {
                          column.getParent().setSortDirection(SWT.DOWN);
                  }

                  if (viewer.getComparator() == sorter) {
                          viewer.refresh();
                  } else {
                          viewer.setComparator(sorter);
                  }
          }
  }

  public int compare(Viewer viewer, Object e1, Object e2) {
          return direction * doCompare(viewer, e1, e2);
  }

  // die callback Methode sozusagen
//  protected abstract int doCompare(Viewer TableViewer, Object e1, Object e2);
  protected int doCompare(Viewer v, Object e1, Object e2) {
    ITableLabelProvider lp = ((ITableLabelProvider) viewer.getLabelProvider());
    
    switch (columnIndex) {
      case ITEM_INDEX_CHECKED:
      case ITEM_INDEX_SRCNAME:
      case ITEM_INDEX_DESTNAME:
      case ITEM_INDEX_DESTPATH:
      case ITEM_INDEX_ROTATE:
      case ITEM_INDEX_SRCPATH:
        String t1 = lp.getColumnText(e1, columnIndex);
        String t2 = lp.getColumnText(e2, columnIndex);
        return t1.compareTo(t2);
      case ITEM_INDEX_FILEDATE:
        Date d1 = ((EditableTableItem)e1).fileDate;
        Date d2 = ((EditableTableItem)e2).fileDate;
        if (d1.before(d2)) return -1;
        if (d1.after(d2)) return 1;
        return 0;
    }

    String t1 = lp.getColumnText(e1, columnIndex);
    String t2 = lp.getColumnText(e2, columnIndex);
    return t1.compareTo(t2);
  }
}
