package com.example.bondesjakk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.gridlayout.widget.GridLayout.LayoutParams;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

//Klassen implementerer settingDialog, som arver AppCompatDialogFragment, den åpner instillinger dialogen
public class MainActivity extends AppCompatActivity implements settingsDialog.settingsDialogListener {
    private boolean gamePlaying = false;  //Dette er for å sjekke om spillet fortsetter eller ikke-
    private boolean gameStopped = true;  //Dette for å bestemme om brettet skal være grått eller ikke.
    //Spillet kan være stoppet selv om den er grønt, i tilfelle når ene spilleren vinner, eller når det er uavgjort.
    private int player = 1;  //tallet 1 er spiller "X" og tallet 2 er spiller "O"
    private int winner = -1; //Siden det er forøvrig ingen som vinner er den satt til -1
    private GameLogic gameLogic; //Egen klasse for å implementere spill-logikken
    private TextView[][] gameBoard; //For å få et enkelt 2D bilde av brettet av alle TextViews
    private Timer timer;
    private TextView tvTimer;
    private int counter = 0;
    private int playerXTotalTime;
    private int playerOTotalTime;
    private Handler mainHandler;
    private int nrOfRows = 3;
    private int nrOfColumns = 3;
    private int maxMoves = nrOfRows * nrOfColumns; //Max moves, for å gjøre spillet uavgjort når brettet er full
    TextView tvResults;  //TextView for å vise resultater
    TextView playerX;
    TextView playerO;
    GridLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        tvTimer = findViewById(R.id.tvTimer);
        timer = new Timer();
        mainHandler = new Handler(Looper.getMainLooper());
        tvResults = (TextView) findViewById(R.id.tvResults);
        playerX = findViewById(R.id.player_X);
        playerO = findViewById(R.id.player_O);
        gameLogic = new GameLogic(nrOfRows, nrOfColumns);

        buildBoard(nrOfRows, nrOfColumns);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_play:
                startGame(nrOfRows, nrOfColumns);
                return true;

            case R.id.action_stop:
                stopGame(nrOfRows, nrOfColumns);
                resetBoard();
                return true;

