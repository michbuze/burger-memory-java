/*
 * Memory Burger
 * (C) Michel Buze 2012
 * Free game under GNU Public License (GPL)
 */
package Burger;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.MessageBox;

public class Main {

  static Display display;
  static Shell shell;
  static ResourceBundle rb;
  static ClassLoader cl;
    
  static Label client1;
  static Label order1;
  static int random1;
  static Label client2;
  static Label order2;
  static int random2;    

  static Label stepLabel;
  static int step = 0;
  static int orders[] = new int[99];
  static Random rand = new Random(); 

  final static Button button[] = new Button[9];
    
  static Menu menuBar;
  static MenuItem langMenuHeader;
  static MenuItem helpMenuAbout;
  
  static String[] words = {
    "burger", //0
    "pizza", //1
    "fries", //2
    "soda", //3
    "hotdog", //4
    "icecream", //5
    "donut", //6
    "icepop", //7
    "tart"}; //8
      
  static Listener listener = new Listener() {

    public void handleEvent(Event event) {
      Button button = (Button) event.widget;

      if (button.getText().equals(rb.getString(words[orders[step - 1]])) == false) {
        gameover((String) button.getData());
        MessageBox box = new MessageBox(shell, SWT.OK);
        box.setMessage(rb.getString("replay"));
        box.open();
        step = 0;
        do_order();
      } else {
        do_order();
      }
    }
  };  

  public static void do_order() {
    step++;
    random1 = rand.nextInt(9);
    random2 = rand.nextInt(9);
    orders[2 * step - 2] = random1;
    orders[2 * step - 1] = random2;
  
    Image icon1 = new Image(display, new ImageData(cl.getResourceAsStream((random1+".gif"))));
    //Image icon1 = new Image(display, ClassLoader.getSystemResourceAsStream(random1+".gif"));      
    client1.setImage(icon1);
    order1.setText("\n\n" + rb.getString("client") + " " + ((2 * step - 2) + 1)  + " " + rb.getString("order") + " " + rb.getString(words[random1]) + ".");
    
    Image icon2 = new Image(display, new ImageData(cl.getResourceAsStream((random2+".gif"))));
    //Image icon2 = new Image(display, ClassLoader.getSystemResourceAsStream(random2+".gif"));      
    client2.setImage(icon2);
    order2.setText(rb.getString("client") + " " + ((2 * step - 1) + 1)  + " " + rb.getString("order") + " " + rb.getString(words[random2]) + ".");
    
    stepLabel.setText(rb.getString("ask") + " " + step + rb.getString("qmark"));      
  }

  public static void gameover(String served) {
	Image icon1 = new Image(display, new ImageData(cl.getResourceAsStream((orders[step - 1]+".gif"))));
    //Image icon1 = new Image(display, ClassLoader.getSystemResourceAsStream(orders[step - 1]+".gif"));      
    client1.setImage(icon1);
    //Font font1 = new Font(display,"Arial",11,SWT.NORMAL); 
    //order1.setFont(font1);    
    order1.setText(rb.getString("gameover") + "\n"  + rb.getString("client") + " " + step + " " + rb.getString("wanted") + " :\n" + rb.getString(words[orders[step - 1]]) + ".");

    Image icon2 = new Image(display, new ImageData(cl.getResourceAsStream((Integer.parseInt(served)+".gif"))));
    //Image icon2 = new Image(display, ClassLoader.getSystemResourceAsStream(Integer.parseInt(served)+".gif"));      
    client2.setImage(icon2);    
    order2.setText(rb.getString("served") + " " + rb.getString(words[Integer.parseInt(served)]) + ".");
    
    stepLabel.setText("");
  }

  public static void change_lang(Locale loc) {
    rb = ResourceBundle.getBundle("burger", loc);
    langMenuHeader.setText(rb.getString("lang"));
    helpMenuAbout.setText(rb.getString("About"));
    for (int i = 0; i < 9; i++)
    {        
      button[i].setText(rb.getString(words[i]));
    }    
    order1.setText("\n\n" + rb.getString("client") + " " + ((2 * step - 2) + 1)  + " " + rb.getString("order") + " " + rb.getString(words[random1]) + ".");    
    order2.setText(rb.getString("client") + " " + ((2 * step - 1) + 1)  + " " + rb.getString("order") + " " + rb.getString(words[random2]) + ".");    
    stepLabel.setText(rb.getString("ask") + " " + step + rb.getString("qmark"));        
  }
  
