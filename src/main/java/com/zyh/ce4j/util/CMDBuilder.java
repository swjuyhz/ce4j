package com.zyh.ce4j.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zyh.ce4j.domain.ExecutedResult;
import com.zyh.ce4j.executor.BaseExecutor;

public class CMDBuilder {
	private List<String> cmds;
	private boolean isWin;//true:win; false:linux
	
	private CMDBuilder(boolean isWin){
		this.isWin = isWin;
		if(this.isWin) {
			cmds = new ArrayList<>(Arrays.asList("cmd","/c"));
		}else {
			cmds = new ArrayList<>();
		}
	}
	
	public static CMDBuilder win() {
		return new CMDBuilder(true);
	}
	
	public static CMDBuilder linux() {
		return new CMDBuilder(false);
	}
	
	public CMDBuilder cd(String dir) {
		if(isWin) {
			
		}
		return null;
	}
	
	public String build2CMDString() {
		return null;
	}
	
	public String[] build2CMDArray() {
		return null;
	}
	
	public List<String> build2CMDList() {
		return null;
	}
	
	public static void main(String[] args) {
		BaseExecutor e = BaseExecutor.newBuilder().build();
		ExecutedResult res1 = e.execute("cmd /c C: && cd C:\\Users\\zhengyh\\Desktop\\progress && dir");
		System.out.println(res1.getStdoutResult().getMsg());
		e.execute(new String[] {"cmd","/c","C:","cd","C:\\Users\\zhengyh\\Desktop\\progress","dir"});
	}
}
