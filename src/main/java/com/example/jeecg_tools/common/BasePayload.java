package com.example.jeecg_tools.common;

import com.example.jeecg_tools.entity.Result;

public interface BasePayload {
    Result checkVUL(String str) throws Exception;
    Result exeVUL(String str,String str2) throws Exception;
    Result getShell(String str) throws Exception;
    Result fileUpload(String str, String filename,String filecontent) throws Exception;
    Result Inject(String url,String xsfilename,String payload) throws Exception;
}
