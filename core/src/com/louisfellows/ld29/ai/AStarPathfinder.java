package com.louisfellows.ld29.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public class AStarPathfinder {

    private static final int HORIZ_VERT_MOVE_COST = 10;
    private static final int DIAG_MOVE_COST = 14;

    private AStarNode goal;

    Array<AStarNode> openList = new Array<AStarNode>();
    Array<AStarNode> closedList = new Array<AStarNode>();

    private OrderedMap<Vector2, Boolean> map = new OrderedMap<Vector2, Boolean>();

    public AStarPathfinder(OrderedMap<Vector2, Boolean> map) {
        this.map = map;
    }

    public Array<AStarNode> calculate(AStarNode start, AStarNode goal) {
        this.goal = goal;
        openList.add(start);

        start.goalCost = calculateCostToGoal(start);

        expand(start);

        while (true) {
            openList.sort();
            if (openList.size == 0) {
                return new Array<AStarNode>();
            }
            AStarNode lowestScore = openList.first();
            if (lowestScore.x == goal.x && lowestScore.y == goal.y) {
                return createPathList(lowestScore);
            }
            expand(lowestScore);
        }
    }

    private Array<AStarNode> createPathList(AStarNode endNode) {
        Array<AStarNode> path = new Array<AStarNode>();
        while (endNode.parent != null) {
            path.add(endNode);
            endNode = endNode.parent;
        }
        path.reverse();
        return path;
    }

    private void expand(AStarNode node) {
        openList.removeValue(node, true);

        openAStarNode(node.x - 1, node.y - 1, node, DIAG_MOVE_COST);
        openAStarNode(node.x - 1, node.y + 1, node, DIAG_MOVE_COST);
        openAStarNode(node.x + 1, node.y - 1, node, DIAG_MOVE_COST);
        openAStarNode(node.x + 1, node.y + 1, node, DIAG_MOVE_COST);

        openAStarNode(node.x - 1, node.y, node, HORIZ_VERT_MOVE_COST);
        openAStarNode(node.x + 1, node.y, node, HORIZ_VERT_MOVE_COST);
        openAStarNode(node.x, node.y - 1, node, HORIZ_VERT_MOVE_COST);
        openAStarNode(node.x, node.y + 1, node, HORIZ_VERT_MOVE_COST);

        closedList.add(node);
    }

    private void openAStarNode(int x, int y, AStarNode parent, int moveCost) {
        AStarNode aStarNode = new AStarNode(x, y, parent);

        AStarNode checkClosedNode = findNodeInClosedList(aStarNode);

        if (map.get(new Vector2(x, y)) != null && map.get(new Vector2(x, y)) && checkClosedNode == null) {

            AStarNode checkOpenNode = findNodeInOpenList(aStarNode);

            if (checkOpenNode == null) {
                aStarNode.moveCost = moveCost;
                aStarNode.goalCost = calculateCostToGoal(aStarNode);
                openList.add(aStarNode);
            } else {
                if (parent.moveCost + moveCost < checkOpenNode.moveCost) {
                    checkOpenNode.moveCost = parent.moveCost + moveCost;
                    checkOpenNode.parent = parent;
                }
            }
        }
    }

    private int calculateCostToGoal(AStarNode node) {
        return (Math.abs(node.x - goal.x) + Math.abs(node.y - goal.y)) * 10;
    }

    private AStarNode findNodeInClosedList(AStarNode node) {
        for (AStarNode n : closedList) {
            if (n.x == node.x && n.y == node.y) {
                return n;
            }
        }
        return null;
    }

    private AStarNode findNodeInOpenList(AStarNode node) {
        for (AStarNode n : openList) {
            if (n.x == node.x && n.y == node.y) {
                return n;
            }
        }
        return null;
    }
}
