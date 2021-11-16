package example.demo;

//余弦相似度
public class JavaCosineDistance {

    public static void main(String[] args) throws InterruptedException {
        int n=100000000;
        long startTime=System.currentTimeMillis();

        for(int i=0;i<n;i++){
            Thread.sleep((long) 1);
        }

        long endTime=System.currentTimeMillis();
        System.out.println("当前程序耗时："+(endTime-startTime)+"ms");
    }
}
