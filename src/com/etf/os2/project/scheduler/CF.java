package com.etf.os2.project.scheduler;

import java.util.LinkedList;
import java.util.List;

import com.etf.os2.project.process.Pcb;
import com.etf.os2.project.process.PcbData;

public class CF extends Scheduler {

	private List<Pcb> readyList;
	
	public CF() {
		readyList=new LinkedList<>();
	}
	@Override
	public Pcb get(int cpuId) {
		if(readyList.size()==0)return null;
		
		Pcb pcb=readyList.remove(0);
	
		long time= Pcb.getCurrentTime();
		pcb.setTimeslice((time-pcb.getPcbData().getTau())/(long)Pcb.getProcessCount());
		
		return pcb;
	}

	@Override
	public void put(Pcb pcb) {
		if(pcb==null)return;

		PcbData pb= pcb.getPcbData();
		if(pb==null) { 
			pb=new PcbData();
			pb.setExtime(0);
		}else 
			pb.setExtime(pb.getExtime()+pcb.getExecutionTime());
		
		pb.setTau(Pcb.getCurrentTime());
		pcb.setPcbData(pb);
		
		int i;
		for(i=0; i<readyList.size();i++) {
			Pcb ppp=readyList.get(i);
			if(ppp.getPcbData().getExtime()<=pb.getExtime())continue;
			else break;
		}
		if(i==readyList.size()) readyList.add(pcb);
		else
		readyList.add(i, pcb);

	}

}
