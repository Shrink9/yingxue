package com.example.yingxue.common.video.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoVo{
    public static final String MQ_QUEUE_NAME="video-vo";
    public static final String ES_INDEX_NAME="video_vo";
    private Integer id;
    private String title;
    private String cover;
    private String category;
    @JsonProperty("created_at")
    private Date createdAt;
    private Integer likes;
    private String uploader;
}
