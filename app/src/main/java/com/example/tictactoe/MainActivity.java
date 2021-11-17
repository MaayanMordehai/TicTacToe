package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    Map<PlayerCode, String> winningStatus = new HashMap() {{
        put(PlayerCode.X, "Player X Won!");
        put(PlayerCode.O, "Player O Won!");
        put(PlayerCode.EMPTY, "It's a tie");
    }};
    Map<PlayerCode, PlayerCode> opponentPlayers = new HashMap() {{
        put(PlayerCode.X, PlayerCode.O);
        put(PlayerCode.O, PlayerCode.X);
    }};
    PlayerCode[] currentGameState = new PlayerCode[9];
    int numFilledCells = 0;
    boolean isGameActive;
    PlayerCode activePlayer;
    TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        // getting pos for turn
        ImageView image = (ImageView) view;
        int cellPosition = Integer.parseInt(image.getTag().toString());
        // if legal, doing turn
        if (this.isGameActive && this.currentGameState[cellPosition].equals(PlayerCode.EMPTY)) {
            playTurn(image, cellPosition);
            switchPlayers();
            checkEndGame();
        }
    }

    private void playTurn(ImageView image, int cellPosition) {
        this.currentGameState[cellPosition] = this.activePlayer;
        image.setImageResource(this.activePlayer.getDrawable());
        this.numFilledCells++;
    }

    private void checkEndGame() {
        PlayerCode winner = checkForWinner();
        if (this.numFilledCells >= this.currentGameState.length || winner != PlayerCode.EMPTY) {
            this.isGameActive = false;
            this.status.setText(winningStatus.get(winner));
        }
    }

    private void switchPlayers() {
        this.activePlayer = opponentPlayers.get(this.activePlayer);
        this.status.setText(String.format("It's %s turn", this.activePlayer.toString()));
    }

    private PlayerCode checkForWinner() {
        for (int[] winPos: this.possibleWinningPositions) {
            if (this.currentGameState[winPos[0]] == this.currentGameState[winPos[1]] &&
                    this.currentGameState[winPos[1]] == this.currentGameState[winPos[2]] &&
                    this.currentGameState[winPos[0]] != PlayerCode.EMPTY) {
                return this.currentGameState[winPos[0]];
            }
        }
        return PlayerCode.EMPTY;
    }

}