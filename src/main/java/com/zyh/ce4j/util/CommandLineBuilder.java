package com.zyh.ce4j.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zyh.ce4j.domain.ExecutedResult;
import com.zyh.ce4j.executor.BaseExecutor;

public class CommandLineBuilder {
	private CommandLineBuilder() {}

	public static CmdBuilder win() {
		return new CmdBuilder();
	}
	
	public static ShellBuilder linux() {
		return new ShellBuilder();
	}
	
	/**
	 * window cmd builder
	 * @author zyh
	 *
	 */
	static class CmdBuilder{
		private String and = "&&";
		private List<String> cmds;
		private CmdBuilder(){
			cmds = new ArrayList<>(Arrays.asList("cmd","/c"));
		}
		
		private void fixAnd() {
			String last = cmds.get(cmds.size() - 1);
			if(!last.equals("/c") && !last.equals(and)) {
				cmds.add(and);
			}
		}
		
		public CmdBuilder cd(String directory) {
			fixAnd();
			String[] dirs = directory.split(":");
			if(dirs != null && dirs.length >= 2) {
				cmds.add(dirs[0].toUpperCase()+":");
				cmds.add(and);
			}
			cmds.add("cd");
			cmds.add(directory);
			return this;
		}
		
		public CmdBuilder dir() {
			fixAnd();
			cmds.add("dir");
			return this;
		}
		
		//another command: TO DO 
		
		public CmdBuilder goal(String goal, String ...arguments) {
			fixAnd();
			cmds.add(goal);
			if(null != arguments && arguments.length > 0) {
				cmds.addAll(Arrays.asList(arguments));
			}
			return this;
		}
		
		public String toString() {
			return String.join(" ", cmds);
		}
		
		public String[] toArray() {
			return cmds.toArray(new String[cmds.size()]);
		}
		
	}
	
	/**
	 * Linux shell builder
	 * @author zyh
	 *
	 */
	static class ShellBuilder{
		private List<String> cmds;
		private ShellBuilder(){
			cmds = new ArrayList<>();
		}
		
		public ShellBuilder cd(String dir) {
			cmds.add("cd " + dir);
			return this;
		}
		
		public ShellBuilder chmod(String filePath, int privilegeCode) {
			cmds.add("chmod " + privilegeCode + " " + filePath);
			return this;
		}
		
		//another command: TO DO 
		
		/**
		 * 需执行的程序及其参数
		 * @param goal
		 * @param arguments
		 * @return
		 */
		public ShellBuilder goal(String goal, String ...arguments) {
			if(null != arguments && arguments.length > 0) {
				goal += String.join(" ", arguments);
			}
			cmds.add(goal);
			return this;
		}
		public List<String> toList() {
			return cmds;
		}
	}
	
	public static void main(String[] args) {
		BaseExecutor e = BaseExecutor.newBuilder().build();
		String cmd = CommandLineBuilder.win().cd("C:\\Users\\zyh\\Desktop\\progress").dir().cd("D:\\360").dir().toString();
		String[] cmds = CommandLineBuilder.win().cd("C:\\Users\\zyh\\Desktop\\progress").dir().cd("D:\\360").dir().toArray();
		System.out.println("-------------: " + cmd);
		ExecutedResult res = e.execute(cmd);
		System.out.println("-------------分割线A---------------");
		e.execute(cmds);
		System.out.println("-------------分割线B---------------");
		ExecutedResult res1 = e.execute("cmd /c C: && cd C:\\Users\\zyh\\Desktop\\progress && dir && D: && cd D:\\360 && dir");
		System.out.println("-------------分割线---------------");
//		System.out.println(res1.getStdoutResult().getMsg());
		e.execute(new String[] {"cmd","/c","C:","&&","cd","C:\\Users\\zyh\\Desktop\\progress","&&","dir"});
		System.out.println("-------------分割线---------------");
		e.execute(new String[] {"cmd","/c","C:","cd","C:\\Users\\zyh\\Desktop\\progress","dir"});
	}
}
