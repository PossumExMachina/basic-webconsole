function fetchData() {
    console.log("Fetching data");
    fetch('/resources')
        .then(response => response.json())
        .then(data => {
            console.log("Received data:", data); // Add this line
            updateUI(data);
        })
        .catch(error => console.error('Error fetching resource data:', error));
}

function updateUI(data) {
    // Update Tomcat status
    const tomcatStatusText = document.getElementById('tomcatStatusText');
    if (data.tomcatState.toString() === "RUNNING") {
        tomcatStatusText.innerHTML = '<p style="color: darkgreen">Tomcat is running.</p>';
    } else {
        tomcatStatusText.innerHTML = '<p style="color: darkred">Tomcat is not running.</p>';
    }


    // Update running applications list
    const applicationsList = document.getElementById('runningAppsList');
    console.log("Applications:", data.applications);
    if (data.applications && data.applications.length > 0) {
        applicationsList.innerHTML = data.applications
            .map(app => {
                const statusStyle = app.status === 'RUNNING' ? 'style="color: darkgreen;"' : 'style="color: darkred;"';
                return `<li>${app.name}, <span ${statusStyle}>Status: ${app.status}</span></span></li>`;
            }).join('');
    } else {
        applicationsList.innerHTML = '<li style="color: darkred">No running applications</li>';
    }


    // Update docker container info
    // Update docker container info
    const dockerContainerList = document.getElementById('dockerContainerList');
    console.log("dockerContainers:", data.dockerContainers);

    if (data.dockerContainers && data.dockerContainers.length > 0) {
        console.log("Processing docker containers");

        let tableHTML = `<table>
                    <tr>
                        <th>Container ID</th>
                        <th>Image</th>
                        <th>Created</th>
                        <th>Status</th>
                        <th>Name</th>
                    </tr>`;

        tableHTML += data.dockerContainers
            .map(container => {
                const statusStyle = container.status === 'EXITED' ? 'style="color: darkred;"' : 'style="color: green;"';
                return `<tr>
                <td>${container.containerID}</td>
                <td>${container.image}</td>
                <td>${container.created}</td>
                <td ${statusStyle}>${container.status}</td>
                <td>${container.names}</td>
            </tr>`;
            })
            .join('');

        tableHTML += '</table>';
        dockerContainerList.innerHTML = tableHTML;
    } else {
        console.log("No docker containers available");
        dockerContainerList.innerHTML = '<p>No docker data available</p>';
    }





    // Update disk usage
    const diskUsageList = document.getElementById('diskUsageList');
    console.log("diskUsage:", data.diskUsage);

    if (data.diskUsage && data.diskUsage.length > 0) {
        console.log("Processing disk usage");
        let tableHTML = `<table>
                        <tr>
                            <th>File System</th>
                            <th>Size</th>
                            <th>Used</th>
                            <th>Available</th>
                            <th>Capacity</th>
                            <th>iUsed</th>
                            <th>iFree</th>
                            <th>Mounted On</th>
                        </tr>`;

        tableHTML += data.diskUsage.map(usage => {
            const capacityStyle = usage.capacity > 95 ? 'style="color: darkred;"' : '';
            return `<tr>
                    <td>${usage.fileSystem}</td>
                    <td>${usage.fileSystemSize}</td>
                    <td>${usage.used}</td>
                    <td>${usage.available}</td>
                    <td ${capacityStyle}>${usage.capacity}%</td>
                    <td>${usage.iused}</td>
                    <td>${usage.ifree}</td>
                    <td>${usage.mountedOn}</td>
                </tr>`;
        }).join('');

        tableHTML += '</table>';
        diskUsageList.innerHTML = tableHTML;
    } else {
        console.log("disk usage not available");
        diskUsageList.innerHTML = '<p>No disk usage data available</p>';
    }


    // Update memory usage
    const freeMemoryList = document.getElementById('freeMemoryList');
    if (data.freeMemory && data.freeMemory.length > 0) {
        console.log("Processing freeMemory");
        freeMemoryList.innerHTML = data.freeMemory
            .map(memory => `<li>${memory}</li>`)
            .join('');
    } else {
        console.log("free mem not available");
        freeMemoryList.innerHTML = '<li>No memory data available</li>';
    }



}


// Call fetchData when the page loads
document.addEventListener('DOMContentLoaded', fetchData);