package de.housekatze.media;

import static de.housekatze.media.helper.FileInfoHolderFactory.AVI_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.FILE_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.MKV_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.MOV_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.MP4_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.MTS_INFO;
import static de.housekatze.media.helper.FileInfoHolderFactory.PICTURE_INFO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import de.housekatze.media.gui.EditableTableItem;
import de.housekatze.media.gui.MediaSorterPreferences;
import de.housekatze.media.gui.ProgressDialog;
import de.housekatze.media.gui.TableCellModifier;
import de.housekatze.media.gui.TableColumnSorter;
import de.housekatze.media.gui.TableLabelProvider;
import de.housekatze.media.helper.FastFileCopy;
import de.housekatze.media.helper.FolderChecker;
import de.housekatze.media.helper.IFileInfoHolder;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MediaSorter extends org.eclipse.swt.widgets.Composite {
  private MediaSorterPreferences prefs;
  
  public static final int ITEM_INDEX_CHECKED = 0;
  public static final int ITEM_INDEX_SRCNAME = 1;
  public static final int ITEM_INDEX_DESTNAME = 2;
  public static final int ITEM_INDEX_DESTPATH = 3;
  public static final int ITEM_INDEX_FILEDATE = 4;
  public static final int ITEM_INDEX_ROTATE = 5;
  public static final int ITEM_INDEX_SRCPATH = 6;
  
  public static final String PROPERTY_CHECKED = "CHECKED";
  public static final String PROPERTY_SRCNAME = "SRCNAME";
  public static final String PROPERTY_DESTNAME = "DESTNAME";
  public static final String PROPERTY_DESTPATH = "DESTPATH";
  public static final String PROPERTY_FILEDATE = "FILEDATE";
  public static final String PROPERTY_ROTATE = "FILEDATE";
  public static final String PROPERTY_SRCPATH = "SRCPATH";

  private Menu          menu1;
  private Button srtChk;
  private TableColumn chkCol;
  private Text destFolderEdit;
  private Composite optionsComposite;
  private CTabItem cTabItem1;
  private CTabFolder menuTabFolder;
  private Button subFolderChk;
  private TableColumn newFileCol;
  private TableColumn newPathCol;
  private TableColumn filePathCol;
  private TableColumn   dateCol;
  private TableColumn   filenameCol;
  private Table         fileGrid;
  private Group         gridGroup;
  private Button        startBtn;
  private Button        folderBtn;
  private Composite     buttonComposite;
  private Composite     optionsGrid;
  private MenuItem      aboutMenuItem;
  private MenuItem      contentsMenuItem;
  private Menu          helpMenu;
  private MenuItem      helpMenuItem;
  private Composite     gridComposite;
  private MenuItem      exitMenuItem;
  private MenuItem      closeFileMenuItem;
  private MenuItem      saveFileMenuItem;
  private MenuItem      newFileMenuItem;
  private MenuItem      openFileMenuItem;
  private Menu          fileMenu;
  private MenuItem      fileMenuItem;

  private String        sourceFolder = "D:\\Daten - Medien\\first\\+ scan";
  private String        destFolder   = "D:\\Daten - Medien\\first";
  private String        filePattern = "%year%-%month%-%day% - %hour%-%minute%-%second% - %filename%";
  public String getFilePattern() {
    return filePattern;
  }

  public void setFilePattern(String filePattern) {
    this.filePattern = filePattern;
  }

  private String        pathPattern = "%year%%dir%%month%%dir%%year%-%month%-%day%";
  
  private FolderChecker checker      = null;
  ProgressDialog progress = null;
  private ProgressBar allProgressBar;
  private Button destBtn;
  private Label label4;
  private Label label3;
  private Label progBarLabel;
  private Text destFileMaskEdit;
  private Group group1;
  private Text destFolderMaskEdit;
  private Label Label2;
  private Label label1;
  private Button appendFilesChk;
  private Text sourceFolderEdit;
  
  private TableColumn rotateCol;

  private TableViewer viewer;
  private java.util.List<EditableTableItem> tableData; 

  private TableEditor editor;

  {
    // Register as a resource user - SWTResourceManager will
    // handle the obtaining and disposing of resources
    SWTResourceManager.registerResourceUser(this);
  }

  public MediaSorter(Composite parent, int style) {
    super(parent, style);
    prefs = new MediaSorterPreferences(this);
    initGUI();
  }

  private void attachColumnSorter(final TableViewer viewer, final TableColumn column, final int columnIndex) {
    TableColumnSorter cSorter = new TableColumnSorter(viewer, column, columnIndex);
    cSorter.setSorter(cSorter, TableColumnSorter.ASC);
  }
  
  private void attachProgbarToItem(final TableItem item, final Table table) {
    //The editor must have the same size as the cell and must
    //not be any smaller than 50 pixels.
    editor.horizontalAlignment = SWT.LEFT;
    editor.grabHorizontal = true;
    editor.minimumWidth = 50;

    // Clean up any previous editor control
    Control oldEditor = editor.getEditor();
    if (oldEditor != null) oldEditor.dispose();

    ProgressBar copyBar = new ProgressBar(table, SWT.SMOOTH);
    copyBar.setSelection(0);

    editor.setEditor(copyBar, item, ITEM_INDEX_DESTNAME);
    editor.getEditor().setVisible(true);

  }
  
  private void attachCellEditors(final TableViewer viewer, Composite parent) {
    
    // CellModifier
    viewer.setCellModifier(new TableCellModifier(viewer));
    
    // Die Reihenfolge der Spalten ist wichtig
    viewer.setColumnProperties(new String[] { 
        PROPERTY_CHECKED, PROPERTY_SRCNAME, PROPERTY_DESTNAME, PROPERTY_DESTPATH, PROPERTY_FILEDATE, PROPERTY_ROTATE, PROPERTY_SRCPATH});
    
    // Die Cell-Editoren
    CellEditor[] editors = new CellEditor[viewer.getColumnProperties().length];
    TextCellEditor textEditor;
    
    CheckboxCellEditor chkBox = new CheckboxCellEditor(fileGrid);
    editors[0] = chkBox;
//    textEditor = new TextCellEditor(fileGrid);
//    editors[1] = textEditor;
    textEditor = new TextCellEditor(fileGrid);
    editors[2] = textEditor;
    textEditor = new TextCellEditor(fileGrid);
    editors[3] = textEditor;
    chkBox = new CheckboxCellEditor(fileGrid);
    editors[5] = chkBox;
//    textEditor = new TextCellEditor(fileGrid);
//    editors[6] = textEditor;
    
    viewer.setCellEditors(editors);
  }

  // Die Ansichtsmethoden 
  private void attachLabelProvider(TableViewer viewer) {
    // Labelprovider
    viewer.setLabelProvider(new TableLabelProvider());
  }

  // die Bereitstellung der Daten
  private void attachContentProvider(TableViewer viewer) {
    // Contentprovider
    viewer.setContentProvider(new IStructuredContentProvider() {
      @SuppressWarnings("unchecked")
      public Object[] getElements(Object inputElement) {
        return ((List<EditableTableItem>) inputElement).toArray() ;
      }

      public void dispose() {
      }

      public void inputChanged(Viewer viewer, Object oldInput,
          Object newInput) {
      }
    });
  }
  
  /**
   * Initializes the GUI.
   */
  private void initGUI() {
    try {
      this.setBackground(SWTResourceManager.getColor(192, 192, 192));
      FormLayout thisLayout = new FormLayout();
      this.setLayout(thisLayout);
      {
        menu1 = new Menu(getShell(), SWT.BAR);
        getShell().setMenuBar(menu1);
        {
          fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
          fileMenuItem.setText("File");
          {
            fileMenu = new Menu(fileMenuItem);
            {
              openFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
              openFileMenuItem.setText("Open");
            }
            {
              newFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
              newFileMenuItem.setText("New");
            }
            {
              saveFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
              saveFileMenuItem.setText("Save");
            }
            {
              closeFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
              closeFileMenuItem.setText("Close");
            }
            {
              exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
              exitMenuItem.setText("Exit");
            }
            fileMenuItem.setMenu(fileMenu);
          }
        }
        {
          helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
          helpMenuItem.setText("Help");
          {
            helpMenu = new Menu(helpMenuItem);
            {
              contentsMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
              contentsMenuItem.setText("Contents");
            }
            {
              aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
              aboutMenuItem.setText("About");
            }
            helpMenuItem.setMenu(helpMenu);
          }
        }
      }
      this.layout();
      pack();
      this.setSize(1200, 600);
      {
        buttonComposite = new Composite(this, SWT.NONE);
        FormLayout buttonCompositeLayout = new FormLayout();
        buttonComposite.setLayout(buttonCompositeLayout);
        FormData buttonCompositeLData = new FormData();
        buttonCompositeLData.width = 1200;
        buttonCompositeLData.height = 42;
        buttonCompositeLData.right =  new FormAttachment(1000, 1000, 0);
        buttonCompositeLData.left =  new FormAttachment(0, 1000, 0);
        buttonCompositeLData.bottom =  new FormAttachment(1000, 1000, 0);
        buttonComposite.setLayoutData(buttonCompositeLData);
        {
          startBtn = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
          startBtn.setText("Start");
          FormData startBtnLData = new FormData();
          startBtnLData.width = 102;
          startBtnLData.height = 21;
          startBtnLData.left =  new FormAttachment(0, 1000, 16);
          startBtnLData.top =  new FormAttachment(0, 1000, 8);
          startBtn.setLayoutData(startBtnLData);
          startBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
              startBtnWidgetSelected(evt);
            }
          });
        }
        {
          sourceFolderEdit = new Text(buttonComposite, SWT.BORDER);
          FormData text1LData = new FormData();
          text1LData.left =  new FormAttachment(0, 1000, 130);
          text1LData.top =  new FormAttachment(0, 1000, 8);
          text1LData.width = 560;
          text1LData.height = 15;
          text1LData.right =  new FormAttachment(1000, 1000, -498);
          sourceFolderEdit.setLayoutData(text1LData);
          sourceFolderEdit.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
              sourceFolderEditKeyPressed(evt);
            }
          });
        }
        {
          subFolderChk = new Button(buttonComposite, SWT.CHECK | SWT.LEFT);
          subFolderChk.setText("Unterverzeichnisse durchsuchen");
          FormData subFolderChkLData = new FormData();
          subFolderChkLData.width = 191;
          subFolderChkLData.height = 16;
          subFolderChkLData.right =  new FormAttachment(1000, 1000, -19);
          subFolderChkLData.top =  new FormAttachment(0, 1000, 12);
          subFolderChk.setLayoutData(subFolderChkLData);
          subFolderChk.setSelection(true);
        }
        {
          appendFilesChk = new Button(buttonComposite, SWT.CHECK | SWT.LEFT);
          appendFilesChk.setText("Dateien hinzufügen");
          FormData button1LData = new FormData();
          button1LData.width = 130;
          button1LData.height = 16;
          button1LData.top =  new FormAttachment(0, 1000, 12);
          button1LData.right =  new FormAttachment(1000, 1000, -214);
          appendFilesChk.setLayoutData(button1LData);
        }
        {
          folderBtn = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
          folderBtn.setText("...");
          FormData folderBtnLData = new FormData();
          folderBtnLData.width = 30;
          folderBtnLData.height = 21;
          folderBtnLData.right =  new FormAttachment(1000, 1000, -469);
          folderBtnLData.top =  new FormAttachment(0, 1000, 8);
          folderBtnLData.bottom =  new FormAttachment(1000, 1000, -13);
          folderBtn.setLayoutData(folderBtnLData);
          folderBtn.setAlignment(SWT.CENTER);
          folderBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
              folderBtnWidgetSelected(evt);
            }
          });
        }
      }
      {
        optionsGrid = new Composite(this, SWT.NONE);
        GridLayout optionsGridLayout = new GridLayout();
        optionsGridLayout.makeColumnsEqualWidth = true;
        optionsGrid.setLayout(optionsGridLayout);
        FormData optionsGridLData = new FormData();
        optionsGridLData.width = 1200;
        optionsGridLData.height = 160;
        optionsGridLData.left =  new FormAttachment(0, 1000, 0);
        optionsGridLData.top =  new FormAttachment(0, 1000, 0);
        optionsGridLData.right =  new FormAttachment(1000, 1000, 0);
        optionsGrid.setLayoutData(optionsGridLData);
        {
          menuTabFolder = new CTabFolder(optionsGrid, SWT.NONE);
          GridData menuTabFolderLData = new GridData();
          menuTabFolderLData.verticalAlignment = GridData.FILL;
          menuTabFolderLData.horizontalAlignment = GridData.FILL;
          menuTabFolderLData.grabExcessVerticalSpace = true;
          menuTabFolderLData.grabExcessHorizontalSpace = true;
          menuTabFolder.setLayoutData(menuTabFolderLData);
          {
            cTabItem1 = new CTabItem(menuTabFolder, SWT.NONE);
            cTabItem1.setText("Einstellungen");
            {
              optionsComposite = new Composite(menuTabFolder, SWT.NONE);
              optionsComposite.setLayout(null);
              cTabItem1.setControl(optionsComposite);
              {
                group1 = new Group(optionsComposite, SWT.NONE);
                group1.setLayout(null);
                group1.setText("Maske");
                group1.setBounds(12, 27, 550, 70);
                {
                  destFolderMaskEdit = new Text(group1, SWT.BORDER);
                  destFolderMaskEdit.setTabs(0);
                  destFolderMaskEdit.setText(pathPattern);
                  destFolderMaskEdit.setBounds(110, 17, 430, 21);
                  destFolderMaskEdit.setSize(430, 21);
                }
                {
                  destFileMaskEdit = new Text(group1, SWT.BORDER);
                  destFileMaskEdit.setTabs(0);
                  destFileMaskEdit.setText(filePattern);
                  destFileMaskEdit.setBounds(110, 38, 430, 21);
                  destFileMaskEdit.setSize(430, 21);
                  destFileMaskEdit.setOrientation(SWT.HORIZONTAL);
                }
                {
                  label3 = new Label(group1, SWT.NONE);
                  label3.setText("Verzeichnis");
                  label3.setBounds(38, 20, 60, 15);
                  label3.setAlignment(SWT.RIGHT);
                }
                {
                  label4 = new Label(group1, SWT.NONE);
                  label4.setText("Datei");
                  label4.setBounds(37, 41, 60, 15);
                  label4.setAlignment(SWT.RIGHT);
                }
              }
              {
                Label2 = new Label(optionsComposite, SWT.NONE);
                Label2.setText("Medientyp");
                Label2.setBounds(612, 12, 88, 15);
              }
              {
                label1 = new Label(optionsComposite, SWT.NONE);
                label1.setText("Zielverzeichnis");
                label1.setBounds(33, 8, 76, 15);
              }
              {
                srtChk = new Button(optionsComposite, SWT.CHECK | SWT.LEFT);
                srtChk.setText("SRT Datei erstellen");
                srtChk.setSelection(true);
                srtChk.setBounds(613, 39, 126, 16);
              }
              {
                destFolderEdit = new Text(optionsComposite, SWT.BORDER);
                destFolderEdit.setText(destFolder);
                destFolderEdit.setTabs(0);
                destFolderEdit.setBounds(122, 5, 400, 21);
                destFolderEdit.setSize(400, 21);
              }
              {
                destBtn = new Button(optionsComposite, SWT.PUSH | SWT.CENTER);
                destBtn.setText("...");
                destBtn.setBounds(521, 5, 30, 21);
                {
                  renameS3Chk = new Button(optionsComposite, SWT.CHECK);
                  renameS3Chk.setLocation(784, 39);
                  renameS3Chk.setSize(135, 16);
                  renameS3Chk.setText("Samung Galaxy S3, S4");
                  renameS3Chk.setSelection(true);
                  renameS3Chk.setAlignment(SWT.RIGHT);
                }
                {
                  lblDateienNurUmbennenen = new Label(optionsComposite, SWT.NONE);
                  lblDateienNurUmbennenen.setBounds(784, 12, 153, 15);
                  lblDateienNurUmbennenen.setText("Dateien nur umbennenen ");
                }
                destBtn.addSelectionListener(new SelectionAdapter() {
                  public void widgetSelected(SelectionEvent evt) {
                    destBtnWidgetSelected(evt);
                  }
                  public void widgetDefaultSelected(SelectionEvent evt) {
                    destBtnWidgetDefaultSelected(evt);
                  }
                });
              }
            }
          }
          menuTabFolder.setSelection(0);
        }
      }
      {
        gridComposite = new Composite(this, SWT.NONE);
        GridLayout gridCompositeLayout = new GridLayout();
        gridCompositeLayout.makeColumnsEqualWidth = true;
        gridComposite.setLayout(gridCompositeLayout);
        FormData gridCompositeLData = new FormData();
        gridCompositeLData.width = 1200;
        gridCompositeLData.height = 400;
        gridCompositeLData.left =  new FormAttachment(0, 1000, 0);
        gridCompositeLData.right =  new FormAttachment(1000, 1000, 0);
        gridCompositeLData.bottom =  new FormAttachment(1000, 1000, -42);
        gridCompositeLData.top =  new FormAttachment(0, 1000, 138);
        gridComposite.setLayoutData(gridCompositeLData);
        {
          gridGroup = new Group(gridComposite, SWT.NONE);
          gridGroup.setBackgroundMode(SWT.INHERIT_DEFAULT);
          GridLayout gridGroupLayout = new GridLayout();
          gridGroupLayout.makeColumnsEqualWidth = true;
          gridGroup.setLayout(gridGroupLayout);
          gridGroup.setText("zu bearbeitende Dateien");
          GridData gridGroupLData = new GridData();
          gridGroupLData.grabExcessHorizontalSpace = true;
          gridGroupLData.horizontalAlignment = GridData.FILL;
          gridGroupLData.verticalAlignment = GridData.FILL;
          gridGroupLData.grabExcessVerticalSpace = true;
          gridGroup.setLayoutData(gridGroupLData);
          {
            GridData fileGridLData1 = new GridData();
            fileGridLData1.horizontalAlignment = SWT.FILL;
            fileGridLData1.grabExcessHorizontalSpace = true;
            fileGridLData1.verticalAlignment = GridData.FILL;
            fileGridLData1.grabExcessVerticalSpace = true;
            fileGrid = new Table(gridGroup, SWT.BORDER | SWT.FULL_SELECTION);
            fileGrid.setLinesVisible(true);
            fileGrid.setForeground(com.swtdesigner.SWTResourceManager.getColor(0, 0, 0));
            fileGrid.setBackgroundMode(SWT.INHERIT_FORCE);
            fileGrid.setLayoutData(fileGridLData1);
            fileGrid.setHeaderVisible(true);
            
            viewer = new TableViewer(fileGrid);
            tableData = new ArrayList<EditableTableItem>();
  
            attachContentProvider(viewer);
            attachLabelProvider(viewer);
            attachCellEditors(viewer, fileGrid);
            viewer.setInput(tableData);
            
            editor  = new TableEditor(fileGrid);
  
            {
              chkCol = new TableColumn(fileGrid, SWT.CENTER);
              chkCol.setWidth(23);
              attachColumnSorter(viewer, chkCol, 0);
            }
            {
              filenameCol = new TableColumn(fileGrid, SWT.NONE);
              filenameCol.setText("Dateiname");
              filenameCol.setWidth(136);
              filenameCol.setMoveable(true);
              attachColumnSorter(viewer, filenameCol, 1);
            }
            {
              newFileCol = new TableColumn(fileGrid, SWT.NONE);
              newFileCol.setText("neuer Dateiname");
              newFileCol.setWidth(236);
              newFileCol.setMoveable(true);
              attachColumnSorter(viewer, newFileCol, 2);
            }
            {
              newPathCol = new TableColumn(fileGrid, SWT.NONE);
              newPathCol.setText("neuer Pfad");
              newPathCol.setWidth(374);
              newPathCol.setMoveable(true);
              attachColumnSorter(viewer, newPathCol, 3);
            }
            {
              dateCol = new TableColumn(fileGrid, SWT.NONE);
              dateCol.setText("Datum/Zeit");
              dateCol.setWidth(138);
              dateCol.setMoveable(true);
              attachColumnSorter(viewer, dateCol, 4);
            }
            {
              rotateCol = new TableColumn(fileGrid, SWT.NONE);
              rotateCol.setText("Drehen");
              rotateCol.setWidth(50);
              attachColumnSorter(viewer, rotateCol, 5);
            }
            {
              filePathCol = new TableColumn(fileGrid, SWT.NONE);
              filePathCol.setText("Quellpfad");
              filePathCol.setWidth(191);
              filePathCol.setMoveable(true);
              attachColumnSorter(viewer, filePathCol, 6);
            }
  
            fileGrid.setSortColumn(filenameCol);
            fileGrid.setSortDirection(SWT.UP);
  
            //            setWidgetSortIndicator();
          }
        }
        {
          progBarLabel = new Label(gridComposite, SWT.HORIZONTAL);
          GridData progBarLabelData = new GridData();
          progBarLabelData.heightHint = 12;
          progBarLabelData.horizontalAlignment = GridData.FILL;
          progBarLabelData.verticalAlignment = GridData.BEGINNING;
          progBarLabel.setText("Blabla");
          progBarLabel.setAlignment(SWT.LEFT);
          progBarLabel.setLayoutData(progBarLabelData);
        }
        {
          allProgressBar = new ProgressBar(gridComposite, SWT.HORIZONTAL | SWT.SMOOTH);
          GridData progressBar1LData = new GridData();
          progressBar1LData.heightHint = 12;
          progressBar1LData.horizontalAlignment = GridData.FILL;
          progressBar1LData.verticalAlignment = GridData.BEGINNING;
          allProgressBar.setLayoutData(progressBar1LData);
        }
      }
      prefs.storeFilterValues();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Auto-generated main method to display this
   * org.eclipse.swt.widgets.Composite inside a new Shell.
   */
  public static void main(String[] args) {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    MediaSorter inst = new MediaSorter(shell, SWT.NULL);
    Point size = inst.getSize();
    shell.setLayout(new FillLayout());
    shell.layout();
    if (size.x == 0 && size.y == 0) {
      inst.pack();
      shell.pack();
    } else {
      Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
      shell.setSize(shellBounds.width, shellBounds.height);
    }
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
  }

  private String getSourcePath() {
    DirectoryDialog fd = new DirectoryDialog(this.getShell(), SWT.OPEN);
    
    fd.setText("Quellverzeichnis auswählen");
    fd.setMessage("Bitte wählen Sie das Quellverzeichnis aus");
    fd.setFilterPath(sourceFolder);
  
    return fd.open();
  }

  private void destBtnWidgetDefaultSelected(SelectionEvent evt) {
    destFolder = getDestPath();
    if (destFolder == null)
      return;
    
    destFolderEdit.setText(destFolder);
  }

  private String getDestPath() {
    DirectoryDialog fd = new DirectoryDialog(this.getShell(), SWT.OPEN);
    
    fd.setText("Zielverzeichnis auswählen");
    fd.setMessage("Bitte wählen Sie das Zielverzeichnis aus");
    fd.setFilterPath(destFolder);
  
    return fd.open();
  }

  private void startBtnWidgetSelected(SelectionEvent evt) {

    // hier noch Oberfläche Events abschalten
    // und CancelBtn über startBtn zeichnen -> Abbruch einbauen
     allProgressBar.setSelection(0); 
     copyFiles(srtChk.getSelection(), false);
    
  }

  private void folderBtnWidgetSelected(SelectionEvent evt) {
    sourceFolder = getSourcePath();
    if (sourceFolder == null)
      return;
    sourceFolderEdit.setText(sourceFolder);
    getFiles();
  }

  private void sourceFolderEditKeyPressed(KeyEvent evt) {
    if (evt.keyCode == 13)
      getFiles();
  }

  private void getFiles() {
    progress = new ProgressDialog(this.getShell());
    progress.open();

    checkFolder();
    fillGrid();
    
    progress.close();
  }
  
  private void fillGrid() {
    pathPattern = destFolderMaskEdit.getText();
    filePattern = destFileMaskEdit.getText();
    
    allProgressBar.setSelection(0); 

    List<IFileInfoHolder> list = checker.getRootNode().getInfoHolderList();
    
    if (!appendFilesChk.getSelection()) {
//      for(TableItem item : fileGrid.getItems()) {
//        item.dispose();
//        item = null;
//      }
//      fileGrid.clearAll();
//      fileGrid.removeAll();
//      for (Control control : fileGrid.getChildren()) {
//        control.dispose();
//        control = null;
//      }
      tableData.clear();
      viewer.refresh();
    }
    
    fileGrid.redraw();
    
    for (IFileInfoHolder holder : list) {
      System.out.println(holder.getFile().getName());
      
      HashMap<String, String> patternMap = createPatternMap(holder);
      
      EditableTableItem item = new EditableTableItem();

      item.checked = true;
      item.sourceName = holder.getFile().getName();
      item.destName =  replacePattern(filePattern, patternMap);
      
      if (!destFolder.endsWith(File.separator))
        destFolder += File.separator;
      item.destPath = replacePattern(destFolder+pathPattern, patternMap);

      item.background = viewer.getTable().getBackground();
      item.foreground = viewer.getTable().getForeground();

      Date date = null;
      if (holder.getExposureDate() != null) {
        date = holder.getExposureDate();
      } 
      else if ((holder.getEstimatedDate() != null) && (holder.getModifiedDate().after(holder.getEstimatedDate()))) {
        date = holder.getEstimatedDate();
        item.background = new Color(null, 255, 157, 157);
      }
      else {
        date = holder.getModifiedDate();
        item.background = new Color(null, 255, 255, 157);
      }
      item.fileDate = date;
      
      if (holder.getOrientation() > 1)
        item.rotate = true;

      item.sourcePath = holder.getFile().getParent();
      item.holder = holder;

      tableData.add(item);
      viewer.refresh();
    }
    TableColumn[] columns = fileGrid.getColumns();
    int i = 0;
    for (i = 0; i < columns.length; i++) {
      if (columns[i] == fileGrid.getSortColumn())
        break;
    }
  }
  
  // globale Vars fürs Kopieren
  EditableTableItem copyFilesItem;
  int copyFilesProgbar_i = 0;
  boolean copyFilesEncode;
  Thread thread;
  Vector<EditableTableItem> items;
  File copyFilesSource;
  File copyFilesDestination;
  long copyBytesTransfered;
  int copyPercentTransfered;
  int copyFilesIndex;
  private Button renameS3Chk;
  private Label lblDateienNurUmbennenen;

  private void copyFiles(boolean createSrt, boolean encode) {
    copyFilesEncode = encode;
    items = new Vector<EditableTableItem>();
    for (int i=0; i < fileGrid.getItemCount(); i++) 
    {
      EditableTableItem item = (EditableTableItem)viewer.getElementAt(i);
      items.add(item);
    }
    
    allProgressBar.setMaximum(items.size());
    copyFilesProgbar_i = 0;

    thread = new Thread() {
        public void run() {
          Display.getDefault().syncExec(new Runnable() {
            public void run() {
              fileGrid.setEnabled(false);  
              progBarLabel.setText("");
            }
          });

          for (int i = 0; i < items.size(); i++) 
          {
            copyFilesItem = items.get(i);
            copyFilesIndex= i;
            
            if (!copyFilesItem.checked)
              continue;
            
            // vor dem Kopieren
            Display.getDefault().syncExec(new Runnable() {
              public void run() {
                  IFileInfoHolder holder = (IFileInfoHolder)copyFilesItem.holder;
                  copyFilesSource      = holder.getFile();
                  copyFilesDestination = new File(copyFilesItem.destPath, copyFilesItem.destName);

                  fileGrid.setSelection(fileGrid.getItem(copyFilesIndex));
                  attachProgbarToItem(fileGrid.getItem(copyFilesIndex), fileGrid);
                  
                  progBarLabel.setText(copyFilesSource.getAbsolutePath()+" -> "+copyFilesDestination.getAbsolutePath());
              }
            });

            // Kopieren
            copyPercentTransfered = 0;
            if (!copyFilesEncode) {
              FastFileCopy fc = new FastFileCopy() {
                protected void setProgress(long overallBytes,
                                           long overallPercentage,
                                           long overallBytesTransfered, 
                                           long percentageOfOverallBytesTransfered) {
                  //System.out.printf("MediaSorter: overall bytes transfered: %s progress %s%%\n", overallBytesTransfered, percentageOfOverallBytesTransfered);
                  copyPercentTransfered = (int)percentageOfOverallBytesTransfered;
                  
                  // hier zweite progbar mit % angaben befeueren
                  Display.getDefault().syncExec(new Runnable() {
                    public void run() {
                      ((ProgressBar)editor.getEditor()).setSelection(copyPercentTransfered);
                    }
                  });
                }
              };
              fc.copy(copyFilesSource, copyFilesDestination, true);
            }
            // wenn Orientation-Flag, dann hier noch Bild drehen!
            // dreheBild();
            
            // nach dem Kopieren
            Display.getDefault().syncExec(new Runnable() {
              public void run() {
                  copyFilesItem.checked = false;
                  viewer.refresh(copyFilesItem);
                
                  ((ProgressBar)editor.getEditor()).setSelection(copyPercentTransfered);
                  allProgressBar.setSelection(copyFilesProgbar_i);
                  try {
                    Thread.sleep(100);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
              }
            });
      
            copyFilesProgbar_i++;
          }          

          Display.getDefault().syncExec(new Runnable() {
            public void run() {
              allProgressBar.setSelection(allProgressBar.getMaximum());
              fileGrid.setEnabled(true);
              editor.getEditor().dispose();
            }
          });
        };
    };
    thread.start();
  }

  private HashMap<String, String> createPatternMap(IFileInfoHolder holder) {
    HashMap<String, String> patternMap = new HashMap<String, String>();
    
    // special renamer
    String hName = holder.getFile().getName().substring(0, holder.getFile().getName().lastIndexOf("."));
    String hExt  = holder.getFile().getName().substring(holder.getFile().getName().lastIndexOf("."));
    
    String right8filename = hName.length() >= 8 ? hName.substring(hName.length()-8)+hExt : hName+hExt;
    patternMap.put("%r8filename%", right8filename);    
    
    // ...ende special
    Date date = (holder.getExposureDate() != null) ? holder.getExposureDate() : holder.getModifiedDate();
    
    // versuchen aus dem String ein Datum zu zaubern
    if (holder.getFile().getName().lastIndexOf("-") > -1) {
      String hOldDate =  holder.getFile().getName().substring(0, holder.getFile().getName().lastIndexOf("-"));
      if ((hOldDate.length() > 15) && (holder.getFile().getName().toLowerCase().endsWith("mkv")))
        hOldDate =  holder.getFile().getName().substring(0, holder.getFile().getName().lastIndexOf("."));
      
      if ((holder.getExposureDate() == null) && (hOldDate.length() > 12)) {
        try { 
          String[] sOldDates = hOldDate.split("-");
          Calendar cal = Calendar.getInstance(Locale.getDefault());
          if (sOldDates.length > 4) {
            int i = 0;
            for (String stag : sOldDates) {
              // Datum draus machen
              int value = Integer.parseInt(stag.trim()); 
              switch(i) {
                case 0 : cal.set(Calendar.YEAR, value);break;
                case 1 : cal.set(Calendar.MONTH, value-1);break;
                case 2 : cal.set(Calendar.DAY_OF_MONTH, value);break;
                case 3 : cal.set(Calendar.HOUR_OF_DAY, value);break;
                case 4 : cal.set(Calendar.MINUTE, value);break;
                case 5 : cal.set(Calendar.SECOND, value);break;
              }
    
              i++;
            }
          }
          else if (sOldDates.length == 4)
          {
            int i = 0;
            for (String stag : sOldDates) {
              // Datum draus machen
              switch(i) {
                case 0 : {
                  cal.set(Calendar.YEAR, Integer.parseInt(stag.trim().substring(0, 4)));
                  cal.set(Calendar.MONTH, Integer.parseInt(stag.trim().substring(4, 6))-1);
                  cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(stag.trim().substring(6, 8)));break;
                }
                case 1 : cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stag.trim()));break;
                case 2 : cal.set(Calendar.MINUTE, Integer.parseInt(stag.trim()));break;
                case 3 : cal.set(Calendar.SECOND, Integer.parseInt(stag.trim()));break;
              }
    
              i++;
            }
          }
          date = cal.getTime();
          if (holder.getModifiedDate().after(date)) {
            holder.setEstimatedDate(date);
          }
          else date = holder.getModifiedDate();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    
    patternMap.put("%year%", new SimpleDateFormat("yyyy").format(date));
    patternMap.put("%month%", new SimpleDateFormat("MM").format(date));
    patternMap.put("%day%", new SimpleDateFormat("dd").format(date));
    patternMap.put("%hour%",  new SimpleDateFormat("HH").format(date));
    patternMap.put("%minute%",  new SimpleDateFormat("mm").format(date));
    patternMap.put("%second%",  new SimpleDateFormat("ss").format(date));
    patternMap.put("%camera%",  holder.getCameraModel()!=null?holder.getCameraModel():"");
    
    if (
        (renameS3Chk.getSelection()) && 
        (isSamsung(holder.getFile().getName()))
       ) 
    {
      String camera = holder.getCameraModel()!=null ? holder.getCameraModel() : "SAMSUNG";
      if (holder.getFile().getName().length() > 22)
        patternMap.put("%filename%", camera+holder.getFile().getName().substring(19));
      else 
        patternMap.put("%filename%", camera+holder.getFile().getName().substring(15));
    }
    else
      patternMap.put("%filename%", holder.getFile().getName());
    
    patternMap.put("%extension%", holder.getFile().getName().substring(holder.getFile().getName().lastIndexOf(".")));
    
    String sepStr = File.separator;
    if (sepStr.equals("\\"))
        sepStr = "\\\\";
    patternMap.put("%dir%", sepStr);
    
    return patternMap;
  }
  
  private boolean isSamsung(String filename) {
    if (filename != null) { 
        if (
            (filename.length() > 22) &&
            (filename.charAt(4) == '-') &&
            (filename.charAt(7) == '-') &&
            (filename.charAt(10) == ' ') &&
            (filename.charAt(13) == '.') &&
            (filename.charAt(16) == '.')
           )
           return true;
    }
    if (filename != null) { 
      if (
          (filename.length() > 18) &&
          (filename.startsWith("20")) &&
          (filename.charAt(8) == '_') &&
          (filename.charAt(15) == '.')
         )
         return true;
    }
    return false;
  }
//  private boolean isSamsung(String cameraModel) {
//    if (cameraModel != null) { 
//        if ( 
//            (cameraModel.startsWith("GT-I93")) ||
//            (cameraModel.startsWith("GT-S56"))  
//           )
//           return true;
//    }
//    return false;
//  }
  
  private String replacePattern(String source, HashMap<String, String> patternMap) {
    String result = source;
    
    for (String key : patternMap.keySet()) {
      String value = patternMap.get(key);
      
      result = result.replaceAll(key, value);
    }
    
    return result;
  }
  
  private void checkFolder() {
    checker = new FolderChecker();

    checker.setInfoHolderTypes(new int[]{FILE_INFO, MP4_INFO, MTS_INFO, MOV_INFO, AVI_INFO, PICTURE_INFO, MKV_INFO});
    checker.setScanSubFolder(subFolderChk.getSelection());

    String startFolder = sourceFolder;

    File startFile = null;
    if (((startFile = new File(startFolder)) == null) || (!startFile.exists())) {
      System.err
          .print("Error: no source folde defined or source folder not exists");
    }
    checker.scan(startFile);
  }

  private void destBtnWidgetSelected(SelectionEvent evt) {
    destFolder = getDestPath();
    if (destFolder == null)
      return;
    
    destFolderEdit.setText(destFolder);
    if (fileGrid.getItems().length > 0)
      fillGrid();
  }
  
  private void clearBtnWidgetSelected(SelectionEvent evt) {
    System.out.println("clearBtn.widgetSelected, event="+evt);
    //TODO add your code for clearBtn.widgetSelected
  }

}


