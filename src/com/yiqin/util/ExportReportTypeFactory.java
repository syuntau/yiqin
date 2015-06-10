package com.yiqin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * export column
 * 
 * @author liujun
 *
 */
public class ExportReportTypeFactory {

	public final static Map<String, String> columnNameMap = new HashMap<String, String>();
	public final static List<String> columnKeyList = new ArrayList<String>();

	static{
		//store shared excel attributes when export
		columnNameMap.clear();
		columnKeyList.clear();
		String columnNames= Configuration.getProperty("export.excel.header.key.name");
		String[] columnNameArray = columnNames.split(",");
		for (int i = 0; i < columnNameArray.length; i++) {
			//exportAttrs[i] form like "key:value"
			String[] tempColumnNameArray = columnNameArray[i].split(":");
			columnNameMap.put(tempColumnNameArray[0], tempColumnNameArray[1]);
		}

		try {
			String defaultColumnKeys = Configuration.getProperty("export.order.column.default");
			if(Util.isNotEmpty(defaultColumnKeys)){
				String[] columnKeysArray = defaultColumnKeys.split(",");
				for (int i = 0; i < columnKeysArray.length; i++) {
					columnKeyList.add(columnKeysArray[i]);
				}
			}
		} catch (Throwable t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
		}
	}
}
