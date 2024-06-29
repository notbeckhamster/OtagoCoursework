import javax.swing.*;
import java.awt.*;

public class SolitairePanel extends JPanel {
    private JButton calculateButton;
    private JTextField pegsField;
    private JTextArea resultArea;
    private JLabel resultLabel;
    public JTable table;
    private JButton exportButton;

    public SolitairePanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel pegsLabel = new JLabel("Enter number of pegs:");
        pegsField = new JTextField(10);
        calculateButton = new JButton("Calculate");
        exportButton = new JButton("Export");

        inputPanel.add(pegsLabel);
        inputPanel.add(pegsField);
        inputPanel.add(calculateButton);
        inputPanel.add(exportButton);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());

         resultLabel = new JLabel("Results:");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

         table = new JTable();
        resultPanel.add(table, BorderLayout.SOUTH);

        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }
    public JButton getCalculateButton() {
        return calculateButton;
    }

    public JTextField getPegsField() {
        return pegsField;
    }
    public JLabel getResultLabel() {
        return resultLabel;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JTextArea getResultArea() {
        return resultArea;
    }
}
