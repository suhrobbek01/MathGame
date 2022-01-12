package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mathgame.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        File file = new File("MathGame.txt");
        try {
            file.createNewFile();
            System.out.println(file.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }


        binding.plus.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Practice.class);
            intent.putExtra("practice", "plus");
            startActivity(intent);
        });
        binding.minus.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Practice.class);
            intent.putExtra("practice", "minus");
            startActivity(intent);
        });
        binding.multiplication.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Practice.class);
            intent.putExtra("practice", "multiplication");
            startActivity(intent);
        });
        binding.division.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Practice.class);
            intent.putExtra("practice", "division");
            startActivity(intent);
        });
        binding.random.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Practice.class);
            intent.putExtra("practice", "random");
            startActivity(intent);
        });

    }

    private String readFile(File file) {
        StringBuilder s1 = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                s1.append(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return s1.toString();

       /* String[] list1 = file.list();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list1.length; i++) {
            stringBuilder.append(list1[i]);
        }
        return stringBuilder.toString();*/
    }

}