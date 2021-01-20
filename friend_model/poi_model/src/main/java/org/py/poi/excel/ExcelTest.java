package org.py.poi.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelTest {

    @Test
    public void testDisplayMergeCell() throws Exception {
        String filePath = "/Users/pengyue.du/Code/Meijia/Work01/learn_test/friend_model/poi_model/doc/test.xlsx";
        FileInputStream in = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            int numMergedRegions = sheet.getNumMergedRegions();
            List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
            Iterator<Row> rowIterator = sheet.rowIterator();
            int idx = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();
                StringBuffer sb = new StringBuffer(String.format("%s\t firNum:%s \t last:%s \t", ++idx, firstCellNum, lastCellNum));
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    String value = cell.getStringCellValue();
//                    sb.append(String.format("%s \t", value));
//                }
                for (int i = 0; i <= lastCellNum; i++) {
                    if (ImportHj.isMergedRegion(sheet, idx-1, i)) {
                        String value = ImportHj.getMergedRegionValue(sheet, idx - 1, i);
                        if (StringUtils.isNotBlank(value)) {
                            sb.append(String.format("%-30s \t", value));
                        }else {
                            sb.append(String.format("%-30s \t", "n"));
                        }
                    }else {
                        Cell cell = row.getCell(i);
                        String value = String.format("%-30s \t", "-");
                        if (cell != null) {
                            value = String.format("%-30s \t", cell.getStringCellValue());
                        }else {
                            int a = 1;
                        }
                        sb.append(String.format("%s \t", value));
                    }
                }
                log.info(sb.toString());
            }
        }
    }

    @Test
    public void testMergeCell() throws Exception {
        String filePath = "/Users/pengyue.du/Code/Meijia/Work01/learn_test/friend_model/poi_model/doc/test.xlsx";
        FileInputStream in = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            int numMergedRegions = sheet.getNumMergedRegions();
            List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
            Iterator<Row> rowIterator = sheet.rowIterator();
            int idx = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();
                StringBuffer sb = new StringBuffer(String.format("%s\t firNum:%s \t last:%s \t", ++idx, firstCellNum, lastCellNum));
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    String value = cell.getStringCellValue();
//                    sb.append(String.format("%s \t", value));
//                }
//                for(int i=firstCellNum, int i)
                log.info(sb.toString());
            }
        }
    }


    @Test
    public void helloTest() throws Exception {
        String filePath = "/Users/pengyue.du/Code/Meijia/Work01/learn_test/friend_model/poi_model/doc/test.xlsx";
        FileInputStream in = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        readExcel(workbook);
        in.close();
    }


    /**
     * 读取 Excel 数据并处理
     * @param workbook 完整的 Workbook 对象
     */
    public static void readExcel(Workbook workbook) {
        for (int m = 0; m < 1; m++) {
            System.out.println("*******************************************************************************************************");
            Sheet sheet = workbook.getSheetAt(m);
            //获取总行数
            int rows = sheet.getPhysicalNumberOfRows();
            //去除表头，从第 1 行开始打印
            for (int i = 0; i < rows; i++) {
                System.out.print(i + "\t");
                Row row = sheet.getRow(i);
                if(row == null) {
                    System.out.println();
                    continue;
                }
                //获取总列数
                int cols = row.getPhysicalNumberOfCells();
                for (int j = 0; j < cols; j++) {
                    Cell cell = row.getCell(j);
                    System.out.print(row.getCell(j) + "\t");
                }
                System.out.println();
            }
        }
    }
}
