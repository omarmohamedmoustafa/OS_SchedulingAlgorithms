package com.example.demo6;


import java.util.ArrayList;

public class schedular_fcfs extends Schedular{
    ArrayList<process> Processes = new ArrayList<>();
    ArrayList<process> remaining = new ArrayList<>();
  
    
    public void addProcess1(int arrival ,int burst){
    	process proc = new process(arrival , burst);
    	Processes.add(proc);
    }
    public void sortnew(int index){
        index++;
        for(int i = index ; i < Processes.size() ; i++){
            remaining.add(Processes.get(i));
        }
        process temp = new process();
    	for(int i = 0 ; i < remaining.size(); i++) {
    		for(int j = 1 ; j < remaining.size()-i ; j++) {
    			if(remaining.get(j-1).arrival > remaining.get(j).arrival) {
    				temp = remaining.get(j-1);
    				remaining.set(j-1, remaining.get(j));
    				remaining.set(j, temp);
    			}
    		}
    	}
        for(int j = 0 ; j < remaining.size() ; j++){
            Processes.set(index, remaining.get(j));
            index++;
        }
        remaining.clear();
    }
    public void sortProcess(){
    	process temp = new process();
    	for(int i = 0 ; i < Processes.size(); i++) {
    		for(int j = 1 ; j < Processes.size()-i ; j++) {
    			if(Processes.get(j-1).arrival > Processes.get(j).arrival) {
    				temp = Processes.get(j-1);
    				Processes.set(j-1, Processes.get(j));
    				Processes.set(j, temp);
    			}
    		}
    	}
    }
    public double averageWaiting() {
    	double totalTime = 0;
    	double totalWaiting = 0;
    	int index = 0;
    	while(index < Processes.size()) {
    		if (index == 0) {
    			totalTime += Processes.get(0).burst;
    			index++;
    			continue;
    		}
    		if (Processes.get(index).arrival >= totalTime) {
        		totalTime = Processes.get(index).burst + Processes.get(index).arrival;
    		}
    		else {
    			totalWaiting += totalTime - Processes.get(index).arrival;
        		totalTime += Processes.get(index).burst;
    		}
    		index++;
    	}
    	return (totalWaiting/Processes.size());
    }
    public double averageTurnaround() {
    	double totalTime = 0;
    	double totalTurnaround = 0;
    	int index = 0;
    	while(index < Processes.size()) {
    		if (index == 0) {
    			totalTime += Processes.get(0).burst;
    			totalTurnaround += Processes.get(0).burst;
    			index++;
    			continue;
    		}
    		if (Processes.get(index).arrival > totalTime) {
    			totalTurnaround += Processes.get(index).burst;
        		totalTime = Processes.get(index).burst + Processes.get(index).arrival;
    		}
    		else {
    			totalTurnaround += totalTime - Processes.get(index).arrival + Processes.get(index).burst;
        		totalTime += Processes.get(index).burst;
    		}
    		index++;
    	}
    	return (totalTurnaround/Processes.size());
    }
    public void print() {
    	for(int i = 0 ; i < Processes.size() ; i++) {
			System.out.println(Processes.get(i).arrival + " / " + Processes.get(i).burst);
		}
    }

}
