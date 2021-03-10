package com.evie.autotest.interfaces;

public class Interruptor {
    private boolean interrupt;
    private String interruptReason;

    public boolean isInterrupt(){
        return  interrupt;
    }

    public void setInterrupt(boolean interrupt){
        this.interrupt = interrupt;
    }

    public String getInterruptReason(){
        return interruptReason;
    }

    public void setInterruptReason(String interruptReason){
        this.interruptReason = interruptReason;
    }
}
