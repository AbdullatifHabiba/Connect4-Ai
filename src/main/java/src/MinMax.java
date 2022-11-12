package src;

import java.util.ArrayList;

import static src.GamePlay.*;

public class MinMax {

    static GamePlay.Pair min(StateNode s, int k) {
        if (k <= 0) return new GamePlay.Pair(s, utility(s));
        int minVal = OO;
        StateNode minChild = null;
        ArrayList<StateNode> children = makeChildrenReady(s);
        for (StateNode child : children) {
            GamePlay.Pair p = max(child, k - 1);
            if (p.val < minVal) {
                minVal = p.val;
                minChild = p.state;
            }
        }
        return new GamePlay.Pair(minChild, minVal);
    }

    static GamePlay.Pair max(StateNode s, int k) {
        if (k <= 0) return new GamePlay.Pair(s, utility(s));
        int maxVal = -OO;
        StateNode maxChild = null;
        ArrayList<StateNode> children = makeChildrenReady(s);
        for (StateNode child : children) {
            GamePlay.Pair p = min(child, k - 1);//////////////change/////////////
            if (p.val > maxVal) {
                maxVal = p.val;
                maxChild = p.state;
            }
        }
        return new GamePlay.Pair(maxChild, maxVal);
    }

    static StateNode decision(StateNode stateNode, int k) {
        return max(stateNode, k).state;
    }
}
