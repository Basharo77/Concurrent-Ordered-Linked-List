public abstract class TestThread {
    SortList list;
    Integer success;
    Integer failure;
    Integer [] nums;

    public TestThread(RandomSeq seq, int seqPart, SortList setList) {
        this.list = setList;
        this.nums = new Integer[seqPart];
        for (int i=0;i<nums.length;i++){
            nums[i] = seq.next();
        }
        success = 0;
        failure = 0;
    }
}
