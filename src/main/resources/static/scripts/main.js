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
    if (data.tomcatState.toString() == "RUNNING") {
        tomcatStatusText.textContent = '<p style="color: darkgreen">Tomcat is running.</p>';
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
    const dockerContainerList = document.getElementById('dockerContainerList');
    console.log("dockerContainers:", data.dockerContainers);
    if (data.dockerContainers && data.dockerContainers.length > 0) {
        console.log("Processing docker containers");
        dockerContainerList.innerHTML = data.dockerContainers
            .map(container => {
                // Check if the status is 'EXITED' and apply a different style
                const statusStyle = container.status === 'EXITED' ? 'style="color: darkred;"' : 'style="color: green;"';
                return `<li>ID: ${container.containerID}, Image: ${container.image}, Created: ${container.created}, <span ${statusStyle}>Status: ${container.status}</span>, Name: ${container.names}</li>`;
            })
            .join('');
    } else {
        console.log("No docker containers available");
        dockerContainerList.innerHTML = '<li>No docker data available</li>';
    }

    // Update disk usage
    const diskUsageList = document.getElementById('diskUsageList');
    console.log("diskUsage:", data.diskUsage);
    if (data.diskUsage && data.diskUsage.length > 0) {
        console.log("Processing freeMemory");
        diskUsageList.innerHTML = data.diskUsage
            .map(diskUsage => `<li>${diskUsage}</li>`)
            .join('');
    } else {
        console.log("free mem not available");
        diskUsageList.innerHTML = '<li>No disk usage data available</li>';
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