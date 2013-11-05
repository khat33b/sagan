package sagan.tools.toolsuite.parser;

import sagan.tools.toolsuite.Architecture;
import sagan.tools.toolsuite.EclipseVersion;
import sagan.tools.toolsuite.ToolSuiteDownloads;
import sagan.tools.toolsuite.ToolSuitePlatform;
import sagan.tools.toolsuite.xml.Download;
import sagan.tools.toolsuite.xml.Release;
import sagan.tools.toolsuite.xml.ToolSuiteXml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ToolXmlConverter_TwoDifferentFileTypesForSameVersionTests {

    private ToolSuiteDownloads toolSuite;
    private ToolXmlConverter toolXmlConverter;

    @Before
    public void setUp() throws Exception {
        ToolSuiteXml toolSuiteXml = new ToolSuiteXml();
        List<Release> releases = new ArrayList<Release>();
        Release release = new Release();
        List<Download> downloads = new ArrayList<Download>();

        Download download = new Download();
        download.setDescription("Mac OS X (Cocoa)");
        download.setOs("mac");
        download.setFile("release/STS/3.3.0/dist/e4.3/spring-tool-suite-3.3.0.RELEASE-e4.3-macosx-cocoa-installer.dmg");
        download.setBucket("http://dist.springsource.com/");
        download.setEclipseVersion("4.3");
        download.setSize("373MB");
        download.setVersion("3.3.0.RELEASE");
        downloads.add(download);

        download = new Download();
        download.setDescription("Mac OS X (Cocoa)");
        download.setOs("mac");
        download.setFile("release/STS/3.3.0/dist/e4.3/spring-tool-suite-3.3.0.RELEASE-e4.3-macosx-cocoa-installer.tar.gz");
        download.setBucket("http://dist.springsource.com/");
        download.setEclipseVersion("4.3");
        download.setSize("373MB");
        download.setVersion("3.3.0.RELEASE");
        downloads.add(download);

        release.setDownloads(downloads);
        release.setName("Spring Tool Suite 3.3.0.RELEASE - based on Eclipse Kepler 4.3");
        releases.add(release);

        toolSuiteXml.setReleases(releases);

        toolXmlConverter = new ToolXmlConverter();
        toolSuite = toolXmlConverter.convert(toolSuiteXml, "Spring Tool Suite", "STS");
    }

    @Test
    public void setsTheReleaseName() {
        assertThat(toolSuite.getReleaseName(), equalTo("3.3.0.RELEASE"));
    }

    @Test
    public void addsTheMacPlatform() throws Exception {
        assertThat(toolSuite.getPlatformList().get(1).getName(), equalTo("Mac"));
    }

    @Test
    public void addsAnEclipseVersionToThePlatform() throws Exception {
        ToolSuitePlatform platform = toolSuite.getPlatformList().get(1);
        assertThat(platform.getEclipseVersions().size(), equalTo(1));
        assertThat(platform.getEclipseVersions().get(0).getName(), equalTo("4.3"));
    }

    @Test
    public void addsAnArchitectureToTheEclipseVersion() throws Exception {
        ToolSuitePlatform platform = toolSuite.getPlatformList().get(1);
        EclipseVersion eclipseVersion = platform.getEclipseVersions().get(0);
        assertThat(eclipseVersion.getArchitectures().size(), equalTo(1));
        assertThat(eclipseVersion.getArchitectures().get(0).getName(), equalTo("Mac OS X (Cocoa)"));
    }

    @Test
    public void addsADownloadLinkTheArchitecture() throws Exception {
        ToolSuitePlatform platform = toolSuite.getPlatformList().get(1);
        EclipseVersion eclipseVersion = platform.getEclipseVersions().get(0);
        Architecture architecture = eclipseVersion.getArchitectures().get(0);

        assertThat(architecture.getDownloadLinks().size(), equalTo(2));
        assertThat(
                architecture.getDownloadLinks().get(0).getUrl(),
                equalTo("http://dist.springsource.com/release/STS/3.3.0/dist/e4.3/spring-tool-suite-3.3.0.RELEASE-e4.3-macosx-cocoa-installer.dmg"));
        assertThat(
                architecture.getDownloadLinks().get(1).getUrl(),
                equalTo("http://dist.springsource.com/release/STS/3.3.0/dist/e4.3/spring-tool-suite-3.3.0.RELEASE-e4.3-macosx-cocoa-installer.tar.gz"));
    }

}