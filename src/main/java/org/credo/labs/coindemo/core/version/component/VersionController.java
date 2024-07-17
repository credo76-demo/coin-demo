package org.credo.labs.coindemo.core.version.component;

import org.credo.labs.coindemo.core.version.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${coin-demo.info.version-path}")
public class VersionController {

    @Value("${coin-demo.info.release-time}")
    private String releaseTime;

    @Value("${coin-demo.info.release-version}")
    private String releaseVersion;

    @Value("${coin-demo.scheduling.enabled}")
    private String schedulingEnabled;

    final BuildProperties buildProperties;

    public VersionController(@Autowired(required = false) @Nullable BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    /**
     * A health check endpoint which returns the version of the application.
     */
    @CrossOrigin
    @GetMapping
    public Version showVersion() {
        Version version = new Version();
        version.setReleaseTime(releaseTime);
        version.setReleaseVersion(releaseVersion);
        version.setSchedulingEnabled(schedulingEnabled);

        if (null != buildProperties) {
            version.setBuildTime(buildProperties.getTime().toString());
            version.setProjectName(buildProperties.getName());
        }
        return version;
    }
}
