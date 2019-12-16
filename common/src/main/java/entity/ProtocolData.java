package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ProtocolData implements Serializable {
    private Long id;
    private String proxy;
    private String method;
    private Object args;
    private Object result;
    private Class resultType;
}
