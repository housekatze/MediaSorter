package de.housekatze.media.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class CustomTableItem extends TableItem {

  private Table table;
  private Button check0;
  private Button check5;
  private boolean showCheck;
  private ProgressBar copyBar;
  
  private int colCount;
  private TableEditor te1 = null;
  private TableEditor te2 = null;
  private TableEditor te3 = null;
  
  public class DataObject {
    public String[]  _textBuffer;
    public boolean[] _boolBuffer;
//    public int[]     _intBuffer;
    public Color[]   _backgroundColors;
    public Object    _object;
  }  
  
  public DataObject getDataObject() {
    DataObject object = new DataObject();
    object._textBuffer = new String[colCount];
    object._boolBuffer = new boolean[colCount];
//    object._intBuffer = new int[colCount];
    object._backgroundColors = new Color[colCount];
    
    for (int j = 0; j < colCount; j++) {
      object._textBuffer[j] = getText(j);
//      object._boolBuffer[j] = isChecked(j);
      object._backgroundColors[j] = getBackground(j);
    }
    object._object = getData();
    
    return object;
  }
  
//  public void removeEditors() {
//    te1 = null;
//    te2 = null;
//    check0 = null;
//    check5 = null;
//  }
  
  public void setDataObject(DataObject object) {
    for (int j = 0; j < colCount; j++) {
      setText(j, object._textBuffer[j]);
//      setChecked(j, object._boolBuffer[j]);
      setBackground(j, object._backgroundColors[j]);
    }
    setData(object._object);
  }
  
  public CustomTableItem(Table table, int style, boolean showCheck) {
      super(table, style);
      this.table = table;
      this.showCheck = showCheck;
      this.colCount = this.getParent().getColumnCount();
      
      init( );
  }
  
  private void init() {
      if (showCheck) {
//        te1 = new TableEditor(table);
//        check0 = new Button(table, SWT.CHECK);
//        check0.setBackground(table.getBackground( ));
//        check0.pack();
//        te1.minimumWidth = check0.getSize ().x;
////        te1.grabHorizontal = true;
////        te1.grabVertical = true;
//        te1.horizontalAlignment = SWT.CENTER;
//        te1.verticalAlignment = SWT.CENTER;
//
//        te1.setEditor(check0, this, 0);
//
//
//        check5 = new Button(table, SWT.CHECK);
//        check5.setBackground(table.getBackground( ));
//        check5.pack();
//        te2 = new TableEditor(table);
//        te2.minimumWidth = check5.getSize ().x;
////      te2.grabHorizontal = true;
////      te2.grabVertical = true;
//        te2.horizontalAlignment = SWT.CENTER;
//        te2.verticalAlignment = SWT.CENTER;
//        te2.setEditor(check5, this, 5);
      }          

      copyBar = new ProgressBar(table, SWT.SMOOTH);
//      copyBar.setSize(100, 10);
      copyBar.setSelection(10);
      te3 = new TableEditor(table);
      te3.grabHorizontal = true;
      te3.grabVertical = true;
      te3.setEditor(copyBar, this, 2);
      te3.getEditor().setVisible(false);
  }

//  public void addSelectionListener(SelectionListener listener) {
//      if (check0 != null) {
//          check0.addSelectionListener(listener);
//      }
//  }
//
//  public void removeSelectionListener(SelectionListener listener) {
//      if (check0 != null) {
//          check0.removeSelectionListener(listener);
//      }
//  }

  public int getProgbarPos(int index) {
    if (index == 2)  
      return copyBar.getSelection( );

    return 0;
  }

  public void setProgbarPos(int index, int value) {
    if (index == 2) {
      if (value > 0)
        te3.getEditor().setVisible(true);
      else
        te3.getEditor().setVisible(false);
      copyBar.setSelection(value);
    }
  }
  
  public boolean isChecked(int index) {
//    if (index == 0)  
//      return check0.getSelection( );
//    if (index == 5)  
//      return check5.getSelection( );
//    return false;
    return true;
  }

  public void setChecked(int index, boolean checked) {
//    if (index == 0)
//      check0.setSelection(checked);
//    else if (index == 5)
//      check5.setSelection(checked);
  }

  public void setData(Object data) {
     super.setData(data);
  }

  @Override
  protected void checkSubclass() {
  }
  
  public ProgressBar getBar() {
    return copyBar;
  }
}