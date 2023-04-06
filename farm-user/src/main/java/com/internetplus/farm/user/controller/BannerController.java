package com.internetplus.farm.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.product.entity.PicInfoEntity;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.user.entity.BannerEntity;
import com.internetplus.farm.user.service.BannerService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-02 16:25:55
 */
@RestController
@RequestMapping("user/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bannerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		BannerEntity banner = bannerService.getById(id);

        return R.ok().put("banner", banner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BannerEntity banner){
		bannerService.save(banner);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BannerEntity banner){
		bannerService.updateById(banner);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        for(int i = 0; i <ids.length; i++) {
            String name = bannerService.getBaseMapper().selectById(ids[i]).getImageurl().substring(24);
            File temp = new File("/www/wwwroot/farm/photo/" + name);
            temp.delete();
        }
        bannerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取轮播图列表
     */
    @RequestMapping("/getList")
    public List getList() {
        return bannerService.list();
    }

    /**
     * 接收前端上传的图片
     */
    @PostMapping("/receive")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        // 获取上传文件的文件名
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // 检查上传文件的文件类型，这里假设只允许上传jpg和png图片
            if (fileName.contains("..") || !(fileName.endsWith("jpg") || fileName.endsWith("png"))) {
                return "上传失败，请选择jpg或png格式的图片";
            }
            // 保存上传文件到指定的上传路径，文件名使用ID进行命名
            File dest = new File("/www/wwwroot/farm/photo/" +  fileName );
            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            // 将上传的文件名和ID保存到数据库中
            BannerEntity image = new BannerEntity();
            image.setImageurl("http://aitmaker.cn:8008/" + fileName);
            bannerService.save(image);
            return "上传成功";
        } catch (IOException e) {
            throw new IOException("上传文件失败", e);
        }
    }

}
