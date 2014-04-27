package com.louisfellows.ld29.ai;

public class AStarNode implements Comparable<AStarNode> {
    public int x;
    public int y;
    public AStarNode parent = null;
    public int moveCost = 0;
    public int goalCost = 0;

    public AStarNode(int x, int y, AStarNode parent) {
        super();
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public AStarNode(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.parent = null;
    }

    public AStarNode(float x, float y) {
        super();
        this.x = (int) x;
        this.y = (int) y;
        this.parent = null;
    }

    public int getNodeCost() {
        return moveCost + goalCost;
    }

    @Override
    public int compareTo(AStarNode o) {
        return getNodeCost() - o.getNodeCost();
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "] move " + moveCost + " goal " + goalCost;
    }
}
