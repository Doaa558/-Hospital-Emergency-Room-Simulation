class Semaphore{
    protected int resources =0;
    protected int rooms_still_wait=0;
    protected int rooms_wake_up=0;
    // constructors
    protected Semaphore(){resources = 0;}
    protected Semaphore(int initial){ resources = initial;}
     
    // wait function
    public synchronized void P(){
        resources-- ;
        if(resources < 0){
            // i will wait so rooms which wait increased 
            rooms_still_wait++;
            while(rooms_wake_up==0){
            try { wait() ; } catch( InterruptedException e ){ }
            }
            rooms_wake_up--;
            rooms_still_wait--;
        }
       
    }

    //signal function
    public synchronized void V(){
        resources++; 
        if(resources <= 0){
            rooms_wake_up++;
            notify();
        }
    }
    public synchronized boolean P_with_timeout(long wait_time ){
        resources--;
        if(resources<0){
            rooms_still_wait++;
            long finish_time=System.nanoTime()+wait_time*1_000_000L;
            while(rooms_wake_up==0){
                long remain_time=finish_time-System.nanoTime();
                if(remain_time<=0){
                            rooms_still_wait--;
                            resources++;
                            return false;
                    }
                try { wait(remain_time/1_000_000L,(int)(remain_time%1_000_000L));} 
                catch( InterruptedException e ){
                      rooms_still_wait--;
                      resources++;
                      Thread.currentThread().interrupt(); 
                      return false;
                    }    
            }
            rooms_still_wait--;
            rooms_wake_up--;
            return true;
        }
       
        // empty room
        else{
            return true;
        }
        
    }
}

