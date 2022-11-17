package src;

import java.util.ArrayList;

public class StateNode {
    boolean[] played;  // should private
    boolean[] color;
    private StateNode parentNode;
    private byte redPoints;
    private byte yellowPoints;
    private ArrayList<Integer> topArr;
    private boolean MinOrMax;
    private int lastIndexPlayed;
    private int heuristic ;

    public StateNode() {
        // 6 * 7
        played = new boolean[42];
        color = new boolean[42];
    }

    public StateNode(StateNode parent, int indexOfPlay, boolean turn, int colIndex) {
        this();
        this.parentNode = parent;
        for (int i = 0; i < 42; i++) {
            played[i] = parent.played[i];
            color[i] = parent.color[i];
        }
        this.lastIndexPlayed = indexOfPlay;
        played[indexOfPlay] = true;
        color[indexOfPlay] = turn;
        MinOrMax = turn;
        topArr = new ArrayList<>(parent.topArr);
        topArr.set(colIndex, parent.getTopArr().get(colIndex) - 1);
        this.redPoints = parent.redPoints;
        this.yellowPoints = parent.yellowPoints;
        this.calculatePoints();
    }

    void calculatePoints() {
        int indexOfPlay = this.lastIndexPlayed;
        int left = 0;
        int right = 0;
        int down = 0;
        int upLeft = 0;
        int upRight = 0;
        int downLeft = 0;
        int downRight = 0;

        // down
        int p = indexOfPlay - 7;
        int i = 0;
        while ( p >= 0 && i < 3 && this.played[p] && this.color[p] == this.color[indexOfPlay]) {
            p -= 7;
            i++;
            down++;
        }
        // right
        p = indexOfPlay + 1;
        i = 0;
        while (p / 7 == indexOfPlay / 7 && i < 3 && this.played[p] && this.color[p] == this.color[indexOfPlay]) {
            p += 1;
            i++;
            right++;
        }
        p = indexOfPlay - 1;
        i = 0;
        // left
        while (p >= 0 && p / 7 == indexOfPlay / 7 && i < 3 && this.played[p] && this.color[p] == this.color[indexOfPlay]) {
            p -= 1;
            i++;
            left++;
        }
        // up left
        p = indexOfPlay + 6;
        i = 0;
        while (p < 42 && p % 7 != 6 && i < 3 && this.played[p] && this.color[p] == this.color[indexOfPlay]) {
            p += 6;
            i++;
            upLeft++;
        }
        // up right
        p = indexOfPlay + 8;
        i = 0;
        while (p < 42 && p % 7 != 0 && i < 3 && this.played[p] && this.color[p] == this.color[indexOfPlay]) {
            p += 8;
            i++;
            upRight++;
        }
        // down left
        p = indexOfPlay - 8;
        i = 0;
        while (p >= 0 && p < 42 && p % 7 != 6 && i < 3 && this.played[p] && this.color[p] == this.color[indexOfPlay]) {
            p -= 8;
            i++;
            downLeft++;
        }
        // down right
        p = indexOfPlay - 6;
        i = 0;
        while (p >= 0 && p < 42 && p % 7 != 0 && i < 3 && this.played[p] && this.color[p] == this.color[indexOfPlay]) {
            p -= 6;
            i++;
            downRight++;
        }
        int points = Math.max(0, down - 2) + Math.max(0, right + left - 2) + Math.max(0, upRight + downLeft - 2) + Math.max(0, upLeft + downRight - 2);
        if (color[indexOfPlay]) redPoints += points;
        else yellowPoints += points;
    }

    public ArrayList<Integer> getTopArr() {
        return this.topArr;
    }

    public void setTopArr(ArrayList<Integer> list) {
        this.topArr = list;
    }

    public boolean getTurn() {
        return this.MinOrMax;
    }

    public void setTurn(boolean turn) {
        this.MinOrMax = turn;
    }

    public StateNode getParentNode() {
        return this.parentNode;
    }

    public void setParentNode(StateNode node) {
        this.parentNode = node;
    }

    public byte getRedPoints() {
        return redPoints;
    }

    public void setRedPoints(byte redPoints) {
        this.redPoints = redPoints;
    }

    public byte getYellowPoints() {
        return yellowPoints;
    }

    public void setYellowPoints(byte yellowPoints) {
        this.yellowPoints = yellowPoints;
    }

    public int getLastIndexPlayed() {
        return lastIndexPlayed;
    }

    public void setLastIndexPlayed(int lastIndexPlayed) {
        this.lastIndexPlayed = lastIndexPlayed;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

}
