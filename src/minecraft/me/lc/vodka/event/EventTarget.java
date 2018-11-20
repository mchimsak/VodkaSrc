package me.lc.vodka.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
    EventPriority priority() default EventPriority.LOW;
    Runnable r2 = ()-> System.out.println("233333333");
}