            case R.id.settings:
                openSettings();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //Funksjon for å bygge brettet
    public void buildBoard(int rows, int cols) {
        gameLayout = (GridLayout) findViewById(R.id.gridLayout);
        //gameLayout.setUseDefaultMargins(true); Kan ha noe margin mellon cellene i brettet, men vil helst ikke bruke
        gameLayout.setAlignmentMode(GridLayout.ALIGN_MARGINS);
        gameLayout.removeAllViewsInLayout();
        gameLayout.setColumnCount(cols); gameLayout.setRowCount(rows);
        gameBoard = new TextView[cols][rows];


        //Koden for å bestemme bredden til cellene i brettet slik at alle ser ut som kvadratisk
        WindowMetrics display = getWindowManager().getCurrentWindowMetrics();
        Rect rect = display.getBounds();
        int screenWidth = rect.width(); screenWidth = screenWidth - screenWidth / 10;
        int screenHeight = rect.height(); screenHeight = screenHeight - screenHeight / 10;

        int cellSize = 0;

        //Bestemme størrelsen avhengig av om skjermen er i "portrait" eller "land" posisjon
        if (screenHeight < screenWidth) {
            cellSize = rows < cols ? (screenHeight - screenHeight / 7) / cols: (screenHeight - screenHeight / 7) / rows;
        } else {cellSize = rows < cols ? screenWidth / cols: screenWidth / rows;}

        //cellSize = cellSize < 100 ? 100: cellSize;


        int index = 0; //indexen i GridLayout
        //Legger alle cellene i GridLayout og i gameBoard[][] basert på antall rader og kolonner.
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                TextView cell = new TextView(getApplicationContext(), null, 0, R.style.board_cell);
                cell.setId(10000 + ++index);
                cell.setBackground(getDrawable(R.drawable.board_cell_grey));
                int tempRow = j; int tempCol = i;
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                cell.setWidth(cellSize); cell.setHeight(cellSize);
                params.setMargins(5,5,5,5);
                cell.setLayoutParams(params);
                gameBoard[i][j] = cell;
                cell.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           onMove(tempRow, tempCol);
                                       }
                                   });

                gameLayout.addView(cell, new LayoutParams(
                        GridLayout.spec(j, GridLayout.START, 1),
                        GridLayout.spec(i, GridLayout.START, 1)));

            }
        }
    }

    //Starter spillet
    public void startGame(int rows, int cols) {
        gamePlaying = true; gameStopped = false;
        maxMoves = cols * rows;
        winner = -1;
        gameLogic = new GameLogic(rows, cols);
        Drawable board_cell_green = (Drawable) getDrawable(R.drawable.board_cell_green);
        tvResults.setText(getResources().getString(R.string.reults));
        Drawable player_cell_green = (Drawable) getDrawable(R.drawable.player_cell_green);
        if (player == 1) {
            playerX.setBackground(player_cell_green);
        } else playerO.setBackground(player_cell_green);
        int index = 0;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                gameBoard[i][j] = findViewById(10000 + ++index);
                gameBoard[i][j].setBackground(board_cell_green);
                gameBoard[i][j].setText("");
            }
        }
        playerXTotalTime = 0; playerOTotalTime = 0;
        stopTimer();
        startTimer();
    }

    //Når en spiller tar en bevegelse. Etter spillet er ferdig er det taperen som begynner.
    public void onMove(int row, int col) {
        TextView tv = (TextView) gameBoard[col][row];
        if (!tv.getText().equals("") || !gamePlaying || winner != -1) return;
        TextView player_X_cell = (TextView) findViewById(R.id.player_X);
        TextView player_O_cell = (TextView) findViewById(R.id.player_O);
        if (player == 1) {
            player_X_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_grey));
            player_O_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_green));
            tv.setText("X");
            tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_X));
        } else {
            player_X_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_green));
            player_O_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_grey));
            tv.setText("O");
            tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_O));
        }
        gameLogic.makeMove(player, col, row);
        maxMoves--;
        winner = gameLogic.checkWinner();
        player = player % 2 + 1;
        if (winner != -1 || maxMoves <= 0) {
            gamePlaying = false;
            showWinner(winner);
            stopTimer();
        } else {
            stopTimer();
            startTimer();
        }
    }

    //Stopper opp spillet
    public void stopGame(int rows, int cols) {
        gamePlaying = false;
        gameStopped = true;
        winner = -1; player = 1;
        gameLogic.removeBoard();
        tvResults.setText(getResources().getString(R.string.reults));
        Drawable player_cell_grey = (Drawable) getDrawable(R.drawable.player_cell_grey);
        playerX = findViewById(R.id.player_X);
        playerX.setBackground(player_cell_grey);
        playerO = findViewById(R.id.player_O);
        playerO.setBackground(player_cell_grey);
        stopTimer();
    }

    //Restarter spillet uten å måtte bygge det hele på nytt.
    public void resetBoard() {
        Drawable board_cell_grey = (Drawable) getDrawable(R.drawable.board_cell_grey);
        int index = 0;
        for (int i = 0; i < nrOfColumns; i++) {
            for (int j = 0; j < nrOfRows; j++) {
                gameBoard[i][j] = findViewById(10000 + ++index);
                gameBoard[i][j].setBackground(board_cell_grey);
                gameBoard[i][j].setText("");
            }
        }
    }

    //Kode for å vise fram vinneren, og eventuelt hvor mye tid hver spiller har brukt
    public void showWinner(int winner) {
        TextView tvResults = (TextView) findViewById(R.id.tvResults);
        String winnerChar = winner == 1 ? "X": "O";
        tvResults.clearComposingText();
        int seconds = playerXTotalTime % 60;
        int minutes =  playerXTotalTime / 60;
        String XTimeStr = String.format("%02d:%02d", minutes, seconds);
        seconds = playerOTotalTime % 60;
        minutes =  playerOTotalTime / 60;
        String OTimeStr = String.format("%02d:%02d", minutes, seconds);
        if (winner == -1) {
            tvResults.append(getResources().getString(R.string.no_winner_text));
        } else {
            tvResults.append(getResources().getString(R.string.winner_text) + " " + winnerChar);
        }
        tvResults.append(getResources().getString(R.string.time_used_X) + XTimeStr);
        tvResults.append(getResources().getString(R.string.time_used_Y) + OTimeStr);
        playerOTotalTime = 0;
        playerXTotalTime = 0;
    }

    //Kode for å åpne innstillinger. Instillinger er bare å velge antall rader og kolonner.
    public void  openSettings() {
        settingsDialog settingsDialog = new settingsDialog();
        settingsDialog.show(getSupportFragmentManager(), "settings_dialog");
    }

    //Implementere det som er i instillinger (når du klikker på OK knappen)
    @Override
    public void applySettings(int rows, int cols) {
        gameLogic = new GameLogic(rows, cols);
        gameBoard = new TextView[cols][rows];
        nrOfRows = rows; nrOfColumns = cols;
        buildBoard(nrOfRows, nrOfColumns);
        if (gameStopped) {
            stopGame(rows, cols);
        } else startGame(rows, cols);
    }

    //Begynner tellingen
    public void startTimer() {
        timer = new Timer();
        try {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    counter++;

                    //Bestmmer hvilke spiller sin tur er dette
                    if (player == 1) {
                        playerXTotalTime++;
                    } else playerOTotalTime++;


                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Kode som vil bli kjørt av hovedtråden:
                            int seconds = counter % 60;
                            int minutes = counter / 60;
                            String elapsedTime = String.format("%02d:%02d", minutes, seconds);
                            tvTimer.setText(String.valueOf(elapsedTime));
                        }
                    });

                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTimer.setText(String.valueOf(elapsedTime));
                        }
                    });*/
                }
            }, 0, 1000);

        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    //Slutter spillet
    public void stopTimer() {
        counter = 0;
        tvTimer.setText("00:00");
        timer.cancel();
        timer.purge();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", counter);
        outState.putBoolean("gamePlaying", gamePlaying);
        outState.putBoolean("gameStopped", gameStopped);
        outState.putInt("player", player);
        outState.putInt("winner", winner);
        outState.putInt("PlayerXTotalTime", playerXTotalTime);
        outState.putInt("PlayerOTotalTime", playerOTotalTime);
        outState.putInt("maxMoves", maxMoves);
        outState.putInt("rows", nrOfRows);
        outState.putInt("cols", nrOfColumns);

        //Dette for å hente en hver kolonne en og en. Brukes i onRestoreInstanceState for å bygge spillet
        //på nytt slik som det var
        for (int i = 0; i < nrOfColumns; i++) {
            outState.putIntArray("gameLogicBoard"+i, gameLogic.getCol(i));
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("counter");
        gamePlaying = savedInstanceState.getBoolean("gamePlaying");
        gameStopped = savedInstanceState.getBoolean("gameStopped");
        player = savedInstanceState.getInt("player");
        winner = savedInstanceState.getInt("winner");
        playerXTotalTime = savedInstanceState.getInt("PlayerXTotalTime");
        playerOTotalTime = savedInstanceState.getInt("PlayerOTotalTime");
        maxMoves = savedInstanceState.getInt("maxMoves");
        nrOfRows = savedInstanceState.getInt("rows");
        nrOfColumns = savedInstanceState.getInt("cols");

        //Henter alle radene i gameLogic
        gameLogic = new GameLogic(nrOfRows, nrOfColumns);
        for (int i = 0; i < nrOfColumns; i++) {
            gameLogic.setCol(savedInstanceState.getIntArray("gameLogicBoard"+i), i);
        }

        int seconds = counter % 60;
        int minutes = counter / 60;
        if (gamePlaying) {
            String elapsedTime = String.format("%02d:%02d", minutes, seconds);
            tvTimer.setText(String.valueOf(elapsedTime));
            startTimer();
        }


        TextView player_X_cell = (TextView) findViewById(R.id.player_X);
        TextView player_O_cell = (TextView) findViewById(R.id.player_O);
        if (player == 1 && !gameStopped) {
            player_O_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_grey));
            player_X_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_green));
        } else if (player == 2 && !gameStopped) {
            player_O_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_green));
            player_X_cell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.player_cell_grey));
        }

        gameBoard = new TextView[nrOfColumns][nrOfRows];
        buildBoard(nrOfRows, nrOfColumns);

        Drawable board_cell;
        if (gameStopped == false) {
            board_cell = (Drawable) getDrawable(R.drawable.board_cell_green);
        } else {
            board_cell = (Drawable) getDrawable(R.drawable.board_cell_grey);
        }
        int index = 0;
        for (int i = 0; i < nrOfColumns; i++) {
            for (int j = 0; j < nrOfRows; j++) {
                gameBoard[i][j] = findViewById(10000 + ++index);
                gameBoard[i][j].setBackground(board_cell);
                if (gameLogic.getPlayer(i, j) == 1) {
                    gameBoard[i][j].setText("X");
                    gameBoard[i][j].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_X));
                }
                else if (gameLogic.getPlayer(i, j) == 2) {
                    gameBoard[i][j].setText("O");
                    gameBoard[i][j].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_O));
                }
                else {
                    gameBoard[i][j].setText("");
                }
            }
        }
    }
}
