package src;

import java.util.ArrayList;

import static src.GamePlay.*;

public class MinMax {

    public static Pair min(StateNode s, int k, int numMoves) {
        if (k <= 0 || numMoves == 42) return new Pair(s, utility(s));
        int minVal = OO;
        StateNode minChild = null;
        ArrayList<StateNode> children = GamePlay.makeChildrenReady(s);
        numMoves++;
        for (StateNode child : children) {
            Pair p = max(child, k - 1, numMoves);
            if (p.val < minVal) {
                minVal = p.val;
                minChild = p.state;
            }
        }
        return new Pair(minChild, minVal);
    }

    public static Pair max(StateNode s, int k, int numMoves) {
        if (k <= 0 || numMoves == 42) return new Pair(s, utility(s));
        int maxVal = -OO;
        StateNode maxChild = null;
        ArrayList<StateNode> children = GamePlay.makeChildrenReady(s);
        numMoves++;
        for (StateNode child : children) {
            Pair p = min(child, k - 1, numMoves);
            if (p.val > maxVal) {
                maxVal = p.val;
                maxChild = p.state;
            }
        }
        return new Pair(maxChild, maxVal);
    }
}
