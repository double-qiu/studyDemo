package cn.didadu.sample.concurrentDesign.future;

/**
 * Created by jinggg on 16/3/23.
 */
public class Client {

    public Data request(final String queryStr){
        final FutureData futureData = new FutureData();
        new Thread(() -> {
            RealData realData = new RealData(queryStr);
            futureData.setRealData(realData);
        }).start();
        return futureData;
    }

    public static void main(String[] args){
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");

        System.out.println("数据=" + data.getResult());
    }

}
