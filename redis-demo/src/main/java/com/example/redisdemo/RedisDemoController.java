package com.example.redisdemo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import redis.clients.jedis.Jedis;

@RestController
public class RedisDemoController implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${REDIS_URL}")
    private String redisUrl;

    @Value("${REDIS_HOST}")
    private String redisHost;

    @Value("${REDIS_PORT}")
    private String redisPort;

    @Value("${REDIS_PASSWORD}")
    private String redisPassword;

    @Value("${REDIS_DB}")
    private String redisDB;

    Jedis jedis;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if (!redisUrl.equals("")) {
                jedis = new Jedis(redisUrl);
            } else {
                jedis = new Jedis(redisHost, Integer.parseInt(redisPort));
            }
            if (!redisPassword.equals("")){
                jedis.auth(redisPassword);
            }
            if (!redisDB.equals("")) {
                jedis.select(Integer.parseInt(redisDB));
            }
        }
        catch (Exception ignored) {
        }
    }
    @GetMapping(value = "/repos/{gitName}")
    public RepoitoryInfo getGitData(@PathVariable("gitName") String gitName) {
        long startTime = System.nanoTime();
        String gitData = jedis.get(gitName);
        boolean isCached = true;
        if (gitData == null) {
            gitData = getGitReposData(gitName);
            isCached = false;
        }

        String responseTime = getResponseTime(System.nanoTime() - startTime, 1_000_000);
        
        RepoitoryInfo repoInfo = new RepoitoryInfo();
        repoInfo.setGitData(gitData);
        repoInfo.setGitName(gitName);
        repoInfo.setCached(isCached);
        repoInfo.setResponseTime(responseTime);

        return repoInfo;
    }

    public static String getResponseTime(long num, double divisor) {
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(num / divisor) + "ms";
    }


    private String getGitReposData(String gitName) {
        try {
            String sURL = String.format("https://api.github.com/users/%s", gitName);
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            try {
                String gitData = jsonObject.get("public_repos").getAsString();
                jedis.setex(gitName, 3600, gitData);
                return gitData;
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
