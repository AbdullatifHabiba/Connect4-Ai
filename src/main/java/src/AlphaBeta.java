package src;

import static src.GamePlay.*;

public class AlphaBeta {

    static GamePlay.Pair maximize(StateNode stateNode, int alpha, int beta, int k) {
        if (k <= 0) return new GamePlay.Pair(stateNode, utility(stateNode));
        GamePlay.Pair pair = new GamePlay.Pair(null, -1 * OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utl = minimize(child, alpha, beta, k - 1).val;
            if (utl > pair.val)
                pair = new GamePlay.Pair(child, utl);
            if (pair.val >= beta)
                break;
            if (pair.val > alpha)
                alpha = pair.val;
        }
        return pair;
    }

    static GamePlay.Pair minimize(StateNode stateNode, int alpha, int beta, int k) {
        if (k <= 0) return new GamePlay.Pair(stateNode, utility(stateNode));
        GamePlay.Pair pair = new GamePlay.Pair(null, OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utl = maximize(child, alpha, beta, k - 1).val;
            if (utl < pair.val) {
                pair.state = child;
                pair.val = utl;
            }
            if (pair.val <= alpha)
                break;
            if (pair.val < beta)
                beta = pair.val;
        }
        return pair;
    }

    static StateNode decision(StateNode stateNode, int k) {
        return maximize(stateNode, -1 * OO, OO, k).state;
    }
}
