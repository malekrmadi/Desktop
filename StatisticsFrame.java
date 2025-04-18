import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class StatisticsFrame extends JFrame {
    private JButton backButton;
    
    public StatisticsFrame() {
        setTitle("Rental System Statistics");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with gradient
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(245, 245, 250), 0, getHeight(), new Color(235, 235, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("System Statistics");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 100));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Back button
        backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> backToDashboard());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Statistics Panel (with tabs)
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Revenue Statistics Tab
        JPanel revenuePanel = new JPanel(new BorderLayout());
        revenuePanel.setOpaque(false);
        revenuePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel revenueChartPanel = createBarChart("Monthly Revenue", 
            new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, 
            new int[]{4250, 3850, 4100, 4700, 5200, 5800, 6200, 6100, 5600, 4900, 4200, 3850},
            new Color(0, 123, 255));
        revenuePanel.add(revenueChartPanel, BorderLayout.CENTER);
        
        tabbedPane.addTab("Monthly Revenue", revenuePanel);
        
        // Client Statistics Tab
        JPanel clientPanel = new JPanel(new BorderLayout());
        clientPanel.setOpaque(false);
        clientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel clientChartPanel = createBarChart("Top Clients by Revenue", 
            new String[]{"Dupont Jean", "Martin Sophie", "Bernard Michel", "Petit Laura", "Robert Thomas"}, 
            new int[]{8400, 7600, 6900, 5800, 5200},
            new Color(40, 167, 69));
        clientPanel.add(clientChartPanel, BorderLayout.CENTER);
        
        tabbedPane.addTab("Top Clients", clientPanel);
        
        // Apartment Statistics Tab
        JPanel apartmentPanel = new JPanel(new BorderLayout());
        apartmentPanel.setOpaque(false);
        apartmentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel apartmentChartPanel = createPieChart("Apartment Distribution by Type",
            new String[]{"T2 Apartments", "T3 Apartments", "T4 Apartments", "Studios"},
            new int[]{35, 30, 20, 15});
        apartmentPanel.add(apartmentChartPanel, BorderLayout.CENTER);
        
        tabbedPane.addTab("Apartment Occupancy", apartmentPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Total Revenue Card
        JPanel revenueCard = createStatCard("Total Annual Revenue", "$56,750", new Color(40, 167, 69));
        summaryPanel.add(revenueCard);
        
        // Average Occupancy Card
        JPanel occupancyCard = createStatCard("Average Occupancy Rate", "78.5%", new Color(0, 123, 255));
        summaryPanel.add(occupancyCard);
        
        // Client Retention Card
        JPanel retentionCard = createStatCard("Client Retention Rate", "92%", new Color(255, 193, 7));
        summaryPanel.add(retentionCard);
        
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout(5, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(color);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBarChart(String title, String[] labels, int[] values, Color barColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw background
                g2d.setColor(new Color(250, 250, 255));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw title
                g2d.setColor(new Color(60, 60, 100));
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                g2d.drawString(title, 20, 30);
                
                // Get max value for scaling
                int maxValue = 0;
                for (int value : values) {
                    maxValue = Math.max(maxValue, value);
                }
                
                // Draw bars
                int barWidth = (getWidth() - 100) / values.length;
                int maxBarHeight = getHeight() - 100;
                
                for (int i = 0; i < values.length; i++) {
                    int barHeight = (int)((double)values[i] / maxValue * maxBarHeight);
                    
                    g2d.setColor(barColor);
                    g2d.fillRect(50 + i * barWidth, getHeight() - 50 - barHeight, 
                        barWidth - 10, barHeight);
                    
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    
                    // Draw label
                    String label = labels[i];
                    if (label.length() > 10) {
                        label = label.substring(0, 7) + "...";
                    }
                    FontMetrics fm = g2d.getFontMetrics();
                    int labelWidth = fm.stringWidth(label);
                    
                    g2d.drawString(label, 50 + i * barWidth + (barWidth - 10) / 2 - labelWidth / 2, 
                        getHeight() - 30);
                    
                    // Draw value
                    String valueStr = String.valueOf(values[i]);
                    int valueWidth = fm.stringWidth(valueStr);
                    
                    g2d.drawString(valueStr, 50 + i * barWidth + (barWidth - 10) / 2 - valueWidth / 2, 
                        getHeight() - 50 - barHeight - 5);
                }
                
                // Draw axes
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawLine(40, 50, 40, getHeight() - 40);
                g2d.drawLine(40, getHeight() - 40, getWidth() - 40, getHeight() - 40);
            }
        };
        
        panel.setPreferredSize(new Dimension(800, 500));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        return panel;
    }
    
    private JPanel createPieChart(String title, String[] labels, int[] values) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw background
                g2d.setColor(new Color(250, 250, 255));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw title
                g2d.setColor(new Color(60, 60, 100));
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                g2d.drawString(title, 20, 30);
                
                // Calculate total
                int total = 0;
                for (int value : values) {
                    total += value;
                }
                
                // Define colors
                Color[] colors = {
                    new Color(255, 99, 132),
                    new Color(54, 162, 235),
                    new Color(255, 206, 86),
                    new Color(75, 192, 192),
                    new Color(153, 102, 255),
                    new Color(255, 159, 64)
                };
                
                // Draw pie chart
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int radius = Math.min(getWidth(), getHeight()) / 3;
                
                int startAngle = 0;
                
                // Draw legend
                int legendX = getWidth() - 200;
                int legendY = 80;
                
                for (int i = 0; i < values.length; i++) {
                    // Calculate sector angle
                    int arcAngle = (int) Math.round((double) values[i] / total * 360);
                    
                    // Draw sector
                    g2d.setColor(colors[i % colors.length]);
                    g2d.fillArc(centerX - radius, centerY - radius, 
                        radius * 2, radius * 2, 
                        startAngle, arcAngle);
                    
                    // Update start angle
                    startAngle += arcAngle;
                    
                    // Draw legend entry
                    g2d.fillRect(legendX, legendY + i * 25, 15, 15);
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    g2d.drawString(labels[i] + " (" + values[i] + "%)", legendX + 25, legendY + i * 25 + 12);
                }
            }
        };
        
        panel.setPreferredSize(new Dimension(800, 500));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        return panel;
    }
    
    private void backToDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.dispose();
    }
} 