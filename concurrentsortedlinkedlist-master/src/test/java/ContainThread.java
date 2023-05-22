public class ContainThread extends TestThread implements Runnable{
    public ContainThread(RandomSeq seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            boolean b = list.contain(nums[i]);
            if (b){
                this.success += 1;
            }
            else this.failure += 1;
        }
    }
}
