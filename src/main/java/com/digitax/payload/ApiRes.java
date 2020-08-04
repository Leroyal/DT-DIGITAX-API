package com.digitax.payload;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ApiRes<T>{
    private int code;
//    private Map<?, ?> extras;
    private String message;
    private T data;

    private ApiRes() {
    }

    public static ApiRes<?> fail(int code) {
        return new ApiRes<Object>().setCode(code);
    }

 
    public static <P> ApiRes<P> success(P da,int code) {
        return new ApiRes<P>().setData(da).setCode(code);
    }
	
}
