package com.etf.os2.project.scheduler;

import java.util.LinkedList;
import java.util.List;

import com.etf.os2.project.process.Pcb;
import com.etf.os2.project.process.PcbData;

public class SJF extends Scheduler {
	private double koef;
	private boolean preemptive;
	private List<Pcb> readyList;

	public SJF(String string, String string2) {
		koef=Double.parseDouble(string);
		if(koef<0 || koef>1) koef=0.5;
		preemptive=Boolean.parseBoolean(string2);
		readyList= new LinkedList<>();
		
	}

	@Override
	public Pcb get(int cpuId) {
		if(readyList.size()==0)return null;
	
		Pcb p=readyList.remove(0);
		return p; 
	}

	@Override
	public void put(Pcb pcb) {
		if(pcb==null)return;

		PcbData pd= pcb.getPcbData();
		if(pd==null) {
			pd=new PcbData();
			pd.setTau(0);
			pcb.setPreempt(preemptive);	
		}else
			pd.setTau((long) (pcb.getExecutionTime()*koef+(1-koef)*koef*pd.getTau()));
		
		pcb.setPcbData(pd);
		
		int i;
		for(i=0; i<readyList.size();i++) {
			Pcb ppp=readyList.get(i);
			if(ppp.getPcbData().getTau()<=pd.getTau())continue;
			else break;
		}
		if(i==readyList.size()) readyList.add(pcb);
		else
		readyList.add(i, pcb);
		
		if(preemptive==true) {
			for(i=0;i<Pcb.RUNNING.length;i++)
				if(Pcb.RUNNING[i]!=null && !Pcb.RUNNING[i].getPreviousState().equals(Pcb.ProcessState.IDLE)) {
					if(Pcb.RUNNING[i].getPcbData().getTau()>pd.getTau()) {
					Pcb.RUNNING[i].preempt(); break;}
			}
		

	}

	}
}
