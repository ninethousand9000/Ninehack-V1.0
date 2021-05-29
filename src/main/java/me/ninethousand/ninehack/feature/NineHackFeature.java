package me.ninethousand.ninehack.feature;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NineHackFeature {
    String name();
    String description() default "No description";
    Category category();
    int key() default Keyboard.KEY_NONE;
}