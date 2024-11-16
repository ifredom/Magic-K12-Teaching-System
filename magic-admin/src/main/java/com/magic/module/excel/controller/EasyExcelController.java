package com.magic.module.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.magic.framework.model.AjaxResult;
import com.magic.framework.utils.easyexcel.ExcelListener;
import com.magic.module.excel.entity.ExcelDataModel;
import com.magic.module.excel.service.MyExcelServiceExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用:
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/10/10 22:28
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/excel")
@Api(tags = "Excel管理")
public class EasyExcelController {

    @Autowired
    private MyExcelServiceExcel myExcelService;

    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    @ApiOperation(value = "excel", notes = "上传excel文件")
    public AjaxResult<String> postExcel(@RequestBody MultipartFile file) throws IOException {
        if(file==null){
            return AjaxResult.error("未选择文件");
        }
        if(file.isEmpty()){
            return AjaxResult.error("文件不能为空");
        }

        EasyExcel.read(file.getInputStream(), ExcelDataModel.class, new ExcelListener<>(myExcelService)).sheet().doRead();
        return AjaxResult.success("success");
    }

    @RequestMapping(value = "/excel2", method = RequestMethod.POST)
    @ApiOperation(value = "excel2", notes = "导出excel文件")
    public AjaxResult<String> getExcel() {
        List<ExcelDataModel> list = myExcelService.getAllExcelData();
        System.out.println(list.size());
        for (ExcelDataModel dataModel : list) {
            System.out.println(dataModel.toString());
        }

        String fileName = "D:\\export.xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, ExcelDataModel.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(list, writeSheet);
        }catch (Exception e){
            return AjaxResult.error("导出失败");
        }
        return AjaxResult.success("success");
    }

    @RequestMapping(value = "/excel3", method = RequestMethod.POST)
    @ApiOperation(value = "excel3", notes = "上传excel文件")
    public AjaxResult<String> postExcel2(@RequestBody MultipartFile file) {
        List<ExcelDataModel> list = myExcelService.getAllExcelData();

        // 可以写绝对路径，没有绝对路径默认放在当前目录下
        String fileName = "竹子数据-" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, ExcelDataModel.class).sheet("竹子数据").doWrite(list);

        return AjaxResult.success("success");
    }
}
