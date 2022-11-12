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

    static Pair min(StateNode s, int k) {
        if (k <= 0) return new Pair(s, utility(s));
        int minVal = OO;
        StateNode minChild = null;
        ArrayList<StateNode> children = makeChildrenReady(s);
        for (StateNode child : children) {
            Pair p = max(child, k - 1);//////////////change/////////////
            if (p.val < minVal) {
                minVal = p.val;
                minChild = p.state;
            }
        }
        return new Pair(minChild, minVal);
    }

    static Pair max(StateNode s, int k) {
        if (k <= 0) return new Pair(s, utility(s));
        int maxVal = -OO;
        StateNode maxChild = null;
        ArrayList<StateNode> children = makeChildrenReady(s);
        for (StateNode child : children) {
            Pair p = min(child, k - 1);//////////////change/////////////
            if (p.val > maxVal) {
                maxVal = p.val;
                maxChild = p.state;
            }
        }
        return new Pair(maxChild, maxVal);
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
        Pair max = max(currentState, k);
        for (int i = 0; i < k - 1; i++) {
            max.state = max.state.getParentNode();
        }
        max.state.setParentNode(currentState);
        currentState = max.state;
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

    Pair maximize(StateNode stateNode, double alpha, double beta, int k) {
        if (k <= 0) return new Pair(stateNode, utility(stateNode));
        Pair pair = new Pair(null, -1 * OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utility = minimize(child, alpha, beta, k - 1).val;
            if (utility > pair.val)
                pair = new Pair(child, utility);
            if (pair.val >= beta)
                break;
            if (pair.val > alpha)
                alpha = pair.val;
        }
        return pair;
    }

    Pair minimize(StateNode stateNode, double alpha, double beta, int k) {
        if (k <= 0) return new Pair(stateNode, utility(stateNode));
        Pair pair = new Pair(null, OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utility = maximize(child, alpha, beta, k - 1).val;
            if (utility < pair.val)
                pair = new Pair(child, utility);
            if (pair.val <= alpha)
                break;
            if (pair.val < beta)
                beta = pair.val;
        }
        return pair;
    }

    StateNode decision(StateNode stateNode, int k) {
        return maximize(stateNode, -1 * OO, OO, k).state;
    }
}