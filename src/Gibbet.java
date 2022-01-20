import java.awt.CardLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class Gibbet extends JPanel {
    /**
     * This class is responsible for displaying the state of the hanging. With each bad
     * guess the image of the hanging is advanced by one.
     */

    private int currentCard = 0;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel();
    private static final int IMAGE_COUNT = Game.MAX_BAD_GUESSES+1;

    Gibbet (){
        cardPanel.setLayout(cardLayout);
        for (int i = 0; i < IMAGE_COUNT; i++ ) {
            try{
                BufferedImage myPicture = ImageIO.read(new File("images/" + i + ".png"));
                cardPanel.add(new JPanel().add(new JLabel(new ImageIcon(myPicture))), ""+i);
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(null, "Image file for gibbet missing, exiting...");
                System.exit(0);
            }
        }
        add(cardPanel);
    }

    void nextImage(){
        if (currentCard < IMAGE_COUNT){
            currentCard += 1;
            cardLayout.show(cardPanel, "" + (currentCard));
        }
    }
}