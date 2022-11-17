package src;

import java.util.ArrayList;
import java.util.Arrays;
public class GamePlay {
    final static int OO = (int) 1e9;
    static int moves;
    static StateNode currentState;
    private static ArrayList<ArrayList<boolean[]>> childrenColorArr;
    private static ArrayList<ArrayList<boolean[]>> childrenPlayedArr;

    static ArrayList<StateNode> makeChildrenReady(StateNode s) {
        ArrayList<StateNode> list = new ArrayList<>();
        ArrayList<boolean[]> colorList = new ArrayList<>();
        ArrayList<boolean[]> PlayedList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (s.getTopArr().get(i) >= 0) {
                StateNode node = playTurn(s, !s.getTurn(), 7 * (5 - s.getTopArr().get(i)) + i, i);
                list.add(node);
                colorList.add(node.color);
                PlayedList.add(node.played);
            }
        }
        childrenColorArr.add(colorList);
        childrenPlayedArr.add(PlayedList);
        return list;
    }

    static StateNode playTurn(StateNode s, boolean turn, int ind, int colIndex) {
        return new StateNode(s, ind, turn, colIndex);
    }

    static StateNode userTurn(int col) {
        GamePlay.setChildren(new ArrayList<>(),new ArrayList<>());
        System.out.println("Number " + moves);
        if (moves == 42) return null;
        if (currentState.getTopArr() == null) {
            currentState.setTopArr(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5, 5, 5)));
        }
        currentState = playTurn(currentState, true, 7 * (5 - currentState.getTopArr().get(col)) + col, col);
        moves++;
        return currentState;
    }

    static StateNode myTurn(int k, boolean alphaBeta) {
        if (moves == 42) return null;
        long G = System.nanoTime();
        Pair max;
        if (alphaBeta) {
            max = AlphaBeta.maximize(currentState, -1 * OO, OO, k, moves);
        } else {
            max = MinMax.max(currentState, k, moves);
        }
        while (!max.state.getParentNode().equals(currentState)) {
            max.state = max.state.getParentNode();
        }
        max.state.setParentNode(currentState);
        currentState = max.state;
        currentState.setHeuristic(max.val);//////////////////////////////
        long G2 = System.nanoTime();
        System.out.println("Current Time in ms: " + (G2 - G)/1000000 );
        moves++;

        return currentState;
    }



    public static int utility(StateNode s) {
        Heuristic obj = new Heuristic();
        return obj.calculateHeuristic(s, currentState);

    }



    public static ArrayList<ArrayList<boolean[]>> getChildrenColorArr() {

        return childrenColorArr;
    }
    public static ArrayList<ArrayList<boolean[]>> getChildrenPlayedArr() {

        return childrenPlayedArr;
    }

    public static void setChildren(ArrayList<ArrayList<boolean[]>> childL,ArrayList<ArrayList<boolean[]>> childR) {
        childrenColorArr=childL;
        childrenPlayedArr =childR;
    }
}