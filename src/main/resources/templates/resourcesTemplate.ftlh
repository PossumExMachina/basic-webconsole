[#ftl ]
<!DOCTYPE html>
<html>
<body>
<#-- Check if Tomcat is running -->
[#if resourceData.tomcatRunning]
    <p>Tomcat is currently running.</p>
[#else]
    <p>Tomcat is not running.</p>
[/#if]

<#-- List running applications -->
[#if resourceData.runningApplications?size gt 0]
    <h2>Running Applications</h2>
    <ul>
        [#list resourceData.runningApplications as app]
            <li>${app}</li>
        [/#list]
    </ul>
[#else]
    <p>No applications are currently running.</p>
[/#if]

<#-- List not running applications -->
[#if resourceData.notRunningApplications?size gt 0]
    <h2>Not Running Applications</h2>
    <ul>
        [#list resourceData.notRunningApplications as app]
            <li>${app}</li>
        [/#list]
    </ul>
[#else]
    <p>All applications are running.</p>
[/#if]
</body>
</html>