package com.soyphea.springcloudgatewayexample.output;
import com.soyphea.springcloudgatewayexample.APILog;

public interface LogOutputer {
    void ouput(APILog apiLog);
}
