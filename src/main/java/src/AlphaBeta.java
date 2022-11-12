package src;

import static src.GamePlay.*;

public class AlphaBeta {

    static GamePlay.Pair maximize(StateNode stateNode, double alpha, double beta, int k) {
        if (k <= 0) return new GamePlay.Pair(stateNode, utility(stateNode));
        GamePlay.Pair pair = new GamePlay.Pair(null, -1 * OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utility = minimize(child, alpha, beta, k - 1).val;
            if (utility > pair.val)
                pair = new GamePlay.Pair(child, utility);
            if (pair.val >= beta)
                break;
            if (pair.val > alpha)
                alpha = pair.val;
        }
        return pair;
    }

    static GamePlay.Pair minimize(StateNode stateNode, double alpha, double beta, int k) {
        if (k <= 0) return new GamePlay.Pair(stateNode, utility(stateNode));
        GamePlay.Pair pair = new GamePlay.Pair(null, OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utility = maximize(child, alpha, beta, k - 1).val;
            if (utility < pair.val)
                pair = new GamePlay.Pair(child, utility);
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
