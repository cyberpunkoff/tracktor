package edu.java.clients.kispython;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import lombok.extern.slf4j.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

@Slf4j
public class JSoupKispythonClient implements KispythonClient {
    private static final String DEFAULT_BASE_URL = "https://kispython.ru/group/23";
    private static final String DEFAULT_GROUP_RESOURCE = "/group/23";
    private final String baseUrl;

    public JSoupKispythonClient() {
        this(DEFAULT_BASE_URL);
    }

    public JSoupKispythonClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public List<Integer> getAllTaskNumbers() {
        Integer amountOfTasks = getTaskCount();

        return IntStream.range(1, amountOfTasks)
            .boxed()
            .toList();
    }

    @Override
    public Integer getLastTaskNumber() {
        return getTaskCount();
    }

    private Integer getTaskCount() {
        Document document = fetchDocument();
        Elements tableHeaderElements = document.select("thead");
        return tableHeaderElements.select("td").size();
    }

    private Document fetchDocument() {
        try {
            String url = getAbsoluteUrl(DEFAULT_GROUP_RESOURCE);
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}
