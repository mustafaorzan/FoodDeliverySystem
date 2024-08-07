package com.example.fooddeliverysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MusteriFiltre extends AppCompatActivity {
    Switch svAcikKapali;
    SeekBar sbTutar, sbMesafe;
    TextView tvTutar, tvMesafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_filtre);

        svAcikKapali = findViewById(R.id.switchAcikKapali);
        sbTutar = findViewById(R.id.seekBarMinTutar);
        sbMesafe = findViewById(R.id.seekBarMesafe);
        tvTutar = findViewById(R.id.textViewTutar);
        tvMesafe = findViewById(R.id.textViewMesafe);

        sbTutar.incrementProgressBy(5);

        svAcikKapali.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    svAcikKapali.setText("Açık");
                }
                else{
                    svAcikKapali.setText("Kapalı");
                }
            }
        });

        sbTutar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 5;
                progress = progress * 5;
                tvTutar.setText(progress + " TL");
                if (progress == 100){
                    tvTutar.setText("Hepsi");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbMesafe.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvMesafe.setText(progress + " km");
                if (progress == 5){
                    tvMesafe.setText("Hepsi");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}