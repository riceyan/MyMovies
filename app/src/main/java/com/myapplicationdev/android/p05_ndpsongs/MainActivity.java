package com.myapplicationdev.android.p05_ndpsongs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;
    Button btnInsert, btnShowList, btnDelete;
    RadioGroup rg;
    TextView tvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etTitle = (EditText) findViewById(R.id.etTitle);
        etSingers = (EditText) findViewById(R.id.etSingers);
        etYear = (EditText) findViewById(R.id.etYear);
        btnInsert = (Button) findViewById(R.id.btnInsertSong);
        btnShowList = (Button) findViewById(R.id.btnShowList);
        rg = (RadioGroup) findViewById(R.id.rgStars);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        tvDelete = (TextView) findViewById(textViewDelete);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String singers = etSingers.getText().toString().trim();
                if (title.length() == 0 || singers.length() == 0){
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }


                String year_str = etYear.getText().toString().trim();
                int year = 0;
                try {
                    year = Integer.valueOf(year_str);
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper dbh = new DBHelper(MainActivity.this);

                int stars = getStars();
                dbh.insertSong(title, singers, year, stars);
                dbh.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();

                etTitle.setText("");
                etSingers.setText("");
                etYear.setText("");

            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Caution!");
                myBuilder.setMessage("Are you sure you want to delete the movie?");
                myBuilder.setCancelable(false);

                // Configure the 'positive' button
                myBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        tvDelete.setText("Movie not deleted");
                    }
                });
                // Configure the 'negative' button
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        tvDelete.setText("Movie has been deleted");
                    }
                });

    }


    private int getStars() {
        int stars = 1;
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.radio1:
                stars = 1;
                break;
            case R.id.radio2:
                stars = 2;
                break;
            case R.id.radio3:
                stars = 3;
                break;
            case R.id.radio4:
                stars = 4;
                break;
            case R.id.radio5:
                stars = 5;
                break;
        }
        return stars;
    }

}
