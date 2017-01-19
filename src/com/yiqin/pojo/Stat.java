package com.yiqin.pojo;

/**
 * 统计实体类
 */
public class Stat implements java.io.Serializable {
	private static final long serialVersionUID = -4640304764140737947L;
	private StatId statId;
	private String zongjia;
	private String mingxi;
	
	public StatId getStatId() {
		return statId;
	}
	public void setStatId(StatId statId) {
		this.statId = statId;
	}
	public String getZongjia() {
		return zongjia;
	}
	public void setZongjia(String zongjia) {
		this.zongjia = zongjia;
	}
	public String getMingxi() {
		return mingxi;
	}
	public void setMingxi(String mingxi) {
		this.mingxi = mingxi;
	}
	
	
	
}
