package View.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Font {
    public static java.awt.Font cooperHewittRegularFont;
    public static java.awt.Font cooperHewittBoldFont;
    public static java.awt.Font montFont;
    public static java.awt.Font checkmarkFont;


    static {
        try {
            InputStream regularFontStream = Font.class.getResourceAsStream("/fonts/CooperHewitt-Medium.otf");
            InputStream boldFontStream = Font.class.getResourceAsStream("/fonts/CooperHewitt-Heavy.otf");
            InputStream montFontStream = Font.class.getResourceAsStream("/fonts/Mont.otf");

            if (regularFontStream == null || boldFontStream == null || montFontStream == null) {
                throw new IOException("One or more font files not found in /fonts");
            }

            cooperHewittRegularFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, regularFontStream).deriveFont(16f);
            cooperHewittBoldFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, boldFontStream).deriveFont(16f);
            montFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, montFontStream).deriveFont(16f);
            checkmarkFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 16);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(cooperHewittRegularFont);
            ge.registerFont(cooperHewittBoldFont);
            ge.registerFont(montFont);

            regularFontStream.close();
            boldFontStream.close();
            montFontStream.close();
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to load custom fonts. Using default fonts.",
                    "Font Loading Error",
                    JOptionPane.WARNING_MESSAGE);
            cooperHewittRegularFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14);
            cooperHewittBoldFont = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14);
            montFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14);
            checkmarkFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 16);
        }
    }
}
