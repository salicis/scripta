import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

/**
 * Game has two JPanels. "Gibbet" displays the state of the hanging. "Guesses" displays the
 * progress towards guessing the word/term and the alphabet buttons that allow the player
 * to choose letters.
 */
class Game extends JFrame {

    Gibbet gibbet;
    private int badGuessCount = 0;
    static final int MAX_BAD_GUESSES = 12;
    WordLists wordLists;

    Game(){
        setTitle("hangman ");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(350, 460));

        gibbet = new Gibbet();
        add(gibbet);
        setVisible(true);

        wordLists = new WordLists(this);
        setTitle("Hangman: "+ wordLists.getCategory());
        Guesses guesses = new Guesses(wordLists.getWord(), this);
        add(guesses);
        setVisible(true);
    }

    void badGuess(){
        gibbet.nextImage();
        badGuessCount++;
        if (badGuessCount >= MAX_BAD_GUESSES){
            gameLost();
        }

}

    void gameWon(){
        String message = "Well done, it was "+ wordLists.getWord() +", another game?";
        endGame(message);
    }

    private void gameLost(){
        String message = "Bad luck, it was "+ wordLists.getWord() + ", another game?";
        endGame(message);
    }

    void endGame(String message){
        int reply = JOptionPane.showConfirmDialog(this, message, "end", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION){
            this.dispose();
            new Game();
        }else{
            System.exit(0);
        }
    }



    public static void main (String[] args){
        new Game();
    }
}




