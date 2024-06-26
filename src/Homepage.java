package src;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;

public class Homepage extends JFrame implements MouseListener, MouseMotionListener {

    private JPanel sidebar;
    private JLabel greetingLabel;
    private int mouseX, mouseY;

    public Homepage(String username) {
        // Set frame properties
        setTitle("Homepage");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Turn off the window navigation

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Create top bar panel
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(224, 31, 147));
        topBar.addMouseListener(this);
        topBar.addMouseMotionListener(this);
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Create close button
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.BLACK);
        closeButton.setBackground(new Color(224, 31, 147));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> System.exit(0)); // Close application on click

        // Add close button to top bar
        topBar.add(closeButton, BorderLayout.EAST);

        // Create sidebar panel
        sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new ShadowBorder());

        // Create greeting label
        greetingLabel = new JLabel("Hi Traveler! " + username, SwingConstants.CENTER);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons for the sidebar
        JButton homepageButton = createSidebarButton("HOMEPAGE");
        JButton createItineraryButton = createSidebarButton("CREATE ITINERARY");
        createItineraryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the Create Itinerary window
                new CreateItinerary().setVisible(true);
                dispose(); // Close the current window
            }
        });
        JButton historyButton = createSidebarButton("HISTORY");
        JButton logoutButton = createSidebarButton("LOGOUT");
        logoutButton.addActionListener(e -> logout());

        // Add components to the sidebar
        sidebar.add(greetingLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        sidebar.add(homepageButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        sidebar.add(createItineraryButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        sidebar.add(historyButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        sidebar.add(logoutButton);

        // Add sidebar and main content to the main panel
        mainPanel.add(sidebar, BorderLayout.WEST);

        // Create the main content panel
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create travel plan panels
        JPanel travelPlanPanel1 = createTravelPlanPanel("Travel Plan 1");
        JPanel travelPlanPanel2 = createTravelPlanPanel("Travel Plan 2");
        JPanel travelPlanPanel3 = createTravelPlanPanel("Travel Plan 3");

        // Add travel plan panels to the main content
        mainContent.add(travelPlanPanel1);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        mainContent.add(travelPlanPanel2);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        mainContent.add(travelPlanPanel3);

        mainPanel.add(mainContent, BorderLayout.CENTER);

        // Create toggle sidebar button
        JButton toggleSidebarButton = new JButton("=");
        toggleSidebarButton.setForeground(Color.BLACK);
        toggleSidebarButton.setBackground(new Color(224, 31, 147));
        toggleSidebarButton.setFocusPainted(false);
        toggleSidebarButton.setBorderPainted(false);
        toggleSidebarButton.setFont(new Font("Arial", Font.BOLD, 16));
        toggleSidebarButton.addActionListener(e -> toggleSidebar());

        // Add toggle button to top bar
        topBar.add(toggleSidebarButton, BorderLayout.WEST);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBackground(new Color(252, 171, 78));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createTravelPlanPanel(String planName) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(500, 100));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(planName, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                travelPlanClicked(planName);
            }
        });

        return panel;
    }

    private void travelPlanClicked(String planName) {
        JOptionPane.showMessageDialog(this, "You clicked on: " + planName, "Travel Plan Clicked", JOptionPane.INFORMATION_MESSAGE);
        // Perform additional actions, like opening a detailed view of the travel plan
    }

    private void toggleSidebar() {
        sidebar.setVisible(!sidebar.isVisible());
        revalidate();
        repaint();
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Logout successful", "Notification", JOptionPane.INFORMATION_MESSAGE);
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true); 
            dispose();
        });
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // MouseMotionListener method
    @Override
    public void mouseDragged(MouseEvent e) {
        int newX = e.getXOnScreen() - mouseX;
        int newY = e.getYOnScreen() - mouseY;
        setLocation(newX, newY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public static void main(String[] args) {
        // For demonstration, replace this with the actual username obtained from login
        String username = "John Doe";
        SwingUtilities.invokeLater(() -> {
            new Homepage(username).setVisible(true);
        });
    }

    // Custom Border for shadow effect
    class ShadowBorder extends AbstractBorder {
        private static final long serialVersionUID = 1L;
        private final int shadowSize = 5;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color shadowColor = new Color(0, 0, 0, 50);
            for (int i = 0; i < shadowSize; i++) {
                g2d.setColor(shadowColor);
                g2d.drawRoundRect(x + i, y + i, width - i * 2 - 1, height - i * 2 - 1, 12, 12);
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize, shadowSize, shadowSize, shadowSize);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = shadowSize;
            return insets;
        }
    }
}
