package com.example.droolsdemo.yunding;

import cn.hutool.extra.mail.MailUtil;
import com.example.droolsdemo.yunding.model.Result;
import com.example.droolsdemo.yunding.model.product.*;
import com.google.gson.*;
import okhttp3.*;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-08-15-15:38
 **/
public class YundingOrderRunner {

    // 用户token
    public static final String TOKEN = "8de00101-10e1-4d70-b5e1-738fb5db533f";

    // 支付密码
    public static final String pwd = "842533";

    // 用户邮箱
    private static final String mail = "8425334@qq.com";

    // 最高价自动买入监控
    public static final double MAX_BUY = 20;

    // 最低价自动买入监控
    public static final double MIN_BUY = 11;

    public static void main(String[] args) throws InterruptedException, IOException {
//         普通抢购
        genPriceMap();
        while (true) {
            System.out.println("=== 开始监控 ===");
            getGoodList();
            Thread.sleep(800);
            System.out.println("====== 结束监控 ====");
        }

//        // 首发抢购
//        Product product = new Product();
//        product.setId(40);
//        createOrder(product);
    }

    static Map<String, Double> priceMap = new HashMap<>();

    // 藏品哈希表，低于价格买入
    private static void genPriceMap() {
        priceMap.put("纪念勋章", 6.66);
        priceMap.put("山海异兽·旋龟", 40.00);
        priceMap.put("山海异兽·薄鱼", 11.00);
        priceMap.put("山海异兽·赤鱬", 11.50);
        priceMap.put("山海异兽·瞿如", 10.50);
        priceMap.put("山海异兽·类", 150.00);
        priceMap.put("山海异兽·毕方", 35.00);
        priceMap.put("山海异兽·乘黄", 99.00);
        priceMap.put("山海异兽·比翼鸟", 30.00);
        priceMap.put("七夕月下", 99.00);
        priceMap.put("梦回敦煌", 13.00);
        priceMap.put("山海旧梦·应龙", 1300.00);
        priceMap.put("山海旧梦·酸与", 66.00);
        priceMap.put("丝绸之路", 16.00);
        priceMap.put("云顶创世·百鸟朝凤", 99.00);
        priceMap.put("精卫填海",20.00);
        priceMap.put("赛博龙龟·黄金款", 1100.00);
        priceMap.put("妖悟净", 99.00);
        priceMap.put("山海神兽·双双", 150.00);
        priceMap.put("创世女神·娲皇补天 ", 999.00);
        priceMap.put("魔兽三国·炽焰关羽", 999.00);
        priceMap.put("山海旧梦·巴蛇", 13.00);
        priceMap.put("山海旧梦·鹿蜀", 30.00);
        priceMap.put("鹿自敦煌来·鹿王本生", 99.00);
        priceMap.put("赛博龙龟·琉璃款", 99.00);
        priceMap.put("山海旧梦·肥遗", 555.00);
        priceMap.put("妖悟能", 33.00);
        priceMap.put("赛博龙龟·国潮款", 999.00);
        priceMap.put("云顶艺术·创世勋章", 88.00);
    }

    // 商品列表获取
    private static OkHttpClient goodsClient = new OkHttpClient();

    private static void getGoodList() {
        Request request = new Request.Builder()
                .url("https://api.vvtok.com/api/basic/product")
                .addHeader("token", TOKEN)
                .post(new RequestBody() {
                    @Nullable
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse("application/json");
                    }

                    @Override
                    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                        Gson gson = new Gson();
                        String s = gson.toJson(new ProductRequest());
                        bufferedSink.write(s.getBytes());
                    }
                })
                .build();
        goodsClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String json = response.body().string();
