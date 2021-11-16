package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView status;
    boolean endgame = false;
    int numFilledCells = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = new View(this);
        status = findViewById(R.id.main_activity_status);
        startGame();
    }

    private void startGame() {
        Arrays.fill(this.currentGameState, PlayerCode.EMPTY);
        this.isGameActive = true;
        this.activePlayer = PlayerCode.X;
        this.status.setText(String.format("It's %s turn", this.activePlayer.toString()));
    }

    public void onBoardClick(View view) {
        if (!this.endgame){
            ImageView image = (ImageView) view;
            int cellPosition = Integer.parseInt(image.getTag().toString());
            if (this.currentGameState[cellPosition].equals(PlayerCode.EMPTY)) {
                this.currentGameState[cellPosition] = this.activePlayer;
                image.setImageResource(this.activePlayer.getDrawable());
                if (isWinner()) {
                    this.status.setText(String.format("Player %s Won!", this.activePlayer.toString()));
                    this.endgame = true;
                } else if (numFilledCells >= this.currentGameState.length - 1) {
                    this.status.setText("It's a tie");
                    this.endgame = true;
                } else {
                    switchPlayers();
                    this.status.setText(String.format("It's %s turn", this.activePlayer.toString()));
                    numFilledCells++;
                }
            }
        }
    }

    private void switchPlayers() {
        if (this.activePlayer.equals(PlayerCode.X)) {
            this.activePlayer = PlayerCode.O;
        } else {
            this.activePlayer = PlayerCode.X;
        }
    }

    private boolean isWinner() {
        for (int[] winPos: this.possibleWinningPositions) {
            if (this.currentGameState[winPos[0]] == this.currentGameState[winPos[1]] &&
                    this.currentGameState[winPos[2]] == this.currentGameState[winPos[1]] &&
                    this.currentGameState[winPos[0]] != PlayerCode.EMPTY) {
                return true;
            }
        }
        return false;
    }

}