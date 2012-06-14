package Burger;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.MessageBox;

public class Main {

	static Display display;
    static Shell shell;
    static ResourceBundle rb;
    
    static Label client1;
    static Label order1;
    static Label client2;
    static Label order2;

    static Label stepLabel;
	static int step = 0;
	static int orders[] = new int[99];
	static Random rand = new Random(); 
	
    static String[] words = {
    		"burger",
            "pizza",
            "fries",
            "soda",
            "hotdog",
            "icecream",
            "donut",
            "icepop",
            "tart"};
  
    
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

public static void do_order()
{
	step++;
	int random1 = rand.nextInt(9);
	int random2 = rand.nextInt(9);
	orders[2 * step - 2] = random1;
	orders[2 * step - 1] = random2;
	
    Image icon1 = new Image(display, ClassLoader.getSystemResourceAsStream(random1+".gif"));	    
    client1.setImage(icon1);
    order1.setText("\n\n" + rb.getString("client") + " " + ((2 * step - 2) + 1)  + " " + rb.getString("order") + " " + rb.getString(words[random1]) + ".");
    
    Image icon2 = new Image(display, ClassLoader.getSystemResourceAsStream(random2+".gif"));	    
    client2.setImage(icon2);
    order2.setText(rb.getString("client") + " " + ((2 * step - 1) + 1)  + " " + rb.getString("order") + " " + rb.getString(words[random2]) + ".");
    
    stepLabel.setText(rb.getString("ask") + " " + step + rb.getString("qmark"));      
}

public static void gameover(String served)
{
    Image icon1 = new Image(display, ClassLoader.getSystemResourceAsStream(orders[step - 1]+".gif"));	    
    client1.setImage(icon1);
	//Font font1 = new Font(display,"Arial",11,SWT.NORMAL); 
	//order1.setFont(font1);    
    order1.setText(rb.getString("gameover") + "\n"  + rb.getString("client") + " " + step + " " + rb.getString("wanted") + " :\n" + rb.getString(words[orders[step - 1]]) + ".");
    
    Image icon2 = new Image(display, ClassLoader.getSystemResourceAsStream(Integer.parseInt(served)+".gif"));	    
    client2.setImage(icon2);    
    order2.setText(rb.getString("served") + " " + rb.getString(words[Integer.parseInt(served)]) + ".");
    
    stepLabel.setText("");
}

	/**
	 * Main method for Configuration_burger module
	 * @param args Not used
	 */	
	public static void main(String[] args) {
		rb = ResourceBundle.getBundle("burger");

		// Draw GUI
		display = new Display();
		shell = new Shell(display);
		shell.setText("Burger Memory");
		
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
		
	    for (int i = 0; i < 9; i++)
	    {
	    Button button = new Button(shell, SWT.PUSH);
	    button.setData(Integer.toString(i));
	    Image icon = new Image(display, ClassLoader.getSystemResourceAsStream(i+".gif"));
	    button.setText(rb.getString(words[i]));
	    button.setImage(icon);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 200;
		button.setLayoutData(gridData);
		button.addListener(SWT.Selection, listener);
	    }
		
	    do_order();
		// Draw the window
		shell.pack();
		shell.open();

		// SWT loop to catch event
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// Stop the application
		display.dispose();
	}
}	