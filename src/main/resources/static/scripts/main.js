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
    if (data.tomcatRunning) {
        tomcatStatusText.textContent = '<p style="color: darkgreen">Tomcat is running.</p>';
    } else {
        tomcatStatusText.innerHTML = '<p style="color: darkred">Tomcat is not running.</p>';
    }

    // Update running applications list
    const runningAppsList = document.getElementById('runningAppsList');

    if (data.runningApplications && data.runningApplications.length > 0) {
        runningAppsList.innerHTML = data.runningApplications
            .map(app => `<li style="color: darkgreen">${app}</li>`)
            .join('');
    } else {
        runningAppsList.innerHTML = '<li style="color: darkred">No running applications</li>';
    }

    // Update not running applications list
    const notRunningAppsList = document.getElementById('notRunningAppsList');
    if (data.notRunningApplications && data.notRunningApplications.length > 0) {
        notRunningAppsList.innerHTML = data.notRunningApplications
            .map(app => `<li style="color: darkred">${app}</li>`)
            .join('');
    } else {
        notRunningAppsList.innerHTML = '<li style="color: darkgreen">All application sare running</li>';
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