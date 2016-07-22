package com.ap2016.threads;

import utils.GetInput;


/**
 * Created by Ali J on 3/27/2015.
 */
public class Factory {

    private Processor processor;
    private final String ANSI_CYAN = "\u001B[36m";
    private GetInput getInput;
    private int packagers,producers, conveyorSize;
    public Factory(){
        packagers = 0;
        producers = 0;
        conveyorSize = 0;
        getInput = new GetInput();
        process();
    }

    private void process(){
        System.out.println("How many items can the Conveyor hold?");
        conveyorSize = getInput.ReadInt();
        processor = new Processor(conveyorSize);
        System.out.println("Enter the number of Packaging workers you would want for your Factroy?");
        packagers = getInput.ReadInt();
        System.out.println("Please Note That The Producers start working 10 seconds after the Packagers have begun");
        System.out.println("Enter the number of producers you would want for your Factory?");
        producers = getInput.ReadInt();
        for (int x=0; x<packagers; x++) {
            MyPackager temp= new MyPackager("Packager #" + (x+1));
            temp.start();
            System.out.println(ANSI_CYAN + "Packager #" + (x+1) + " started working");
        }
        for (int x=0; x<producers; x++) {
            MyProducer temp= new MyProducer("Producer #" + (x+1));
            temp.start();
            System.out.println(ANSI_CYAN + "Producer #" + (x+1) + " has been deployed");
        }
    }
    private class MyPackager extends Thread {
        public MyPackager(String s) {
            super(s);
        }
        public void run() {
            processor.pack();
        }
    }
    private class MyProducer extends Thread {
        public MyProducer (String s) {
            super(s);
        }
        public void run() {
            processor.use();
        }
    }
}
