package me.enrico.dynamic.minecraftclient.api.player.standard.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.enrico.dynamic.minecraftclient.api.player.standard.event.EventListener.Priority;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
	public Priority priority() default Priority.NORMAL;
}