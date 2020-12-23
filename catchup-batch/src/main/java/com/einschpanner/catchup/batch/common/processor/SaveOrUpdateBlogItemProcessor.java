package com.einschpanner.catchup.batch.common.processor;

import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.user.domain.User;
import com.sun.syndication.feed.atom.Person;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SaveOrUpdateBlogItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) {
        log.info("userId : {}, name : {}", user.getUserId(), user.getNickname());

        Map<String, Blog> map = new HashMap<>();

        for (Blog b : user.getBlogs()) {
            map.put(b.getLink(), b);
            System.out.println(b.getTitle());
        }
        System.out.println();

        List<Blog> newBlogs = buildBlogList(user);
        for (Blog newBlog : newBlogs) {
            String link = newBlog.getLink();
            Blog exists = map.get(link);
            if (exists == null) user.addBlog(newBlog);
            else exists.update(newBlog); // 기존에 있는건 업데이트 하도록
        }

        log.info("{}", user.getBlogs().size());
        return user;
    }

    private List<Blog> buildBlogList(User user){
        List<Blog> newBlogs = new ArrayList<>();

        try {
            URL feedSource = new URL(user.getAddrRss());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));

            for (Object object : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) object;

                Blog newBlog = buildBlog(user, entry);
                newBlogs.add(newBlog);
            }
        } catch (FeedException | IOException e) {
            e.printStackTrace();
        }

        return newBlogs;
    }

    /**
     * User, SyndEntry로 새로운 Blog 인스턴스 생성
     */
    private Blog buildBlog(User user, SyndEntry entry) throws IOException {

        String title = entry.getTitle();
        String link = entry.getLink();
        String description = getDescription(entry.getDescription().getValue());
        String urlThumbnail = getUrlThumbnail(link);
        LocalDateTime publishedDate = getLocalDateTime(entry.getPublishedDate().getTime());

        return Blog.builder()
                .title(title)
                .link(link)
                .description(description)
                .urlThumbnail(urlThumbnail)
                .publishedDate(publishedDate)
                .user(user)
                .cntLike(0)
                .build();
    }

    /**
     * 페이지 Description 가져오기
     */
    private String getDescription(String parsing) {
        Document doc = Jsoup.parse(parsing);
        String description = doc.text();

        if (description.length() > 150) {
            description = description.substring(0, 150).concat("…");
        }
        return description;
    }

    /**
     * URL 썸네일 가져오기
     * + Naver Blog 같은 경우 iframe에 감춰져 있어 한 번 더 검사함
     * + 그래도 없으면 img 태그 서치
     */
    private String getUrlThumbnail(String url) throws IOException {
        String urlThumbnail = null;

        // open graph 태그 검사
        Document doc = Jsoup.connect(url).timeout(5000).get();
        urlThumbnail = doc.select("meta[property=og:image]").attr("content");
        if (!urlThumbnail.isEmpty()) return urlThumbnail;

        // iframe 검사
        Elements elements = doc.select("iframe[src]");
        if (!elements.isEmpty()) {
            String iframeUrl = elements.first().attr("abs:src");
            Document iframeDoc = Jsoup.connect(iframeUrl).timeout(3000).get();
            urlThumbnail = iframeDoc.select("meta[property=og:image]").attr("content");
            if (!urlThumbnail.isEmpty()) return urlThumbnail;
        }

        // img 태그 검사
        urlThumbnail = doc.getElementsByTag("img").first().absUrl("src");
        return urlThumbnail;
    }

    /**
     * Date to LocalDateTime 메서드
     */
    private LocalDateTime getLocalDateTime(long time) {
        return new java.sql.Timestamp(time).toLocalDateTime();
    }
}
