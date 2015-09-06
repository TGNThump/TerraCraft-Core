 package uk.co.terragaming.code.terracraft.utils;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Callback<A, B> implements Callable<Void>{
	
	private Runnable runnable;
	private Consumer<A> consumer;
	private BiConsumer<A,B> biConsumer;
	private A arg1;
	private B arg2;
	
	public Callback(Runnable callback){
		runnable = callback;
	}
	
	public Callback(Consumer<A> callback, A arg){
		consumer = callback;
		this.arg1 = arg;
	}
	
	public Callback(BiConsumer<A,B> callback, A arg1, B arg2){
		biConsumer = callback;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	public Void call(){
		if (biConsumer != null) biConsumer.accept(arg1, arg2);
		else if (consumer != null) consumer.accept(arg1);
		else runnable.run();
		return null;
	}

	public static Callback<Void, Void> create(Runnable callback) {
		return new Callback<Void, Void>(callback);
	}

	public static <A extends Object> Callback<A, Void> create(Consumer<A> callback, A arg) {
		return new Callback<A, Void>(callback, arg);
	}
	
	public static <A extends Object, B extends Object> Callback<A, B> create(BiConsumer<A,B> callback, A arg1, B arg2) {
		return new Callback<A, B>(callback, arg1, arg2);
	}
	
}
