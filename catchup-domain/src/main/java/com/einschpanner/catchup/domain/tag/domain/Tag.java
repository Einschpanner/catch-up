package com.einschpanner.catchup.domain.tag.domain;

import com.einschpanner.catchup.domain.post.domain.PostTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_TAG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags;

}

