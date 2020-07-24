package com.qrilt.oaky;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    // Properties
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private static String tempAudioFileName;
    private boolean isRecording = false;
    private boolean isPlaying = false;

    // Views
    Button recordButton;
    Button playButton;

    // Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup widgets
        recordButton = findViewById(R.id.activity_main_record_button);
        playButton = findViewById(R.id.activity_main_play_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording) {
                    stopRecording();
                    isRecording = false;
                    recordButton.setText(R.string.main_activity_record_button_start_text);
                } else {
                    startRecording();
                    isRecording = true;
                    recordButton.setText(R.string.main_activity_record_button_stop_text);
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    stopPlaying();
                    isPlaying = false;
                    playButton.setText(R.string.main_activity_play_button_start_text);
                } else {
                    startPlaying();
                    isPlaying = true;
                    playButton.setText(R.string.main_activity_play_button_stop_text);
                }
            }
        });
    }

    void startRecording() {
        try {
            // setup MediaRecorder
            mediaRecorder = new MediaRecorder();

            int sourceOptions = MediaRecorder.AudioSource.DEFAULT;
            mediaRecorder.setAudioSource(sourceOptions);

            int outputOptions = MediaRecorder.OutputFormat.DEFAULT;
            mediaRecorder.setOutputFormat(outputOptions);

            tempAudioFileName = getExternalCacheDir().getAbsolutePath().concat("tempAudioFile.wav");
            mediaRecorder.setOutputFile(tempAudioFileName);

            int encoderOptions = MediaRecorder.AudioEncoder.DEFAULT;
            mediaRecorder.setAudioEncoder(encoderOptions);

            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    void startPlaying() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(tempAudioFileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void stopPlaying() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}