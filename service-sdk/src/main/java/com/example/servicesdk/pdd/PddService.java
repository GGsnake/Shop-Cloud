package com.example.servicesdk.pdd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.servicesdk.utils.SDKClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPromotionUrlGenerateResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/pdd")
public class PddService {

    /**
     * query(拼多多关键词搜索-服务)
     * @param request
     * @return JSONObject
     * @author liujupeng
     */
    @RequestMapping("/query")
    public JSONObject serachGoodsAll(PddDdkGoodsSearchRequest request) {
        JSONObject data = new JSONObject(10);
        PopClient popClient = SDKClient.getPopClient();
        PddDdkGoodsSearchResponse response = null;
        try {
            response = popClient.syncInvoke(request);
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


    //查询拼多多商品详情
    @RequestMapping("/detail")
    public JSONObject Detail(Long goodIdList) {
        PopClient popClient = SDKClient.getPopClient();

        PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();
        List goodlist = new ArrayList();
        goodlist.add(goodIdList);
        request.setGoodsIdList(goodlist);
        PddDdkGoodsDetailResponse response = null;
        try {
            response = popClient.syncInvoke(request);
            JSONObject temp = new JSONObject();
            JSONArray arrary = new JSONArray();
            if (response.getGoodsDetailResponse().getGoodsDetails().size() == 0) {
                temp.put("list", arrary);
                return temp;
            }
            List<String> goodsGalleryUrls = response.getGoodsDetailResponse().getGoodsDetails().get(0).getGoodsGalleryUrls();
            if (goodsGalleryUrls == null) {
                temp.put("list", arrary);
                return temp;
            }
            temp.clear();
            temp.put("list", goodsGalleryUrls);
            return temp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    //生成推广链接
    @RequestMapping("convert")
    public JSONObject convert(@NonNull String pid, @NonNull Long goodId) {
        JSONObject data = new JSONObject();
        PopClient popClient = SDKClient.getPopClient();
        PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
        request.setPId(pid);
        List<Long> goodsIdList = new ArrayList<Long>();
        goodsIdList.add(goodId);
        request.setGoodsIdList(goodsIdList);
        request.setGenerateShortUrl(true);
        request.setMultiGroup(false);
        try {
            PddDdkGoodsPromotionUrlGenerateResponse response = popClient.syncInvoke(request);
            PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponseGoodsPromotionUrlListItem listItem = response.getGoodsPromotionUrlGenerateResponse().getGoodsPromotionUrlList().get(0);
            data.put("urlBean",listItem);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
            return null;
        }
    }

}
