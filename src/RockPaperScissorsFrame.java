import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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

    Random rnd = new Random();
    ArrayList<String> r_p_OR_s = new ArrayList<>();
    int index;
    String computerChoice;
    String playerChoice;
    String playerMove;

    ArrayList<Integer> playerChoiceStats = new ArrayList<>();


    int timesPlayerChoseRock;
    int timesPlayerChosePaper;
    int timesPlayerChoseScissors;
    int leastUsed;
    int lowestIndex;

    String computerStrategyChoice;

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

    public RockPaperScissorsFrame() {
        r_p_OR_s.add("rock");
        r_p_OR_s.add("paper");
        r_p_OR_s.add("scissors");

        playerChoiceStats.add(0, 0); // rock choices
        playerChoiceStats.add(1, 0); // paper choices
        playerChoiceStats.add(2, 0); // scissors choices

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createDisplayPanel();
        mainPnl.add(displayPnl, BorderLayout.NORTH);

        createStatsPanel();
        mainPnl.add(statsPnl, BorderLayout.CENTER);

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

    private void computerChooses() {
        Random randomGenerator = new Random();

        // this code would go inside your for-loop:
        int randNumber = randomGenerator.nextInt(5);
        switch(randNumber) {
            case 1:
                String leastUsedStrategy = leastUsedStrategy();
                computerChoice = leastUsedStrategy();
                computerStrategyChoice = "Least Used";
                break;
            case 2:
                computerChoice = mostUsedStrategy();
                computerStrategyChoice = "Most Used";
                break;
            case 3:
                computerChoice = lastUsedStrategy();
                computerStrategyChoice = "Last Used";
                break;
            case 4:
                computerChoice = RandomStrategy.RandomStrategy();
                computerStrategyChoice = "Random";
                break;
            case 5:
                Strategy cheatStrategy = new CheatStrategyDemo;
                computerChoice = cheatStrategy.getMove(playerMove);
                computerStrategyChoice = "Cheat";
                break;
            default:
                break;
        }
    }
/*

        ArrayList<Consumer<Void>> listOfMethods = new ArrayList<>();
        listOfMethods.add(1, Random.RandomStrategy);
        return computerStrategyChoice;
    }

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


    private void createDisplayPanel()
    {
        displayPnl = new JPanel();                                                  // initialize display panel
        displayTA = new JTextArea(14, 24);                       // set size of display -- is dependent on font and size of font!
        displayTA.setEditable(false);                                         // make sure the user can't edit the display
        displayTA.setFont(new Font("Verdana", Font.PLAIN, 17)); // set font, font style, and font size for display
        scrollbar = new JScrollPane(displayTA);                               // make the display scrollable
        displayPnl.add(scrollbar);                                                  // add scrollbar to display panel
    }


    private void createControlPanel()
    {
        buttonPnl = new JPanel();
        buttonPnl.setLayout(new GridLayout(1, 4));
        buttonPnl.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        rockButton = new JButton("ROCK");
        tooBigRock = new ImageIcon("src/rock.jpg");
        // HAVE TO RESIZE THE IMAGE
        // got idea from trolologuy, https://stackoverflow.com/a/18335435 on 02/16/2026... License - CC BY-SA 3.0
        Image bigRkImage = tooBigRock.getImage();
        Image smallRkImage = bigRkImage.getScaledInstance(80, 60, Image.SCALE_DEFAULT);
        ImageIcon rock = new ImageIcon(smallRkImage);
        // resizing complete
        rockButton = new JButton(rock);
        rockButton.addActionListener((ActionEvent aeRock) -> {                      // make the button clickable
            playerChoice = "R";
            // computerChoice = computerChooses();
            totalGamesCnt++;
            playerWinsTA.setText(String.valueOf(playerWinsCnt));
            computerWinsTA.setText(String.valueOf(computerWinsCnt));
            tiesTA.setText(String.valueOf(tiesCnt));
            totalGamesTA.setText(String.valueOf(totalGamesCnt));
            playerChoiceStats.set(0, playerChoiceStats.getFirst() + 1);
            displayTA.append(String.valueOf(playerChoiceStats.getFirst()));
            String toBeAppended = leastUsedStrategy();
            displayTA.append(toBeAppended);
        });

        tooBigPaper = new ImageIcon("src/paper.jpg");
        // HAVE TO RESIZE THE IMAGE
        Image bigPpImage = tooBigPaper.getImage();
        Image smallPpImage = bigPpImage.getScaledInstance(80, 60, Image.SCALE_DEFAULT);
        ImageIcon paper = new ImageIcon(smallPpImage);
        // resizing complete

        paperButton = new JButton(paper);
        paperButton.addActionListener((ActionEvent aeScissors) -> {
            playerChoiceStats.set(1, playerChoiceStats.get(1) + 1);
            displayTA.append(String.valueOf(playerChoiceStats.get(1)));
        });


        tooBigScissors = new ImageIcon("src/scissors.jpg");
        // HAVE TO RESIZE THE IMAGE
        // got idea from trolologuy, https://stackoverflow.com/a/18335435 on 02/16/2026... License - CC BY-SA 3.0
        Image bigSsImage = tooBigScissors.getImage();
        Image smallSsImage = bigSsImage.getScaledInstance(80, 60, Image.SCALE_DEFAULT);
        ImageIcon scissors = new ImageIcon(smallSsImage);
        // resizing complete
        scissorsButton = new JButton(scissors);
        scissorsButton.addActionListener((ActionEvent ae1) -> {
            playerChoiceStats.set(2, playerChoiceStats.get(2) + 1);
            displayTA.append(String.valueOf(playerChoiceStats.get(2)));
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

    private String leastUsedStrategy() {

        if (!playerChoiceStats.isEmpty()) {
            int leastUsed = playerChoiceStats.getFirst();
            int lowestIndex = 0;

            for (int s = 1; s < playerChoiceStats.size(); s++){
                int curValue = playerChoiceStats.get(s);
                if (curValue > leastUsed) {
                    leastUsed = curValue;
                    lowestIndex = s;
                }
            }
        }
        return "lowest used = " + leastUsed + " indexOf = " + lowestIndex;
    }
}
