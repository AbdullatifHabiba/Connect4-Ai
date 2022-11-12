package src;

import java.util.ArrayList;
import java.util.Arrays;

public class GamePlay {
    final static int OO = (int) 1e9;
    static int moves;
    static StateNode currentState;

    static ArrayList<StateNode> makeChildrenReady(StateNode s) {

        ArrayList<StateNode> list = new ArrayList<>();
        ///dumy variable///////////////////////
        int j = 0;
        for (int i = 0; i < 7; i++) // check whether the index is available or not
        {
            //System.out.println(s.getTopArr());
            if (s.getTopArr().get(i) >= 0) {
                StateNode node = playTurn(s, !s.getTurn(), 7 * (5 - s.getTopArr().get(i)) + i, i);
                list.add(node);

                //////////////////////////////////////////////////////////////////
               // print(list.get(j).color, list.get(j).played);
              //  System.out.println();
                j++;
                ////////////////////////////////////////////////////////////////////

            }
        }
        return list;
    }


    static StateNode playTurn(StateNode s, boolean turn, int ind, int colIndx) {
        return new StateNode(s, ind, turn, colIndx); ///////////////////what inserted///////////////////////////////
    }

    static Pair min(StateNode s, int k) {
        if (k <= 0) return new Pair(s, utility(s));
        int minVal = OO;
        StateNode minChild = null;
        // get children
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
        // get children
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

    static StateNode userTurn(int k, int col) {
        if (moves == 42) return null;
        int ind = col; //index of playing comes from gui

        if (currentState.getTopArr() == null) {
            currentState.setTopArr(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5, 5, 5)));
        }
        StateNode s = playTurn(currentState, true, 7 * (5 - currentState.getTopArr().get(ind)) + ind, ind);
        currentState = s;
        moves++;
        currentState.calculatePoints(); // can be exchanged with method in stateNode class changes it
        ////////////////////////////////////////////////////////////
        // print(s.color,s.played);
        System.out.println("Number of 4 red: " + s.getRedPoints());
        ///////////////////////////////////////////////////////
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

        //////////////////////////////////////////////////////////////////
        System.out.println("goooooooooooooooooooal");
        //print(currentState.color, currentState.played);

        //////////////////////////////////////////////////////////////////

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


}