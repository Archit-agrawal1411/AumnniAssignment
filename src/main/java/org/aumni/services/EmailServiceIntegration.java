package org.aumni.services;

public class EmailServiceIntegration implements Runnable{
//  Todo: Create a service that sends periodic emails
//  (Not a real time one), just need to print something as if it is sending email and
//  multi threading - Work on having multiple orders being created parallely.
//  The code that needs to be executed by the thread is written inside of it
    @Override
    public void run() {
        try {
            System.out.println("This thread is sending a email");
            System.out.println("Email Something Something");
            System.out.println("Thread has completed its execution");
            Thread.sleep(2000); //This can throw IE,Simulating a 2 second delay
        }catch (InterruptedException e){
            System.out.println("In catch block inside of the run method in EmailServiceInteration");
        }
    }
}