import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Objects;

/**
 * Text files contain lists of words or terms. The filename is used as a category for the words e.g.
 * the file "birds" contains a list of bird names. The player chooses from a list of filenames
 * and a random word/term from that list becomes the target word for the game. Words/terms can
 * contain spaces, hyphens and other punctuation. The maximum length is 20 characters (sufficient
 * for "Johann Sebastian Bach" or "Lennon and McCartney". The minimum length is three characters
 * (sorry "Ye"). If there's a problem finding a valid word list, the program falls back to a
 * hard-coded default list.
 */
public class WordLists {
    private String category;
    private static final String wordListDirectory = "wordLists/";
    private final String targetWord;
    private static final int maxWordLength = 20;
    private static final int minWordLength = 3;

    WordLists(JFrame frame){
        ArrayList<String> words;
        try {
            chooseCategory(frame);
            words = getWordsInCategory(frame);
        }catch (IOException e){
            JOptionPane.showMessageDialog(frame, "No word lists found, using default words instead");
            words=loadDefaultWords();
        }
        Random random = new Random();
        String word = words.get(random.nextInt(words.size()));
        targetWord = word.toUpperCase().trim();
    }

    String getCategory(){
        return category;
    }

    String getWord(){
        return targetWord;
    }

    private void chooseCategory(JFrame  frame) throws IOException {
        String[] fileList = getCategories();
        if (fileList.length>0) {
            category = fileList[0];
            JComboBox<String> jComboBox = new JComboBox<>(fileList);
            jComboBox.addActionListener(e -> category = Objects.requireNonNull(jComboBox.getSelectedItem()).toString());
            JOptionPane.showMessageDialog(frame, jComboBox, "Word list",
                    JOptionPane.QUESTION_MESSAGE);
        } else {
            throw new IOException("No word lists found");
        }
    }

    private String[] getCategories(){
        var folder = new File(wordListDirectory);
        File[] files = folder.listFiles();
        List<String> fileList = new ArrayList<>();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isFile()) {
                fileList.add(file.getName());
            }
        }
        return fileList.toArray(new String[0]);
    }

    private ArrayList<String> getWordsInCategory(JFrame  frame){
        ArrayList<String> words = new ArrayList<>();
        File file = new File(wordListDirectory+category);
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                String word = sc.nextLine();
                if (word.length() >= minWordLength && word.length() <= maxWordLength){
                    words.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Text file for words missing, using default words instead");
            words=loadDefaultWords();
        }
        if (words.size()<1){
            JOptionPane.showMessageDialog(frame, "Selected list empty, using default words instead");
            words=loadDefaultWords();
        }
        return words;
    }

    private ArrayList<String> loadDefaultWords(){
        category = "default list";
        ArrayList<String> words = new ArrayList<>();
        words.add("FOXGLOVE");
        words.add("MICROWAVE");
        words.add("ZOMBIE");
        words.add("PUPPY");
        words.add("RHUBARB");
        words.add("DWARF");
        words.add("BICYCLE");
        words.add("BUZZARD");
        words.add("OWL");
        words.add("CHAFFINCH");
        words.add("KIRIBATI");
        words.add("LIECHTENSTEIN");
        words.add("MOZAMBIQUE");
        return words;
    }

}
