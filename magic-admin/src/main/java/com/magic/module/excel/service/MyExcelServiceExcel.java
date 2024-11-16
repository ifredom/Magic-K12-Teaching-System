package com.magic.module.excel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.magic.framework.utils.easyexcel.ExcelBusinessServiceDetail;
import com.magic.module.excel.entity.ExcelDataModel;

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
 * @date 2024/10/10 15:57
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */

public interface MyExcelServiceExcel extends IService<ExcelDataModel>, ExcelBusinessServiceDetail {
    List<ExcelDataModel> getAllExcelData();
}
