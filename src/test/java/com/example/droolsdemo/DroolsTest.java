package com.example.droolsdemo;

import com.example.droolsdemo.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-07-06-18:23
 **/

@Slf4j
@SpringBootTest
class DroolsTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testRedisAddRule() throws IOException {
        String drlUrl = "https://cos.ap-beijing.myqcloud.com/xh-cdn-1300455117/drools-cms/prod/drools/1657526668747_member.drl";
        URL url = new URL(drlUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        int c;
        StringBuilder sb = new StringBuilder();
        while ((c = reader.read()) != -1) {
            sb.append((char) c);
        }
        log.info("=== set drl content into redis ===");
        redisTemplate.opsForValue().set("testRule", sb.toString());
        log.info("==== end set drl content into redis ====");
        KieHelper kieHelper = new KieHelper();
        log.info("=== read redis drl content ===");
        String drlContent = redisTemplate.opsForValue().get("testRule");
        log.info("content:\n{}", drlContent);
        log.info("=== end read redis drl content ===");
        kieHelper.addContent(drlContent, ResourceType.DRL);
        //  establish KieBase It's a costly one
        Results results = kieHelper.verify();
        List<Message> messages = results.getMessages(Message.Level.ERROR);
        for (Message message : messages) {
            log.error("发生错误： {}", message.getText());

        }
        if (!messages.isEmpty()) {
            return;
        }
        KieBase kieBase = kieHelper.build(EqualityBehaviorOption.IDENTITY);
        System.out.println(kieBase);
        //  establish KieSession The cost is small
        KieSession kieSession = kieBase.newKieSession();
        kieSession.setGlobal("logger", log);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void testTheHelloWorld() {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks = kc.newKieSession("testTheHelloWorld");
        List<String> hobbys = new ArrayList<>();;
        hobbys.add("唱歌");
        hobbys.add("跳舞");
        Person person = new Person();
        person.setAge(1);
        person.setGender("男");
        person.setName("geo");
        person.setHobby(hobbys);
        ks.insert(person);
        ks.setGlobal("logger", log);
        String s = "123213";
        ks.insert(s);
        int count = ks.fireAllRules();
        log.info("count:{}", count);
        ks.dispose();
    }

    @Test
    void testMatches() {
        KieServices kss  = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks = kc.newKieSession("testMatches");
        String s = "15622575941";
        ks.insert(s);
        int count = ks.fireAllRules();
        System.out.println(count);
        ks.dispose();
    }

    @Test
    void testnoLoopRule() {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks = kc.newKieSession("noLoopRule");
        List<String> hobbys = new ArrayList<>();
        hobbys.add("唱歌");
        hobbys.add("跳舞");
        Person person = new Person();
        person.setAge(1);
        person.setGender("男");
        person.setName("geo");
        person.setHobby(hobbys);
        ks.insert(person);
        int c = ks.fireAllRules();
        System.out.println(c);
        System.out.println(person);
        ks.dispose();

    }
}
