package b.h.z.rabbit.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/9 14:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductManager {

    private String name;

    private int age;

    private Date birthday;
}
