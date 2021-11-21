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
    int numOfFilledCells;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = findViewById(R.id.main_activity_status);
        startGame();
    }

    private void startGame() {
        numOfFilledCells = 0;
        Arrays.fill(this.currentGameState, PlayerCode.EMPTY);
        this.isGameActive = true;
        this.activePlayer = PlayerCode.X;
        this.status.setText(String.format("It's %s turn", this.activePlayer.toString()));
    }

    public void onBoardClick(View view) {
        if (isGameActive) {
            ImageView imageView = (ImageView) view;
            int cellPosition = Integer.parseInt(imageView.getTag().toString());
            if (isPossibleMove(cellPosition)) {
                markCell(cellPosition, imageView);
                if(isEndGame()) {
                    this.isGameActive = false;
                } else {
                    switchPlayers();
                }
            }
        }
    }

    public void onResetClick(View view) {
        ((ImageView) findViewById(R.id.main_activity_cell0)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell1)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell2)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell3)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell4)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell5)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell6)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell7)).setImageResource(0);
        ((ImageView) findViewById(R.id.main_activity_cell8)).setImageResource(0);
        startGame();
    }

    private void switchPlayers() {
        if (this.activePlayer.equals(PlayerCode.X)) {
            this.activePlayer = PlayerCode.O;
        } else {
            this.activePlayer = PlayerCode.X;
        }
        this.status.setText(String.format("It's %s turn", this.activePlayer.toString()));
    }

    private boolean isWinner() {
        for (int[] winPos: this.possibleWinningPositions) {
            if (
                    (this.currentGameState[winPos[0]] != PlayerCode.EMPTY) &&
                    (this.currentGameState[winPos[0]] == this.currentGameState[winPos[1]]) &&
                    (this.currentGameState[winPos[1]] == this.currentGameState[winPos[2]])
            ) {
                return true;
            }
        }
        return false;
    }

    private boolean isTie() {
        return (numOfFilledCells >= this.currentGameState.length);
    }

    private boolean isPossibleMove(int cellPosition) {
        return this.currentGameState[cellPosition].equals(PlayerCode.EMPTY);
    }

    private void markCell(int cellPosition, ImageView imageView) {
        this.currentGameState[cellPosition] = this.activePlayer;
        imageView.setImageResource(this.activePlayer.getDrawable());
        numOfFilledCells++;
    }

    private boolean isEndGame() {
        if (isWinner()) {
            this.status.setText(String.format("Player %s Won!", this.activePlayer.toString()));
            return true;
        } else if (isTie()) {
            this.status.setText("It's a tie");
            return true;
        }

        return false;
    }
}