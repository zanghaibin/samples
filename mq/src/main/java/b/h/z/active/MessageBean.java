package b.h.z.active;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/14 11:37
 */
@Data
public class MessageBean implements Serializable {

    private String name;

    private String address;

    private int age;
}
