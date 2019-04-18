package com.neu.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multithread {
	private static final int MAX_T = 30;
	
	public static void demo()
	{
		List<Runnable> runnables = new ArrayList<Runnable>();
		
		for(int i=0; i<MAX_T; i++)
		{
			runnables.add(new Task("Thread" + i));
		}
		
		ExecutorService pool = Executors.newFixedThreadPool(1);
		
		for(Runnable r: runnables)
		{
			pool.execute(r);
		}
		
		pool.shutdown();
		
	}
}

