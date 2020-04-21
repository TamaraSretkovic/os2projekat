package com.etf.os2.project.scheduler;

import java.util.ArrayList;
import java.util.LinkedList;

import com.etf.os2.project.process.Pcb;
import com.etf.os2.project.process.PcbData;

public class MFQ extends Scheduler {
	private int numQueue;
	private ArrayList<LinkedList<Pcb>> readyQueue;
	private Long [] timeSliceArray;
	

	public MFQ(String string, String[] schedArgs) {
		numQueue=Integer.parseInt(string);
		readyQueue=new ArrayList<>(numQueue);
		timeSliceArray=new Long[numQueue];
		for(int i=0; i<numQueue;i++) {
			readyQueue.add(i,new LinkedList<>());
			timeSliceArray[i]= Long.parseLong(schedArgs[i]);
		}
		
	}

	@Override
	public Pcb get(int cpuId) {
		for(int i=0;i<readyQueue.size();i++) {
		if(readyQueue.get(i).size()!=0) return readyQueue.get(i).remove(0);
	}
		return null;
		}

	@Override
	public void put(Pcb pcb) {
		if(pcb==null)return;
		
		if(pcb.getPreviousState().equals(Pcb.ProcessState.CREATED)) {
			int prio=pcb.getPriority();
			if(prio>=numQueue)prio=numQueue-1;
			
			pcb.setTimeslice(timeSliceArray[prio]);
			
			PcbData pd=new PcbData();
			pd.setQueue(prio);
			pcb.setPcbData(pd);
			
			readyQueue.get(prio).add(pcb);
			
			return;
		}
		else if(pcb.getPreviousState().equals(Pcb.ProcessState.BLOCKED)) {
			int q=pcb.getPcbData().getQueue();
			if(q>0) {
				q--;
				pcb.getPcbData().setQueue(q);}
			
			readyQueue.get(q).add(pcb);
			
			return;
		}
		else if(pcb.getPreviousState().equals(Pcb.ProcessState.READY)) {
			int q=pcb.getPcbData().getQueue();
			if(q<numQueue-1) {
				q++;
				pcb.getPcbData().setQueue(q);}
			
			readyQueue.get(q).add(pcb);
			
			return;
		}

		
	}

}
