package com.stormizn.cupicons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.Icon;

/**
 * Shared icon instance. The {@code @2x} variant is picked up automatically on HiDPI displays.
 */
public final class CupIcons {
    public static final Icon CUP = IconLoader.getIcon("/icons/cup.png", CupIcons.class);

    private CupIcons() {
    }
}