  /**
   * Main method for Configuration_burger module
   * @param args Not used
   */  
  public static void main(String[] args) {
    final Locale FRENCH = new Locale("fr","","");
    final Locale ENGLISH = new Locale("en","","");
    final Locale SPANISH = new Locale("es","","");

    // Draw GUI
    display = new Display();
    shell = new Shell(display);
    shell.setText("Memory Burger");

    rb = ResourceBundle.getBundle("burger", FRENCH);
    cl  = Thread.currentThread().getContextClassLoader();
        
    menuBar = new Menu(shell, SWT.BAR);

    // Menu item Lang
    langMenuHeader = new MenuItem(menuBar, SWT.CASCADE, 0);
    langMenuHeader.setText(rb.getString("lang"));

    // sub menu Lang
    Menu langMenu = new Menu(shell, SWT.DROP_DOWN);
    langMenuHeader.setMenu(langMenu);
    
    MenuItem langMenuFrench = new MenuItem(langMenu, SWT.PUSH);
    langMenuFrench.setText("Français");
    
    
    langMenuFrench.setImage(new Image(shell.getDisplay(), new ImageData(cl.getResourceAsStream("flags_fr.png"))));
    //langMenuFrench.setImage(new Image(shell.getDisplay(), ClassLoader.getSystemResourceAsStream("flags_fr.png")));
    langMenuFrench.addSelectionListener(new SelectionListener() {

      public void widgetSelected(SelectionEvent e) {
        change_lang(FRENCH);
      }
      public void widgetDefaultSelected(SelectionEvent e) {
        change_lang(FRENCH);
      }
    });

    MenuItem langMenuEnglish = new MenuItem(langMenu, SWT.PUSH);
    langMenuEnglish.setText("English");
    langMenuEnglish.setImage(new Image(shell.getDisplay(), new ImageData(cl.getResourceAsStream("flags_en.png"))));
    //langMenuEnglish.setImage(new Image(shell.getDisplay(), ClassLoader.getSystemResourceAsStream("flags_en.png")));
    langMenuEnglish.addSelectionListener(new SelectionListener() {

      public void widgetSelected(SelectionEvent e) {
        change_lang(ENGLISH);
      }
      public void widgetDefaultSelected(SelectionEvent e) {
        change_lang(ENGLISH);
      }
    });

    MenuItem langMenuSpanish = new MenuItem(langMenu, SWT.PUSH);
    langMenuSpanish.setText("Español");
    langMenuSpanish.setImage(new Image(shell.getDisplay(), new ImageData(cl.getResourceAsStream("flags_es.png"))));
    //langMenuSpanish.setImage(new Image(shell.getDisplay(), ClassLoader.getSystemResourceAsStream("flags_es.png")));
    langMenuSpanish.addSelectionListener(new SelectionListener() {

      public void widgetSelected(SelectionEvent e) {
        change_lang(SPANISH);
      }
      public void widgetDefaultSelected(SelectionEvent e) {
        change_lang(SPANISH);
      }
    });
    
    // Menu item Help
    MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE, 1);
    helpMenuHeader.setText("?");
    
    // sub menu Help
    Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
    helpMenuHeader.setMenu(helpMenu);

    helpMenuAbout = new MenuItem(helpMenu, SWT.PUSH);
    helpMenuAbout.setText(rb.getString("About"));
    helpMenuAbout.setImage(new Image(shell.getDisplay(), new ImageData(cl.getResourceAsStream("information.png"))));
    //helpMenuAbout.setImage(new Image(shell.getDisplay(), ClassLoader.getSystemResourceAsStream("information.png")));

    helpMenuAbout.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        widgetDefaultSelected(e);
      }
      public void widgetDefaultSelected(SelectionEvent e) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK | SWT.RETRY | SWT.IGNORE);
        messageBox.setText("Memory Burger");
        messageBox.setMessage("Memory Burger\n\nFree game under GNU public licence (GPL)\n\n(C) Michel Buze 2012");
        messageBox.open();
      }
    });

    shell.setMenuBar(menuBar);    
    GridData gridData;      
    GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    layout.makeColumnsEqualWidth = true;
    shell.setLayout(layout);
      
    order1 = new Label(shell, SWT.NONE);
    Font font1 = new Font(display,"Arial",16,SWT.NORMAL); 
    order1.setFont(font1);
    gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    gridData.horizontalSpan = 2;
    gridData.widthHint = 400;
    gridData.heightHint = 100;
    order1.setLayoutData(gridData);

    client1 = new Label(shell, SWT.NONE);
    gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    client1.setLayoutData(gridData);
    
    order2 = new Label(shell, SWT.NONE);
    Font font2 = new Font(display,"Arial",16,SWT.NORMAL); 
    order2.setFont(font2);
    
    gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    gridData.horizontalSpan = 2;
    gridData.widthHint = 400;
    order2.setLayoutData(gridData);

    client2 = new Label(shell, SWT.NONE);
    gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    client2.setLayoutData(gridData);
    
    stepLabel = new Label(shell, SWT.NONE);
    Font font3 = new Font(display,"Arial",16,SWT.NORMAL); 
    stepLabel.setFont(font3);

    gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    gridData.horizontalSpan = 3;
    gridData.widthHint = 400;
    stepLabel.setLayoutData(gridData);
    
    for (int i = 0; i < 9; i++) {        
      button[i] = new Button(shell, SWT.PUSH);
      button[i].setData(Integer.toString(i));
      Image icon = new Image(shell.getDisplay(), new ImageData(cl.getResourceAsStream(i+".gif")));
      //Image icon = new Image(display, ClassLoader.getSystemResourceAsStream(i+".gif"));
      button[i].setText(rb.getString(words[i]));
      button[i].setImage(icon);
      gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
      gridData.widthHint = 200;
      button[i].setLayoutData(gridData);
      button[i].addListener(SWT.Selection, listener);
    }
    
    do_order();
    // Draw the window
    shell.pack();
    shell.open();

    // SWT loop to catch event
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    // Stop the application
    display.dispose();
  }
}
