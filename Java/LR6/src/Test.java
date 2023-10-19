public class Test {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        myThread.firstMethod();
        myThread.secondMethod();
        myThread.thirdMethod(5);
    }
}
