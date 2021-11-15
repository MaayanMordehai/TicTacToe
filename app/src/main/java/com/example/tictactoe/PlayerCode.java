package com.example.tictactoe;

public enum PlayerCode {
    X(R.drawable.x),
    O(R.drawable.o),
    EMPTY(null);

    final Integer drawable;

    PlayerCode(Integer drawable) {
        this.drawable = drawable;
    }

    public Integer getDrawable() {
        return drawable;
    }
}
