package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class MultiPlayerGame extends AppCompatActivity {

    private AppCompatButton clearButton;
    private ImageView backbtn, player1SymbolView, player2SymbolView;

    private CardView PlayerOneCard , PlayerTwoCard;
    private TextView playeroneNameTextView, playertwoNameTextView, player2Points, player1Points;

    private ImageView[] cells = new ImageView[9];
    private int[] board = new int[9]; // 0: empty, 1: player1, 2: player2
    private boolean playerTurn = true; // true: player1's turn, false: player2's turn

    private int playerOneSymbol;  // Store the player's symbol drawable resource
    private int playerTwoSymbol;  // Store the second player's symbol drawable resource

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private  SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_game);
        EdgeToEdge.enable(this);

        // Initialize the grid cells
        cells[0] = findViewById(R.id.img_1);
        cells[1] = findViewById(R.id.img_2);
        cells[2] = findViewById(R.id.img_3);
        cells[3] = findViewById(R.id.img_4);
        cells[4] = findViewById(R.id.img_5);
        cells[5] = findViewById(R.id.img_6);
        cells[6] = findViewById(R.id.img_7);
        cells[7] = findViewById(R.id.img_8);
        cells[8] = findViewById(R.id.img_9);

        clearButton = findViewById(R.id.clear_button);
        backbtn = findViewById(R.id.game_back_icon);


        String playerOneName = getIntent().getStringExtra("PLAYER1_NAME");
        String playerTwoName = getIntent().getStringExtra("PLAYER2_NAME");
        String playerOneSymbolString = getIntent().getStringExtra("PLAYER1_SYMBOL");



        soundManager = new SoundManager(this);

        // player 1
        playeroneNameTextView = findViewById(R.id.FplayerName);
        player1Points = findViewById(R.id.FPlayerScore);
        player1SymbolView = findViewById(R.id.FplayerSymbol);
        PlayerOneCard = findViewById(R.id.playerOneCard);

           // player 2

        playertwoNameTextView = findViewById(R.id.SplayerName);
        player2Points = findViewById(R.id.SPlayerScore);
        player2SymbolView = findViewById(R.id.SplayerSymbol);
        PlayerTwoCard = findViewById(R.id.playerTwoCard);


        if (playerOneSymbolString.equals("X")) {
            playerOneSymbol = R.drawable.xsymbol;
            playerTwoSymbol = R.drawable.o;
        } else {
            playerOneSymbol = R.drawable.o;
            playerTwoSymbol = R.drawable.xsymbol;
        }


        playeroneNameTextView.setText(playerOneName);
        playertwoNameTextView.setText(playerTwoName);
        player1SymbolView.setImageResource(playerOneSymbol);
        player2SymbolView.setImageResource(playerTwoSymbol);


        for (int i = 0; i < cells.length; i++) {
            final int index = i;
            cells[i].setOnClickListener(v -> {
                soundManager.playClickSound();
                onCellClicked(index);
            });
        }

        clearButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            clearScore();
        });
        backbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            BackPressed();
        });

        updateTurnIndicator();
    }
    public void BackPressed() {
        showQuitDialog();
    }


    private void showQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.quit_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        AppCompatButton quitButton = view.findViewById(R.id.quit_btn);
        AppCompatButton continueButton = view.findViewById(R.id.continue_btn);


        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        View dialogView = LayoutInflater.from(this).inflate(R.layout.quit_dialog, null);
        dialog.setContentView(dialogView);
        quitButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MultiPlayerGame.this, menu.class);
            startActivity(intent);
            finish();
        });

        continueButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            dialog.dismiss();
        });

        dialog.show();
    }
    private void onCellClicked(int index) {

        if (board[index] != 0) {
            return;   // Cell already occupied
        }

        if (playerTurn) {
            board[index] = 1;
            cells[index].setImageResource(playerOneSymbol);
            if (checkWinCondition(1)) {
                playerOneScore++;
                player1Points.setText(String.valueOf(playerOneScore));
                showWinDialog(playeroneNameTextView.getText().toString());
                return;
            }
        } else {
            board[index] = 2;
            cells[index].setImageResource(playerTwoSymbol);
            if (checkWinCondition(2)) {
                playerTwoScore++;
                player2Points.setText(String.valueOf(playerTwoScore));
                showWinDialog(playertwoNameTextView.getText().toString());
                return;
            }
        }

        playerTurn = !playerTurn; // Toggle turn
        updateTurnIndicator();    // Update turn indicator
        if (isDraw()) {
            showDrawDialog();
        }
    }

    private boolean checkWinCondition(int player) {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
                {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] winCondition : winConditions) {
            int a = winCondition[0], b = winCondition[1], c = winCondition[2];
            if (board[a] == player && board[a] == board[b] && board[a] == board[c]) {
                return true;
            }
        }

        return false;
    }

    private boolean isDraw() {
        for (int value : board) {
            if (value == 0) {
                return false;
            }
        }
        return true;
    }

    private void clearBoard() {
        for (int i = 0; i < cells.length; i++) {
            board[i] = 0;
            cells[i].setImageDrawable(null); // Clear the images
        }
        playerTurn = true; // Reset to player 1's turn
        updateTurnIndicator(); // Update turn indicator
    }

    private void clearScore() {
        playerOneScore = 0;
        playerTwoScore = 0;
        player1Points.setText(String.valueOf(playerOneScore));
        player2Points.setText(String.valueOf(playerTwoScore));
        clearBoard(); // Optionally clear the board as well
    }

    private void updateTurnIndicator() {

        // Reset borders for both player cards
        PlayerOneCard.setBackground(null);
        PlayerTwoCard.setBackground(null);

        if (playerTurn) {
            // Player 1's turn: Highlight Player 1's card
            PlayerOneCard.setBackground(ContextCompat.getDrawable(this, R.drawable.redcard_border));
            PlayerTwoCard.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_card_without_border));
        } else {
            // Player 2's turn: Highlight Player 2's card
            PlayerTwoCard.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_card_white_border));
            PlayerOneCard.setBackground(ContextCompat.getDrawable(this, R.drawable.redcard_without_border)); // Highlight PlayerOneCard
        }
    }


    private void showWinDialog(String winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.win_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        TextView winMessage = view.findViewById(R.id.win_message_details);
        AppCompatButton continueButton = view.findViewById(R.id.offline_game_draw_continue_btn);
        AppCompatButton quitButton = view.findViewById(R.id.offline_game_draw_quit_btn);
        // Set the background of the dialog window to be transparent
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Optionally, adjust the size of the dialog window
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        View dialogView = LayoutInflater.from(this).inflate(R.layout.win_dialog, null);
        dialog.setContentView(dialogView);
        winMessage.setText( winner + " Wins!");

        continueButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            dialog.dismiss();
            clearBoard(); // Clear the board when continuing
        });

        quitButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MultiPlayerGame.this, menu.class);
            startActivity(intent);
            dialog.dismiss();
            finish(); // Quit the game
        });

        dialog.show();
    }

    private void showDrawDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.draw_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        AppCompatButton continueButton = view.findViewById(R.id.offline_game_draw_continue_btn);
        AppCompatButton quitButton = view.findViewById(R.id.offline_game_draw_quit_btn);
        // Set the background of the dialog window to be transparent
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Optionally, adjust the size of the dialog window
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        View dialogView = LayoutInflater.from(this).inflate(R.layout.draw_dialog, null);
        dialog.setContentView(dialogView);
        continueButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            dialog.dismiss();
            clearBoard(); // Clear the board when continuing
        });

        quitButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MultiPlayerGame.this, menu.class);
            startActivity(intent);
            dialog.dismiss();
            finish(); // Quit the game
        });

        dialog.show();
    }
}
