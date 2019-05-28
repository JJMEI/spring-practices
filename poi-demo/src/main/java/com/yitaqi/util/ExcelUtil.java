package com.yitaqi.util;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author meijunjie
 * @date 2019/5/27
 */
public class ExcelUtil {
    public static void exportExcel(List<List<String>> data, List<String> headers, String sheetName, HttpServletResponse response){

        if(data!=null && data.size() > 0) {
            //获取分页指标
            //数据总数
            int total = data.size();
            //每个sheet中数据数量
            int mus = 50000;
            //sheet数量
            int avg = (total / mus) + 1;

            //获取工作簿对象
            //第一步：创建Workbook，对应一个excel文件
            //每次缓存100条到内存，其余的写到磁盘中，避免内存溢出
            SXSSFWorkbook wb = new SXSSFWorkbook(100);
            //样式

            CellStyle styleContent = createHeadCellStyle(wb);
            CellStyle style = createDataStyle(wb);
            CellStyle style_date = createDateStyle(wb);


            //第二步：在workbook添加sheet
            for (int m = 0; m < avg; m++) {
                SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(sheetName + (m + 1));
                int sheet_row = 0;
                //设置列宽-（从0开始）
                for (int i = 0; i < headers.size(); i++) {
                    sheet.setColumnWidth(i, 8000);
                }

                SXSSFRow sheet_row1 = (SXSSFRow) sheet.createRow(sheet_row);
                //设置行高
                sheet_row1.setHeight((short) 400);
                for (int i = 0; i < headers.size(); i++) {
                    SXSSFCell sheetCell11 = (SXSSFCell) sheet_row1.createCell(i);
                    sheetCell11.setCellValue(headers.get(i));
                    sheetCell11.setCellStyle((styleContent));
                }
                sheet_row = sheet_row + 1;


                SXSSFRow sheet_rows = null;
                SXSSFCell sheetcell = null;
                for (int n = mus * m; n < mus * (m + 1); n++) {
                    if (n < total) {
                        sheet_rows = (SXSSFRow) sheet.createRow(sheet_row);
                        //设置行高
                        sheet_rows.setHeight((short) 400);


                        for (int i = 0; i < data.get(n).size(); i++) {
                            sheetcell = (SXSSFCell) sheet_rows.createCell(i);
                            sheetcell.setCellValue(String.valueOf(data.get(n).get(i)));
                            sheetcell.setCellStyle(style);
                        }
                        sheet_row = sheet_row + 1;
                        sheet_rows = null;
                        sheetcell = null;
                    }
                }
            }

            //第四步：保存
            try {
                String fileName = sheetName + ".xlsx";
                response.setContentType("application/ms-excel");
                response.setHeader("Content-Disposition",
                        "attachment;filename="+new String(fileName.getBytes("gb2312"),"iso-8859-1"));
                wb.write(response.getOutputStream());
                wb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * 创建表头样式
     * @param workbook
     * @return
     */
    public static CellStyle createHeadCellStyle(SXSSFWorkbook workbook){
        //创建样式1（列标识样式）
        CellStyle styleContent = workbook.createCellStyle();
        //背景色
        styleContent.setFillBackgroundColor((short)13);
        //前景色
        styleContent.setFillForegroundColor((short)15);
        styleContent.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //水平居中
        styleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        styleContent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //上边框
        styleContent.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //右边框
        styleContent.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //下边框
        styleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //左边框
        styleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Font fontContent = workbook.createFont();
        fontContent.setFontName("宋体");
        fontContent.setBold(true);
        //字体大小
        fontContent.setFontHeightInPoints((short)11);
        styleContent.setFont(fontContent);
        //自动换行
        styleContent.setWrapText(true);
        return styleContent;
    }


    /**
     * 创建数据样式
     * @param workbook
     * @return
     */
    public static CellStyle createDataStyle(SXSSFWorkbook workbook){

        //创建样式2(数据项样式)
        CellStyle style= workbook.createCellStyle();
        //背景色
        style.setFillBackgroundColor(HSSFColor.WHITE.index);
        //前景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //水平居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //上边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //右边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //下边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //左边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Font font = workbook.createFont();
        font.setFontName("宋体");
        //字体大小
        font.setFontHeightInPoints((short)11);
        style.setFont(font);
        //自动换行
        style.setWrapText(true);
        return style;
    }


    /**
     * 创建日期样式
     * @param workbook
     * @return
     */
    public static CellStyle createDateStyle(SXSSFWorkbook workbook){
        //创建样式3(数据项样式-日期)
        CellStyle style_date = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style_date.setDataFormat(format.getFormat("yyyy-MM-dd"));
        //背景色
        style_date.setFillBackgroundColor(HSSFColor.WHITE.index);
        //前景色
        style_date.setFillForegroundColor(HSSFColor.WHITE.index);
        style_date.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //水平居中
        style_date.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        style_date.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //上边框
        style_date.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //右边框
        style_date.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //下边框
        style_date.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //左边框
        style_date.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Font font_date = workbook.createFont();
        font_date.setFontName("宋体");
        //字体大小
        font_date.setFontHeightInPoints((short)11);
        style_date.setFont(font_date);
        //自动换行
        style_date.setWrapText(true);
        return style_date;
    }
}

