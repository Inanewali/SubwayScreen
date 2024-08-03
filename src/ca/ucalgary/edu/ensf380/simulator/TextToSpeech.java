package ca.ucalgary.edu.ensf380.simulator;

import com.sun.speech.freetts.*;

public class TextToSpeech {
    private static final String VOICE_NAME = "kevin16";

    public static void speak(String text) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice(VOICE_NAME);

        if (voice != null) {
            voice.allocate();
            voice.speak(text);
            voice.deallocate();
        } else {
            System.out.println("Voice not found: " + VOICE_NAME);
        }
    }
}

