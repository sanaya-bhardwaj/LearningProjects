import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WordGuessGameGUI extends JFrame implements ActionListener {
    private String secretWord;
    private StringBuilder guessedWord;
    private int maxAttempts;
    private int attemptsLeft;

    private JTextField letterField;
    private JLabel wordLabel;
    private JLabel attemptsLabel;
    private JButton guessButton;

    public WordGuessGameGUI(String word) {
        secretWord = word.toUpperCase();
        maxAttempts = 10;
        attemptsLeft = maxAttempts;
        guessedWord = new StringBuilder("_".repeat(secretWord.length()));

        setTitle("Word Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(4, 1));

        wordLabel = new JLabel(guessedWord.toString(), JLabel.CENTER);
        add(wordLabel);

        attemptsLabel = new JLabel("Attempts left: " + attemptsLeft, JLabel.CENTER);
        add(attemptsLabel);

        letterField = new JTextField();
        add(letterField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(this);
        add(guessButton);
    }

    private void updateUI() {
        wordLabel.setText(guessedWord.toString());
        attemptsLabel.setText("Attempts left: " + attemptsLeft);

        if (attemptsLeft == 0 || guessedWord.indexOf("_") == -1) {
            endGame();
        }
    }

    private void endGame() {
        guessButton.setEnabled(false);
        if (attemptsLeft == 0) {
            JOptionPane.showMessageDialog(this, "Sorry, you ran out of attempts. The word was: " + secretWord);
        } else {
            JOptionPane.showMessageDialog(this, "Congratulations! You guessed the word: " + secretWord);
        }
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String guess = letterField.getText().toUpperCase();
        if (guess.length() == 1 && Character.isLetter(guess.charAt(0))) {
            checkGuess(guess.charAt(0));
            letterField.setText("");
            updateUI();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid letter.");
        }
    }

    private void checkGuess(char guess) {
        boolean correctGuess = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guess) {
                guessedWord.setCharAt(i, guess);
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            attemptsLeft--;
        }
    }

    public static void main(String[] args) {
        String[] words = {"JAVA", "PYTHON", "HTML", "CSS", "JAVASCRIPT"};
        Random rand = new Random();
        String selectedWord = words[rand.nextInt(words.length)];

        SwingUtilities.invokeLater(() -> {
            WordGuessGameGUI game = new WordGuessGameGUI(selectedWord);
            game.setVisible(true);
        });
    }
}

