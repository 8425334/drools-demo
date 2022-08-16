package com.example.droolsdemo;

import com.example.droolsdemo.model.ApiProperty;
import com.example.droolsdemo.model.Field;
import com.example.droolsdemo.model.Property;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.protobuf.Api;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.Request.Builder;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-07-22-17:38
 **/
@Slf4j
public class ParameterBatchSave {

    public static void main(String[] args) throws InterruptedException {
        readMap();
        Gson gson = new Gson();
        for (Field field : fields) {
//            System.out.println(field);
//            request("http://localhost:8978/field/add", gson.toJson(field));
//            Thread.sleep(1000);
        }
        log.info("所有数据处理完毕");
    }

    private static Map<String, Object> readMap() {
        String path = "/Users/shenxuehai/Downloads/分发.json";
        Gson gson = new Gson();
        ApiProperty apiProperty = null;
        try {
            apiProperty = gson.fromJson(new FileReader(path), ApiProperty.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Queue<ApiProperty> queue = new LinkedList<>();
        queue.add(apiProperty);
        while (!queue.isEmpty()) {
            ApiProperty poll = queue.poll();
            log.info("开始处理：{}", poll.getRequired());
            readProperties(poll, queue);
            log.info("结束处理：{}", poll.getRequired());
        }
        return null;
    }

    private static List<Field> fields = new ArrayList<>();

    private static void readProperties(ApiProperty apiProperty, Queue<ApiProperty> properties) {
        Map<String, Property> map = apiProperty.getProperties();
        if (map == null) {
            log.error("获取map失败，apiProperty: {}", apiProperty);
            return;
        }
        Set<String> keySet = map.keySet();
        for (String key: keySet) {
            Property property = map.get(key);
            if (property.getItems() != null) {
                properties.add(property.getItems());
            }
            if (property.getType().equals("object")) {
                continue;
            }
            Field field = new Field();
            field.setFieldCode(key);
            String fieldName = property.getDescription();
            field.setFieldName(fieldName.substring(0, Math.min(fieldName.length(), 9)));
            field.setBizLine(2);
            field.setRemark(property.getDescription());
            field.setTypeOfData(property.getType());
            if (field.getRemark().contains("同盾")) {
                field.setFieldTypeId("20220707000000001001");
            } else if (field.getRemark().contains("百融")) {
                field.setFieldTypeId("20220707000000002000");
            } else {
                field.setFieldTypeId("20220707000000001000");
            }

            fields.add(field);

        }
    }


    static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(5000, TimeUnit.MINUTES)
            .callTimeout(5000, TimeUnit.MINUTES)
            .build();
    private static void request(String url, String json) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("cookie", "SESSION=Njc2ZGFkYjgtYzhjNC00ZWFiLTk1YTktMzIwMGFiOWU4Yzc2; TGC-prod=TGT-ceb2663c23874b97a2ac80cb3920b1ed; sidebarStatus=0")
                .method("POST", new RequestBody() {
                    @Nullable
                    @Override
                    public MediaType contentType() {
                        return MediaType.get("application/json");
                    }

                    @Override
                    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
//                        String json = "{\"fieldName\":\"2131\",\"fieldTypeId\":\"20220629000000013013\",\"fieldCode\":\"321321\",\"typeOfData\":\"21321\",\"bizLine\":0,\"remark\":\"23121\",\"dictionary\":\"\",\"development\":\"21321\",\"businesser\":\"\",\"provider\":\"\"}";
                        log.info("开始写入: {}", json);
                        bufferedSink.write(json.getBytes());
                    }
                })
                .build();
        log.info("开始请求, url:{}, json:{}", url, json);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("请求失败",e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.info("请求完成: {}", response.body().string());
            }
        });
    }
}
