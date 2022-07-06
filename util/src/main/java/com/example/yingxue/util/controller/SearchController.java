package com.example.yingxue.util.controller;

import com.example.yingxue.common.video.vo.VideoVo;
import com.example.yingxue.util.feignclient.LikeClient;
import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class SearchController{
    @Autowired
    private Gson gson;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private LikeClient likeClient;
    @GetMapping("/search/videos")
    public HashMap<String,Object> getVideoByTitleKeyword(@RequestParam("q") String keyword,@RequestParam("page") Integer pageNum,@RequestParam("per_page") Integer pageSize) throws Exception{
        SearchRequest searchRequest=new SearchRequest(VideoVo.ES_INDEX_NAME);
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //这里可以通过QueryBuilders实现各种复杂查询
        sourceBuilder.query(QueryBuilders.termQuery("title",keyword));
        //from
        sourceBuilder.from((pageNum-1)*pageSize);
        //size
        sourceBuilder.size(pageSize);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse=restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHit[] hits=searchResponse.getHits()
                                       .getHits();
        int totalCount=hits.length;
        HashMap<String,Object> res=new HashMap<>();
        res.put("total_count",totalCount);
        List<VideoVo> videoVos=new ArrayList<>();
        //输出结果
        for(SearchHit hit: hits){
            VideoVo videoVo=gson.fromJson(hit.getSourceAsString(),VideoVo.class);
            videoVo.setLikes(likeClient.getLikeCountByVideoId(videoVo.getId()));
            videoVos.add(videoVo);
        }
        res.put("items",videoVos);
        return res;
    }
}
