import java.util.ArrayList;
import java.util.*;

class RandomStrategy implements Strategy {
    @Override
    public String getMove(String playerMove) {
        Random randomizer = new Random();
        ArrayList<String> possibleChoices = new ArrayList<>();

        /*
        if (computerChoice.equals("rock")) {
                displayTA.append("Rock and rock, it's a tie!\n");
                tiesCnt++;
            }
            else if (computerChoice.equals("paper")) {
                displayTA.append("Paper wraps rock. (Computer wins!)\n");
                computerWinsCnt++;
            }
            else {
                displayTA.append("Rock breaks scissors. (Player wins!)\n");
                playerWinsCnt++;
            }
         */

        possibleChoices.add("R");
        possibleChoices.add("P");
        possibleChoices.add("S");

        int index = randomizer.nextInt(possibleChoices.size());
        return possibleChoices.get(index);
    }
}
