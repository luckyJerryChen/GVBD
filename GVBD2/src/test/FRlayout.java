package test;

import gvbd.config.DataConfig;
import gvbd.config.FRLayoutConfig;
import gvbd.config.ForceLayoutConfig;
import gvbd.data.BSPNodeFormatImpl;
import gvbd.data.GraphData;
import gvbd.evaluate.Evaluate;
import gvbd.layout.FRForceLayout;
import gvbd.layout.FRForceLayout2;
import gvbd.layout.ForceLayout;
import gvbd.layout.Layout;
import gvbd.util.Output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class FRlayout {
    public static void main(String[] args) {
		DataConfig.setDataPath("dataSimple/data1.txt");
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(DataConfig.getDataPath())));
			DataConfig.setDataReader(br);
			DataConfig.setNodeFormat(new BSPNodeFormatImpl());
			GraphData graphData=new GraphData();
			graphData.loadNodeData(DataConfig.getDataReader(), DataConfig.getNodeFormat(),1000);
			FRLayoutConfig layoutConfig=new FRLayoutConfig();
			layoutConfig.setDirected(false);
			layoutConfig.setHeight(1024);
			layoutConfig.setK(1);
			layoutConfig.setLayoutByTimes(true);
		//	layoutConfig.setLayoutByTimes(false);
		//	layoutConfig.setSpeed(1);
			layoutConfig.setWidth(1024);
			//layoutConfig.setForceThreshold(10);
			layoutConfig.setTimes(200);
			layoutConfig.setCool(0.95f);
			layoutConfig.setTemperature(140);
			Layout layout=new FRForceLayout(graphData.getGraph(),layoutConfig);
			long start=System.currentTimeMillis();
			//layout.initAlgo();
			layout.doLayout();
			long end=System.currentTimeMillis();
			System.out.println(Evaluate.cheng(graphData.getGraph()));
			System.out.println(Evaluate.noack(graphData.getGraph()));

			System.out.println(Evaluate.standardLength(graphData.getGraph(), layoutConfig));
			System.out.println(end-start);
			Output.outputJson(graphData.getGraph());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
