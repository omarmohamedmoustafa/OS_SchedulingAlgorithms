package com.example.demo6;



import java.util.ArrayList;
import java.util.Collections;


public class schedular_sjf extends Schedular{

    ArrayList<process> result = new ArrayList<>();
    //NON-preemptive
    ArrayList<process> Processes = new ArrayList<>();
    ArrayList<process> remaining = new ArrayList<>();

    ArrayList<process> input_copy = new ArrayList<>();
    ArrayList<Integer> waiting;
    int t =0;
    

    
    public void addProcess1(int arrival , int burst){

        process proc = new process(arrival , burst);
        //System.out.println(proc.burst);
        Processes.add(proc);
        input_copy.add(proc);
       // System.out.println(input_copy.get(input_copy.size()-1).id + "/" + input_copy.get(input_copy.size()-1).arrival + "/" + input_copy.get(input_copy.size()-1).burst);
    }
    public void newsort_nonprem(int index){
        index++;
        for(int i = 0 ; i < Processes.size() ; i++){
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
        int total_time = Processes.get(0).arrival;
        int available_index = 0;
        int start = 0;
        int count = remaining.size();
        while(count > 0){
           available_index =0 ;
           while (available_index < remaining.size() && remaining.get(available_index).arrival <= total_time)  available_index++;
           available_index--;

           //ava_index = 1
           for(int i = start ; i < available_index; i++) {
               for(int j = start + 1 ; j <= available_index ; j++) {
                   if(remaining.get(j-1).burst > remaining.get(j).burst) {
                       temp = remaining.get(j-1);
                       remaining.set(j-1 , remaining.get(j));
                       remaining.set(j , temp);
                   }
               }
           }
           // this.print();
           total_time+=remaining.get(start).burst;
           start ++ ;
           count--;
        }
        for(int j = index; j < remaining.size() ; j++){
            Processes.set(j, remaining.get(j));
        }
        remaining.clear();
    }


    public void   nonPreemptive_sortProcess(){



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
        int total_time = Processes.get(0).arrival;
        int available_index = 0;
        int start = 0;
        int count = Processes.size();
        while(count > 0){
           available_index =0 ;
           while (available_index < Processes.size() && Processes.get(available_index).arrival <= total_time)  available_index++;
           available_index--;

           //ava_index = 1
           for(int i = start ; i < available_index; i++) {
               for(int j = start + 1 ; j <= available_index ; j++) {
                   if(Processes.get(j-1).burst > Processes.get(j).burst) {
                       temp = Processes.get(j-1);
                       Processes.set(j-1 , Processes.get(j));
                       Processes.set(j , temp);
                   }
               }
           }
           // this.print();
           total_time+=Processes.get(start).burst;
           start ++ ;
           count--;
       }
    }
    public double nonPreemptive_averageWaiting() {

        //start - arrive
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
    public double nonPreemptive_averageTurnaround() {
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
            if (Processes.get(index).arrival >= totalTime) {
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
    
    //preemptive
    int laststop = 0;
    public void newsort_prem(int finishedIndex , int burst) {

        ArrayList<process> finished =new ArrayList<>();

        finished.addAll(Processes.subList(0,finishedIndex-laststop));


        System.out.println("FINISH:");
        for(int i = finished.size()-1 ; i >=0 ; i--) {
            System.out.println("P" + finished.get(i).id +" -- " +finished.get(i).arrival + " / " + finished.get(i).burst );
        }


        input_copy.add(new process(finishedIndex , burst));


        for(int i = finished.size()-1 ; i >=0 ; i--) {
            for(int j = 0 ; j <input_copy.size() ; j++) {
                if(input_copy.get(j).id == finished.get(i).getId()) {
                    input_copy.get(j).burst--;
                }
            }
        }
        laststop = finishedIndex;

        finished.clear();
        for(int i = 0 ; i< input_copy.size() ;i++ ) {
            if( input_copy.get(i).burst <=0) {
                System.out.println(input_copy.get(i).id + "deleted");
                input_copy.remove(i);
            }
        }
        t = finishedIndex;
        this.Preemptive_sortProcess();
    }

    public void Preemptive_sortProcess(){

        for(int i = 0 ; i< input_copy.size() ;i++ ) {
            if( input_copy.get(i).burst <=0) {
                System.out.println(input_copy.get(i).id + "deleted");
                input_copy.remove(i);
            }
        }


        System.out.println("input:");
        for(int i = 0 ; i<input_copy.size() ;i++ ) {
            System.out.println("P" + input_copy.get(i).id +" -- " +input_copy.get(i).arrival + " / " + input_copy.get(i).burst );
        }

        process temp = new process();
        int current = 0;
        Processes.clear();
        result.clear();
        //Copy input to Processes
        for(int i = 0 ; i<input_copy.size() ; i++) {
           // System.out.println("P" + input_copy.get(i).id +" -- " +input_copy.get(i).arrival + " / " + input_copy.get(i).burst );
            process t1 = new process(input_copy.get(i).arrival , input_copy.get(i).burst , 0 , input_copy.get(i).id );
            Processes.add(t1);
        }

        //Sort Processes
        for(int i = 0 ; i < Processes.size(); i++) {
            for(int j = 1 ; j < Processes.size()-i ; j++) {
                if(Processes.get(j-1).arrival > Processes.get(j).arrival) {
                    temp = Processes.get(j-1);
                    Processes.set(j-1, Processes.get(j));
                    Processes.set(j, temp);
                }
            }
        }

        //Copy sorted Processes into result
        for(int i = 0 ; i< Processes.size() ; i++) {
            process t1 = new process(Processes.get(i).arrival , Processes.get(i).burst , 0 , Processes.get(i).id );
            result.add(t1);
        }

        waiting = new ArrayList<Integer>(Collections.nCopies(result.size(), 0));

        Processes.clear();

        while(result.size() > 0) {
            for(int i =0; i < result.size() ; i++) {
                if( result.get(i).burst < result.get(current).burst  && result.get(i).arrival <= t )
                {
                    current = i;
                }
            }

            Processes.add(new process(result.get(current).arrival , result.get(current).burst ,0, result.get(current).id));

            if(result.get(current).burst != 0)              //???????????
                result.get(current).burst --;


            for(int i = 0 ;i < result.size() ; i++) {

                if ( i != current && result.get(i).arrival <= t && result.get(i).burst!=0 )
                {
                    waiting.set(result.get(i).id-1 ,    (waiting.get(result.get(i).id-1)+1));
                }
            }

            if(result.get(current).burst == 0) {

                result.remove(current);
                current = 0;
            }
            t++;
        }

        System.out.println("output:");
        result.removeAll(result);
        print_sjf();
        System.out.println("----------------------");

    }

    public double PreemptiveSJF_averageWaiting()
    {
    	double total =0;
    	for (int i = 0;i<waiting.size();i++)
    	{
            total += waiting.get(i);
        }
    	System.out.println(waiting.toString());
    	total = total / waiting.size();
    	return total;
    }
    public double PreemptiveSJF_averageTurnAround()
    {
        double total = 0;
        ArrayList<Integer> turnAround = new ArrayList<Integer>(Collections.nCopies(waiting.size(), 0));
        for(int i = 0; i<Processes.size();i++)
        {
            if(Processes.get(i).burst == 1)
            {
                turnAround.set( Processes.get(i).id-1 , (i - Processes.get(i).arrival + 1));
            }
        }
        System.out.println(turnAround.toString());
        for(int i =0 ; i < turnAround.size();i++)
            total += turnAround.get(i);
        return total/turnAround.size();
    }
    public void print_sjf() {
        for(int i = 0 ; i < Processes.size() ; i++) {
            System.out.println("P" + Processes.get(i).id +" -- " +Processes.get(i).arrival + " / " + Processes.get(i).burst );
        }
    }
}