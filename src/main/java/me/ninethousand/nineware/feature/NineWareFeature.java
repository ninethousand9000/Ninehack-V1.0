package me.ninethousand.nineware.feature;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NineWareFeature {
    String name();
    String description() default "No description";
    Category category();
    int key() default Keyboard.KEY_NONE;
}