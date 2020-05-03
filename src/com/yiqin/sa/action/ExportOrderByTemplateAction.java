package com.yiqin.sa.action;

import com.yiqin.util.ExcelUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.User;
import com.yiqin.service.ShoppingManager;
import com.yiqin.service.UserManager;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.Amount2RMB;
import com.yiqin.util.ExcelExportOrder;
import com.yiqin.util.ExcelExportOrderByXss;
import com.yiqin.util.Util;

/**
 * 选定模板，导出订单信息
 */
public class ExportOrderByTemplateAction extends ActionSupport {

    private static final long serialVersionUID = 5617607939328804382L;
    // 订单Id列表
//	private String ids;
    // 导出类型
//	private String type;

    private UserManager userManager;

    private ShoppingManager shoppingManager;


    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public ShoppingManager getShoppingManager() {
        return shoppingManager;
    }

    public void setShoppingManager(ShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
    }

    public String exportOne() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String ids = request.getParameter("ids");
            System.out.println("###### ids : " + ids);
            if (Util.isEmpty(ids)) {
                System.out.println("###### ids null, return");
                return null;
            }

            List<OrderView> ovList = shoppingManager.findOrderList(ids);
            if (Util.isEmpty(ovList)) {
                System.out.println("###### no order list, return");
                return null;
            }

            System.out.println("###### Excel Export Order start ...");
            String tplFile = getText("tpl.file.11");
            ExcelExportOrder excel = new ExcelExportOrderByXss(tplFile, 0);
            excel.createFooter();
            System.out.println("###### createFooter over ...");

            OrderView order = ovList.get(0);
            User user = userManager.findUserByUserId(order.getUserId());
            String customer =
                    Util.isNotEmpty(user.getCompany()) ? user.getCompany() : order.getUserId();
            // 填充表头
            Map<String, String> map = new HashMap<String, String>();
            map.put("#ORDER_ID#", String.valueOf(order.getId()));
            map.put("#CUSTOMER_NAME#", customer);
            map.put("#ADDRESS#", order.getAddress());
            map.put("#ORDER_DATE#", order.getCrateDate());
            map.put("#CONTACT#", order.getName());
            map.put("#TEL#", order.getMobile());
            excel.replaceExcelData(map);
            System.out.println("###### excel header set over ...");

            List<Cart> cartList = order.getProductList();
            if (Util.isNotEmpty(order.getOrderNote())) {
                Cart cart = new Cart();
                cart.setCount(1);
                cart.setProductName(order.getOrderNote());
                cart.setZhekouPrice(order.getBeizhuzongjia());
                cartList.add(cart);
            }
            int startRow = Integer.parseInt(getText("tpl.start.row.11")); //起始行
            int rows = cartList.size(); //插入行数
            if (rows > 1) {
                excel.insertRows(startRow, rows - 1);
                System.out.println(
                        "###### excel start row : " + startRow + " insert : " + (rows - 1)
                                + " rows over ...");
            }

            // 获取单元格样式
            XSSFRow rowCellStyle = excel.getXssSheet().getRow(startRow);
            XSSFCellStyle[] styleArr = new XSSFCellStyle[5];
            for (int i = 0; i < 5; i++) {
                styleArr[i] = rowCellStyle.getCell(i).getCellStyle();
            }
            short height = rowCellStyle.getHeight();

            int rowId = startRow;
            int idx = 1;
            float totalPrice = 0;
            for (Cart cart : cartList) {
                XSSFRow row = excel.getXssSheet().getRow(rowId++);
                row.setHeight(height);
                int j = 0;
                XSSFCell cell = row.getCell(j);
                cell.setCellValue(idx++);
                cell.setCellStyle(styleArr[j]);

                cell = row.getCell(++j);
                cell.setCellValue(cart.getProductName().replaceAll("<br>", "\n"));
                cell.setCellStyle(styleArr[j]);

                String zhekouPrice =
                        Util.isEmpty(cart.getZhekouPrice()) ? "0" : cart.getZhekouPrice();
                cell = row.getCell(++j);
                cell.setCellValue(zhekouPrice);
                cell.setCellStyle(styleArr[j]);

                cell = row.getCell(++j);
                cell.setCellValue(cart.getCount());
                cell.setCellStyle(styleArr[j]);

                cell = row.getCell(++j);
                float subTotal = cart.getCount() * Float.valueOf(zhekouPrice);
                cell.setCellValue(new DecimalFormat("########0.00").format(subTotal)); // 需要小计
                cell.setCellStyle(styleArr[j]);
                totalPrice += subTotal;
            }
            map = new HashMap<String, String>();
            map.put("#total#", new DecimalFormat("########0.00").format(totalPrice));
            excel.replaceFooterData(map);
            System.out.println("###### excel order set over ...");

