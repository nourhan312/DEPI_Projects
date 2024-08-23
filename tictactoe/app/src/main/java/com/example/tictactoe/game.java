package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class game extends AppCompatActivity {
    private AppCompatButton clearButton;
    private ImageView backbtn;

    private TextView playerNameTextView;
    private ImageView playerSymbolView;
    private  ImageView ComputerSymbolView;
    private  TextView computerPoints;
    private  TextView playerPoints;

    private ImageView[] cells = new ImageView[9];
    private int[] board = new int[9]; // 0: empty, 1: player, 2: computer
    private boolean playerTurn = true; // true: player's turn, false: computer's turn

    private int playerSymbol;  // Store the player's symbol drawable resource
    private int computerSymbol; // Store the computer's symbol drawable resource

    private int playerScore = 0;
    private int computerScore = 0;
    private  SoundManager soundManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_singleuser_game);

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


        soundManager = new SoundManager(this);
        clearButton = findViewById(R.id.clear_button);
        backbtn = findViewById(R.id.game_back_icon);

        // Retrieve the data from Intent
        String playerName = getIntent().getStringExtra("PLAYER_NAME");
        String playerSymbolString = getIntent().getStringExtra("PLAYER_SYMBOL");

        // Initialize Views
        playerNameTextView = findViewById(R.id.playerName);
        playerSymbolView = findViewById(R.id.playerSymbol);
        ComputerSymbolView= findViewById(R.id.computerSymbol);
        computerPoints = findViewById(R.id.computerScore);
        playerPoints = findViewById(R.id.PlayerScore);



        // Set player's symbol based on the passed string ("X" or "O")
        if (playerSymbolString.equals("X")) {
            playerSymbol = R.drawable.xsymbol;
            computerSymbol = R.drawable.o;
        } else {
            playerSymbol = R.drawable.o;
            computerSymbol = R.drawable.xsymbol;
        }

        // Display the Player's Name and Symbol
        playerNameTextView.setText(playerName);
        playerSymbolView.setImageResource(playerSymbol);
        ComputerSymbolView.setImageResource(computerSymbol);

        // Set up click listeners for each cell
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
        });
        backbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            BackPressed();
        });
    }

    private void onCellClicked(int index) {
        if (board[index] != 0) {
            return; // Cell already occupied
        }

        if (playerTurn) {
            board[index] = 1;
            cells[index].setImageResource(playerSymbol); // Use the player's chosen symbol
            playerTurn = false;

            if (checkWinCondition(1)) {
                Toast.makeText(this, "Player Wins!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Computer's move
            computerMove();
        }
    }

    private void computerMove() {
        // Simple AI: Choose the first available cell
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                board[i] = 2;
                cells[i].setImageResource(computerSymbol); // Use the computer's symbol
                playerTurn = true;

                if (checkWinCondition(2)) {
                    Toast.makeText(this, "Computer Wins!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
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
                if (player == 1) {
                    playerScore++;
                    playerPoints.setText(String.valueOf(playerScore));
                    showWinDialog(playerNameTextView.getText().toString());
                } else if (player == 2) {
                    computerScore++;
                    computerPoints.setText(String.valueOf(computerScore));
                    showWinDialog("Computer");
                }
                return true;
            }
        }

        // Check for a draw
        boolean draw = true;
        for (int value : board) {
            if (value == 0) {
                draw = false;
                break;
            }
        }
        if (draw) {
            showDrawDialog();
            return true;
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
        clearBoard(); // Optionally clear the board as well
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

        quitButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(game.this, menu.class);
            startActivity(intent);
            // Handle the quit action
            finish(); // Or call any method to exit the application
        });

        continueButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    private void showWinDialog(String winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.win_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        TextView winMessage = view.findViewById(R.id.win_message_details);
        AppCompatButton continueButton = view.findViewById(R.id.offline_game_draw_continue_btn);
        AppCompatButton quitButton = view.findViewById(R.id.offline_game_draw_quit_btn);

        winMessage.setText(    winner + " Wins!");

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

        dialog.show();
    }


    private void showDrawDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.draw_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        AppCompatButton continueButton = view.findViewById(R.id.offline_game_draw_continue_btn);
        AppCompatButton quitButton = view.findViewById(R.id.offline_game_draw_quit_btn);

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

        dialog.show();
    }



}
