package dto;

import lombok.Data;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardDTO {
    private String owner;
    private String isShared;
    private String name;

}
