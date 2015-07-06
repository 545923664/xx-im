package com.jzwl.common.pom;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PomUtil {
	public static void main(String[] args) throws IOException {
		File pFile = new File("E:\\workspace\\mynew\\WebContent\\WEB-INF\\lib");
		//File pFile = new File("E:/pp/AmssyErp/WebRoot/WEB-INF/lib");
		File[] files = pFile.listFiles();
		StringBuffer sb = new StringBuffer();
		StringBuffer fileStr = new StringBuffer();
		String fileName = null;
		boolean flag = false;
		int t =0;
		for (File file : files) {
			if (file.getName().endsWith(".jar")) {
				t++; 
				fileName=file.getName().replace(".jar", "");
				fileStr.append(fileName).append("\n");
				String[] str = fileName.split("-");
				sb.append("<dependency>");
				sb.append("\n");
				for (int i = 0; i < str.length; i++) {
					if(0==i){
					  flag = false;
					}
					if(deal(str[i])){
						sb.append("\t");
						sb.append("<groupId>").append(str[0]).append("</groupId>");
						sb.append("\t");
						sb.append("\n");
						sb.append("\t");
						sb.append("<artifactId>").append(fileName.substring(0,fileName.indexOf(str[i])-1)).append("</artifactId>");
						sb.append("\t");
						sb.append("\n");
						sb.append("\t");
						sb.append("<version>").append(str[i]).append("</version>");
						sb.append("\t");
						sb.append("\n");
						flag = true;
					}
				}
				if(!flag){
					sb.append("\t");
					sb.append("<groupId>").append(str[0]).append("</groupId>");
					sb.append("\t");
					sb.append("\n");
					sb.append("\t");
					sb.append("<artifactId>").append(fileName).append("</artifactId>");
					sb.append("\t");
					sb.append("\n");
					sb.append("\t");
					sb.append("<version>").append("1.0").append("</version>");
					sb.append("\t");
					sb.append("\n");
				}
				sb.append("\t");
				sb.append("<scope>system</scope>");
				sb.append("\t");
				sb.append("\n");
				sb.append("\t");
				sb.append("<systemPath>${lib.dir}/").append(file.getName()).append("</systemPath>");
				sb.append("\t");
				sb.append("\n");
				sb.append("</dependency>");
				sb.append("\n");
			}
		}
		System.out.println(sb.toString());
	}

	private static boolean deal(String string) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(string.subSequence(0, 1));
		if (!isNum.matches()) {
			return false;
		}
		String[] str = {"1.","2.","3.","4.","5.","6.","7.","8.","9."};
		for (int i = 0; i < str.length; i++) {
			if(string.indexOf(str[i])!=-1){
				return true;
			}
		}
		return false;
	}
}
