package com.zyh.comand;

import java.util.Arrays;
import java.util.List;

import com.zyh.ce4j.domain.ExecutedResult;
import com.zyh.ce4j.domain.Result;
import com.zyh.ce4j.executor.BaseExecutor;
import com.zyh.ce4j.executor.Executor;
import com.zyh.ce4j.strategy.CheckStrategy;
import com.zyh.ce4j.strategy.ColonEndStrategy;

public class ExecuteDemo {
	
	public static void main(String[] args) {
		//几中配置和使用demo，插件默认会使用STDOUT流输出，以避免命令执行时阻塞
		
		//插件默认：使用Stdout输出流，不使用错误输出流，不收集命令行输出，不判定执行结果（即使用空判定策略）
		Executor executor = BaseExecutor.newBuilder().build();
		/*或者（效果同上，此为插件默认配置,完整版）：
		Executor executor3 = BaseExecutor
							.newBuilder()
							.useStdoutStreamGobbler(true, CheckStrategy.NONE_CHECK_STRATEGY_INSTANCE)
							.collectAllOutputStdout(false)
							.build();
		*/
		//window 
		//单条命令行
		if(executor.isWin()) {
			ExecutedResult res = executor.execute("cmd /c dir");
			System.out.println(res);
			//多条命令行
			ExecutedResult res1 = executor.execute("cmd /c C: && cd C:\\Users\\zhengyh\\Desktop\\learning && dir");
			System.out.println(res1);
		}else {
			//Linux
			//单命令行
			ExecutedResult res2 = executor.execute("ls");
			System.out.println(res2);
			//多命令行
			ExecutedResult res3 = executor.executeMutilShell(Arrays.asList("cd /root/deploy/weibo-accounts-manager/bin/poems_generator/", "python3 compose_poem.py 佳"));
			System.out.println(res3);
		}
		
		
		//自定义执行结果判定策略、自定义使用流（stdoutStream or errorStream）、定义是否收集命令行输出
		Executor executor1 = BaseExecutor
								.newBuilder()
								//使用StdoutStream,使用自定义执行结果判定状态,ColonEndStrategy为插件实现的一种场景的判定策略,自定义判定策略请参照ColonEndStrategy的实现
								.useStdoutStreamGobbler(true, new ColonEndStrategy())
								.collectAllOutputStdout(true)//收集命令行输出
								.build();
		//window 
		//单条命令行
		if(executor1.isWin()) {
			ExecutedResult res = executor1.execute("cmd /c dir");
			System.out.println(res);
			Result stdouResult = res.getStdoutResult();
			if(stdouResult.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			//多条命令行
			ExecutedResult res1 = executor1.execute("cmd /c C: && cd C:\\Users\\zhengyh\\Desktop\\learning && dir");
			System.out.println(res1);
			Result stdouResult1 = res1.getStdoutResult();
			if(stdouResult1.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult1.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
		}else {
			//Linux
			//单命令行
			ExecutedResult res2 = executor1.execute("ls");
			System.out.println(res2);
			Result stdouResult = res2.getStdoutResult();
			if(stdouResult.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			//多命令行
			ExecutedResult res3 = executor1.executeMutilShell(Arrays.asList("cd /root/deploy/weibo-accounts-manager/bin/poems_generator/", "python3 compose_poem.py 佳"));
			System.out.println(res3);
			Result stdouResult3 = res3.getStdoutResult();
			if(stdouResult.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult3.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
		}
		
		//自定义执行结果判定策略、使用errorStream流（stdoutStream 默认开启，否则可能导致命令行执行堵塞，除非命令行执行后没有输出）、定义是否收集命令行输出
		Executor executor2 = BaseExecutor
								.newBuilder()
								//使用错误输出流Runtime.getRuntime().exec(comandLine).getErrorStream()
								.useErrorStreamGobbler(true, new CheckStrategy() {
									@Override //自定义策略
									public Result endCheck(String lastPrint) {
										return new Result(Result.Status.UNKNOWN, "未检查执行结果，执行结果未知。");
									}})
								.collectAllOutputError(true)//收集错误流输出的命令行
								.build();
		//window 
		//单条命令行
		if(executor1.isWin()) {
			ExecutedResult res = executor2.execute("cmd /c dir");
			System.out.println(res);
			Result stdouResult = res.getStdoutResult();
			if(stdouResult.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			
			Result errorResult = res.getErrorResult();
			if(stdouResult.getStatus() == Result.Status.UNKNOWN) {//依据自定义的策略
				//收集的命令行执行的ERROR输出(Runtime.getRuntime().exec(comandLine).getErrorStream())
				List<String> lines = errorResult.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			
			
			//多条命令行
			ExecutedResult res1 = executor2.execute("cmd /c C: && cd C:\\Users\\zhengyh\\Desktop\\learning && dir");
			System.out.println(res1);
			Result stdouResult1 = res1.getStdoutResult();
			if(stdouResult1.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult1.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			Result errorResult1 = res1.getErrorResult();
			if(stdouResult.getStatus() == Result.Status.UNKNOWN) {//依据自定义的策略
				//收集的命令行执行的ERROR输出(Runtime.getRuntime().exec(comandLine).getErrorStream())
				List<String> lines = errorResult1.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
		}else {
			//Linux
			//单命令行
			ExecutedResult res2 = executor2.execute("ls");
			System.out.println(res2);
			Result stdouResult = res2.getStdoutResult();
			if(stdouResult.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			Result errorResult1 = res2.getErrorResult();
			if(stdouResult.getStatus() == Result.Status.UNKNOWN) {//依据自定义的策略
				//收集的命令行执行的ERROR输出(Runtime.getRuntime().exec(comandLine).getErrorStream())
				List<String> lines = errorResult1.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			//多命令行
			ExecutedResult res3 = executor2.executeMutilShell(Arrays.asList("cd /root/deploy/weibo-accounts-manager/bin/poems_generator/", "python3 compose_poem.py 佳"));
			System.out.println(res3);
			Result stdouResult3 = res3.getStdoutResult();
			if(stdouResult.getStatus() == Result.Status.SUCCESS) {
				//收集的命令行执行的STDOUT输出(Runtime.getRuntime().exec(comandLine).getInputStream())
				List<String> lines = stdouResult3.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
			Result errorResult2 = res3.getErrorResult();
			if(stdouResult.getStatus() == Result.Status.UNKNOWN) {//依据自定义的策略
				//收集的命令行执行的ERROR输出(Runtime.getRuntime().exec(comandLine).getErrorStream())
				List<String> lines = errorResult2.getData();
				for(String line:lines) {
					System.out.println(line);
				}
			}
		}
	}
	
}

