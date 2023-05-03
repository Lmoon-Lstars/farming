package com.internetplus.farm.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.user.dao.InfoDao;
import com.internetplus.farm.user.entity.CuponEntity;
import com.internetplus.farm.user.service.CuponService;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.user.entity.InfoEntity;
import com.internetplus.farm.user.service.InfoService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-03-22 14:52:47
 */
@RestController
@RequestMapping("user/info")
public class InfoController {
    @Autowired
    private InfoService infoService;

    @Autowired
    private CuponService cuponService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userInfoId}")
    public R info(@PathVariable("userInfoId") Integer userInfoId){
		InfoEntity info = infoService.getById(userInfoId);

        return R.ok().put("info", info);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody InfoEntity info){
		infoService.save(info);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody InfoEntity info){
		infoService.updateById(info);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] userInfoIds){
		infoService.removeByIds(Arrays.asList(userInfoIds));

        return R.ok();
    }

    /**
     * 获取种源点
     */
    @RequestMapping("/getPlant")
    public R getPlant(@RequestParam(value = "userId")String userId) {
        R r = new R();
        InfoEntity info = infoService.getById(userId);
        r.put("plantPoint",info.getPlantPoint());
        return r;
    }

    /**
     * 获取优惠券数量
     */
    @RequestMapping("/getCupon")
    public R getCupon(@RequestParam(value = "userId")String userId) {
        R r = new R();
        QueryWrapper<CuponEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_info_id",userId);
        List<CuponEntity> list = cuponService.list(wrapper);
        r.put("num",list.size());
        return r;
    }

    /**
     * 根据id修改用户的种源点
     */
    @RequestMapping("/setPoint")
    public void setPoint(@RequestParam(value = "userId")Integer userId,@RequestParam(value = "plantPoint")String point) {
        QueryWrapper<InfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_info_id",userId);
        InfoEntity info = infoService.getOne(wrapper);
        String plantPoint = String.valueOf(Integer.parseInt(info.getPlantPoint()) + Integer.parseInt(point));
        info.setPlantPoint(plantPoint);
        infoService.updateById(info);
    }

    /**
     * 保存授权登录信息
     */
    @ResponseBody
    @RequestMapping("/save/info")
    public R saveInfo(@RequestParam(value="code",required=false)String code,@RequestParam(value = "imgurl")String imgurl,
        @RequestParam(value = "nickname")String nickname){
        System.out.println("code:"+code+" "+"nickname"+nickname+" "+"url"+imgurl);
        //微信官方提供获取openid的url
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        //替换appid，appsecret，和code
        String requestUrl = WX_URL.replace("APPID", "wx7b3b6fb59174a7e2").//填写自己的appid
            replace("SECRET", "13c2895a274513cbf0443ee5c076b2a0").replace("JSCODE", code).//填写自己的appsecret，
            replace("authorization_code", "authorization_code");
        JSONObject convertvalue=new JSONObject();
        //调用get方法
        String  returnvalue=GET(requestUrl);
        convertvalue= (JSONObject) JSON.parse(returnvalue);
        String openid= (String) convertvalue.get("openid");
        System.out.println(openid);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("open_id",openid);
        List<InfoEntity> list = infoService.list(wrapper);
        if(list.size() == 0) {
            InfoEntity info = new InfoEntity();
            info.setAvatarUrl(imgurl);
            info.setNickName(nickname);
            info.setOpenId(openid);
            info.setRegisterTime(new Date());
            info.setUserState(1);
            infoService.save(info);
            list.add(info);
        } else {
            System.out.println("用户已存在");
        }
        int userId = list.get(0).getUserInfoId();
        String nickName = list.get(0).getNickName();
        String url = list.get(0).getAvatarUrl();
        String phoneNumber = list.get(0).getMobilePhone();
        int state = list.get(0).getUserState();
        R r = new R();
        r.put("userId",String.valueOf(userId));
        r.put("nickName",nickName);
        r.put("imgurl",url);
        r.put("phoneNumber",phoneNumber);
        r.put("state",state);
        return r;
    }
    public  String GET(String url) {
        String result="";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try{
            //构造url对象
            URL realUrl = new URL(url);
            //获取一个对应该URL的URLConnection对象
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("异常出现");
        }
        //关闭资源
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("出现异常");
            }
        }
        return result;
    }

}
