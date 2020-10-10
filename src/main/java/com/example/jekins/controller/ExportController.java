package com.example.jekins.controller;

import com.example.jekins.entity.User;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导出Excel控制器
 */
@Log4j2
@RestController
public class ExportController {

    /**
     * 导出文件excel文件
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/export")
    public void exportExcel(HttpServletResponse response) throws IOException{
        log.info("导出文件excel文件");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
        //创建工作簿对象
        Workbook wb = new XSSFWorkbook();
        //抽出行标题字段
        String[] title = {"序号","姓名","年龄","备注"};
        //设置sheet名称，并创建新的sheet对象
        String sheetName = "商户资质审核记录";
        //获取sheet对象
        Sheet sheet = getSheet(wb,sheetName,title);
        //把从数据库中取得的数据一一写入excel文件中
        List list = new ArrayList();
        for(int i = 0;i < 5; i++){
            User user = new User();
            user.setName("张三"+i);
            user.setAge("16");
//            user.setRemark("备注"+i);
            list.add(user);
        }
        fillData(sheet,list,sdf);

        //设置文件名
        String fileName = sdf1.format(new Date()) + sheetName + ".xlsx";

        OutputStream fileOut = null;
        try{
            //通过浏览器下载的方式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
            fileOut = response.getOutputStream();
            wb.write(fileOut);
        }catch(Exception e){
            log.error("导出文件失败！",e);
            e.printStackTrace();
        }finally {
            if(fileOut != null){
                fileOut.close();
            }
        }
    }



    /**
     * 把从数据库中取得的数据一一写入excel文件中
     * @param sheet
     * @param list
     * @param sdf
     */
    public void fillData(Sheet sheet,List<User> list,SimpleDateFormat sdf){
        Row row = null;
        for(int i=0; i<list.size(); i++){
            //创建list.size()行数据
            row = sheet.createRow(i + 1);
            //把值一一写进单元格里
            //设置第一列为自动递增的序号
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i).getName()!=null ? list.get(i).getName() : "");
            row.createCell(2).setCellValue(list.get(i).getAge() != null ? list.get(i).getAge() : "");
//            row.createCell(3).setCellValue(list.get(i).getRemark() != null ? list.get(i).getRemark() : "");
        }
    }


    /**
     * 获取Sheet 导出xlsx文件
     * @param wb 工作簿对象
     * @param sheetName sheet名
     * @param title sheet标题数组
     * @return Sheet
     */
    public static Sheet getSheet(Workbook wb, String sheetName, String[] title) {
        Sheet sheet = wb.createSheet(sheetName);
        //获取表头行
        Row titleRow = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        // 设置字体
        Font headfont = wb.createFont();
        headfont.setFontHeightInPoints((short) 12);// 字体大小
        headfont.setBoldweight(Font.BOLDWEIGHT_BOLD);// 加粗
        sheet.createFreezePane(0, 1);// 第一行冻结
        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setFont(headfont);

        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        // 填充模式
        style.setFillPattern(CellStyle.FINE_DOTS);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft((short) 1);// 边框的大小
        style.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
        style.setBorderRight((short) 1);// 边框的大小
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderBottom((short) 1);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop((short) 1);

        Cell cell = null;
        //把已经写好的标题行写入excel文件中
        for(int i=0; i<title.length; i++){
            cell = titleRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //设置单元格宽度自适应，在此基础上把宽度调至1.5倍 for (int i = 0; i < title.length; i++) {
        for (int i = 0; i < title.length; i++) {
//            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }
        return sheet;
    }

}
