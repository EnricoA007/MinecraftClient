package me.enrico.dynamic.minecraftclient.api.player.standard.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.enrico.dynamic.minecraftclient.api.player.standard.event.EventListener.Priority;

public class EventFactory {
	
	private List<EventListener> listeners = new ArrayList<>();
	private Class<? extends Event>[] accessibleEvents;
	
	public EventFactory(@SuppressWarnings("unchecked") Class<? extends Event>... accessibleEvents) {
		this.accessibleEvents=accessibleEvents.clone();
	}
	
	public void registerListener(EventListener listener) {
		this.listeners.add(listener);
	}

	public void unregisterListener(EventListener listener) {
		this.listeners.add(listener);
	}
	
	public boolean accessibleEvent(Event event) {
		for(Class<? extends Event> clz : accessibleEvents) {
			if(clz.equals(event.getClass())) return true;
		}
		return false;
	}
	
	public void callEvent(Event event) {
		
		if(!accessibleEvent(event)) throw new IllegalAccessError("The event \"" + event.getClass().getName() + "\" is not accessible");
		
		ArrayList<Object[]> handlers = new ArrayList<>();
		
		listeners.forEach(listener -> {
			for(Method m : listener.getClass().getMethods()) {
				try {
					Annotation a = m.getAnnotation(EventHandler.class);
					if(m.getParameterCount() == 1 && m.getParameters()[0].getType().getSuperclass().equals(Event.class) && m.getParameters()[0].getType().equals(event.getClass())) {
						handlers.add(new Object[] {listener, m, (EventHandler)a});
						break;	
					}
				}catch(Exception e) {}
			}
		});
		
		if(handlers.isEmpty()) {
			return;
		}
		
		ArrayList<Object[]> sorted = new ArrayList<>();
		
		for(Priority priority : Priority.values()) {
			sort(handlers, priority, sorted);
		}
		
		sorted.forEach(obj->{
			try {
				System.out.println(event.getClass().getSimpleName() + " " + ((Method)obj[1]).getName());
				((Method)obj[1]).invoke((EventListener)obj[0], event);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		
	}
	
	private void sort(List<Object[]> obj, Priority priority, List<Object[]> into) {
		try {
			obj.forEach(o->{
				if(((EventHandler)o[2]).priority() == priority) {
					into.add(new Object[] {o[0], o[1]});
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
