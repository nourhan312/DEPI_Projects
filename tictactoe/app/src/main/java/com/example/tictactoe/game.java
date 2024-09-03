package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class game extends AppCompatActivity {
    private AppCompatButton clearButton;
    private ImageView backbtn;

    private TextView playerNameTextView;
    private ImageView playerSymbolView;
    private ImageView ComputerSymbolView;
    private TextView computerPoints;
    private TextView playerPoints;

    private ImageView[] cells = new ImageView[9];
    private int[] board = new int[9]; // 0: empty, 1: player, 2: computer
    private boolean playerTurn = true; // true: player's turn, false: computer's turn

    private int playerSymbol;  // Store the player's symbol drawable resource
    private int computerSymbol; // Store the computer's symbol drawable resource

    private int playerScore = 0;
    private int computerScore = 0;
    private SoundManager soundManager;

    private CardView PlayerOneCard, ComputerCard;
    private boolean dialogShowing = false; // Prevent multiple dialogs from showing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_singleuser_game);



        String playerName = getIntent().getStringExtra("PLAYER_NAME");
        String playerSymbolString = getIntent().getStringExtra("PLAYER_SYMBOL");



        cells[0] = findViewById(R.id.img_1);
        cells[1] = findViewById(R.id.img_2);
        cells[2] = findViewById(R.id.img_3);
        cells[3] = findViewById(R.id.img_4);
        cells[4] = findViewById(R.id.img_5);
        cells[5] = findViewById(R.id.img_6);
        cells[6] = findViewById(R.id.img_7);
        cells[7] = findViewById(R.id.img_8);
        cells[8] = findViewById(R.id.img_9);

        soundManager = new SoundManager(this);
        clearButton = findViewById(R.id.clear_button);
        backbtn = findViewById(R.id.game_back_icon);

        // Initialize Views

        playerNameTextView = findViewById(R.id.playerName);
        playerSymbolView = findViewById(R.id.playerSymbol);
        ComputerSymbolView = findViewById(R.id.computerSymbol);
        computerPoints = findViewById(R.id.computerScore);
        playerPoints = findViewById(R.id.PlayerScore);
        PlayerOneCard = findViewById(R.id.playerCard);
        ComputerCard = findViewById(R.id.comuterCard);

        // Set player's symbol based on the passed string ("X" or "O")

        if (playerSymbolString.equals("X")) {
            playerSymbol = R.drawable.xsymbol;
            computerSymbol = R.drawable.o;
        } else {
            playerSymbol = R.drawable.o;
            computerSymbol = R.drawable.xsymbol;
        }


        playerNameTextView.setText(playerName);
        playerSymbolView.setImageResource(playerSymbol);
        ComputerSymbolView.setImageResource(computerSymbol);


        for (int i = 0; i < cells.length; i++) {
            final int index = i;
            cells[i].setOnClickListener(v -> {
                soundManager.playClickSound();
                onCellClicked(index);
            });
        }

        clearButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            clearBoard();
            clearScore();
        });
        backbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            BackPressed();
        });

        updateTurnIndicator();
    }

    private void computerMove() {
      //  ComputerCard.setBackground(ContextCompat.getDrawable(this, R.drawable.redcard_border));

        List<Integer> availableCells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                availableCells.add(i);
            }
        }

        if (!availableCells.isEmpty()) {
            int move = availableCells.get(new Random().nextInt(availableCells.size()));
            board[move] = 2;
            cells[move].setImageResource(computerSymbol);

            if (checkWinCondition(2)) {

                Toast.makeText(this, R.string.computer_wins, Toast.LENGTH_SHORT).show();
            }
            playerTurn = true;
            updateTurnIndicator();
        }
    }

    private boolean isDraw() {
        for (int value : board) {
            if (value == 0) {
                return false;
            }
        }
        return true;
    }

    private void onCellClicked(int index) {
        if (dialogShowing || board[index] != 0) {
            return;
        }

        if (playerTurn) {
            board[index] = 1;
            cells[index].setImageResource(playerSymbol);
            if (checkWinCondition(1)) {
                return; // Exit early if the player wins
            }
            if (isDraw()) {
                showDrawDialog();
                return;
            }
            playerTurn = false;
            computerMove();
        }
        updateTurnIndicator();
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
                if (player == 1) {
                    playerScore++;
                    playerPoints.setText(String.valueOf(playerScore));
                    showWinDialog(playerNameTextView.getText().toString());
                } else if (player == 2) {
                    computerScore++;
                    computerPoints.setText(String.valueOf(computerScore));
                    showWinDialog(getString(R.string.computer));
                }
                return true;
            }
        }

        return false;
    }

    private void clearBoard() {
        for (int i = 0; i < cells.length; i++) {
            board[i] = 0;
            cells[i].setImageDrawable(null); // Clear the images
        }
        playerTurn = true; // Reset to player's turn
    }

    private void clearScore() {
        playerScore = 0;
        computerScore = 0;
        playerPoints.setText(String.valueOf(playerScore));
        computerPoints.setText(String.valueOf(computerScore));
        clearBoard();
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

        quitButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(game.this, menu.class);
            startActivity(intent);
            finish(); // Handle the quit action
        });

        continueButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showWinDialog(String winner) {
        dialogShowing = true; // Set flag to true
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.win_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        TextView winMessage = view.findViewById(R.id.win_message_details);
        AppCompatButton continueButton = view.findViewById(R.id.offline_game_draw_continue_btn);
        AppCompatButton quitButton = view.findViewById(R.id.offline_game_draw_quit_btn);

        winMessage.setText(winner + getString(R.string.wins));

        continueButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            dialog.dismiss();
            clearBoard(); // Clear the board when continuing
        });

        quitButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(game.this, menu.class);
            startActivity(intent);
            dialog.dismiss();
            finish(); // Quit the game
        });

        dialog.setOnDismissListener(dialogInterface -> dialogShowing = false); // Reset flag when dialog is dismissed
        dialog.show();
    }

    private void showDrawDialog() {
        dialogShowing = true; // Set flag to true
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.draw_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        AppCompatButton continueButton = view.findViewById(R.id.offline_game_draw_continue_btn);
        AppCompatButton quitButton = view.findViewById(R.id.offline_game_draw_quit_btn);

        continueButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            dialog.dismiss();
            clearBoard();
        });

        quitButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(game.this, menu.class);
            startActivity(intent);
            dialog.dismiss();
            finish(); // Quit the game
        });

        dialog.setOnDismissListener(dialogInterface -> dialogShowing = false);

        dialog.show();
    }

    private void updateTurnIndicator() {
        if (playerTurn) {
            PlayerOneCard.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_card_white_border));
            ComputerCard.setBackground(ContextCompat.getDrawable(this, R.drawable.redcard_without_border));
        } else {
            ComputerCard.setBackground(ContextCompat.getDrawable(this, R.drawable.redcard_border));
            PlayerOneCard.setCardBackgroundColor(ContextCompat.getColor(this, R.drawable.blue_card_without_border));
        }
    }
}
