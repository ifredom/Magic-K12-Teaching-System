package com.magic.framework.utils;

import cn.hutool.core.util.PageUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用: 自定义 分页工具类
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/10/11 01:43
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
@Data
@NoArgsConstructor
public class PageUtils implements Serializable {
    private static final long serialVersionUID = 1L;

    //每页记录数
    private int pageSize;
    //当前页数
    private int pageNum;
    //总页数
    private int pages;
    //总记录数
    private int total;
    //列表数据
    private List<?> list;

    /**
     * 分页
     * @param list        列表数据
     * @param total       总记录数
     * @param pageSize    每页记录数
     * @param pageNum     当前页数
     */
    public PageUtils(List<?> list, int total, int pageSize, int pageNum) {
        this.list = list;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.pages = (int)Math.ceil((double)total/pageSize);
    }

    /**
     * @return
     * @description 内存分页，hutool工具类相关
     * @Param
     * @author tzh
     * @date
     */
    public static PageUtils getPageDto(List<?> list, int pageNum, int pageSize) {
        PageUtils pageDtoUtil = new PageUtils();
        //总页数
        int pages = PageUtil.totalPage(list.size(), pageSize);
        //开始位置和结束位置
        int[] startEnd = PageUtil.transToStartEnd(pageNum - 1, pageSize);
        //分页后的结果集
        List<?> pageList = null;
        if (startEnd[1] < list.size()) {
            pageList = list.subList(startEnd[0], startEnd[1]);
        } else {
            pageList = list.subList(startEnd[0], list.size());
        }

        pageDtoUtil.setPageSize(pageSize);//页大小
        pageDtoUtil.setPageNum(pageNum);//当前页码
        pageDtoUtil.setPages(pages);//总页数
        pageDtoUtil.setTotal(list.size());//数据总数
        pageDtoUtil.setList(pageList);//分页结果集

        return pageDtoUtil;
    }

}
