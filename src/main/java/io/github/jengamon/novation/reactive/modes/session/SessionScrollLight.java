package io.github.jengamon.novation.reactive.modes.session;

import com.bitwig.extension.controller.api.HardwareLightVisualState;
import io.github.jengamon.novation.ColorTag;
import io.github.jengamon.novation.internal.Session;
import io.github.jengamon.novation.reactive.SessionSendableLightState;
import io.github.jengamon.novation.reactive.atomics.BooleanSyncWrapper;

public class SessionScrollLight extends SessionSendableLightState {
    private int mID;
    private ColorTag mColor = new ColorTag(0xff, 0xa1, 0x61);
    private BooleanSyncWrapper mCanScroll;

    public SessionScrollLight(int id, BooleanSyncWrapper canScroll) {
        mID = id;
        mCanScroll = canScroll;
    }

    ColorTag calculateColor() {
        if(mCanScroll.get()) {
            return mColor;
        } else {
            return ColorTag.NULL_COLOR;
        }
    }

    @Override
    public HardwareLightVisualState getVisualState() {
        ColorTag color = calculateColor();
        return HardwareLightVisualState.createForColor(color.toBitwigColor());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != SessionScrollLight.class) return false;
        // TODO Actually implement proper comparison
        return false;
    }

    @Override
    public void send(Session session) {
        ColorTag color = calculateColor();
        session.sendMidi(0xB0, mID, color.selectNovationColor());
    }
}
