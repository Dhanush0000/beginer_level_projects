package com.practise3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWindow extends JFrame {
    private JLabel heading;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private Font font = new Font("Arial", Font.BOLD, 40); // Larger font for better visibility
    private Font dateFont = new Font("Arial", Font.PLAIN, 24); // Font for date with smaller size

    MyWindow() {
        super.setTitle("My Digital Clock");
        super.setSize(600, 300); // Larger window size for a better experience
        super.setLocationRelativeTo(null); // Center the window on the screen
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the program when the window is closed
        createGUI(); // Create the GUI components
        this.startClock(); // Start the clock functionality
        super.setVisible(true); // Make the window visible
    }

    public void createGUI() {
        // Initialize heading and labels
        heading = new JLabel("Digital Clock");
        heading.setFont(new Font("Arial", Font.BOLD, 50)); // Bold and large font for heading
        heading.setHorizontalAlignment(SwingConstants.CENTER); // Center the heading text

        dateLabel = new JLabel(""); // For displaying the current date
        timeLabel = new JLabel(""); // For displaying the current time
        dateLabel.setFont(dateFont); // Set the date label font
        timeLabel.setFont(font); // Set the time label font

        // Set background color and text color for a better look
        heading.setForeground(Color.WHITE); // White color for heading
        dateLabel.setForeground(Color.LIGHT_GRAY); // Light gray for the date
        timeLabel.setForeground(Color.CYAN); // Cyan color for the time (makes it stand out)

        // Set background color for the entire frame
        this.getContentPane().setBackground(Color.BLACK); // Set a black background for the window

        // Set layout for the JFrame
        this.setLayout(new BorderLayout());

        // Add components to the window
        this.add(heading, BorderLayout.NORTH);
        this.add(dateLabel, BorderLayout.CENTER);
        this.add(timeLabel, BorderLayout.SOUTH);
    }

    // Method to start the clock and update the time and date every second
    public void startClock() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a SimpleDateFormat to format the date and time
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy"); // Day, Month, Date, Year
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a"); // Time format with AM/PM

                // Get current date and time
                String date = dateFormat.format(new Date());
                String time = timeFormat.format(new Date());

                // Update the labels with current date and time
                dateLabel.setText(date); // Set the date
                timeLabel.setText(time); // Set the time
            }
        });
        timer.start(); // Start the timer
    }

    public static void main(String[] args) {
        new MyWindow(); // Run the application
    }
}
