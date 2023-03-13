package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.CommentInfoDao;
import com.internetplus.farm.order.entity.CommentInfoEntity;
import com.internetplus.farm.order.service.CommentInfoService;


@Service("commentInfoService")
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoDao, CommentInfoEntity> implements CommentInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentInfoEntity> page = this.page(
                new Query<CommentInfoEntity>().getPage(params),
                new QueryWrapper<CommentInfoEntity>()
        );

        return new PageUtils(page);
    }

}