//                        System.out.println("获取商品列表" + json);
                        System.out.println("");
                        if (!json.contains("<html>")) {
                            Gson gson = new Gson();
                            JsonObject jsonObject = new JsonParser().parse(json)
                                    .getAsJsonObject();
                            JsonArray jsonArray = jsonObject.get("data").getAsJsonObject().get("data").getAsJsonArray();
                            for (JsonElement element : jsonArray) {
                                Product product = gson.fromJson(element, Product.class);
                                Double price = Double.parseDouble(product.getSellPrice());
                                if (product.getStatus().equals("5")) {
                                    if (priceMap.containsKey(product.getName())) {
                                        Double aDouble = priceMap.get(product.getName());
                                        if (price < aDouble) {
                                            System.out.println("触发map价，name: " + product.getName() + ",originPrice:" + product.getSellPrice() + ", setting price: " + aDouble );
                                            createOrder(product);
                                        }
                                    } else {
                                        System.out.println("未匹配到商品存在,name: " + product.getName());
                                        if (price < MIN_BUY) {
                                            System.out.println("触发最低价买入：product:" + product);
                                            createOrder(product);
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("请求错误:" + json);
                        }
                    }
                });
    }


    /**
     * 创建订单
     */
    private static OkHttpClient createOrderClient = new OkHttpClient();
    private static void createOrder(Product product) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.vvtok.com/api/works/creatOrders")
                .header("token", TOKEN)
                .post(new RequestBody() {
                    @Nullable
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse("application/json");
                    }

                    @Override
                    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                        OrderRequest orderRequest = new OrderRequest();
                        orderRequest.setId(product.getId() + "");
                        Gson gson = new Gson();
                        String s = gson.toJson(orderRequest);
                        System.out.println("create order: " + s + ", product:" + product);
                        bufferedSink.write(s.getBytes());
                    }
                })
                .build();
        Response execute = createOrderClient.newCall(request).execute();
        String json = execute.body().string();
        Gson gson = new Gson();
        Result<OrderResult> result = gson.fromJson(json, Result.class);
        if (result.getCode() == 1) {
            System.out.println("抢购结果:" + json);
            MailUtil.send(
                    mail,
                    "云顶自动抢购提醒",
                    "抢购商品：" + product +
                            "\n抢购时间:" + new Date().toString() +
                            "\n抢购结果："+ json,
            false);
            JsonObject jsonObject = new JsonParser().parse(json)
                    .getAsJsonObject();
            JsonElement element = jsonObject.get("data");
            OrderResult orderResult = gson.fromJson(element, OrderResult.class);
            if (MAX_BUY < Double.parseDouble(product.getSellPrice())) {
                MailUtil.send(
                        mail,
                        "云顶手动支付提醒",
                        "抢购商品：" + product +
                                "\n抢购时间:" + new Date().toString(),
                        false);
            }
            buyOrder(product, orderResult);
        }
        System.out.println(json);
    }


    private static OkHttpClient buyClient = new OkHttpClient();
    private static void buyOrder(Product product, OrderResult result) {
        // 休眠2秒再支付
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setOrder_id(result.getId());
        buyRequest.setBank_id("");
        buyRequest.setClient("app");
        buyRequest.setPay_pwd(pwd);
        buyRequest.setPay_status("money");
        Gson gson = new Gson();
        String s = gson.toJson(buyRequest);
        Request request = new Request.Builder()
                .url("https://api.vvtok.com/api/works/payOrder")
                .header("token", TOKEN)
                        .post(new RequestBody() {
                            @Nullable
                            @Override
                            public MediaType contentType() {
                                return MediaType.parse("application/json");
                            }

                            @Override
                            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                                System.out.println("开始自动支付：" + s);
                                bufferedSink.write(s.getBytes());
                            }
                        })
                                .build();
        buyClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                System.out.println("支付结果：" + json);
                MailUtil.send(
                        mail,
                        "云顶自动支付提醒",
                        "抢购商品：" + product +
                                "\n抢购时间:" + new Date().toString() +
                                "\n支付请求：" + s +
                                "\n支付结果："+ json,
                        false);
            }
        });

    }

}
