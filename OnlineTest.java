import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

class OnlineTest extends JFrame implements ActionListener {
    JButton btnNext, btnSubmit;
    JLabel label, timerLabel;
    ButtonGroup bg;
    int current = 0, count = 0;
    JRadioButton radioButton[] = new JRadioButton[5];
    Timer timer;
    int timeLeft = 30; // 30-second timer for the whole quiz

    String[][] questions = {
        {"Who invented C++?", "Dennis Ritchie", "Ken Thompson", "Brian Kernighan", "Bjarne Stroustrup", "3"},
        {"Which concept allows you to reuse the written code?", "Encapsulation", "Abstraction", "Inheritance", "Polymorphism", "2"},
        {"Which user-defined header file extension is used in C++?", "hg", "cpp", "h", "hf", "2"},
        {"Wrapping data and its related functionality into a single entity is known as?", "Abstraction", "Encapsulation", "Polymorphism", "Modularity", "1"},
        {"Which correctly declares an array in C++?", "array{10};", "array array[10];", "int array;", "int array[10];", "3"}
    };

    OnlineTest(String s) {
        super(s);
        label = new JLabel();
        add(label);

        timerLabel = new JLabel("Time left: " + timeLeft + " seconds");
        add(timerLabel);

        bg = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            radioButton[i] = new JRadioButton();
            add(radioButton[i]);
            bg.add(radioButton[i]);
        }

        btnNext = new JButton("Next Question");
        btnSubmit = new JButton("Submit");
        btnNext.addActionListener(this);
        btnSubmit.addActionListener(this);
        add(btnNext);
        add(btnSubmit);

        setQuestion();

        label.setBounds(30, 40, 450, 20);
        radioButton[0].setBounds(50, 80, 450, 20);
        radioButton[1].setBounds(50, 110, 450, 20);
        radioButton[2].setBounds(50, 140, 450, 20);
        radioButton[3].setBounds(50, 170, 450, 20);
        timerLabel.setBounds(500, 10, 200, 20);
        btnNext.setBounds(100, 240, 150, 40);
        btnSubmit.setBounds(270, 240, 150, 40);
        btnSubmit.setEnabled(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(250, 100);
        setVisible(true);
        setSize(700, 600);

        getContentPane().setBackground(new Color(255, 150, 0));

        startTimer();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNext) {
            if (checkAnswer()) {
                count++;
            }
            current++;
            if (current == questions.length - 1) {
                btnNext.setEnabled(false);
                btnSubmit.setEnabled(true);
            }
            setQuestion();
        }

        if (e.getSource() == btnSubmit) {
            if (checkAnswer()) {
                count++;
            }
            showResults();
        }
    }

    void setQuestion() {
        radioButton[4].setSelected(true); // Clear selection
        label.setText("Q" + (current + 1) + ": " + questions[current][0]);
        for (int i = 0; i < 4; i++) {
            radioButton[i].setText(questions[current][i + 1]);
        }
    }

    boolean checkAnswer() {
        return radioButton[Integer.parseInt(questions[current][5]) - 1].isSelected();
    }

    void showResults() {
        timer.cancel();
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("You answered ").append(count).append(" out of ").append(questions.length).append(" questions correctly.\n\n");
        for (int i = 0; i < questions.length; i++) {
            resultMessage.append("Q").append(i + 1).append(": ").append(questions[i][0]).append("\n");
            resultMessage.append("Correct Answer: ").append(questions[i][Integer.parseInt(questions[i][5])]).append("\n\n");
        }
        JOptionPane.showMessageDialog(this, resultMessage.toString());
        System.exit(0);
    }

    void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft + " seconds");
                } else {
                    timer.cancel();
                    JOptionPane.showMessageDialog(null, "Time's up! Submitting your answers.");
                    btnSubmit.doClick();
                }
            }
        }, 1000, 1000);
    }

    public static void main(String args[]) {
        new OnlineTest("Online Test App");
    }
}
