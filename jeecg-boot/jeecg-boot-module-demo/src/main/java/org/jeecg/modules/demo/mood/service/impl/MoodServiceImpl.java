package org.jeecg.modules.demo.mood.service.impl;

import org.jeecg.modules.demo.mood.entity.Mood;
import org.jeecg.modules.demo.mood.mapper.MoodMapper;
import org.jeecg.modules.demo.mood.service.IMoodService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 情绪轮列表
 * @Author: jeecg-boot
 * @Date:   2021-02-02
 * @Version: V1.0
 */
@Service
public class MoodServiceImpl extends ServiceImpl<MoodMapper, Mood> implements IMoodService {

}
