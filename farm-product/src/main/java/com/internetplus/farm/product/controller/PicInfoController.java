package com.internetplus.farm.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.product.entity.PicInfoEntity;
import com.internetplus.farm.product.service.PicInfoService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 商品图片信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 16:42:29
 */
@RestController
@RequestMapping("product/picinfo")
public class PicInfoController {
    @Autowired
    private PicInfoService picInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = picInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{productPicId}")
    public R info(@PathVariable("productPicId") Integer productPicId){
		PicInfoEntity picInfo = picInfoService.getById(productPicId);

        return R.ok().put("picInfo", picInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PicInfoEntity picInfo){
		picInfoService.savePicinfo(picInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PicInfoEntity picInfo){
		picInfoService.updataPicinfo(picInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] productPicIds){
		picInfoService.removeByIds(Arrays.asList(productPicIds));

        return R.ok();
    }

    /**
     * 获取图片
     */
    @RequestMapping("/getPic")
    public String getUrl(@RequestParam(value = "productId")String productId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id",Integer.valueOf(productId));
        return picInfoService.getOne(queryWrapper).getPicUrl();
    }



    /**
     * 接收前端上传的图片
     */
    @PostMapping("/receive")
    public R uploadImage(@RequestParam(value = "id",required = false) String id, @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return R.error("上传失败，请选择文件");
        }
        // 获取上传文件的文件名
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // 检查上传文件的文件类型，这里假设只允许上传jpg和png图片
            if (fileName.contains("..") || !(fileName.endsWith("jpg") || fileName.endsWith("png"))) {
                return R.error("上传失败，请选择jpg或png格式的图片");
            }
            // 保存上传文件到指定的上传路径，文件名使用ID进行命名
            File dest = new File("/www/wwwroot/farm/photo/" +  fileName );
            if(picInfoService.getOne(new QueryWrapper<PicInfoEntity>().eq("product_id",id)) != null && picInfoService.getOne(new QueryWrapper<PicInfoEntity>().eq("product_id",id)).getPicUrl() != null && picInfoService.getOne(new QueryWrapper<PicInfoEntity>().eq("product_id",id)).getPicUrl().length() != 0) {
                String name = picInfoService.getOne(new QueryWrapper<PicInfoEntity>().eq("product_id",id)).getPicUrl().substring(24);
                File temp = new File("/www/wwwroot/farm/photo/" + name);
                temp.delete();
            }
            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            // 将上传的文件名和ID保存到数据库中
            PicInfoEntity image = new PicInfoEntity();
            image.setProductId(Integer.valueOf(id));
            image.setPicUrl("http://aitmaker.cn:8008/" + fileName);
            picInfoService.saveOrUpdate(image, new QueryWrapper<PicInfoEntity>().eq("product_id", id));
            return R.ok("上传成功");
        } catch (IOException e) {
            throw new IOException("上传文件失败", e);
        }
    }

    @RequestMapping("/search")
    public List search(@Param("productId")Integer productId) {
        QueryWrapper<PicInfoEntity> queryWrapper = new QueryWrapper<>();
        if(productId != null) {
            queryWrapper.like("product_id",productId);
        }
        return picInfoService.getBaseMapper().selectList(queryWrapper);
    }

}
