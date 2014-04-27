package com.louisfellows.ld29.util;

public enum ControllerSelection {
    KEYBOARD_A("Keyboard A"), KEYBOARD_B("Keyboard B"), KEYBOARD_C("Keyboard C"), CONTROLLER_1("Controller 1"), CONTROLLER_2("Controller 2"), CONTROLLER_3(
            "Controller 3"), CONTROLLER_4("Controller 4"), AI("Computer"), NONE("None");

    private String stringValue;

    private ControllerSelection(String toString) {
        stringValue = toString;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public static ControllerSelection getEnumFromString(String string) {
        for (ControllerSelection selection : ControllerSelection.values()) {
            if (string.equals(selection.toString())) {
                return selection;
            }
        }
        return NONE;
    }
}
