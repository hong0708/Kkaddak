package com.example.kkaddak.api.utils;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeatureExtractor {
    public static float[][] extractMFCC(File audioFile) throws UnsupportedAudioFileException, IOException {
        int sampleRate = 1500;
        int bufferSize = 512;
        int bufferOverlap = 256;
        final List<float[]> mfccList = new ArrayList<>();

        System.out.println(audioFile.getTotalSpace());
        System.out.println(audioFile.getAbsolutePath());
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFile.getAbsolutePath(), sampleRate, bufferSize, bufferOverlap);
        final MFCC mfcc = new MFCC(bufferSize, sampleRate);

        dispatcher.addAudioProcessor(mfcc);
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                float[] mfccArray = mfcc.getMFCC();
                mfccList.add(mfccArray);
                return true;
            }

            @Override
            public void processingFinished() {
            }
        });

        dispatcher.run();

        float[][] mfcc2DArray = new float[mfccList.size()][];
        for (int i = 0; i < mfccList.size(); i++) {
            mfcc2DArray[i] = mfccList.get(i);
        }

        return mfcc2DArray;
    }
}
