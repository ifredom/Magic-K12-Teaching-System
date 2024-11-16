package com.magic.module.excel.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.magic.module.excel.entity.ExcelDataModel;
import com.magic.module.excel.mapper.ExcelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @date 2024/10/10 16:17
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
@Service
public class MyExcelServiceImplExcel extends ServiceImpl<ExcelMapper, ExcelDataModel> implements MyExcelServiceExcel {

    @Autowired
    private ExcelMapper excelMapper;

    @Override
    public List<ExcelDataModel> getAllExcelData() {
        return excelMapper.selectAllData();
    }

    @Override
    public void save(List<?> dataList) {
        excelMapper.writeExcel2DB((List<ExcelDataModel>) dataList);
    }
}
