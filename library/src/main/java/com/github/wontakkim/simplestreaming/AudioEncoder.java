package com.github.wontakkim.simplestreaming;

import android.media.MediaCodec;
import android.media.MediaFormat;

import java.io.IOException;
import java.nio.ByteBuffer;

import static android.media.AudioFormat.CHANNEL_IN_DEFAULT;
import static android.media.MediaFormat.KEY_BIT_RATE;
import static android.media.MediaFormat.KEY_MAX_INPUT_SIZE;

public class AudioEncoder extends MediaEncoder {

    private final String TAG = "AUDIO_ENCODER";

    private String mimeType;
    private int sampleRate;
    private int channelCount;
    private int bitrate;

    private Callback callback;

    public AudioEncoder(String mimeType, int sampleRate, int channelCount, int bitrate) {
        this.mimeType = mimeType;
        this.sampleRate = sampleRate;
        this.channelCount = channelCount;
        this.bitrate = bitrate;
    }

    @Override
    protected MediaFormat buildMediaFormat() {
        MediaFormat format = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, sampleRate, CHANNEL_IN_DEFAULT);
        format.setInteger(KEY_MAX_INPUT_SIZE, 0);
        format.setInteger(KEY_BIT_RATE, 96000);
        return format;
    }

    @Override
    protected MediaCodec buildMediaCodec() throws IOException {
        return MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC);
    }

    @Override
    protected void onEncodedFrame(ByteBuffer buffer, MediaCodec.BufferInfo bufferInfo) {
        if (callback != null)
            callback.onEncodedAudioFrame(buffer, bufferInfo);
    }

    public interface Callback {

        void onEncodedAudioFrame(ByteBuffer buffer, MediaCodec.BufferInfo bufferInfo);
    }
}