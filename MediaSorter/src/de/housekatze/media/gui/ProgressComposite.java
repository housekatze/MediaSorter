package de.housekatze.media.gui;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ProgressComposite extends org.eclipse.swt.widgets.Composite {
  private ProgressBar partProgress;
  private ProgressBar allProgress;

  /**
  * Auto-generated main method to display this 
  * org.eclipse.swt.widgets.Composite inside a new Shell.
  */
  public static void main(String[] args) {
    showGUI();
  }
  
  /**
  * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
  */  
  protected void checkSubclass() {
  }
  
  /**
  * Auto-generated method to display this 
  * org.eclipse.swt.widgets.Composite inside a new Shell.
  */
  public static void showGUI() {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    ProgressComposite inst = new ProgressComposite(shell, SWT.NULL);
    Point size = inst.getSize();
    shell.setLayout(new FillLayout());
    shell.layout();
    if(size.x == 0 && size.y == 0) {
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

  public ProgressComposite(org.eclipse.swt.widgets.Composite parent, int style) {
    super(parent, style);
    initGUI();
  }

  private void initGUI() {
    try {
      this.setLayout(new FormLayout());
      this.layout();
      pack();
      this.setSize(600, 250);
      {
        partProgress = new ProgressBar(this, SWT.NONE);
        FormData partProgressLData = new FormData();
        partProgressLData.left =  new FormAttachment(0, 1000, 17);
        partProgressLData.top =  new FormAttachment(0, 1000, 75);
        partProgressLData.width = 560;
        partProgressLData.height = 17;
        partProgress.setLayoutData(partProgressLData);
      }
      {
        allProgress = new ProgressBar(this, SWT.NONE);
        FormData allProgressLData = new FormData();
        allProgressLData.left =  new FormAttachment(0, 1000, 17);
        allProgressLData.top =  new FormAttachment(0, 1000, 34);
        allProgressLData.width = 560;
        allProgressLData.height = 17;
        allProgress.setLayoutData(allProgressLData);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
