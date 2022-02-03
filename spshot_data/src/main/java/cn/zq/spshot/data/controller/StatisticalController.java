package cn.zq.spshot.data.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.data.service.StatisticalService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 华韵流风
 * @ClassName StatisticalController
 * @Date 2021/12/19 13:15
 * @packageName cn.zq.spshot.data.controller
 * @Description TODO
 */
@RestController
//@CrossOrigin
@RequestMapping("/statistical")
@RefreshScope
public class StatisticalController {

    @Autowired
    private StatisticalService statisticalService;

    @Resource
    private HttpServletResponse response;


    /**
     * 获取运营数据
     *
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/getReport")
    public Result getReport() {
        try {
            return new Result(StatusCode.OK, true, "", getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 导出数据
     *
     * @return Result
     */
    @GetMapping("/exportReport")
    public Result exportReport() {
        try {

            Map<String, Object> map = getResult();
            String reportDate = (String) map.get("reportDate");
            Long todayNewUser = (Long) map.get("todayNewUser");
            Long totalUser = (Long) map.get("totalUser");
            Long thisWeekNewUser = (Long) map.get("thisWeekNewUser");
            Long thisMonthNewUser = (Long) map.get("thisMonthNewUser");
            Long todayIssureNumber = (Long) map.get("todayIssureNumber");
            Long todayPartNumber = (Long) map.get("todayPartNumber");
            Long thisWeekIssureNumber = (Long) map.get("thisWeekIssureNumber");
            Long thisWeekPartNumber = (Long) map.get("thisWeekPartNumber");
            Long thisMonthIssureNumber = (Long) map.get("thisMonthIssureNumber");
            Long thisMonthPartNumber = (Long) map.get("thisMonthPartNumber");
            Long todayFeedBackNumber = (Long) map.get("todayFeedBackNumber");
            Long todayDealNumber = (Long) map.get("todayDealNumber");
            Long thisWeekFeedBackNumber = (Long) map.get("thisWeekFeedBackNumber");
            Long thisWeekDealNumber = (Long) map.get("thisWeekDealNumber");
            Long thisMonthFeedBackNumber = (Long) map.get("thisMonthFeedBackNumber");
            Long thisMonthDealNumber = (Long) map.get("thisMonthDealNumber");
            List<Map<String, Object>> hotissure = (List<Map<String, Object>>) map.get("hotissure");

            //获取模板文件在项目部署中的虚拟路径  File.separator 分隔符 根据系统来决定是/还是\。
            String filePath = this.getClass().getClassLoader().getResource("templates"+File.separator+"report_template.xlsx").getPath();
            //基于模板在内存中创建一个excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            //读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);
            //第3行
            XSSFRow row = sheet.getRow(2);
            //日期
            row.getCell(5).setCellValue(reportDate);

            //用户数据统计
            //第5行
            row = sheet.getRow(4);
            //新增用户数
            row.getCell(5).setCellValue(todayNewUser);
            //总用户数
            row.getCell(7).setCellValue(totalUser);
            //第6行
            row = sheet.getRow(5);
            //本周新增用户
            row.getCell(5).setCellValue(thisWeekNewUser);
            //本月新增会员
            row.getCell(7).setCellValue(thisMonthNewUser);

            //投票数据统计
            //第8行
            row = sheet.getRow(7);
            //今日发布投票数
            row.getCell(5).setCellValue(todayIssureNumber);
            //今日参与投票数
            row.getCell(7).setCellValue(todayPartNumber);
            //第9行
            row = sheet.getRow(8);
            //本周发布投票数
            row.getCell(5).setCellValue(thisWeekIssureNumber);
            //本周参与投票数
            row.getCell(7).setCellValue(thisWeekPartNumber);
            //第10行
            row = sheet.getRow(9);
            //本月发布投票数
            row.getCell(5).setCellValue(thisMonthIssureNumber);
            //本月参与投票数
            row.getCell(7).setCellValue(thisMonthPartNumber);

            //用户反馈统计
            //第12行
            row = sheet.getRow(11);
            //今日用户反馈
            row.getCell(5).setCellValue(todayFeedBackNumber);
            //今日处理反馈
            row.getCell(7).setCellValue(todayDealNumber);
            //第13行
            row = sheet.getRow(12);
            //本周用户反馈
            row.getCell(5).setCellValue(thisWeekFeedBackNumber);
            //本周处理反馈
            row.getCell(7).setCellValue(thisWeekDealNumber);
            //第14行
            row = sheet.getRow(13);
            //本月用户反馈
            row.getCell(5).setCellValue(thisMonthFeedBackNumber);
            //本月处理反馈
            row.getCell(7).setCellValue(thisMonthDealNumber);

            //热门套餐
            int rowNum = 16;
            for (Map<String, Object> issure : hotissure) {
                //第 rowNum + 1 行
                row = sheet.getRow(rowNum++);
                //发布者
                row.getCell(4).setCellValue((String) issure.get("username"));
                //标题
                row.getCell(5).setCellValue((String) issure.get("title"));
                //参与人数
                row.getCell(7).setCellValue((Integer) issure.get("currperson"));
            }
            //使用输出流进行表格下载，基于浏览器作为客户端下载
            OutputStream out = response.getOutputStream();
            //告诉浏览器是什么类型的文件 代表的是Excel文件类型
            response.setContentType("application/vnd.ms-excel");
            //设置响应头信息 指定以附件形式进行下载
            response.setHeader("content-Disposition", "attachment;filename=report_" + reportDate + ".xlsx");
            excel.write(out);
            out.flush();
            out.close();
            excel.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "导出错误");
    }


    /**
     * 获取数据
     *
     * @return Map<String, Object>
     */
    private Map<String, Object> getResult() {
        Map<String, Object> map = new HashMap<>();
        map.put("reportDate", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        map.put("todayNewUser", statisticalService.getTodayNewUser());
        map.put("totalUser", statisticalService.getTotalUser());
        map.put("thisWeekNewUser", statisticalService.getThisWeekNewUser());
        map.put("thisMonthNewUser", statisticalService.getThisMonthNewUser());
        map.put("todayIssureNumber", statisticalService.getTodayIssureNumber());
        map.put("todayPartNumber", statisticalService.getTodayPartNumber());
        map.put("thisWeekIssureNumber", statisticalService.getThisWeekIssureNumber());
        map.put("thisWeekPartNumber", statisticalService.getThisWeekPartNumber());
        map.put("thisMonthIssureNumber", statisticalService.getThisMonthIssureNumber());
        map.put("thisMonthPartNumber", statisticalService.getThisMonthPartNumber());
        map.put("todayFeedBackNumber", statisticalService.getTodayFeedBackNumber());
        map.put("todayDealNumber", statisticalService.getTodayDealNumber());
        map.put("thisWeekFeedBackNumber", statisticalService.getThisWeekFeedBackNumber());
        map.put("thisWeekDealNumber", statisticalService.getThisWeekDealNumber());
        map.put("thisMonthFeedBackNumber", statisticalService.getThisMonthFeedBackNumber());
        map.put("thisMonthDealNumber", statisticalService.getThisMonthDealNumber());
        map.put("hotissure", statisticalService.getHotIssure());
        return map;
    }

}
