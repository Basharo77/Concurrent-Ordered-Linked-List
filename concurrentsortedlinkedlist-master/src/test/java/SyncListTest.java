import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SyncListTest extends TestCase {

    public void testAddList(){

        SyncList syncList = new SyncList();
//        syncList.remove(Integer.MAX_VALUE);
        syncList.add(1);
        syncList.add(2);
        syncList.add(3);
        syncList.add(Integer.MIN_VALUE);
        syncList.add(3);
        System.out.println(syncList.contain(5));
        System.out.println(syncList.contain(2));
        syncList.remove(3);
    }

    public void testRandSeq() {
        RandomSeq randomSeq = new RandomSeq(0, 80_000);
        for (int i = 0; i < 10; i++) {
            System.out.print(randomSeq.next() + " ");
        }
    }

    int randLen = 40_000;
    int threadNum = 8;
   public void testHelp(SortList list, String label) {
        RandomSeq seq = new RandomSeq(0, 80_000);
        List<Thread> addThreads = new ArrayList<>();
        List<AddThread> xAdd = new ArrayList<>();
        List<Thread> containThreads = new ArrayList<>();
       List<ContainThread> xContain = new ArrayList<>();
       List<Thread> removeThreads = new ArrayList<>();
       List<RemoveThread> xRemove = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            AddThread addThread = new AddThread(seq, randLen / threadNum, list);
            xAdd.add(addThread);
            ContainThread containThread = new ContainThread(seq, randLen / threadNum, list);
            xContain.add(containThread);
            RemoveThread removeThread = new RemoveThread(seq, randLen / threadNum, list);
            xRemove.add(removeThread);

            Thread threadA = new Thread(addThread);
            addThreads.add(threadA);
            Thread threadC = new Thread(containThread);
            containThreads.add(threadC);
            Thread threadR = new Thread(removeThread);
            removeThreads.add(threadR);
        }
        int listLengthAfterAdds = 0;
        int listLengthAfterRemovals = 0;
        long startA = System.currentTimeMillis();

        addThreads.stream().forEach(e -> e.start() );
        addThreads.stream().forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        long endA = System.currentTimeMillis() - startA;
       Integer sum1,sum2;
       sum1 = 0;
       sum2 = 0;
       for (AddThread i : xAdd){
           sum1 += i.success;
           sum2 += i.failure;
       }
       listLengthAfterAdds = sum1;
        System.out.println("ADD "+label+" execution task: "+endA+" ms");
        System.out.println("List length after add "+ listLengthAfterAdds);
        long startC = System.currentTimeMillis();
        containThreads.stream().forEach(e -> e.start() );
        containThreads.stream().forEach(e -> {
           try {
               e.join();
           } catch (InterruptedException ex) {
               throw new RuntimeException(ex);
           }
        });
       long endC = System.currentTimeMillis() - startC;
       sum1 = 0;
       sum2 = 0;
       for (ContainThread i : xContain){
           sum1 += i.success;
           sum2 += i.failure;
       }
       System.out.println("Contain "+label+" execution task: "+endC+" ms");
       System.out.println("Total number of successes found: "+ sum1 + ", failures found: " + sum2);
       long startR = System.currentTimeMillis();

       removeThreads.stream().forEach(e -> e.start() );
       removeThreads.stream().forEach(e -> {
           try {
               e.join();
           } catch (InterruptedException ex) {
               throw new RuntimeException(ex);
           }
       });
       long endR = System.currentTimeMillis() - startR;
       sum1 = 0;
       sum2 = 0;
       for (RemoveThread i : xRemove){
           sum1 += i.success;
           sum2 += i.failure;
       }
       listLengthAfterRemovals = listLengthAfterAdds - sum1;
       System.out.println("Remove "+label+" execution task: "+endR+" ms");
       System.out.println("List length after removals "+ listLengthAfterRemovals);
       System.out.println("Total number of successes removed: "+ sum1 + ", failures removed: " + sum2);
   }

    public void testRun(){
        SyncList syncList = new SyncList();
        testHelp(syncList,"Synchronization");
        System.out.println("==============");
        RWLockList rwLockList = new RWLockList();
        testHelp(rwLockList, "RWLock");
        System.out.println("==============");
        LockList list = new LockList();
        testHelp(list,"Lock");
    }
}
