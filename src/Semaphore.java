class Semaphore{
    protected int resources =0;
    // constructors
    protected Semaphore(){resources = 0;}
    protected Semaphore(int initial){ resources = initial;}

    // wait function
    public synchronized void P() throws InterruptedException {
        resources-- ;
        if(resources < 0)
            try { wait() ; } catch( InterruptedException e ){ throw new InterruptedException(); }
    }

    //signal function
    public synchronized void V() {
        resources++ ; if (resources <= 0) notify() ;
    }

    // non-blocking wait: takes a resource only if one is free right now
    public synchronized boolean tryP() {
        if (resources > 0) {
            resources--;
            return true;
        }
        return false;
    }


    public synchronized boolean P_with_timeout(long wait_time ){
        resources--;
        // no empty room
        if(resources < 0) {
            long finish_time=System.nanoTime()+wait_time*1_000_000L;
            while(finish_time>System.nanoTime()){
                long remain_time=finish_time-System.nanoTime();
                try { wait(remain_time/1_000_000L,(int)(remain_time%1_000_000L));} catch( InterruptedException e ){  resources++;Thread.currentThread().interrupt(); return false;}
                if(resources>=0){
                    return true;
                }
            }
            resources++;
            return false;
        }
        // empty room
        else{
            return true;
        }
    }
}
