package org.carl.canal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings({ "deprecation" })
public class RedisKeys {
	private static final String SA = "statistics.attacks";
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat SD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//statistics.attacks    100 apt 200 ddos 300 ips 500 web
	//statistics.attacks.20151103   10 00  11 01  
	/**
	 * @param: current_time
	 *             当前时间(datetime类型)
	 * @return String 返回当前时间攻击次数分布的redis关键字
	 * @throws ParseException
	 */
	public static String[] statistics_attacks_day_hour(String current_time) throws ParseException {
		String[] objs = new String[2];
		java.util.Date time = SD.parse(current_time);
		objs[0] = SA +"."+ SDF.format(time);
		objs[1] = String.valueOf(time.getHours());
		objs[1]=objs[1].length()==1?("0"+objs[1]):objs[1];
		return objs;
	}

	/**
	 * @show 源ip地址统计攻击总次数
	 * @param sourceip
	 *            String sourceip 源IP地址
	 * @return
	 */
	public static String[] statistics_attacks_sourceip(String sourceip) {
		String[] objs = new String[2];
		objs[0] = SA + ".sourceip";
		objs[1] = sourceip;
		return objs;
	}

	/**
	 * @show 目标ip地址统计攻击总次数
	 * @param sourceip
	 *            String targetip 源IP地址
	 * @return
	 */
	public static String[] statistics_attacks_targetip(String targetip) {
		String[] objs = new String[2];
		objs[0] = SA + ".targetip";
		objs[1] = targetip;
		return objs;
	}

	/**
	 * 
	 * @param table
	 *            String 统计(ddos, webd, ips, apt)4种类型攻击总次数
	 * @return
	 */
	public static String[] statistics_attacks_type(String table) {
		// #table in [APT_DETECTION, IPS_LOG, WEB_DEFEND, DDOS_ATTACK]
		String[] objs = new String[2];
		objs[0] = SA+".type";
		objs[1]=getTypeByTable(table);
		return objs;
	}
	/**
	 * @show 根据表类型生成Redis key
	 * @param table
	 *            String 统计(ddos, webd, ips, apt)4种类型攻击总次数
	 * @return
	 */
	public static String[] statistics_attacks_count(String table) {
		String objs[]=new String[2];
		// #table in [APT_DETECTION, IPS_LOG, WEB_DEFEND, DDOS_ATTACK]
		objs[0]=SA;
		objs[1]=getTypeByTable(table);
		return objs;
	}
	
	private static String getTypeByTable(String table){
		String obj=null;
		switch (table.toUpperCase()) {
		case "APT_DETECTION":
			obj = "apt";
			break;
		case "IPS_LOG":
			obj = "ips";
			break;
		case "WEB_DEFEND":
			obj = "web";
			break;
		case "DDOS_ATTACK":
			obj = "ddos";
			break;
		default:
			break;
		}
		return obj;
	}

	public static void main(String[] args) throws ParseException {
		String objs[] = statistics_attacks_day_hour("2015-11-03 2:40:20");
//		String objs[] = statistics_attacks_type("WEB_DEFEND");
		System.out.println(objs[0] + "\t" + objs[1]);
	}
}
