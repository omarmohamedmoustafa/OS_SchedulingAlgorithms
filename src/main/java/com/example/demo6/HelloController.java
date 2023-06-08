package com.example.demo6;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Duration;



public class HelloController implements Initializable  {

    ArrayList<process> Processes = new ArrayList<>();
    ArrayList<process> LiveProcesses = new ArrayList<>();
    double waiting;
    double turnaround;
    boolean sorted = false;
    int dynamicTime = 0;
    int slice;
    int newArrival;
    private Timeline timeline;
    private boolean isPaused = false;
    schedular_fcfs fcfs;
    schedular_Priority priority;
    schedular_sjf sjf;
    scheduler_RoundRobin rr;

        @FXML
        private Button btnadddynamic;
        @FXML
        private Spinner<Integer> dynamicburst;
        @FXML
        private Button btnAdd;
        @FXML
        private Button btnpause;
        @FXML
        private Button btnfinish;
        @FXML
        private Button Reset;
        @FXML
        private Spinner<Integer> dynamicpriority;
        @FXML
        private Spinner<Integer> Priority;
        @FXML
        private Spinner<Integer> Arrival;
        @FXML
        private Spinner<Integer> Burst;
        @FXML
        private ComboBox<String> SchedularList;
        @FXML
        private Spinner<Integer> timeSlice;
        @FXML
        private Canvas BOARD;
        @FXML
        private Button livebtn;
        @FXML
        private Label tn;
        @FXML
        private Label wait;
        @FXML
        private TableView<process> tView;
        @FXML
        private TableColumn<process, Integer> Arrival_T;

