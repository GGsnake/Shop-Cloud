package com.example.servicesdk.pdd;

import com.alibaba.fastjson.JSONObject;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pdd")
public class PddService {

    /**
     * query(拼多多关键词搜索-服务)
     *
     * @param request
     * @return JSONObject
     * @author liujupeng
     */
    @RequestMapping("/query")
    public JSONObject serachGoodsAll(PddDdkGoodsSearchRequest request) {
        JSONObject data = new JSONObject(10);
        PopClient client = new PopHttpClient("48dcc8985c8e4838be2dea3aa9b6176f", "62b91c07f697bce84beec4123b01f6e108d2fa38");
        PddDdkGoodsSearchResponse response = null;
        try {
            response = client.syncInvoke(request);
            List<PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem> goodsList = response.getGoodsSearchResponse().getGoodsList();
            Integer totalCount = response.getGoodsSearchResponse().getTotalCount();
            data.put("data", goodsList);
            data.put("count", totalCount);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
