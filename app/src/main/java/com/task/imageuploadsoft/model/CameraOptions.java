package com.task.imageuploadsoft.model;

import org.jetbrains.annotations.NotNull;

public class CameraOptions {
    public final String text;
    public final int icon;

    public CameraOptions(String text, Integer icon) {
        this.text = text;
        this.icon = icon;
    }

    @NotNull
    @Override
    public String toString() {
        return text;
    }
}
