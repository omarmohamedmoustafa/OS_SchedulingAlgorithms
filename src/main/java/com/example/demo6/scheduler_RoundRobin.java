package com.example.demo6;



import java.util.*;

public class scheduler_RoundRobin extends Schedular{
	
    ArrayList<process> Processes = new ArrayList<>();
    ArrayList<process> processCopy = new ArrayList<>();
    int firstarrival;
    ArrayList<process> TL = new ArrayList<>();
    double size=0;
    ArrayList<Integer> arrivals = new ArrayList<>();
    int total_burst=0;
    double total_TA=0;
    
    public void addProcess1(int arrival, int burst) {
        total_burst+=burst;
        arrivals.add(arrival);
        process proc = new process(arrival, burst);
        Processes.add(proc);
    }
    void sort(ArrayList<process>arr)
    {
        process temp = new process();
        for(int i = 0 ; i < arr.size(); i++) {
            for(int j = 1 ; j < arr.size()-i ; j++) {
                if(arr.get(j-1).arrival > arr.get(j).arrival) {
                    temp = arr.get(j-1);
                    arr.set(j-1, arr.get(j));
                    arr.set(j, temp);
                }
            }
        }
    }
    public void RR(int time_slice) {
        size = Processes.size(); 
        sort(Processes);
        processCopy.addAll(Processes) ;
        firstarrival = Processes.get(0).arrival;
        int t= 0;

        while(Processes.size() > 0)
        {
            if(Processes.get(0).arrival <= t)
            {
                if(Processes.get(0).burst > time_slice) {

                    TL.add(Processes.get(0));
                    Processes.get(0).burst -= time_slice;
                    t+=time_slice;
                    Processes.get(0).arrival = t;
                    Processes.add(new process(Processes.get(0).arrival, Processes.get(0).burst,0, Processes.get(0).id));
                    Processes.remove(0);
                    sort(Processes);
                }
                else    // ==  <
                {
                    t+=Processes.get(0).burst;
                    Processes.get(0).burst = 0 ;
                    Processes.get(0).arrival=t;
                    TL.add(Processes.get(0));
                    Processes.remove(0);
                }

            }
            else{
                t = Processes.get(0).arrival;
            }

        }
        Processes.removeAll(Processes);
        Processes.addAll(TL);
//        System.out.println(TL.get(0).arrival);
    }
    public double RR_averageWaiting(){
        return (total_TA-total_burst)/size;
    }
    public double RR_averageTurnAround(){

      for(int i=0;i<TL.size();i++)
      {
          if(TL.get(i).burst == 0)
          {
              total_TA+= (TL.get(i).arrival - arrivals.get(TL.get(i).id - 1 ));
          }
      }
      return total_TA/size;
    }
    public void print() {
        for(int i = 0 ; i < TL.size() ; i++) {
            System.out.println("P" + TL.get(i).id +" -- " +TL.get(i).arrival + " / " + TL.get(i).burst );
        }
    }
}
