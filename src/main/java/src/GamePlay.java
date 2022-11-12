package src;

import java.util.ArrayList;
import java.util.Arrays;

public class GamePlay {
    final static int OO = (int) 1e9;
    static int moves;
    static StateNode currentState;

    public static void setChildren(ArrayList<ArrayList<StateNode>> children) {
        GamePlay.children = children;
    }

    private static ArrayList<ArrayList<StateNode>> children;

    static ArrayList<StateNode> makeChildrenReady(StateNode s) {

        ArrayList<StateNode> list = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < 7; i++) // check whether the index is available or not
        {
            if (s.getTopArr().get(i) >= 0) {
                StateNode node = playTurn(s, !s.getTurn(), 7 * (5 - s.getTopArr().get(i)) + i, i);
                list.add(node);
                j++;
            }
        }
        children.add(list);
        return list;
    }


    static StateNode playTurn(StateNode s, boolean turn, int ind, int colIndex) {
        return new StateNode(s, ind, turn, colIndex); ///////////////////what inserted///////////////////////////////
    }

    static StateNode userTurn(int k, int col,boolean alphaBeta) {
        GamePlay.setChildren(new ArrayList<>());
        if (moves == 42) return null;
        int ind = col; //index of playing comes from gui
        if (currentState.getTopArr() == null) {
            currentState.setTopArr(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5, 5, 5)));
        }
        StateNode s = playTurn(currentState, true, 7 * (5 - currentState.getTopArr().get(ind)) + ind, ind);
        currentState = s;
        moves++;
        currentState.calculatePoints(); // can be exchanged with method in stateNode class changes it
        System.out.println("Number of 4 red: " + s.getRedPoints());
        return currentState;
    }

    static StateNode myTurn(int k) {
        if (moves == 42)
            return null;
        long G = System.nanoTime();
        StateNode max = AlphaBeta.decision(currentState, Math.min(k, 42 - moves));
        for (int i = 0; i < k - 1; i++) {
            max = max.getParentNode();
        }
        max.setParentNode(currentState);
        currentState = max;
        long G2 = System.nanoTime();
        System.out.println("Current Time in ms: " + (G2 - G) / 1000000);
        System.out.println("goooooooooooooooooooal");
        moves++;
        currentState.calculatePoints();/////////////////////////why 0 /////////////////////
        System.out.println("Number of 4 Yellow: " + currentState.getYellowPoints());
        return currentState;
    }


    static int utility(StateNode s) {
        Heuristic obj = new Heuristic();
        return obj.calculateHeuristic(s.getLastIndexPlayed(), s.color[s.getLastIndexPlayed()]);
    }

    static class Pair {
        int val;
        StateNode state;
        public Pair(StateNode s, int val) {
            this.val = val;
            this.state = s;
        }
    }


    public static void print(boolean[] arr, boolean[] played) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] && played[i]) {
                System.out.print(1);
            } else if (!arr[i] && played[i]) {
                System.out.print(0);
            } else {
                System.out.print("-");
            }
            if (i % 7 == 0) {
                System.out.println();
            }
        }
    }

    public static ArrayList<ArrayList<StateNode>> getChildren() {
        return children;
    }
}