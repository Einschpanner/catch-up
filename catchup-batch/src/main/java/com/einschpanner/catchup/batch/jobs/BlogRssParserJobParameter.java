package com.einschpanner.catchup.batch.jobs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Getter
@NoArgsConstructor
public class BlogRssParserJobParameter {

    @Value("#{jobParameters[version]}")
    private String version;

//    @Value("#{jobParameters[createDate]}")
//    public void setCreateDate(String createDate) {
//        this.createDate = LocalDate.parse(createDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//    }
}