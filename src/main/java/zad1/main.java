package zad1;

import java.util.ArrayList;

public class main {

    public static void main(String[] args) {


        /*
        * Dodatkowe pomiary:
        *DONE -średni czas oczekiwania każdego procesu na dostęp do procesora;
        *DONE -średni czas oczekiwania każdego procesu na zakończenie tego procesu;
        *DONE SJF: najdłuższy czas oczekiwania jednego procesu;
        *DONE          dodać czas sortowania/ liczbę sortowań
        *DONE RR: liczba przełączeń między procesami (czas przełączeń/ liczba przełączeń)
        *
        * całkowity czas (różne wyniki dla różnych algorytmów przy tych samych dancych to implementacja jest zła)
        */


        //--------------DATA SET 1--------------\\
        int processNumber = 1000;
        int maxValueForProcessingTime = 30;
        int maxValueForArrivalTime = 10000;
        int roundRobinTimeWindow = 30;
        String dataSetName = "Data set 1";

        ArrayList<Process> dataSet1 = new ArrayList<>();

        ArrayList<Integer> processingTimeList = utilities.betaDistribution(processNumber, maxValueForProcessingTime, 2,2);
        ArrayList<Integer> arrivalTimeList = utilities.betaDistribution(processNumber, maxValueForArrivalTime, 1,1);

        utilities.createHistogram(processingTimeList,"Processing Time Histogram",maxValueForProcessingTime, 15, dataSetName);
        utilities.createHistogram(arrivalTimeList,"Arrival Time Histogram",maxValueForArrivalTime,20, dataSetName);

        dataSet1 = utilities.combineTwoLists(processingTimeList,arrivalTimeList);


        ArrayList<Process> fcfsList = fcfs(dataSet1);

        ArrayList<Integer> fcfsWaitingTime = new ArrayList<>();
        for (Process p:fcfsList) {
            fcfsWaitingTime.add(p.getWaitingTime());
        }

        utilities.createOutputChart(fcfsWaitingTime,"FCFS waiting time",  "zad1.Process Number", "Waiting Time", dataSetName);

        utilities.resetList(dataSet1);

        ArrayList<Process> sjfList = sjf(dataSet1);

        ArrayList<Integer> sjfWaitingTime = new ArrayList<>();
        for (Process p:sjfList) {
            sjfWaitingTime.add(p.getWaitingTime());
        }

        utilities.createOutputChart(sjfWaitingTime,"SJF waiting time", "zad1.Process Number", "Waiting Time",dataSetName);

        utilities.resetList(dataSet1);

        ArrayList<Process> rrList = rr(dataSet1,roundRobinTimeWindow);

        ArrayList<Integer> rrWaitingTime = new ArrayList<>();
        for (Process p:rrList) {
            rrWaitingTime.add(p.getWaitingTime());
        }
        ArrayList<Integer> rrServiceTime = new ArrayList<>();
        for (Process p:rrList) {
            rrServiceTime.add(p.getCompletionTime()-p.getArrivalTime());
        }
        utilities.createOutputChart(rrWaitingTime,"RR waiting time until first service", "zad1.Process Number", "Waiting Time",dataSetName);
        utilities.createOutputChart(rrServiceTime,"RR process completion time from arrival until fully completed", "zad1.Process Number", "Completion Time",dataSetName);

        utilities.resetList(dataSet1);

    }

    public static ArrayList<Process> fcfs(ArrayList<Process> list){
        int time=0;
        ArrayList<Process> queue = new ArrayList<>();
        ArrayList<Process> done = new ArrayList<>();
        utilities.sortByArrivalTime(list);

        //dopóki done będzie wielkości list to pętla działa
        while(!(done.size() == list.size())){


            if(!queue.isEmpty()){

                //zwiększamy progres procesu o 1
                queue.get(0).setProgress(queue.get(0).getProgress()+1);

                //jeśli proces skończony to dodajemy go do done i usuwamy z queue
                if(queue.get(0).getProgress()==queue.get(0).getProcessingTime()){
                    queue.get(0).setFinished(true);
                    queue.get(0).setCompletionTime(time+1);
                    done.add(queue.get(0));
                    queue.remove(0);
                }

                //zwiększamy waiting time dla każdego procesu znajdującego się w kolejce
                for(int x = 0;x< queue.size();x++){
                    queue.get(x).setWaitingTime(queue.get(x).getWaitingTime()+1);
                }
            }

            //sprawdzamy czy w tym czasie pojawiły się jakieś procesy do dodania
            for(int x = 0; x<list.size();x++){
                if(list.get(x).getArrivalTime()==time){
                    queue.add(list.get(x));
                }
            }

            time++;
        }
        System.out.println("FCFS:");
        utilities.printingStats(done);


        System.out.println("Total time spent: " + time);
        return done;
    }

