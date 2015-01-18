package com.yiqin.shop.bean;

public class PrinterBase extends ProductBase {

	private static final long serialVersionUID = 1258461306038206987L;
	private String series;              // 系列
	private String model;               // 型号
	private String size;                // 尺寸
	private String weight;              // 重量
	private String color;               // 颜色
	private String paperSize;           // 纸张规格

	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPaperSize() {
		return paperSize;
	}
	public void setPaperSize(String paperSize) {
		this.paperSize = paperSize;
	}	
}
