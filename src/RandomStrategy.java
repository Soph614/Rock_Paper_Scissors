import java.util.ArrayList;
import java.util.*;

class RandomStrategy implements Strategy {
    /**  getMove()
     * @param playerMove is whatever the player moved this round
     * @return returns the computer's move choice
     */
    @Override
    public String getMove(String playerMove) {
        Random randomizer = new Random();
        ArrayList<String> possibleChoices = new ArrayList<>();

        possibleChoices.add("R");
        possibleChoices.add("P");
        possibleChoices.add("S");

        int index = randomizer.nextInt(possibleChoices.size());
        return possibleChoices.get(index);
    }
}
