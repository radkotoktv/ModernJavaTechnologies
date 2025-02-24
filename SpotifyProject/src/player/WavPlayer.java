package player;

import exception.WavPlayerException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

import static player.WAVPlayerConstants.WAV_BUFFER_SIZE;
import static player.WAVPlayerConstants.WAV_PATH;

public class WavPlayer implements Runnable {
    private final String fileName;

    public WavPlayer(String fileName) {
        this.fileName = WAV_PATH + fileName;
    }

    public void playWavFile(String filePath) {
        File audioFile = new File(filePath);

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            try (SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info)) {
                audioLine.open(format);
                audioLine.start();
                byte[] buffer = new byte[WAV_BUFFER_SIZE];
                int bytesRead;
                try {
                    while ((bytesRead = audioStream.read(buffer)) != -1) {
                        if (Thread.interrupted()) {
                            throw new InterruptedException();
                        }
                        audioLine.write(buffer, 0, bytesRead);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Playback was interrupted!");
                }

                audioLine.drain();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new WavPlayerException("Error playing WAV file", e);
        }
    }

    @Override
    public void run() {
        playWavFile(fileName);
    }
}
