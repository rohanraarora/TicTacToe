package com.example.android.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int NO_PLAYER = 0;
    public static final int PLAYER_O = 1;
    public static final int PLAYER_X = 2;

    public static final int INCOMPLETE = 0;
    public static final int PLAYER_O_WON = 1;
    public static final int PLAYER_X_WON = 2;
    public static final int DRAW = 3;
    public int currentStatus = INCOMPLETE;

    public int size = 3;

    public int currentPlayer = PLAYER_O;

    LinearLayout rootLayout;
    LinearLayout[] rows = new LinearLayout[size];
    CustomButton[][] board = new CustomButton[size][size];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String name = i.getStringExtra(StartActivity.KEY_USERNAME);
        int number = i.getIntExtra("number",-1);
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();

        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);

        setUpBoard();
    }

    public void setUpBoard(){

        rows = new LinearLayout[size];
        board = new CustomButton[size][size];

        currentPlayer = PLAYER_O;
        currentStatus = INCOMPLETE;

        rootLayout.removeAllViews();

        for(int i = 0;i<size;i++){

            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            rows[i] = linearLayout;
            rootLayout.addView(linearLayout);

        }


        for(int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){

                CustomButton button = new CustomButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(params);
                button.setOnClickListener(this);
                board[i][j] = button;
                rows[i].addView(button);

            }
        }

    }

    @Override
    public void onClick(View view) {

       if(currentStatus == INCOMPLETE){
           CustomButton customButton = (CustomButton)view;
           customButton.setEnabled(false);
           if(!customButton.isEmpty()){
               Toast.makeText(this,"Invalid move",Toast.LENGTH_SHORT).show();
               return;
           }

           customButton.setPlayer(currentPlayer);
           checkGameStatus();


           toggleCurrentPlayer();
       }


    }

    private void checkGameStatus() {

        for(int i = 0;i<size;i++){
            boolean rowSame = true;
            for(int j = 0;j<size;j++){
                if(board[i][j].isEmpty() || board[i][0].getPlayer() != board[i][j].getPlayer()){
                    rowSame = false;
                    break;
                }
            }
            if(rowSame){
                int winner = board[i][0].getPlayer();
                setWinner(winner);
                return;
            }
        }

        for(int j = 0;j<size;j++){
            boolean colSame = true;
            for(int i = 0;i<size;i++){
                if(board[i][j].isEmpty() || board[0][j].getPlayer() != board[i][j].getPlayer()){
                    colSame = false;
                    break;
                }
            }
            if(colSame){
                int winner = board[0][j].getPlayer();
                setWinner(winner);
                return;
            }
        }


        // dor diagonal 1
        boolean d1Same = true;
        for(int i = 0;i<size;i++){
            if(board[i][i].isEmpty() || board[0][0].getPlayer() != board[i][i].getPlayer()){
                d1Same = false;
                break;
            }
        }
        if(d1Same){
            int winner = board[0][0].getPlayer();
            setWinner(winner);
            return;
        }

        boolean d2Same = true;
        for(int i = 0;i<size;i++){
            int col = size - 1 -i;
            if(board[i][col].isEmpty() || board[0][size-1].getPlayer() != board[i][col].getPlayer()){
                d2Same = false;
                break;
            }
        }
        if(d2Same){
            int winner = board[0][size-1].getPlayer();
            setWinner(winner);
            return;
        }

        for(int i = 0;i<size;i++){
            for (int j = 0;j<size;j++){
                if(board[i][j].isEmpty()){
                    currentStatus = INCOMPLETE;
                    return;
                }
            }
        }

        currentStatus = DRAW;
        Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();


    }

    private void toggleCurrentPlayer() {

        currentPlayer = currentPlayer == PLAYER_O?PLAYER_X: PLAYER_O;

    }

    public void setWinner(int player){
        if(player == PLAYER_O){
            currentStatus  = PLAYER_O_WON;
            Toast.makeText(this,"O Won",Toast.LENGTH_SHORT).show();

        }
        else {
            currentStatus  = PLAYER_X_WON;
            Toast.makeText(this,"X Won",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.abc,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.reset){
            size = 3;
            setUpBoard();
        }
        else if(id == R.id.test){
            size = 4;
            setUpBoard();
        }

        return super.onOptionsItemSelected(item);
    }
}
