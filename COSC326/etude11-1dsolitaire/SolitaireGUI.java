import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SolitaireGUI extends JFrame {
    private SolitairePanel PegGenPanel;
    DefaultTableModel model = new DefaultTableModel();

    public SolitaireGUI() {
        setTitle("1-D Solitaire Solver");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        PegGenPanel = new SolitairePanel();
        add(PegGenPanel);
        initializeListeners();
    }

    private void initializeListeners() {
        // Get the calculate button from SolitairePanel and add action listener
        PegGenPanel.getCalculateButton().addActionListener(e -> {
            String input = PegGenPanel.getPegsField().getText();
            int targetNum;
            try {
                targetNum = Integer.parseInt(input);
                if (targetNum < 1) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SolitaireGUI.this, "Please enter a valid number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (PegGen.getWins() == null) {
                PegGen.initWins(); // Initialize wins ArrayList in PegGen class
            }
            PegGenPanel.getResultArea().setText("Fetching results... Please wait.");
            PegGen.calculateWins(targetNum); // Use PegGen class to calculate wins
         //   System.out.println("The number of solvable states with " + targetNum + " pegs is : "
               //     + PegGen.getWins().get(targetNum - 1).size() + "\nLeading and trailing blanks are removed");

            StringBuilder resultBuilder = new StringBuilder();

            model.addColumn("Solvable States");

            // while(PegGenPanel.getResultArea().getText().length() < 1) {
            // PegGenPanel.getResultArea().setText("Fetching results... Please wait.");
            // }
            TreeSet<StringBuilder> startingPositionsTS = PegGen.getWins().get(targetNum - 1);
            ArrayList<String> winnableStartingPos = new ArrayList<String>();
            for (StringBuilder eachSB : startingPositionsTS) {
                winnableStartingPos.add(eachSB.toString());
            }
            Collections.sort(winnableStartingPos, new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    if (str1.length() > str2.length()) {
                        return -1;
                    } else if (str1.length() < str2.length()) {
                        return 1;
                    } else {
                        int numOfPegs1 = 0;
                        int numOfPegs2 = 0;
                        for (int i = 0; i < str1.length() / 2; i++) {

                            if (str1.charAt(i) == '●') {
                                numOfPegs1++;
                            }
                            if (str2.charAt(i) == '●') {
                                numOfPegs2++;
                            }
                        }

                        if (numOfPegs1 > numOfPegs2) {
                            return -1;
                        } else if (numOfPegs1 < numOfPegs2) {
                            return 1;
                        } else {
                            return 0;

                        }
                    }
                }
            });
            for (String startingPos : winnableStartingPos) {
                resultBuilder.append(startingPos).append("\n"); // Append each entry followed by a new line
                    model.addRow(new Object[] { startingPos });
                
  //              System.out.println(startingPos);
                // i++;

            }

            // Set the text of the result area with the StringBuilder content
            PegGenPanel.getResultLabel().setText("Results: There are " + PegGen.getWins().get(targetNum - 1).size()
                    + " distinct states with " + targetNum + " pegs");
            PegGenPanel.getResultArea().setText(resultBuilder.toString());

 //           System.out.println(model);

        });

        PegGenPanel.getExportButton().addActionListener(e -> {
            String currentResult = PegGenPanel.getResultArea().getText();

            exportTableDataToCSV(currentResult);
        });

    }

    private void exportTableDataToCSV(String model) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(SolitaireGUI.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.endsWith(".csv")) {
                filename += ".csv";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                // header
                writer.write("Solvable States");
                writer.newLine();

                // for (int i = 0; i < model.getRowCount(); i++) {
                //     writer.write(model.getValueAt(i, 0).toString());

                //     writer.newLine();
                // }
                String [] lines = model.split("\n");
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }


                JOptionPane.showMessageDialog(SolitaireGUI.this, "CSV file exported successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SolitaireGUI.this, "Error exporting data.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SolitaireGUI gui = new SolitaireGUI();
            gui.setVisible(true);
        });
    }
}
