package com.davidemolin.intellij.plugin.activeeditor.listeners;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.impl.EditorImpl;
import org.jetbrains.annotations.NotNull;
import java.awt.Color;

import com.intellij.codeInsight.daemon.impl.EditorTrackerListener;

public class CurrentEditorListener implements EditorTrackerListener {

    public static Color darkerColor(Color c, double amount) {
        float[] hsbvals = new float[3];
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbvals);
        hsbvals[2] = Math.min(1f, Math.max(0f, (float) (hsbvals[2] - amount)));
        int rgb = Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2]);
        rgb |= c.getAlpha() << 24;
        return new Color(rgb, true);
    }

    public static Color lighterColor(Color c, double amount) {
        return darkerColor(c, -amount);
    }

    @java.lang.Override
    public void activeEditorsChanged(java.util.@NotNull List<? extends Editor> activeEditors) {
        for (int i = 0; i < activeEditors.size(); i++) {
            EditorImpl editor = (EditorImpl) activeEditors.get(i);
            EditorColorsScheme colorScheme = editor.getColorsScheme();
            Color defaultBg = colorScheme.getDefaultBackground();

            // the editor is the active one if it's in position 0
            if (i == 0) {
                Color bgColor = editor.getBackgroundColor();
                editor.setBackgroundColor(lighterColor(defaultBg, 0.05));
            } else {
                editor.setBackgroundColor(darkerColor(defaultBg, 0.05));
            }
        }
    }
}