# ce4j简介
基于java.lang.Runtime封装的用于简化java调用命令行的插件。
在使用java原生库的java.lang.Runtime进行命令行调用的过程中，需要重复编写不少代码。
由于本人所在公司恰好使用java调用命令行的操作比较多，于是基于java.lang.Runtime做了一套封装，以简化java调用命令行的编码。
本着工具的简单化，封装过程没有使用第三方插件，采用策略模式提供命令执行结果的判断自定义，采用builder模式提供Executor的自定义构建。
# 使用demo
## 默认配置Executor
		//插件默认：使用Stdout输出流，不使用错误输出流，不收集命令行输出，不判定执行结果（即使用空判定策略)
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
			ExecutedResult res1 = executor.execute("cmd /c C: && cd C:\\Users\\zyh\\Desktop\\learning && dir");
			System.out.println(res1);
		}else {
			//Linux
			//单命令行
			ExecutedResult res2 = executor.execute("ls");
			System.out.println(res2);
			//多命令行
			ExecutedResult res3 = executor.executeMutilShell(Arrays.asList("cd /root/deploy/zyh/bin/poems_generator/", "python3 compose_poem.py 佳"));
			System.out.println(res3);
		}
		
## 自定义配置Executor
### 自定义执行结果判定策略、使用流stdoutStream、收集命令行输出
		Executor executor1 = BaseExecutor
					.newBuilder()
					//使用StdoutStream,使用自定义执行结果判定状态,ColonEndStrategy为插件实现的一种场景的判定策略,
                			//自定义判定策略实现com.zyh.ce4j.strategy.CheckStrategy即可
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
			ExecutedResult res3 = executor1.executeMutilShell(Arrays.asList("cd /root/deploy/zyh/bin/poems_generator/", "python3 compose_poem.py 佳"));
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
