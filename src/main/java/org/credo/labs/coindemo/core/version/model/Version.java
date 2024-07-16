package org.credo.labs.coindemo.core.version.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Version implements Serializable {

    @Serial
    private static final long serialVersionUID = 1519919957270087531L;

    private String projectName;
    private String buildTime;
    private String releaseTime;
    private String releaseVersion;
}
