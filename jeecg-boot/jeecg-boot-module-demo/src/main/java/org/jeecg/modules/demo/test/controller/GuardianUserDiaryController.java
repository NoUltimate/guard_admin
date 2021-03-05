package org.jeecg.modules.demo.test.controller;

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
import org.jeecg.modules.demo.test.entity.GuardianUserDiary;
import org.jeecg.modules.demo.test.service.IGuardianUserDiaryService;

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
 * @Description: 用户日记
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Api(tags="用户日记")
@RestController
@RequestMapping("/test/guardianUserDiary")
@Slf4j
public class GuardianUserDiaryController extends JeecgController<GuardianUserDiary, IGuardianUserDiaryService> {
	@Autowired
	private IGuardianUserDiaryService guardianUserDiaryService;
	
	/**
	 * 分页列表查询
	 *
	 * @param guardianUserDiary
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "用户日记-分页列表查询")
	@ApiOperation(value="用户日记-分页列表查询", notes="用户日记-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(GuardianUserDiary guardianUserDiary,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GuardianUserDiary> queryWrapper = QueryGenerator.initQueryWrapper(guardianUserDiary, req.getParameterMap());
		Page<GuardianUserDiary> page = new Page<GuardianUserDiary>(pageNo, pageSize);
		IPage<GuardianUserDiary> pageList = guardianUserDiaryService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param guardianUserDiary
	 * @return
	 */
	@AutoLog(value = "用户日记-添加")
	@ApiOperation(value="用户日记-添加", notes="用户日记-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody GuardianUserDiary guardianUserDiary) {
		guardianUserDiaryService.save(guardianUserDiary);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param guardianUserDiary
	 * @return
	 */
	@AutoLog(value = "用户日记-编辑")
	@ApiOperation(value="用户日记-编辑", notes="用户日记-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody GuardianUserDiary guardianUserDiary) {
		guardianUserDiaryService.updateById(guardianUserDiary);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户日记-通过id删除")
	@ApiOperation(value="用户日记-通过id删除", notes="用户日记-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		guardianUserDiaryService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户日记-批量删除")
	@ApiOperation(value="用户日记-批量删除", notes="用户日记-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.guardianUserDiaryService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户日记-通过id查询")
	@ApiOperation(value="用户日记-通过id查询", notes="用户日记-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		GuardianUserDiary guardianUserDiary = guardianUserDiaryService.getById(id);
		if(guardianUserDiary==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(guardianUserDiary);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param guardianUserDiary
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, GuardianUserDiary guardianUserDiary) {
        return super.exportXls(request, guardianUserDiary, GuardianUserDiary.class, "用户日记");
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
        return super.importExcel(request, response, GuardianUserDiary.class);
    }

}
