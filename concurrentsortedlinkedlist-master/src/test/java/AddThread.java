public class AddThread extends TestThread implements Runnable {
    public AddThread(RandomSeq seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            boolean b = list.add(nums[i]);
            if (b){
                this.success += 1;
            }
            else this.failure += 1;
        }
    }
}
