package src;

public class Heuristic {
    private final int[] heuristicArr = new int[42];

    public int calculateHeuristic(StateNode s) {
        setHeuristic();
        int yellowAddedPoints =  (s.getYellowPoints() - s.getParentNode().getYellowPoints());
        int redAddedPoints =  (s.getRedPoints() - s.getParentNode().getRedPoints());
        if (!s.color[s.getLastIndexPlayed()]) {
            return (heuristicArr[s.getLastIndexPlayed()] + 3 * yellowAddedPoints);
        } else {
            return -1 * (heuristicArr[s.getLastIndexPlayed()] + 3 * redAddedPoints);
        }
    }

    public void setHeuristic() {
        heuristicArr[0] = 3;
        heuristicArr[1] = 4;
        heuristicArr[2] = 5;
        heuristicArr[3] = 7;
        heuristicArr[4] = 5;
        heuristicArr[5] = 4;
        heuristicArr[6] = 3;
        heuristicArr[7] = 4;
        heuristicArr[8] = 6;
        heuristicArr[9] = 8;
        heuristicArr[10] = 10;
        heuristicArr[11] = 8;
        heuristicArr[12] = 6;
        heuristicArr[13] = 4;
        heuristicArr[14] = 5;
        heuristicArr[15] = 8;
        heuristicArr[16] = 11;
        heuristicArr[17] = 13;
        heuristicArr[18] = 11;
        heuristicArr[19] = 8;
        heuristicArr[20] = 5;
        heuristicArr[21] = 5;
        heuristicArr[22] = 8;
        heuristicArr[23] = 11;
        heuristicArr[24] = 13;
        heuristicArr[25] = 11;
        heuristicArr[26] = 8;
        heuristicArr[27] = 5;
        heuristicArr[28] = 4;
        heuristicArr[29] = 6;
        heuristicArr[30] = 8;
        heuristicArr[31] = 10;
        heuristicArr[32] = 8;
        heuristicArr[33] = 6;
        heuristicArr[34] = 4;
        heuristicArr[35] = 3;
        heuristicArr[36] = 4;
        heuristicArr[37] = 5;
        heuristicArr[38] = 7;
        heuristicArr[39] = 5;
        heuristicArr[40] = 4;
        heuristicArr[41] = 3;
    }


}
