package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.louisfellows.ld29.ai.AStarNode;
import com.louisfellows.ld29.ai.AStarPathfinder;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.screens.BattleScreen;

public class AIListener implements ControlListener {

    private static final int MAP_HEIGHT = 22;
    private static final int MAP_WIDTH = 40;
    private static final float STEP_TIME = 0.3f;
    private float planStepTime = STEP_TIME;
    private static final int MOVEMENT_INFLUENCE = 1;
    private SubActionListener listener;
    private final BattleScreen battleScreen;
    private boolean blockedMapInitialised = false;

    private final OrderedMap<Vector2, Boolean> blockedSquare = new OrderedMap<Vector2, Boolean>();

    public AIListener(BattleScreen battleScreen) {
        this.battleScreen = battleScreen;

    }

    @Override
    public void checkKeysAndUpdate(float delta) {
        if (listener != null) {
            if (!blockedMapInitialised) {
                createBlockedArray();
            }
            planStepTime -= delta;
            if (planStepTime < 0) {
                planStepTime = STEP_TIME;

                Vector2 start = getControlledLocationAsGrid();
                Vector2 goal = getNearestPlayerPosition();

                Vector2 v = action(start, goal);

                listener.alterXDirection(v.x);
                listener.alterYDirection(v.y);

                if (Math.abs(v.x) + Math.abs(v.y) != 0) {
                    listener.fire(v);
                }
            }
        }
    }

    @Override
    public void addListener(SubActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener(SubActionListener listener) {
        removeAllListeners();
    }

    @Override
    public void removeAllListeners() {
        this.listener = null;
    }

    private Vector2 action(Vector2 start, Vector2 goal) {
        AStarPathfinder pf = new AStarPathfinder(blockedSquare);

        Array<AStarNode> nodes = pf.calculate(new AStarNode(start.x, start.y), new AStarNode(goal.x, goal.y));

        int x = 0;
        int y = 0;

        if (nodes.size > 2) {
            AStarNode n = nodes.get(1);

            if (start.x > n.x) {
                x = -MOVEMENT_INFLUENCE;
            } else if (start.x < n.x) {
                x = MOVEMENT_INFLUENCE;
            }

            if (start.y > n.y) {
                y = -MOVEMENT_INFLUENCE;
            } else if (start.y < n.y) {
                y = MOVEMENT_INFLUENCE;
            }
        }
        return new Vector2(x, y);
    }

    private void createBlockedArray() {
        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                for (RectangleMapObject mo : battleScreen.getMapObjectLayer().getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle r = mo.getRectangle();
                    if (r.contains(new Vector2(getCenterPoint(i), getCenterPoint(j)))) {
                        blockedSquare.put(new Vector2(i, j), false);
                        break;
                    } else {
                        blockedSquare.put(new Vector2(i, j), true);
                    }
                }
            }
        }
        blockedMapInitialised = true;
    }

    private int getCenterPoint(int i) {
        return (i * 32) + 16;
    }

    private Vector2 getNearestPlayerPosition() {
        Vector2 current = getControlledLocation();

        Vector2 closest = current;
        double distance = 1000000;

        for (Vector2 p : battleScreen.getPlayerLocations()) {
            if (current.x != p.x || current.y != p.y) {
                double length = Math.sqrt(((current.x - p.x) * (current.x - p.x)) + ((current.y - p.y) * (current.y - p.y)));
                if (length < distance) {
                    closest = p;
                    distance = length;
                }
            }
        }

        return gridizeVector(closest);
    }

    private Vector2 getControlledLocation() {
        Sub sub = (Sub) listener;
        Vector2 start = new Vector2(sub.getX(), sub.getY());
        return start;
    }

    private Vector2 getControlledLocationAsGrid() {
        Sub sub = (Sub) listener;
        Vector2 start = new Vector2(sub.getX(), sub.getY());
        return gridizeVector(start);
    }

    private Vector2 gridizeVector(Vector2 vector) {
        vector.x = (int) (vector.x / 32);
        vector.y = (int) (vector.y / 32);

        return vector;
    }
}
