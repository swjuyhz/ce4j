/**
 * ============================================================================
 * = COPYRIGHT
 * PAX Computer Technology(Shenzhen) CO., LTD PROPRIETARY INFORMATION
 * This software is supplied under the terms of a license agreement or nondisclosure
 * agreement with PAX Computer Technology(Shenzhen) CO., LTD and may not be copied or
 * disclosed except in accordance with the terms in that agreement.
 * Copyright (C) 2018/11/5-? PAX Computer Technology(Shenzhen) CO., LTD All rights reserved.
 * Description:
 * Revision History:
 * Date                         Author                    Action
 * 2018/11/5 15:13           liming                   Result
 * ============================================================================
 */
package com.zyh.ce4j.domain;

public class ExecutedResult {

	private Result.Status status = Result.Status.SUCCESS;//默认为成功
	private Result stdoutResult;
	private Result errorResult;
	
	public Result.Status getStatus() {
		return status;
	}
	public void setStatus(Result.Status status) {
		this.status = status;
	}
	public Result getStdoutResult() {
		return stdoutResult;
	}
	public void setStdoutResult(Result stdoutResult) {
		this.stdoutResult = stdoutResult;
	}
	public Result getErrorResult() {
		return errorResult;
	}
	public void setErrorResult(Result errorResult) {
		this.errorResult = errorResult;
	}
	@Override
	public String toString() {
		return "ExecutedResult [status=" + status + ", stdoutResult=" + stdoutResult + ", errorResult=" + errorResult
				+ "]";
	}

}
