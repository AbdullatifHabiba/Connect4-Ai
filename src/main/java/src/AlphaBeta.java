package src;

import static src.GamePlay.*;

public class AlphaBeta {
    final static int OO = (int) 1e9;

    static Pair maximize(StateNode stateNode, int alpha, int beta, int k ,int moveNumb) {
        if (k <= 0|| moveNumb ==42) return new Pair(stateNode, minMaxWithout.utility(stateNode));
        Pair pair = new Pair(null, -1 * OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utl = minimize(child, alpha, beta, k - 1,moveNumb+1).val;
            if (utl > pair.val)
                pair = new Pair(child, utl);
            if (pair.val >= beta)
                break;
            if (pair.val > alpha)
                alpha = pair.val;
        }
        return pair;
    }

    static Pair minimize(StateNode stateNode, int alpha, int beta, int k,int moveNumb) {
        if (k <= 0 || moveNumb ==42) return new Pair(stateNode, minMaxWithout.utility(stateNode));
        Pair pair = new Pair(null, OO);
        for (StateNode child : makeChildrenReady(stateNode)) {
            int utl = maximize(child, alpha, beta, k - 1 ,moveNumb+1).val;
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

//    static StateNode decision(StateNode stateNode, int k ,int moveNumb) {
//        return maximize(stateNode, -1 * OO, OO, k, moveNumb).state;
//    }
}