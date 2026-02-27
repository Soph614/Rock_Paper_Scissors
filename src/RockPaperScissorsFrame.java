import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame
{
    JPanel mainPnl;
    JPanel displayPnl;
    JPanel statsPnl;
    JPanel buttonPnl;

    // TOP PANEL (DISPLAY PANEL)
    JTextArea displayTA;
    JScrollPane scrollbar;

    // MIDDLE PANEL (STATS PANEL)
    JLabel computerWinsLbl;
    JTextArea computerWinsTA;
    JPanel computerWinsPnl;
    int computerWinsCnt = 0;

    JPanel playerWinsPnl;
    JLabel playerWinsLbl;
    JTextArea playerWinsTA;
    int playerWinsCnt = 0;

    JPanel tiesPnl;
    JLabel tiesLbl;
    JTextArea tiesTA;
    int tiesCnt = 0;

    JPanel totalGamesPnl;
    JLabel totalGamesLbl;
    JTextArea totalGamesTA;
    int totalGamesCnt = 0;

    // declare variables for bottom panel (buttonPnl)
    JButton rockButton;
    JButton paperButton;
    JButton scissorsButton;
    JButton quitButton;
    ImageIcon tooBigRock;
    ImageIcon tooBigPaper;
    ImageIcon tooBigScissors;

    ArrayList<String> r_p_OR_s = new ArrayList<>();
    String computerMove;
    String playerMove;
    String playersLastMove;

    ArrayList<Integer> playerChoiceStats = new ArrayList<>();
    String computerStrategyChoice;
    int index = 0;
    int leastUsed;
    int indexOfLeastUsed;
    int mostUsed;
    int indexOfMostUsed;

    // HERE'S THE CODE THAT RUNS THE GAME
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new RockPaperScissorsFrame();
            }
        });
    }

    /**
     * @author Sophia Broyles
     * Adds rock, paper and scissors to ArrayList r_p_OR_s
     * Adds rock, paper and scissors choices to arrayList playerChoiceStats
     * Creates a Jframe that is centered in the computer screen
     */
    public RockPaperScissorsFrame() {
        r_p_OR_s.add("rock");
        r_p_OR_s.add("paper");
        r_p_OR_s.add("scissors");

        playerChoiceStats.add(0, 0); // rock choices
        playerChoiceStats.add(1, 0); // paper choices
        playerChoiceStats.add(2, 0); // scissors choices

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createStatsPanel();
        mainPnl.add(statsPnl, BorderLayout.NORTH);

        createDisplayPanel();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        createControlPanel();
        mainPnl.add(buttonPnl, BorderLayout.SOUTH);

        add(mainPnl);
        {
            // CENTER FRAME IN SCREEN...CODE ADAPTED FROM CAY HORSTMANN
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;

            setSize(screenWidth * 1/4, screenHeight * 1/2);
            double twoPointFive = 2.5;
            setLocation((int) (screenWidth / twoPointFive), screenHeight / 4);


            setTitle("Rock, Paper, Scissors, Shoot!");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }
    }

    /**  computerChoosesStrategyAndMove()
     * @author Sophia Broyles
     * Uses a random generator to generate a number between -1 and 100 (0-99)
     * Uses an if statement to define probability for what strategy will be chosen
     * Chooses a strategy, updating computerMove, and updates variable "computerStrategyChoice"
     */
    private void computerChoosesStrategyAndMove() {
        Random randomGenerator = new Random();
        int strategyPercent = randomGenerator.nextInt(100);
        if (strategyPercent < 21) { // 10 percent (0-10)
            leastUsedStrategy();
            computerStrategyChoice = "Computer: Least Used)";
        }
        else if (strategyPercent < 41) {
            mostUsedStrategy();
            computerStrategyChoice = "Computer: Most Used)";
        }
        else if (strategyPercent < 61 && totalGamesCnt > 0) {
            lastUsedStrategy();
            computerStrategyChoice = "Computer: Last Used)";
        }
        else if (strategyPercent < 91) {
            Strategy randomStrategy = new RandomStrategy();
            computerMove = randomStrategy.getMove(playerMove);
            computerStrategyChoice = "Computer: Random)";
        }
        else {
            Strategy cheatStrategy = new Cheat();
            computerMove = cheatStrategy.getMove(playerMove);
            computerStrategyChoice = "Computer: Cheat)";
        }
    }

    /**  createStatsPanel()
     * @author Sophia Broyles
     * Makes a panel of statistics panels with textareas and labels to display
     * player wins, computer wins, ties, and total of games played
     *
     * Sets layout of statsPnl to a GridLayout
     */
    private void createStatsPanel()
    {
        statsPnl = new JPanel();
        statsPnl.setLayout(new GridLayout(4, 1));

        playerWinsTA = new JTextArea(1, 25);
        playerWinsTA.setEditable(false);
        playerWinsLbl = new JLabel("Player Wins:");
        playerWinsPnl = new JPanel();
        playerWinsPnl.add(playerWinsLbl);
        playerWinsPnl.add(playerWinsTA);

        computerWinsTA = new JTextArea(1, 23);
        computerWinsTA.setEditable(false);
        computerWinsLbl = new JLabel("Computer Wins:");
        computerWinsPnl = new JPanel();
        computerWinsPnl.add(computerWinsLbl);
        computerWinsPnl.add(computerWinsTA);

        tiesTA = new JTextArea(1, 29);
        tiesTA.setEditable(false);
        tiesLbl = new JLabel("Ties:");
        tiesPnl = new JPanel();
        tiesPnl.add(tiesLbl);
        tiesPnl.add(tiesTA);

        totalGamesTA = new JTextArea(1, 21);
        totalGamesTA.setEditable(false);
        totalGamesLbl = new JLabel("Total Games Played:");
        totalGamesPnl = new JPanel();
        totalGamesPnl.add(totalGamesLbl);
        totalGamesPnl.add(totalGamesTA);

        statsPnl.add(playerWinsPnl);
        statsPnl.add(computerWinsPnl);
        statsPnl.add(tiesPnl);
        statsPnl.add(totalGamesPnl);
    }

    /**  createDisplayPanel()
     * @author Sophia Broyles
     * Makes a display panel with a big JTextArea
     */
    private void createDisplayPanel()
    {
        displayPnl = new JPanel();                                             // initialize display panel
        displayTA = new JTextArea(14, 24);                       // set size of display -- is dependent on font and size of font!
        displayTA.setEditable(false);                                          // make sure the user can't edit the display
        displayTA.setFont(new Font("Verdana", Font.PLAIN, 17));    // set font, font style, and font size for display
        scrollbar = new JScrollPane(displayTA);                                // make the display scrollable
        displayPnl.add(scrollbar);                                             // add scrollbar to display panel
    }

    /**  createControlPanel()
     * @author Sophia Broyles
     * Makes a panel with a gridLayout for four buttons,
     * rockButton, paperButton, scissorsButton, and quitButton
     *
     * Resizes images for buttons
     *
     * For each button except quit button, sets playerMove and playersLastMove,
     * runs computerChoosesStrategyAndMove() and determineAndPrintGameResult(),
     * appends computerStrategyChoice to display textarea, increases totalGamesCnt by 1,
     * resets playerWinsCnt, computerWinsCnt, tiesCnt, and totalGamesCnt on the stats panel,
     * and adds the player's choice to the playerChoiceStats ArrayList.
     */
    private void createControlPanel() {
        buttonPnl = new JPanel();
        buttonPnl.setLayout(new GridLayout(1, 4));
        buttonPnl.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        tooBigRock = new ImageIcon("src/rock.jpg");
        Image bigRkImage = tooBigRock.getImage();
        Image smallRkImage = bigRkImage.getScaledInstance(80, 60, Image.SCALE_DEFAULT);
        ImageIcon rock = new ImageIcon(smallRkImage);

        rockButton = new JButton(rock);
        rockButton.addActionListener((ActionEvent aeRock) -> {
            playerMove = "R";
            playersLastMove = "R";
            computerChoosesStrategyAndMove();
            determineAndPrintGameResult();
            displayTA.append(computerStrategyChoice + "\n");
            totalGamesCnt++;
            playerWinsTA.setText(String.valueOf(playerWinsCnt));
            computerWinsTA.setText(String.valueOf(computerWinsCnt));
            tiesTA.setText(String.valueOf(tiesCnt));
            totalGamesTA.setText(String.valueOf(totalGamesCnt));
            playerChoiceStats.set(0, playerChoiceStats.getFirst() + 1);
        });

        tooBigPaper = new ImageIcon("src/paper.jpg");
        Image bigPpImage = tooBigPaper.getImage();
        Image smallPpImage = bigPpImage.getScaledInstance(80, 60, Image.SCALE_DEFAULT);
        ImageIcon paper = new ImageIcon(smallPpImage);

        paperButton = new JButton(paper);
        paperButton.addActionListener((ActionEvent aeScissors) -> {
            playerMove = "P";
            playersLastMove = "P";
            computerChoosesStrategyAndMove();
            determineAndPrintGameResult();
            displayTA.append(computerStrategyChoice + "\n");
            totalGamesCnt++;
            playerWinsTA.setText(String.valueOf(playerWinsCnt));
            computerWinsTA.setText(String.valueOf(computerWinsCnt));
            tiesTA.setText(String.valueOf(tiesCnt));
            totalGamesTA.setText(String.valueOf(totalGamesCnt));
            playerChoiceStats.set(1, playerChoiceStats.get(1) + 1);
        });

        tooBigScissors = new ImageIcon("src/scissors.jpg");
        Image bigSsImage = tooBigScissors.getImage();
        Image smallSsImage = bigSsImage.getScaledInstance(80, 60, Image.SCALE_DEFAULT);
        ImageIcon scissors = new ImageIcon(smallSsImage);

        scissorsButton = new JButton(scissors);
        scissorsButton.addActionListener((ActionEvent ae1) -> {
            playerMove = "S";
            playersLastMove = "S";
            computerChoosesStrategyAndMove();
            determineAndPrintGameResult();
            displayTA.append(computerStrategyChoice + "\n");
            totalGamesCnt++;
            playerWinsTA.setText(String.valueOf(playerWinsCnt));
            computerWinsTA.setText(String.valueOf(computerWinsCnt));
            tiesTA.setText(String.valueOf(tiesCnt));
            totalGamesTA.setText(String.valueOf(totalGamesCnt));
            playerChoiceStats.set(2, playerChoiceStats.get(2) + 1);
        });


        // make the quit button actionable and set font, font style, and font size
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Verdana", Font.PLAIN, 20));

        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));

        // add the buttons to the button panel
        buttonPnl.add(rockButton);
        buttonPnl.add(paperButton);
        buttonPnl.add(scissorsButton);
        buttonPnl.add(quitButton);

    }

    /**  determineAndPrintGameResult()
     * @author Sophia Broyles
     * Uses an if statement to figure out who won the round.
     *
     * For each case:
     * increases count of result type,
     * appends result to the display,
     * and updates the statistics panel (statsPnl)
     */
    private void determineAndPrintGameResult() {
        if (computerMove.equals("R") && playerMove.equals("R")) {
            tiesCnt++;
            displayTA.append("Rock and rock. (It's a tie! ");
            tiesTA.setText(String.valueOf(tiesCnt));
        }
        else if (computerMove.equals("R") && playerMove.equals("P")) {
            playerWinsCnt++;
            displayTA.append("Paper wraps rock. (Player wins! ");
            playerWinsTA.setText(String.valueOf(playerWinsCnt));
        }
        else if (computerMove.equals("R") && playerMove.equals("S")) {
            computerWinsCnt++;
            displayTA.append("Rock smashes scissors. (Computer wins! ");
            computerWinsTA.setText(String.valueOf(computerWinsCnt));
        }
        else if (computerMove.equals("P") && playerMove.equals("R")) {
            displayTA.append("Paper wraps rock. (Computer wins! ");
            computerWinsCnt++;
            computerWinsTA.setText(String.valueOf(computerWinsCnt));
        }
        else if (computerMove.equals("P") && playerMove.equals("P")) {
            displayTA.append("Paper and paper. (It's a tie! ");
            tiesCnt++;
            tiesTA.setText(String.valueOf(tiesCnt));
        }
        else if (computerMove.equals("P") && playerMove.equals("S")) {
            displayTA.append("Scissors cut paper. (Player wins! ");
            playerWinsCnt++;
            playerWinsTA.setText(String.valueOf(playerWinsCnt));
        }
        else if (computerMove.equals("S") && playerMove.equals("R")) {
            displayTA.append("Rock smashes scissors. (Player wins! ");
            playerWinsCnt++;
            playerWinsTA.setText(String.valueOf(playerWinsCnt));
        }
        else if (computerMove.equals("S") && playerMove.equals("P")) {
            displayTA.append("Scissors cut paper. (Computer wins! ");
            computerWinsCnt++;
            computerWinsTA.setText(String.valueOf(computerWinsCnt));
        }
        else if (computerMove.equals("S") && playerMove.equals("S")) {
            displayTA.append("Scissors and scissors. (It's a tie! ");
            tiesCnt++;
            tiesTA.setText(String.valueOf(tiesCnt));
        }
    }

    /**
     * @author Sophia Broyles
     * Sets the computer's move to whatever the player's last move was.
     */
    private void lastUsedStrategy() {
        computerMove = playersLastMove;
    }

    /**  leastUsedStrategy()
     * @author Sophia Broyles
     * Sets computer's move to whatever the player has used the least.
     */
    private void leastUsedStrategy() {

        if (!playerChoiceStats.isEmpty()) {
            leastUsed = Collections.<Integer>min(playerChoiceStats);

            for (index = 1; index < playerChoiceStats.size(); index++){
                int curValue = playerChoiceStats.get(index);
                if (leastUsed < curValue) {
                    leastUsed = curValue;
                    indexOfLeastUsed = index;
                }
                if (indexOfLeastUsed == 0) {
                    computerMove = "R";
                }
                if (indexOfLeastUsed == 1) {
                    computerMove = "P";
                }
                if (indexOfLeastUsed == 2){
                    computerMove = "S";
                }
            }
        }
    }

    /**  mostUsedStrategy()
     * @author Sophia Broyles
     * Sets computer's move to whatever the player has used the most.
     */
    private void mostUsedStrategy() {
        if (!playerChoiceStats.isEmpty()) {
            mostUsed = Collections.max(playerChoiceStats);

            for (index = 1; index < playerChoiceStats.size(); index++){
                int curValue = playerChoiceStats.get(index);
                if (curValue > mostUsed) {
                    mostUsed = curValue;
                    indexOfMostUsed = index;
                }
                if (indexOfMostUsed == 0) {
                    computerMove = "R";
                }
                if (indexOfMostUsed == 1) {
                    computerMove = "P";
                }
                if (indexOfMostUsed == 2){
                    computerMove = "S";
                }
            }
        }
    }
}
