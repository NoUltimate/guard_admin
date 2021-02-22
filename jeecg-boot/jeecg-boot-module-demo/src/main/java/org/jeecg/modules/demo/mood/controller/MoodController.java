package org.jeecg.modules.demo.mood.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.mood.entity.Mood;
import org.jeecg.modules.demo.mood.service.IMoodService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 情绪轮列表
 * @Author: jeecg-boot
 * @Date:   2021-02-02
 * @Version: V1.0
 */
@Api(tags="情绪轮列表")
@RestController
@RequestMapping("/mood/mood")
@Slf4j
public class MoodController extends JeecgController<Mood, IMoodService> {
	@Autowired
	private IMoodService moodService;
	
	/**
	 * 分页列表查询
	 *
	 * @param mood
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "情绪轮列表-分页列表查询")
	@ApiOperation(value="情绪轮列表-分页列表查询", notes="情绪轮列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Mood mood,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Mood> queryWrapper = QueryGenerator.initQueryWrapper(mood, req.getParameterMap());
		Page<Mood> page = new Page<Mood>(pageNo, pageSize);
		IPage<Mood> pageList = moodService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param mood
	 * @return
	 */
	@AutoLog(value = "情绪轮列表-添加")
	@ApiOperation(value="情绪轮列表-添加", notes="情绪轮列表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Mood mood) {
		moodService.save(mood);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param mood
	 * @return
	 */
	@AutoLog(value = "情绪轮列表-编辑")
	@ApiOperation(value="情绪轮列表-编辑", notes="情绪轮列表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Mood mood) {
		moodService.updateById(mood);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "情绪轮列表-通过id删除")
	@ApiOperation(value="情绪轮列表-通过id删除", notes="情绪轮列表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		moodService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "情绪轮列表-批量删除")
	@ApiOperation(value="情绪轮列表-批量删除", notes="情绪轮列表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.moodService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "情绪轮列表-通过id查询")
	@ApiOperation(value="情绪轮列表-通过id查询", notes="情绪轮列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Mood mood = moodService.getById(id);
		if(mood==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(mood);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mood
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Mood mood) {
        return super.exportXls(request, mood, Mood.class, "情绪轮列表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Mood.class);
    }

}
