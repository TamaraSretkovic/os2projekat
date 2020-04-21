package com.etf.os2.project.scheduler;
import com.etf.os2.project.process.Pcb;

public abstract class Scheduler {

	public abstract Pcb get(int cpuId);
    public abstract void put(Pcb pcb);

    public static Scheduler createScheduler(String[] args) {
		
    	switch (args[0].toUpperCase()) {
    	
		case "SJF":{
			if(args.length<3 || (!args[2].equals("true") && !args[2].equals("false"))) {
				System.out.println("Unesite argumente ponovo! Format:'brCpu brProc SJF koef true/false'");
				System.exit(1);
			}
			try {
				double proba= Double.parseDouble(args[1]);
			}catch (Exception e) {
				System.out.println("Unesite argumente ponovo! Format:'brCpu brProc SJF koef true/false'");
				System.exit(1);
			}
			return new SJF(args[1],args[2]);	
		}
			
		case "MFQ":{
			String[] schedArgs = new String[args.length - 2];
	        java.lang.System.arraycopy(args, 2, schedArgs, 0, schedArgs.length);
	        try {
	        int proba=Integer.parseInt(args[1]);
	        if(proba>schedArgs.length) {
				System.out.println("Unesite argumente ponovo! Format:'brCpu brProc MFQ brRedova ts1 ts2 .. tsn'");
				System.exit(1);
				}
	        for(int i=0; i< proba; i++) {
	        	int proba2=Integer.parseInt(schedArgs[i]);
	        	}
	        }catch (Exception e) {
	        	System.out.println("Unesite argumente ponovo! Format:'brCpu brProc MFQ brRedova ts1 ts2 .. tsn'");
				System.exit(1);
			}
	        
			return new MFQ(args[1],schedArgs);
			}
		
		case "CF":{
			return new CF();
		}
		
		default:{
			System.out.println("Pogresan format! Unesite argumente ponovo");
			
			System.exit(1);
			}
		}
		return null;	

    }
}
