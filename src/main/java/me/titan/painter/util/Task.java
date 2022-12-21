package me.titan.painter.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class Task implements Runnable{
	ScheduledFuture future;


	ScheduledExecutorService executorService;

	public Task() {

		executorService = Executors.newSingleThreadScheduledExecutor();
	}

	public Task runTaskTimer(int initalDelay, int delay, TimeUnit timeUnit){

		this.future = executorService.scheduleAtFixedRate(this,initalDelay,delay,timeUnit);

		return this;
	}
	public Task runTask(int delay , TimeUnit timeUnit){
		this.future = executorService.schedule(this,delay,timeUnit);
		return this;
	}
	public void cancel(){
		future.cancel(false);
		onFinish();
	}


	public void onFinish(){

	}
}
