package com.example.demo6;

public class process {
	
        int arrival;
        int Priority;
        int burst;
        static int count ;
        int id ;
        public process(){
        }
        public process(int arrival ,int burst){
            this.arrival = arrival;
            this.burst = burst;
            this.id =++count;
        }
        public process(int arrival ,int burst ,int Priority){
            this.arrival = arrival;
            this.burst = burst;
            this.Priority = Priority;
            this.id =++count ;
        }
        public process(int arrival ,int burst ,int Priority , int id){
            this.arrival = arrival;
            this.burst = burst;
            this.Priority = Priority;
            this.id = id;
        }

        public int getArrival() {
            return arrival;
        }

        public int getPriority() {
            return Priority;
        }

        public int getBurst() {
            return burst;
        }

        public static int getCount() {
            return count;
        }

        public int getId() {
            return id;
        }

        public static void setting(){
                  count = 0 ;
        }
}