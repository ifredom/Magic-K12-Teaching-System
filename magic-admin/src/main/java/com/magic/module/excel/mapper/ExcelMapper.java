package com.magic.module.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.magic.module.excel.entity.ExcelDataModel;
import org.apache.ibatis.annotations.Mapper;

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
 * @date 2024/10/10 12:20
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
@Mapper
public interface ExcelMapper extends BaseMapper<ExcelDataModel> {

    List<ExcelDataModel> selectAllData();

    // 尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    void writeExcel2DB(List<ExcelDataModel> dataModelList);
}