            String fileName = "order_" + ids + ".xlsx";
            excel.downloadExcel(response, fileName);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String exportOrderTpl() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String ids = request.getParameter("ids");
            String type = request.getParameter("type");
            System.out.println("###### ids : " + ids + ", type : " + type);
            if (Util.isEmpty(ids) || Util.isEmpty(type)) {
                System.out.println("###### ids or type null, return");
                return null;
            }
            if ("41".equals(type)) {
                return exportOrderTplTax(response, request, ids, type);
            }

            List<OrderView> ovList = shoppingManager.findOrderList(ids);
            if (Util.isEmpty(ovList)) {
                System.out.println("###### no order list, return");
                return null;
            }

            Set<String> userIdSet = new HashSet<String>();
            List<Cart> orderMergeList = new ArrayList<Cart>();
            for (OrderView ov : ovList) {
                userIdSet.add(ov.getUserId());
                for (Cart cart : ov.getProductList()) {
                    orderMergeList.add(cart);
                }
                if (Util.isNotEmpty(ov.getOrderNote())) {
                    Cart cart = new Cart();
                    cart.setCount(1);
                    cart.setProductName(ov.getOrderNote());
                    cart.setZhekouPrice(ov.getBeizhuzongjia());
                    orderMergeList.add(cart);
                }
            }
            if (userIdSet.size() > 1) {
                System.out.println("###### userId non-unique, return");
                return null;
            }

            System.out.println("###### Excel Export Order start ...");
            String tplFile = getText("tpl.file." + type);
            ExcelExportOrder excel = new ExcelExportOrderByXss(tplFile, 0);
            excel.createFooter();
            System.out.println("###### createFooter over ...");

            OrderView order = ovList.get(0);
            String userId = order.getUserId();
            User user = userManager.findUserByUserId(userId);
            String customer = Util.isNotEmpty(user.getCompany()) ? user.getCompany() : userId;
            // 填充表头
            Map<String, String> map = new HashMap<String, String>();
            map.put("#CUSTOMER_NAME#", customer);
            map.put("#ADDRESS#", order.getAddress());
            map.put("#UPDATE_DATE#", order.getUpdateDate().substring(0, 10));
            map.put("#TEL#", order.getMobile());
            excel.replaceExcelData(map);
            System.out.println("###### excel header set over ...");

            int startRow = Integer.parseInt(getText("tpl.start.row." + type)); //起始行
            int rows = orderMergeList.size(); //插入行数
            if (rows > 1) {
                excel.insertRows(startRow, rows - 1);
                System.out.println(
                        "###### excel start row : " + startRow + " insert : " + (rows - 1)
                                + " rows over ...");
            }

            // 获取单元格样式
            XSSFRow rowCellStyle = excel.getXssSheet().getRow(startRow);
            XSSFCellStyle[] styleArr = new XSSFCellStyle[5];
            for (int i = 0; i < 5; i++) {
                styleArr[i] = rowCellStyle.getCell(i).getCellStyle();
            }
            short height = rowCellStyle.getHeight();

            int rowId = startRow;
            int idx = 1;
            float totalPrice = 0;
            int totalCnt = 0;
            String rmb = null;
            for (Cart cart : orderMergeList) {
                XSSFRow row = excel.getXssSheet().getRow(rowId++);
                row.setHeight(height);
                int j = 0;
                XSSFCell cell = row.getCell(j);
                cell.setCellValue(idx++);
                cell.setCellStyle(styleArr[j]);

                cell = row.getCell(++j);
                cell.setCellValue(cart.getProductName().replaceAll("<br>", "\n"));
                cell.setCellStyle(styleArr[j]);

                cell = row.getCell(++j);
                String zhekouPrice =
                        Util.isEmpty(cart.getZhekouPrice()) ? "0" : cart.getZhekouPrice();
                cell.setCellValue(zhekouPrice);
                cell.setCellStyle(styleArr[j]);

                cell = row.getCell(++j);
                cell.setCellValue(cart.getCount());
                cell.setCellStyle(styleArr[j]);
                totalCnt += cart.getCount();

                cell = row.getCell(++j);
                float subTotal = cart.getCount() * Float.valueOf(zhekouPrice);
                cell.setCellValue(new DecimalFormat("########0.00").format(subTotal)); // 需要小计
                cell.setCellStyle(styleArr[j]);
                totalPrice += subTotal;
            }
            map = new HashMap<String, String>();
            map.put("#total#", new DecimalFormat("########0.00").format(totalPrice));
            map.put("#totalCnt#", String.valueOf(totalCnt));
            rmb = Amount2RMB.convert(map.get("#total#"));
            System.out.println("###### rmb : " + rmb);
            map.put("#rmb#", rmb);
            excel.replaceFooterData(map);
            System.out.println("###### excel order set over ...");

