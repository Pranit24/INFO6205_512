package com.neu.multithread;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.neu.helper.Helper;
import com.neu.io.FileIO;
import com.neu.pojo.Individual;
import com.neu.solver.GeneticAlgo;

public class Task implements Runnable{

	private String name;
	
	public Task(String name) {
		super();
		this.name = name;
	}
	
	class Result{
		public volatile boolean done = false;
	}
	public final Result result = new Result();
	
	private final Lock lock = new ReentrantLock();
	
	CountDownLatch latch = new CountDownLatch(1);

	@Override
	public synchronized void run() {
		while(!result.done) 
		{
			System.out.println(name+" started!");
			
			result.done=true;
			//killing all the remaining threads
			if(result.done)
			{
				for (Thread t : Thread.getAllStackTraces().keySet()) 
				{  if (t.getState()==Thread.State.RUNNABLE) 
				     t.stop(); 
				}
			}
	}

}
}
