package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeApp extends JFrame implements ActionListener {

    private JSlider slider;
    private JLabel from;
    private JLabel to;
    private JLabel amount;
    private JLabel conversion;
    private JLabel holder;
    private JButton calculateButton;
    private JTextField fromText;
    private JTextField toText;
    private JLabel updated;
    private int usd;

    public ExchangeApp() {

        JFrame frame = new JFrame("ExchangeApp");
        frame.setSize(500, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(new GridLayout(6, 3));

        from = new JLabel("From");
        from.setBounds(10,20,80,25);
        from.setHorizontalAlignment(JTextField.CENTER);

        fromText = new JTextField(20);
        fromText.setBounds(100,20,165,25);
        fromText.setText("United States");
        fromText.setEditable(false);

        to = new JLabel("To");
        to.setBounds(10,50,80,25);
        to.setHorizontalAlignment(JTextField.CENTER);

        toText = new JTextField(20);
        toText.setBounds(100,50,165,25);

        amount = new JLabel("Amount", JLabel.CENTER);

        slider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                amount.setText("" + ((JSlider)e.getSource()).getValue());
                usd = ((JSlider)e.getSource()).getValue();
            }
        });

        conversion = new JLabel("", JLabel.CENTER);

        holder = new JLabel("", JLabel.CENTER);

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(10, 80, 80, 25);
        calculateButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(10, 80, 80, 25);
        clearButton.addActionListener(this);

        updated = new JLabel("", JLabel.CENTER);

        JLabel dateUpdated = new JLabel("", JLabel.CENTER);

        panel.add(from);
        panel.add(fromText);
        panel.add(to);
        panel.add(toText);
        panel.add(amount);
        panel.add(slider);
        panel.add(conversion);
        panel.add(holder);
        panel.add(calculateButton);
        panel.add(clearButton);
        panel.add(updated);
        panel.add(dateUpdated);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Calculate")) {
            conversion.setText("" + usd + " United States dollar = ");
            bt_CalculateActionPerformed(e);
        }
        else {
            toText.setText("");
            conversion.setText("");
            holder.setText("");
            updated.setText("");
        }
    }

    private void bt_CalculateActionPerformed(java.awt.event.ActionEvent evt) {
        if(toText.getText().equals("")||toText.getText().equals(" ")){
            JOptionPane.showMessageDialog(rootPane, "Enter Country You Wish to Input", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            FileReader fr = null;
            try {
                File file = new File("C:\\Users\\Lorda\\IdeaProjects\\ExchangeApp\\src\\com\\company\\foreign_exchange_rates.txt");
                fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                ArrayList<String> elements = new ArrayList<String>();
                String lineJustFetched = null;
                String[] tokens;

                while(true){
                    lineJustFetched = br.readLine();
                    if(lineJustFetched == null){
                        break;
                    }else{
                        tokens = lineJustFetched.split("\t");
                        for(String each : tokens){
                            if(!"".equals(each)){
                                elements.add(each);
                            }
                        }
                    }
                }

                br.close();

                String country = toText.getText();
                double conv = 0f;
                String currency = "";
                String date = "";

                for(int i = 5; i < elements.size(); i++){
                    if(elements.get(i).equals(country)){
                        conv = Double.parseDouble(elements.get(i+3));
                        currency = elements.get(i+1);
                        date = elements.get(i+4).toString();
                    }
                }

                //int usdInt = Integer.parseInt(usd);
                double converted = usd * conv;
                String usdString = Integer.toString(usd);
                String convertedString = String.valueOf(converted);

                conversion.setText(usdString + " United States dollar = ");
                holder.setText(convertedString + " " + currency);
                updated.setText("Last Updated: " + date);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExchangeApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ExchangeApp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExchangeApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public static void main(String[] args) {

        ExchangeApp app = new ExchangeApp();

        //app.setVisible(true);

    }
}