        @FXML
        private TableColumn<process, Integer> Burst_T;
        @FXML
        private Button tSlice;
        @FXML
        private TableColumn<process,Integer> P_ID_T;
        @FXML
        private TableColumn<process, Integer> Priority_T;




    
    
    
    
    
    //ObservableList<process> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SchedularList.getItems().addAll("First Come First Serve" , "Shortest Job First Preemptive", "Shortest Job First Non-Preemptive", "Priority Preemptive", "Priority Non-Preemptive", "Round Robin");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String input = change.getText();
            if (Pattern.matches("[0-9]*", input)) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter = new TextFormatter<>(filter);
        Arrival.getEditor().setTextFormatter(textFormatter);
        UnaryOperator<TextFormatter.Change> filter1 = change -> {
            String input = change.getText();
            if (Pattern.matches("[0-9]*", input)) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter1 = new TextFormatter<>(filter);
        Priority.getEditor().setTextFormatter(textFormatter1);
        UnaryOperator<TextFormatter.Change> filter2 = change -> {
            String input = change.getText();
            if (Pattern.matches("[0-9]*", input)) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter2 = new TextFormatter<>(filter);
        Burst.getEditor().setTextFormatter(textFormatter2);
        UnaryOperator<TextFormatter.Change> filter3 = change -> {
            String input = change.getText();
            if (Pattern.matches("[0-9]*", input)) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter3 = new TextFormatter<>(filter);
        timeSlice.getEditor().setTextFormatter(textFormatter3);
        UnaryOperator<TextFormatter.Change> filter4 = change -> {
            String input = change.getText();
            if (Pattern.matches("[0-9]*", input)) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter4 = new TextFormatter<>(filter);
        dynamicburst.getEditor().setTextFormatter(textFormatter4);
        UnaryOperator<TextFormatter.Change> filter5 = change -> {
            String input = change.getText();
            if (Pattern.matches("[0-9]*", input)) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter5 = new TextFormatter<>(filter);
        dynamicpriority.getEditor().setTextFormatter(textFormatter5);
        
        Arrival.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        Priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        Burst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        dynamicburst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        dynamicpriority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        timeSlice.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        String style = "-fx-background-color: white; -fx-text-fill: black; -fx-font-family: Arial; -fx-font-size: 12pt;";
        wait.setStyle(style);
        tn.setStyle(style);

        /*********************************************************************************************************************************/
        P_ID_T.setCellValueFactory(new PropertyValueFactory<>("id"));
        Arrival_T.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        Burst_T.setCellValueFactory(new PropertyValueFactory<>("burst"));
        Priority_T.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        tSlice.setVisible(false);

    }
    process temp;

    static int ID=0;
    @FXML
    private void btnAdd(ActionEvent event) {
        int s = Arrival.getValue();
        int s1 = Burst.getValue();
        if(SchedularList.getValue().contains("Priority")){
            process p = new process(Arrival.getValue(),Burst.getValue(),Priority.getValue(),++ID);
            tView.getItems().add(p);
            int s3 = Priority.getValue();
            priority.addProcess((s),(s1),(s3));

        }
        else if(SchedularList.getValue().equals("First Come First Serve")){
            process p = new process(Arrival.getValue(),Burst.getValue(),0,++ID);
            tView.getItems().add(p);
            fcfs.addProcess1((s),(s1));
        }
        else if(SchedularList.getValue().contains("Shortest Job First")){
            process p = new process(Arrival.getValue(),Burst.getValue(),0,++ID);
            tView.getItems().add(p);
            sjf.addProcess1((s),(s1));
        }
        else if(SchedularList.getValue().equals("Round Robin")){
            process p = new process(Arrival.getValue(),Burst.getValue(),0,++ID);
            tView.getItems().add(p);
            rr.addProcess1((s),(s1));
        }

        Arrival.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE ));
        Priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        Burst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        timeSlice.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
    }

    @FXML
    private void btnSubmit(ActionEvent event) {
        tSlice.setVisible(false);
        livebtn.setDisable(false);
        Reset.setDisable(false);
        btnAdd.setDisable(false);
        btnfinish.setDisable(false);
        timeSlice.setDisable(true);
        Arrival.setDisable(false);
        Burst.setDisable(false);
        if(SchedularList.getValue().contains("Priority")){
            Priority.setDisable(false);
            priority = new schedular_Priority();
        }
        else if(SchedularList.getValue().equals("First Come First Serve")){
            Priority.setDisable(true);
            fcfs = new schedular_fcfs();
        }
        else if(SchedularList.getValue().contains("Shortest Job First")){
            Priority.setDisable(true);
            sjf = new schedular_sjf();
        }
        else if(SchedularList.getValue().equals("Round Robin")){

            tSlice.setVisible(true);
            tSlice.setDisable(false);
            btnAdd.setDisable(true);
            btnfinish.setDisable(true);
            Arrival.setDisable(true);
            Burst.setDisable(true);
            Priority.setDisable(true);
            rr = new scheduler_RoundRobin();
            timeSlice.setDisable(false);
            tSlice.setVisible(true);

        }

    }
    int tslice;
    @FXML
    void btntSlice(ActionEvent e) {
        btnAdd.setDisable(false);
        btnfinish.setDisable(false);
        Burst.setDisable(false);
        Arrival.setDisable(false);
        tslice= timeSlice.getValue();
        timeSlice.setDisable(true);
        tSlice.setVisible(false);
    }
    
    int x=0;
    int count =0;
    int d_arrival;
    
    @FXML
    void btnFinish(ActionEvent event) {
        GraphicsContext gcc = BOARD.getGraphicsContext2D();
        gcc.clearRect(0, 0, BOARD.getWidth(), BOARD.getHeight());
        btnfinish.setDisable(true);
        btnAdd.setDisable(true);
        var gc = BOARD.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        if(sorted){
            Processes.addAll(LiveProcesses);
            if(SchedularList.getValue().contains("Priority")){
                if(SchedularList.getValue().contains("Non-Preemptive"))
                {
                    turnaround = priority.nonPreemptive_averageTurnaround();
                    waiting = priority.nonPreemptive_averageWaiting();
                }
                else {
                    turnaround = priority.Preemptive_averageTurnAround();
                    waiting = priority.Preemptive_averageWaiting();
                }
            }
            else if(SchedularList.getValue().equals("First Come First Serve")){
                turnaround = fcfs.averageTurnaround();
                waiting = fcfs.averageWaiting();
            }
            else if(SchedularList.getValue().contains("Shortest Job First")){
                if(SchedularList.getValue().contains("Non-Preemptive"))
                {
                    turnaround = sjf.nonPreemptive_averageTurnaround();
                    waiting = sjf.nonPreemptive_averageWaiting();
                }
                else
                {
                    turnaround = sjf.PreemptiveSJF_averageTurnAround();
                    waiting = sjf.PreemptiveSJF_averageWaiting();
                }
            }
            else if(SchedularList.getValue().equals("Round Robin")){
                
            }
        }
        else{
            if(SchedularList.getValue().contains("Priority")){
                if(SchedularList.getValue().contains("Non-Preemptive"))
                {
                    priority.nonPreemptive_sortProcess();
                    turnaround = priority.nonPreemptive_averageTurnaround();
                    waiting = priority.nonPreemptive_averageWaiting();
                }
                else {
                    priority.Preemptive_sortProcess();
                    turnaround = priority.Preemptive_averageTurnAround();
                    waiting = priority.Preemptive_averageWaiting();
                }
                Processes = priority.Processes;
            }
            else if(SchedularList.getValue().equals("First Come First Serve")){
                fcfs.sortProcess();
                turnaround = fcfs.averageTurnaround();
                waiting = fcfs.averageWaiting();
                Processes = fcfs.Processes;
            }
            else if(SchedularList.getValue().contains("Shortest Job First")){
                if(SchedularList.getValue().contains("Non-Preemptive"))
                {
                    sjf.nonPreemptive_sortProcess();
                    turnaround = sjf.nonPreemptive_averageTurnaround();
                    waiting = sjf.nonPreemptive_averageWaiting();
                }
                else
                {
                    sjf.Preemptive_sortProcess();
                    turnaround = sjf.PreemptiveSJF_averageTurnAround();
                    waiting = sjf.PreemptiveSJF_averageWaiting();
                }
                Processes = sjf.Processes;
            }
            else if(SchedularList.getValue().equals("Round Robin")){
                slice = tslice;
                rr.RR(tslice);
                Processes = rr.Processes;
            }
            LiveProcesses.addAll(Processes);
            sorted = true;
        }
        
        /***************************************************************/
        if(SchedularList.getValue().contains("Non-Preemptive") || SchedularList.getValue().contains("First Come First Serve"))
        {
            for (int i = 0; i < Processes.size(); i++) {
                if (Processes.get(i).arrival > (x / 20)) {

                    gc.fillRect(x, 20, 20 * (Processes.get(i).arrival - count), 100);
                    gc.fillText(Integer.toString(count), x, 15);
                    x = 20 * Processes.get(i).arrival;
                    count = Processes.get(i).arrival;
                }

                gc.strokeRect(x, 20, 20 * Processes.get(i).burst, 100);
                gc.fillText(Integer.toString(count), x, 15);
                gc.fillText("P" + Integer.toString(Processes.get(i).id), x + 10 * (Processes.get(i).burst) - 7, 50);
                count += Processes.get(i).burst;
                x += 20 * Processes.get(i).burst;
            }
            gc.fillText(Integer.toString(count), x, 15);
        }
        else if(SchedularList.getValue().contains("Preemptive"))
        {
            int counter=0;
            int x =0;
            for (int i = 0; i < Processes.size(); i++,counter++) {
                if (Processes.get(i).arrival > (x / 50)) {
                    gc.fillRect(x, 20, 50*Processes.get(i).arrival - x , 100);
                    gc.fillText(Integer.toString(counter), x, 15);
                    counter+=(50*Processes.get(i).arrival - x)/50;
                    x +=50*Processes.get(i).arrival - x;
                    //count = Processes.get(i).arrival;
                }
                gc.strokeRect(x, 20, 50, 100);
                gc.fillText(Integer.toString(counter), x, 15);
                gc.fillText("P" + Integer.toString(Processes.get(i).id), x + 20, 50);
                gc.fillText("+" + Integer.toString(Processes.get(i).burst - 1), x + 20, 75);
                x+=50;
            }
            gc.fillText(Integer.toString(counter), x, 15);
        }
        else if(SchedularList.getValue().equals("Round Robin"))
        {
            for(int z = 0 ; z < Processes.size() ; z++){
                    System.out.println(Processes.get(z).id + "/" + Processes.get(z).arrival + "/" + Processes.get(z).burst);
            }
            System.out.println(slice);
            turnaround = rr.RR_averageTurnAround();
            waiting = rr.RR_averageWaiting();
            int x =0;
            int counter=0;
            for (int i = 0; i < Processes.size(); i++ , counter++) {
                if(i!= 0){
                    if((Processes.get(i).arrival - Processes.get(i - 1).arrival) > slice){
                        int gap = (Processes.get(i).arrival - Processes.get(i - 1).arrival) - slice;
                        gc.fillRect(x, 20, gap*30 , 100);
                        gc.fillText(Integer.toString(x/30), x, 15);
                        x += gap*30;
                    } 
                }
                if(i == 0 && rr.firstarrival > 0 ){
                        gc.fillRect(x, 20, rr.firstarrival*30 , 100);
                        gc.fillText(Integer.toString(x/30), x, 15);
                        x += rr.firstarrival*30;
                }
                gc.strokeRect(x, 20, Processes.get(i).arrival*30 -x, 100);
                gc.fillText(Integer.toString(x/30), x, 15);
                gc.fillText("P" + Integer.toString(Processes.get(i).id), x + (Processes.get(i).arrival*30 - x)/2 - 5, 50);
                gc.fillText("+" + Integer.toString(Processes.get(i).burst),x + (Processes.get(i).arrival*30 - x)/2 - 5, 75);

                x=30*Processes.get(i).arrival;
            }
              gc.fillText(Integer.toString(x/30), x, 15);
        }
        wait.setText(Double.toString(waiting));
        tn.setText(Double.toString(turnaround));
        
    }
    
    @FXML
    void btnreset(ActionEvent event) {
        dynamicTime = 0;
        isPaused = false;
        ID =0;
        y = 0;
        i = 0;
        x=0;
        sorted = false;
        count =0;
        process.setting();
        Processes.clear();
        LiveProcesses.clear();
        
        Arrival.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        Priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        Burst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        timeSlice.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        dynamicburst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        dynamicpriority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        
        SchedularList.setValue("");
        wait.setText("");
        tn.setText("");
        GraphicsContext gc = BOARD.getGraphicsContext2D();
        gc.clearRect(0, 0, BOARD.getWidth(), BOARD.getHeight());
        
        tView.getItems().clear();
        btnAdd.setDisable(true);
        btnfinish.setDisable(true);
        tSlice.setDisable(true);
        btnpause.setDisable(true);
        btnadddynamic.setDisable(true);

        livebtn.setDisable(true);
        Arrival.setDisable(true);
        Priority.setDisable(true);
        Burst.setDisable(true);
        timeSlice.setDisable(true);
        dynamicburst.setDisable(true);
        dynamicpriority.setDisable(true);
        Reset.setDisable(true);

    }
    
    @FXML
    void livechart(ActionEvent event) {
        btnpause.setDisable(false);
        btnadddynamic.setDisable(false);
        dynamicburst.setDisable(false);
        if(SchedularList.getValue().contains("Priority"))
        {
            dynamicpriority.setDisable(false);
        }
        else
        {
            dynamicpriority.setDisable(true);
        }
        y = 0;
        i = 0;
        roundrobinvar = 0;
        dynamicTime = 0;
        if(!sorted){
            if(SchedularList.getValue().contains("Priority")){
                if(SchedularList.getValue().contains("Non-Preemptive"))
                {
                    priority.nonPreemptive_sortProcess();
                }
                else {
                    priority.Preemptive_sortProcess();
                }
                LiveProcesses = priority.Processes;
            }
            else if(SchedularList.getValue().equals("First Come First Serve")){
                fcfs.sortProcess();
                LiveProcesses = fcfs.Processes;
            }
            else if(SchedularList.getValue().contains("Shortest Job First")){
                if(SchedularList.getValue().contains("Non-Preemptive"))
                {
                    sjf.nonPreemptive_sortProcess();
                }
                else
                {
                    sjf.Preemptive_sortProcess();
                }
                LiveProcesses = sjf.Processes;
            }
            else if(SchedularList.getValue().equals("Round Robin")){
                slice = tslice;
                rr.RR(tslice);
                LiveProcesses = rr.Processes;
            }
            sorted = true;
        }
        GraphicsContext gc = BOARD.getGraphicsContext2D();
        gc.clearRect(0, 0, BOARD.getWidth(), BOARD.getHeight());
        value = LiveProcesses.get(0).burst;
        if(SchedularList.getValue().equals("Round Robin")){
            gap = rr.firstarrival;
        }
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, e -> createBox()));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1)));
        timeline.play();
    }
    
    @FXML
    void btnpause(ActionEvent event) { 
        // boolean isPaused;
        if (isPaused) {
            // Resume the timeline to resume the live drawing
            timeline.play();
            btnpause.setText("Pause");
        } 
        else {
            // Pause the timeline to temporarily stop the live drawing
            timeline.pause();
            btnpause.setText("Resume");
        }
        isPaused = !isPaused;
    }
    
    @FXML
    void adddynmicaly(ActionEvent event) {
        newArrival = dynamicTime;
        int newburst = dynamicburst.getValue();
        int newpriority = dynamicpriority.getValue();

        //non Preemptive
        if(SchedularList.getValue().contains("Priority")){
            if(SchedularList.getValue().contains("Non-Preemptive"))
            {
                process p = new process(newArrival,newburst,newpriority,++ID);
                tView.getItems().add(p);
                priority.addProcess(newArrival , newburst , newpriority);
                if(isgap){
                    priority.newsort_nonprem(i - 1);
                    value = LiveProcesses.get(i).burst;
                }
                else{
                    priority.newsort_nonprem(i);
                }
            }
            else
            {
                //Preemptiveeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                priority.newsort_prem(newArrival,newburst,newpriority);
                i=0;
                tView.getItems().add(new process(newArrival,newburst,newpriority,++ID));

                if(isgap){
                    //sjf.newsort_prem(i - 1);
                    value = LiveProcesses.get(i).burst;
                }
                else{
                    //sjf.newsort_nonprem(i);
                }

            }
        }

        else if(SchedularList.getValue().contains("Shortest Job First")){
            if(SchedularList.getValue().contains("Non-Preemptive"))
            {
                process p = new process(newArrival,newburst,0,++ID);
                tView.getItems().add(p);
                sjf.addProcess1(newArrival , newburst);
                if(isgap){
                    sjf.newsort_nonprem(i - 1);
                    value = LiveProcesses.get(i).burst;
                }
                else{
                    sjf.newsort_nonprem(i);
                }
            }
            else
            {
                //Preemptiveeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                sjf.newsort_prem(newArrival,newburst);
                i=0;
                tView.getItems().add(new process(newArrival,newburst,0,++ID));

                if(isgap){
                    //sjf.newsort_prem(i - 1);
                    value = LiveProcesses.get(i).burst;
                }
                else{
                    //sjf.newsort_nonprem(i);
                }

            }

        }




        else if(SchedularList.getValue().equals("Round Robin"))
        {
            //
        }





        else if(SchedularList.getValue().equals("First Come First Serve")){
            process p = new process(newArrival,newburst,0,++ID);
            tView.getItems().add(p);
            fcfs.addProcess1(newArrival , newburst);
            if(isgap){
                fcfs.sortnew(i - 1);
                value = LiveProcesses.get(i).burst;
            }
            else{
                fcfs.sortnew(i);
            }
        }








//        else if(SchedularList.getValue().equals("Round Robin")){
//            process p = new process(Arrival.getValue(),Burst.getValue(),0,++ID);
//            tView.getItems().add(p);
//            rr.addProcess1((s),(s1));
//        }

        /*******************************************************************************************************/
        dynamicpriority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        dynamicburst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        /*******************************************************************************************************/

    }
    
    int y = 0;
    int i = 0;
    boolean isgap = false;
    int roundrobinvar = 0;
    int value;
    int gap;
    String cprocess;
    
    private void createBox() {
        var gc = BOARD.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        /*  valid for fcfs & non-preempitive */ 
        if(SchedularList.getValue().contains("Non-Preemptive") || SchedularList.getValue().contains("First Come First Serve")){
            if(dynamicTime < LiveProcesses.get(i).arrival){
                isgap = true ;
                gc.fillText(Integer.toString(dynamicTime) , y , 15);
                gc.fillRect(y , 20 , 50 , 100);
                y += 50;
                dynamicTime++;
            }
            else{
                if(value > 0){
                    cprocess = "p" + LiveProcesses.get(i).id;
                    value--;
                    isgap = false;
                }
                else{
                    i++;
                    isgap = false;
                    if(i >= LiveProcesses.size()){
                        gc.fillText(Integer.toString(dynamicTime), y, 15);
                        timeline.stop();
                        btnAdd.setDisable(true);
                        btnadddynamic.setDisable(true);
                        btnpause.setDisable(true);
                        Arrival.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        Priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        Burst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        timeSlice.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        dynamicburst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        dynamicpriority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        Arrival.setDisable(true);
                        Priority.setDisable(true);
                        Burst.setDisable(true);
                        timeSlice.setDisable(true);
                        dynamicburst.setDisable(true);
                        dynamicpriority.setDisable(true);
                        return;
                    }
                    else{
                        value = LiveProcesses.get(i).burst;
                        return;
                    }
                }
                
                gc.fillText(Integer.toString(dynamicTime), y, 15);
                gc.strokeRect(y,20 , 50 , 100);
                gc.fillText(cprocess, y + 18, 50);
                y += 50;
                dynamicTime++;
            }
        }
        /*valid preempitive*/
        else if(SchedularList.getValue().contains("Preemptive"))
        {

                if(i >= LiveProcesses.size()){
                    gc.fillText(Integer.toString(dynamicTime), y, 15);
                    timeline.stop();
                    btnAdd.setDisable(true);
                    btnadddynamic.setDisable(true);
                    btnpause.setDisable(true);
                    Arrival.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                    Priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                    Burst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                    timeSlice.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                    dynamicburst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                    dynamicpriority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                    Arrival.setDisable(true);
                    Priority.setDisable(true);
                    Burst.setDisable(true);
                    timeSlice.setDisable(true);
                    dynamicburst.setDisable(true);
                    dynamicpriority.setDisable(true);
                    return;
                }
                else{
                    if(dynamicTime < LiveProcesses.get(i).arrival){
                        gc.fillText(Integer.toString(dynamicTime) , y , 15);
                        gc.fillRect(y , 20 , 50 , 100);
                        y += 50;
                        dynamicTime++;
                        return;
                    }
                    cprocess = "p" + LiveProcesses.get(i).id;
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                    gc.fillText(Integer.toString(dynamicTime), y, 15);
                    gc.strokeRect(y,20 , 50 , 100);
                    gc.fillText(cprocess, y + 18, 50);
                    y += 50;
                    dynamicTime++;
                    i++;
                }
        }
        else if(SchedularList.getValue().equals("Round Robin")){
            if(gap > 0){
                gc.fillText(Integer.toString(roundrobinvar) , y , 15);
                gc.fillRect(y, 20, 50 , 100);
                roundrobinvar++; 
                y += 50;
                gap--;
            }
            else{
                if( roundrobinvar < LiveProcesses.get(i).arrival){
                    cprocess = "p" + LiveProcesses.get(i).id;
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                    gc.fillText(Integer.toString(roundrobinvar), y, 15);
                    gc.strokeRect(y,20 , 50 , 100);
                    gc.fillText(cprocess, y + 18, 50);
                    y += 50;
                    roundrobinvar++;
                }
                else{
                    i++;
                    if(i >= LiveProcesses.size()){
                        gc.fillText(Integer.toString(roundrobinvar), y, 15);
                        timeline.stop();
                        btnAdd.setDisable(true);
                        btnadddynamic.setDisable(true);
                        btnpause.setDisable(true);
                        Arrival.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        Priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        Burst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        timeSlice.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        dynamicburst.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        dynamicpriority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
                        Arrival.setDisable(true);
                        Priority.setDisable(true);
                        Burst.setDisable(true);
                        timeSlice.setDisable(true);
                        dynamicburst.setDisable(true);
                        dynamicpriority.setDisable(true);
                        return;
                    }
                    else{
                        gap = LiveProcesses.get(i).arrival - LiveProcesses.get(i-1).arrival - slice;
                        if(gap > 0){
                            return;
                        }
                        cprocess = "p" + LiveProcesses.get(i).id;
                        gc.setFill(Color.WHITE);
                        gc.setStroke(Color.WHITE);
                        gc.fillText(Integer.toString(roundrobinvar), y, 15);
                        gc.strokeRect(y,20 , 50 , 100);
                        gc.fillText(cprocess, y + 18, 50);
                        y += 50;
                        roundrobinvar++;
                    }
                }
            } 
        }
    }
}