    public static ArrayList<Process> sjf(ArrayList<Process> list){
        int time=0;
        int sortingCounter = 0;
        boolean added = false;
        ArrayList<Process> queue = new ArrayList<>();
        ArrayList<Process> done = new ArrayList<>();
        utilities.sortByArrivalTime(list);

        int addedTime=0;

        //dopóki done będzie wielkości list to pętla działa
        while(!(done.size() == list.size())){

            if(!queue.isEmpty()){
                //bierzemy pierwszy proces i go kończymy
                queue.get(0).setProgress(queue.get(0).getProcessingTime());
                addedTime=queue.get(0).getProcessingTime();
                queue.get(0).setFinished(true);
                queue.get(0).setCompletionTime(time+addedTime);
                //dodajemy do done i usuwamy z queue
                done.add(queue.get(0));
                queue.remove(0);

            }else{
                //jeśli queue była pusta to zwiększamy czas o jeden
                addedTime=1;
            }
            //dodajemy nowe procesy, które doszły w czasie trwania pierwszego procesu || po dodaniu 1 do time
            for(int x = 0;x<list.size();x++){
                //warunek: arrivalTime procesu <= aktualny czas && arrivalTime procesu > poprzedniego czasu
                //żeby nie dodawać już dodanych procesów
                if(list.get(x).getArrivalTime()<=time+addedTime && list.get(x).getArrivalTime()>time){
                    queue.add(list.get(x));
                    added = true;
                }
            }
            //aktualizacja waitingTime każdego procesu w kolejce
            for(int x =0; x<queue.size();x++){
                if(queue.get(x).getWaitingTime()==0){
                    //jeśli proces dopiero co doszedł ustawiamy jego waitingTime jako aktualny czas - arrivalTime procesu
                    //bo proces mógł się pojawić np w połowie obsługi innego procesu
                    queue.get(x).setWaitingTime( time+addedTime-queue.get(x).getArrivalTime());
                } else
                    //jeśli proces już istniał to zwiększamy waitingTime o czas obsługi zerowego procesu
                    queue.get(x).setWaitingTime( queue.get(x).getWaitingTime()+addedTime);
            }
            time+=addedTime;

            //sortowanie kolejki queue po processingTime

            if(!queue.isEmpty() && added){
                utilities.sortByProcessingTime(queue);
                sortingCounter++;
                added=false;
            }
        }
        System.out.println("SJF:");



        utilities.printingStats(done);
        System.out.println("Number of sorts done: " + sortingCounter);

        System.out.println("Total time spent: " + time);
        return done;
    }
    public static ArrayList<Process> rr(ArrayList<Process> list, int k){
        int time=0;
        int addedTime=0;
        int numberOfProcessSwitches = 0;

        ArrayList<Process> queue = new ArrayList<>();
        ArrayList<Process> done = new ArrayList<>();
        utilities.sortByArrivalTime(list);


        int index =0;
        //dopóki done będzie wielkości list to pętla działa
        while(!(done.size() == list.size())){

            if(!queue.isEmpty()){
                //jeśli proces ma jeszcze do wykonania mniej niż k jednostek czasu
                if(queue.get(index).getProcessingTime()-queue.get(index).getProgress()<=k){
                    //aktualizacja dodanego czasu
                    addedTime=queue.get(index).getProcessingTime()-queue.get(index).getProgress();
                    queue.get(index).setProgress(queue.get(index).getProgress()+ queue.get(index).getProcessingTime()-queue.get(index).getProgress());
                    queue.get(index).setFinished(true);
                    queue.get(index).setCompletionTime(time+addedTime);
                    done.add(queue.get(index));
                    queue.remove(index);
                    //aktualizowanie liczby zmian kontekstu
                    numberOfProcessSwitches++;
                    //aktualizowanie indeksu po którym iterujemy - żeby później zacząć iterować od 0-wego elementu listy
                    index--;
                } else{
                    //jeśli proces ma jeszcze do wykonania więcej niż k jednostek czasu
                    queue.get(index).setProgress(queue.get(index).getProgress()+ k);
                    addedTime=k;
                }
                index++;

            }else{
                addedTime=1;
            }
            //dodawanie nowych elementów do listy queue
            for(int x = 0;x< list.size();x++){
                if(list.get(x).getArrivalTime()<=time+addedTime && list.get(x).getArrivalTime()>time){
                    queue.add(list.get(x));
                }
            }
            //aktualizacja czasu oczekiwania
            for(int x =0; x<queue.size();x++){
                if(queue.get(x).getWaitingTime()==0 && queue.get(x).getProgress()==0){
                    queue.get(x).setWaitingTime( (time+addedTime)-queue.get(x).getArrivalTime());
                } else if (queue.get(x).getProgress()==0)
                    queue.get(x).setWaitingTime( queue.get(x).getWaitingTime()+addedTime);
            }
            //aktualizacja czasu
            time+=addedTime;
            //resetowanie indeksu po którym iterujemy jeśli kolejka ma rozmiar taki jak indeks
            if(index== queue.size()){
                numberOfProcessSwitches+=index;
                index=0;
            }
        }
        System.out.println("RR:");
        utilities.printingStats(done);
        System.out.println("Total number of process switches: " + numberOfProcessSwitches);


        System.out.println("Total time spent: " + time);
        return done;
    }




}
