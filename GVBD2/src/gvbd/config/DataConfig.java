package gvbd.config;

import gvbd.data.NodeFormat;

import java.io.BufferedReader;

public class DataConfig {
	private static String dataPath;
	private static BufferedReader dataReader;
	private static NodeFormat nodeFormat;
	private static int NodeNum;
	
	public static int getNodeNum() {
		return NodeNum;
	}
	public static void setNodeNum(int nodeNum) {
		NodeNum = nodeNum;
	}
	public static String getDataPath() {
		return dataPath;
	}
	public static void setDataPath(String dataPath) {
		DataConfig.dataPath = dataPath;
	}
	public static BufferedReader getDataReader() {
		return dataReader;
	}
	public static void setDataReader(BufferedReader dataReader) {
		DataConfig.dataReader = dataReader;
	}
	public static NodeFormat getNodeFormat() {
		return nodeFormat;
	}
	public static void setNodeFormat(NodeFormat nodeFormat) {
		DataConfig.nodeFormat = nodeFormat;
	}

}
