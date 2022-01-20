import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

class Guesses extends JPanel{
    /**
     * The target word/term is represented by a template with each letter replaced by a placeholder.
     * Buttons represent each letter of the alphabet. When a correct letter is selected the placeholder
     * is replaced. Wrong guesses and wins are passed back to Game.
     */
        private char[] template;
        private JLabel templateLabel;
        private static final char placeHolder = '*';


    Guesses(String word, Game game) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(350, 200));
        createTemplate(word);
        createTemplateLabel();
        add(templateLabel);
        addAlphabetButtons(word, game);
    }

    private void addAlphabetButtons(String word, Game game){
        JPanel alphabetButtons = new JPanel();
        for (char c = 'A'; c <= 'Z'; ++c) {
            JButton button = new JButton(Character.toString(c));
            button.addActionListener(e -> {
                 button.setEnabled(false);
                 char selectedCharacter = e.getActionCommand().charAt(0);
                 if (word.contains(Character.toString(selectedCharacter))) {
                     updateTemplate(word, selectedCharacter);
                     if (checkForWin(word)) {
                         game.gameWon();
                     }
                 }else{
                     game.badGuess();
                 }
            });
            alphabetButtons.add(button);
        }
        add(alphabetButtons);
    }

    private void createTemplate(String word) {
        template = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (Character.isLetter(word.charAt(i))) {
                template[i] = placeHolder;
            }
        }
    }

    private void createTemplateLabel(){
         templateLabel = new JLabel(new String(template));
         templateLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
         templateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void updateTemplate (String word, char guess) {
        for (int n = 0; n < word.length(); n++) {
            if (word.charAt(n) == guess) {
                template[n] = guess;
                templateLabel.setText(new String(template));

            }
        }
    }

    private boolean checkForWin (String word){
        boolean win = true;
        for(int n = 0; n < word.length(); n++){
            if (template[n] == placeHolder) {
                win = false;
                break;
            }
        }
        return win;
    }
}

