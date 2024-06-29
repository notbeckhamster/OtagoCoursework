import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Creates GUI Frame and intializes DrawingPanel
 * @author Beckham Wilson 7564267
 */
public class QuiltFrame extends JFrame implements ActionListener {
    private DrawingPanel drawingPanel;


    
    public QuiltFrame(double[] scalingArr, Color[] depthToColorArr) {
        super("Quilt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) screenSize.getHeight();
       
        //Add quilt drawings to frame
        drawingPanel = new DrawingPanel(height - 100, scalingArr, depthToColorArr);
        getContentPane().add(drawingPanel);

        //Add save button
        JMenuBar menuBar = new JMenuBar();
        JMenuItem saveImageItem = new JMenuItem("Save");
        saveImageItem.addActionListener(this);
        menuBar.add(saveImageItem);
        setJMenuBar(menuBar);
        // Pack the frame to the drawing panel size
        pack();
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Save_Image");

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                saveImage(imageFilepath);
            } catch (Exception ex) {
                JOptionPane optionPane = new JOptionPane("Unable to save " + ex,
                        JOptionPane.ERROR_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new String[] { "Ok" });
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
            }
        }

    }

    public void saveImage(String imageFileName) throws Exception {

        if (imageFileName.lastIndexOf(".") == -1) {
            imageFileName += "." + "jpg";
        }

        String extension = imageFileName.substring(1 + imageFileName.lastIndexOf(".")).toLowerCase();

        boolean result = ImageIO.write(drawingPanel.getImage(), extension, new File(imageFileName));
        if (result == false) {
            throw new Exception("Error writing image to file");

        }

    }
}
