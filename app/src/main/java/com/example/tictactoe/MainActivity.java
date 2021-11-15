package com.example.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    final int[][] possibleWinningPositions = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
    boolean isGameActive;
    PlayerCode activePlayer;
    PlayerCode[] currentGameState = new PlayerCode[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame();
    }

    private void startGame() {
        Arrays.fill(this.currentGameState, PlayerCode.EMPTY);
        this.isGameActive = true;
        this.activePlayer = PlayerCode.X;
    }

    public void onBoardClick(View view) {
        ImageView image = (ImageView) view;
        int cellPosition = Integer.parseInt(image.getTag().toString());
        if (this.currentGameState[cellPosition].equals(PlayerCode.EMPTY)) {
            this.currentGameState[cellPosition] = this.activePlayer;
            image.setImageResource(this.activePlayer.getDrawable());
            switchPlayers();
        }
    }

    private void switchPlayers() {
        if (this.activePlayer.equals(PlayerCode.X)) {
            this.activePlayer = PlayerCode.O;
        } else {
            this.activePlayer = PlayerCode.X;
        }
    }
}