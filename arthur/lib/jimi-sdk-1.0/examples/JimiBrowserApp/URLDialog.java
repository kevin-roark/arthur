import java.awt.*;
import java.awt.event.*;

class URLDialog extends Dialog implements ActionListener
{
    private JimiBrowserApp app;
    private Frame owner;
    private TextField txtUrlfield;
    private Label lblDescription;
    private Button btnOk, btnCancel;

    public URLDialog(Frame owner, JimiBrowserApp app)
    {
        super(owner, "Open from URL");
        this.owner = owner;
        this.app = app;

        this.setLayout(new BorderLayout(0,0));

        Panel north = new Panel();
		north.setBackground(SystemColor.window);
		north.setForeground(SystemColor.desktop);

        lblDescription = new Label("Image URL");
        north.add(lblDescription);
        txtUrlfield = new TextField(25);
		txtUrlfield.addActionListener(this);
        north.add(txtUrlfield);
        this.add(north, BorderLayout.NORTH);

        Panel south = new Panel();
		south.setBackground(SystemColor.window);
		south.setForeground(SystemColor.desktop);

        btnOk = new Button("Ok");
        south.add(btnOk);
        btnOk.addActionListener(this);
        btnCancel = new Button("Cancel");
        south.add(btnCancel);
        btnCancel.addActionListener(this);

        this.add(south, BorderLayout.CENTER);
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e)
    {
		String url = txtUrlfield.getText();
		url = validateInput(url);
		this.setVisible(false);
		//                app.setStatus("Loading "+url+" ... please wait!");
		// open/load image
		app.openImage(url);
		// clean textfield
		txtUrlfield.setText("");
    }
	
    private String validateInput(String input)
    {
        boolean http = input.startsWith("http://");
        boolean ftp  = input.startsWith("ftp://");
		if(http || ftp) { return input; }
		else { return "http://" + input; }
    }

    public void center()
    {
        Point pos = owner.getLocationOnScreen();
        int ownerHeight = owner.getSize().height;
        int ownerWidth = owner.getSize().width;

        int cx = pos.x + (ownerWidth / 2 - (this.getSize().width / 2));
        int cy = pos.y + (ownerHeight /2 - (this.getSize().height / 2));

        this.setLocation(cx, cy);
    }
}
