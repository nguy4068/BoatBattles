public class check {
    public static final double constant = 2.71828;
    public static void changeValue(int num){
        num = 6;
    }
    public static int random(int num){
        double firstRan = (Math.random())*num;
        System.out.println(firstRan);
        int  numRandom = (int) firstRan;
        return numRandom;
    }
    public static void main(String[] args){



    }
}
