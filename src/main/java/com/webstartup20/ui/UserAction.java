package com.webstartup20.ui;


public enum UserAction {
    EXPORT(1, "Export"),
    IMPORT(2,"Import"),
    EXIT(3, "Exit");
    private int number;
    private String name;

    private UserAction(int number, String name) {
        this.number = number;
        this.name = name;
    }
    public static UserAction getActionFromIndex(Integer index) {
        for (UserAction userAction:values()) {
            if (userAction.number == index) {
                return userAction;
            }
        }
        return null;
    }

}
