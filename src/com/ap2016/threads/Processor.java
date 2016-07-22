package com.ap2016.threads;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Ali J on 3/27/2015.
 */
public class Processor {

    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_BLACK = "\u001B[30m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_PURPLE = "\u001B[35m";
    private final String ANSI_CYAN = "\u001B[36m";
    private final String ANSI_WHITE = "\u001B[37m";
    private Random random;
    private LinkedList<Integer> list;
    private int starttime,standardbreak = 10000;
    private int conveyorsize;
    private Object conveyor;//just an object we're locking on...for no reason :P
    private int packedATotalOf,producedATotalOf;
    public Processor(int conveyorsize){
        this.conveyorsize = conveyorsize;
        random = new Random();
        list = new LinkedList<Integer>();
        conveyor = new Object();
        packedATotalOf = 0;
    }

    public void pack(){
        int value = 0;
        int currentPacker = 0,packedATotalOfAtTime = 0;
        boolean running = true;
        while (running){
            long start,end;
            synchronized (conveyor){
                while (list.size() == conveyorsize){
                    try {
                        System.out.println(ANSI_RED + "The Conveyor is Full!!");
                        System.out.println("Looks like the Producers have been Slacking!!" + ANSI_RESET);
                        System.out.println(ANSI_BLACK + "Boss:You there!! get back to work!!" + ANSI_RESET);
                        conveyor.wait();//wait if the list is full
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(value++);
                currentPacker++;
                start = System.currentTimeMillis();
                packedATotalOfAtTime = ++packedATotalOf;
                System.out.println(ANSI_BLUE + Thread.currentThread().getName() + " started packing an item");
                conveyor.notifyAll();
            }
            try {
                Thread.sleep(random.nextInt(1000));
                end = System.currentTimeMillis();
                System.out.print(ANSI_BLUE + Thread.currentThread().getName() + " finished packing the item,it took him/her " + (end - start) + " milliseconds to produce this item" + ANSI_RESET);
                System.out.println(ANSI_BLUE + ";he/she has packed " + currentPacker + " items" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "The Worker(s) Packed " + packedATotalOfAtTime + " items in total" + ANSI_RESET);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void use(){
        starttime = (int) (System.nanoTime()/1000000);
        while (((int)(System.nanoTime()/1000000)) - starttime < standardbreak){}
        System.out.println(ANSI_CYAN + "a Producer has started working");
        int currentProducer = 0,producedATotalOfAtTime = 0;
        boolean running = true;
        while (running){
            long start,end;
            synchronized (conveyor){
                while (list.size() == 0){
                    try {
                        System.out.println(ANSI_RED + "The Conveyor is Empty!!");
                        System.out.println("Looks like the Packagers have been Slacking!!" + ANSI_RESET);
                        System.out.println(ANSI_BLACK + "Boss:You there!! get back to work!!" + ANSI_RESET);
                        conveyor.wait();//wait if the list is empty
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(ANSI_YELLOW + "we have " + list.size() + " item(s) on our Conveyor" + ANSI_RESET);
                list.removeFirst();/*removingfirst so we have a FIFO structure*/
                currentProducer++;
                start = System.currentTimeMillis();
                producedATotalOfAtTime = ++producedATotalOf;
                System.out.println(ANSI_PURPLE + Thread.currentThread().getName() + " Started the Production of an item" + ANSI_RESET);
                conveyor.notifyAll();
            }
            try {
                Thread.sleep(random.nextInt(1000));//sleeping on average for half a second
                end = System.currentTimeMillis();
                System.out.print(ANSI_PURPLE + Thread.currentThread().getName() + " finished the consumption of an item,it took him/her "+ (end - start) + " milliseconds to produce this item" + ANSI_RESET);
                System.out.println(ANSI_PURPLE + ";he/she has consumed" + currentProducer + " item(s)" + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "The Worker(s) consumed " + producedATotalOfAtTime + " item(s) in total" + ANSI_RESET);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void stop(){
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
