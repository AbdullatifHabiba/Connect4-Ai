package src;

import java.util.ArrayList;
import java.util.Arrays;

public class GamePlay {
    final static int OO = (int) 1e9;
    static int moves;
    static StateNode currentState;
    private static ArrayList<ArrayList<StateNode>> children;

    static ArrayList<StateNode> makeChildrenReady(StateNode s) {
        ArrayList<StateNode> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (s.getTopArr().get(i) >= 0) {
                StateNode node = playTurn(s, !s.getTurn(), 7 * (5 - s.getTopArr().get(i)) + i, i);
                list.add(node);
            }
        }
        children.add(list);
        return list;
    }

    static StateNode playTurn(StateNode s, boolean turn, int ind, int colIndex) {
        return new StateNode(s, ind, turn, colIndex);
    }

    static StateNode userTurn(int col) {
        GamePlay.setChildren(new ArrayList<>());
        System.out.println("Number " + moves);
        if (moves == 42) return null;
        int ind = col; //index of playing comes from gui

        if (currentState.getTopArr() == null) {
            currentState.setTopArr(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5, 5, 5)));
        }
        StateNode s = playTurn(currentState, true, 7 * (5 - currentState.getTopArr().get(ind)) + ind, ind);
        currentState = s;
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
            max = minMaxWithout.max(currentState, k, moves);
        }
        for (int i = 1; max.state.getParentNode() != currentState; i++) {
            max.state = max.state.getParentNode();
        }
        max.state.setParentNode(currentState);
        currentState = max.state;
        long G2 = System.nanoTime();
        System.out.println("Current Time in ms: " + (G2 - G) / 1000000);
        System.out.println("goooooooooooooooooooal");
        moves++;
        System.out.println("Number of 4 Yellow: " + currentState.getYellowPoints());
        System.out.println("Number of 4 Red: " + currentState.getRedPoints());
        System.out.println("Number computer " + moves);
        return currentState;
    }

    static int utility(StateNode s) {
        Heuristic obj = new Heuristic();
        return obj.calculateHeuristic(s);
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

    public static void setChildren(ArrayList<ArrayList<StateNode>> children) {
        GamePlay.children = children;
    }
}