            String fileName = userId + "_" + Util.format(new Date()) + "_by_" + getText(
                    "tpl.name.prefix." + type) + ".xlsx";
            excel.downloadExcel(response, fileName);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String exportOrderTplTax(HttpServletResponse response, HttpServletRequest request,
            String ids, String type) throws Exception {
        System.out.println("###### ids : " + ids + ", type : " + type);
        if (Util.isEmpty(ids) || Util.isEmpty(type)) {
            System.out.println("###### ids or type null, return");
            return null;
        }

        List<OrderView> ovList = shoppingManager.findOrderList(ids);
        if (Util.isEmpty(ovList)) {
            System.out.println("###### no order list, return");
            return null;
        }

        Set<String> userIdSet = new HashSet<String>();
        List<Cart> orderMergeList = new ArrayList<Cart>();
        for (OrderView ov : ovList) {
            userIdSet.add(ov.getUserId());
            for (Cart cart : ov.getProductList()) {
                orderMergeList.add(cart);
            }
            if (Util.isNotEmpty(ov.getOrderNote())) {
                Cart cart = new Cart();
                cart.setCount(1);
                cart.setProductName(ov.getOrderNote());
                cart.setZhekouPrice(ov.getBeizhuzongjia());
                orderMergeList.add(cart);
            }
        }
        if (userIdSet.size() > 1) {
            System.out.println("###### userId non-unique, return");
            return null;
        }

        System.out.println("###### Excel Export Order start ...");
        String tplFile = getText("tpl.file." + type);
        ExcelExportOrder excel = new ExcelExportOrderByXss(tplFile, 0);
        excel.createFooter();
        System.out.println("###### createFooter over ...");

        OrderView order = ovList.get(0);
        String userId = order.getUserId();
        User user = userManager.findUserByUserId(userId);
        String customer = Util.isNotEmpty(user.getCompany()) ? user.getCompany() : userId;

        int startRow = Integer.parseInt(getText("tpl.start.row." + type)); //起始行
        int rows = orderMergeList.size(); //插入行数
        if (rows > 1) {
            excel.insertRows(startRow, rows - 1);
            System.out.println("###### excel start row : " + startRow + " insert : " + (rows - 1)
                    + " rows over ...");
        }

        // 获取单元格样式
        XSSFRow rowCellStyle = excel.getXssSheet().getRow(startRow);
        XSSFCellStyle[] styleArr = new XSSFCellStyle[21];
        for (int i = 0; i < 21; i++) {
            styleArr[i] = rowCellStyle.getCell(i).getCellStyle();
        }

        short height = rowCellStyle.getHeight();

        double taxRate = Double.parseDouble(getText("tpl.tax.rate")); //税率

        int rowId = startRow;
        int idx = 1;
        for (Cart cart : orderMergeList) {

//            Row newRow=excel.getXssSheet().createRow(rowId++);//新建一行
//            ExcelUtil.copyRow(excel.getXssWb(),templateRow,newRow,true);
//            newRow.setHeight(height);

            XSSFRow row = excel.getXssSheet().getRow(rowId++);

            XSSFCell cell = row.getCell(0);
            cell.setCellValue(idx++);
            cell.setCellStyle(styleArr[0]);

            cell = row.getCell(1);
            cell.setCellValue(cart.getProductName().replaceAll("<br>", "\n"));
            cell.setCellStyle(styleArr[1]);

            cell = row.getCell(4);
            cell.setCellValue(cart.getCount());
            cell.setCellStyle(styleArr[4]);

            cell = row.getCell(5);
            BigDecimal p = null;
            try {
//                =ROUND(M11*E11,2)
                p = new BigDecimal(cart.getPrice()).multiply(new BigDecimal(cart.getCount()))
                        .setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(p.toString());
                cell.setCellStyle(styleArr[5]);

            } catch (Exception e) {
                System.out.println();
            }

            cell = row.getCell(6);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(taxRate);
            cell.setCellStyle(styleArr[6]);

            try {
                cell = row.getCell(9);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(p.divide(new BigDecimal(1+taxRate), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(taxRate))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                cell.setCellStyle(styleArr[9]);
            } catch (Exception e) {
                System.out.println();
            }

            cell = row.getCell(12);
            cell.setCellValue(cart.getPrice());
            cell.setCellStyle(styleArr[12]);

            cell = row.getCell(13);
            cell.setCellValue(1);
            cell.setCellStyle(styleArr[13]);

            cell = row.getCell(14);
            cell.setCellValue("30.0");
            cell.setCellStyle(styleArr[14]);

            cell = row.getCell(17);
            cell.setCellValue("0");
            cell.setCellStyle(styleArr[17]);

            cell = row.getCell(20);
            cell.setCellValue("0");
            cell.setCellStyle(styleArr[20]);
        }

        System.out.println("###### excel order set over ...");

        String fileName =
                userId + "_" + Util.format(new Date()) + "_by_" + getText("tpl.name.prefix." + type)
                        + ".xlsx";
        excel.downloadExcel(response, fileName);
        return null;
    }
}
