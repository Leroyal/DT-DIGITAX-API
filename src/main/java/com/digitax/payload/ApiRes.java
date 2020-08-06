package com.digitax.payload;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ApiRes<T> {
    private Object status;
    private T data;

    private ApiRes() {
    }

    public static ApiRes<?> fail(Object status) {
        return new ApiRes<Object>().setStatus(status);
    }


    public static <P> ApiRes<P> success(P da, Object status) {
        return new ApiRes<P>().setData(da).setStatus(status);
    }


}
