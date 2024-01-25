function fetchData() {
    console.log("Fetching data");

    Promise.all([
        fetch('/resources').then(response => response.json()),
        fetch('/dockerInfo').then(response=> response.json()),
        fetch('/tomcatInfo').then(response => response.json()),
        fetch('/resources/availability').then(response => response.json())
    ])
        .then(([resourcesData, dockerInfo, tomcatInfo, availability]) => {
            const combinedData = {
                resources: resourcesData,
                docker: dockerInfo,
                tomcat: tomcatInfo,
                availability: availability
            };

            updateUI(combinedData);
            displayResourcePanels(combinedData.availability);

            setTimeout(fetchData, 5000);
        })
        .catch(error => {


            setTimeout(fetchData, 5000);
            console.error('Error fetching data:', error);
        });
}


function updateUI(x) {
    // Update Tomcat status
    const tomcatStatusText = document.getElementById('tomcatStatusText');
    if (x.tomcat.tomcatState.toString() === "RUNNING") {
        tomcatStatusText.innerHTML = '<p style="color: darkgreen">Tomcat is running.</p>';
    } else {
        tomcatStatusText.innerHTML = '<p style="color: darkred">Tomcat is not running.</p>';
    }


    const applicationsList = document.getElementById('runningAppsList');
    console.log("Applications:", x.tomcat.applications);
    if (x.tomcat.applications && x.tomcat.applications.length > 0) {
        applicationsList.innerHTML = x.tomcat.applications
            .map(app => {
                const statusStyle = app.state === 'RUNNING' ? 'style="color: darkgreen;"' : 'style="color: darkred;"';
                return `<li>${app.name}, <span ${statusStyle}>Status: ${app.state}</span></span></li>`;
            }).join('');
    } else {
        applicationsList.innerHTML = '<li style="color: darkred">No running applications</li>';
    }


    const dockerContainerList = document.getElementById('dockerContainerList');
    console.log("dockerContainers:", x.docker.dockerContainers);

    if (x.docker.dockerContainers && x.docker.dockerContainers.length > 0) {
        console.log("Processing docker containers");

        let tableHTML = `<table>
                    <tr>
                        <th>Container ID</th>
                        <th>Image</th>
                        <th>Created</th>
                        <th>Status</th>
                        <th>Name</th>
                        <th>Action</th>
                    </tr>`;

        tableHTML += x.docker.dockerContainers
            .map(container => {
                const statusStyle = container.state === 'EXITED' ? 'style="color: darkred;"' : 'style="color: green;"';
                return `<tr>
                <td>${container.containerID}</td>
                <td>${container.image}</td>
                <td>${container.created}</td>
                <td ${statusStyle}>${container.state}</td>
                <td>${container.name}</td>
                <td>
                <button onclick="startContainer('${container.containerID}')">Start</button>
                <button onclick="stopContainer('${container.containerID}')">Stop</button>
                <button onclick="removeContainer('${container.containerID}')">Remove</button>
                </td>
            </tr>`;
            })
            .join('');

        tableHTML += '</table>';
        dockerContainerList.innerHTML = tableHTML;
    } else {
        console.log("No docker containers available");
        dockerContainerList.innerHTML = '<p>No docker data available</p>';
    }

    // Updates disk usage
    const diskUsageList = document.getElementById('diskUsageList');
    console.log("diskUsage:", x.resources.diskUsage);

    if (x.resources.diskUsage && x.resources.diskUsage.length > 0) {
        console.log("Processing disk usage");
        let tableHTML = `<table>
                        <tr>
                            <th>File System</th>
                            <th>Size</th>
                            <th>Used</th>
                            <th>Available</th>
                            <th>Capacity</th>
                            <th>Mounted On</th>
                        </tr>`;

        tableHTML += x.resources.diskUsage.map(usage => {
            const capacityStyle = usage.capacity > 95 ? 'style="color: darkred;"' : '';
            return `<tr>
                    <td>${usage.fileSystem}</td>
                    <td>${usage.fileSystemSize}</td>
                    <td>${usage.used}</td>
                    <td>${usage.available}</td>
                    <td ${capacityStyle}>${usage.capacity}%</td>
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
    if (x.resources.freeMemory && x.resources.freeMemory.length > 0) {
        console.log("Processing freeMemory");
        let tableHTML = `<table>
                        <tr>
                            <th>Total</th>
                            <th>Used</th>
                            <th>Free</th>
                        </tr>`;

        tableHTML += x.resources.freeMemory.map(memory => {
            return `<tr>
                    <td>${memory.total} MB</td>
                    <td>${memory.used} MB</td>
                    <td>${memory.free} MB</td>
                </tr>`;
        }).join('');

        tableHTML += '</table>';
        freeMemoryList.innerHTML = tableHTML;
    } else {
        console.log("memory usage not available");
        freeMemoryList.innerHTML = '<p>No memory usage data available</p>';
    }

}

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.control-button').forEach(function(button) {
        button.addEventListener('click', function() {
            const resourceType = this.getAttribute('data-type');
            const action = this.getAttribute('data-action');

            fetch(`/${resourceType}/${action}/`, { method: 'POST' })
                .then(response => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error(`Failed to ${action} ${resourceType}`);
                    }
                })
                .then(data => {
                    alert(data); // Display success message
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert(error.message); // Display error message
                });
        });
    });


    function startContainer(containerID) {
        sendContainerCommand(containerID, 'start');
    }

    function stopContainer(containerID) {
        sendContainerCommand(containerID, 'stop');
    }

    function removeContainer(containerID) {
        sendContainerCommand(containerID, 'remove');
    }

    function sendContainerCommand(containerID, action) {
        fetch(`/${containerID}/${action}/`, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error(`Failed to ${action} container ${containerID}`);
                }
            })
            .then(data => {
                alert(data); // Display success message
                fetchData(); // Refresh data after action
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message); // Display error message
            });
    }

});

function startContainer(containerID) {
    console.log("Starting")
    sendContainerCommand(containerID, 'start');
}

function stopContainer(containerID) {
    console.log("Stopping")
    sendContainerCommand(containerID, 'stop');
}

function removeContainer(containerID) {
    sendContainerCommand(containerID, 'remove');
}

function sendContainerCommand(containerID, action) {
    console.log("Sending container command")
    fetch(`/${containerID}/${action}`, { method: 'POST' })
        .then(response => {
            if (response.ok) {
                console.log("response is ok i guess")
                return response.text();
            } else {
                throw new Error(`Failed to ${action} container ${containerID}`);
            }
        })
        .then(data => {
            alert(data); // Display success message
            fetchData(); // Refresh data after action
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message); // Display error message
        });
}

function displayResourcePanels(availability) {
    const dockerPanel = document.getElementById('dockerContainers');
    const tomcatPanel = document.getElementById('clickable');

    if (availability.dockerAvailable) {
        dockerPanel.style.display = 'block';
    } else {
        dockerPanel.style.display = 'none';
    }

    if (availability.tomcatAvailable) {
         tomcatPanel.style.display = 'block';
     } else {
         tomcatPanel.style.display = 'none';
     }
}


// Call fetchData when the page loads
document.addEventListener('DOMContentLoaded', fetchData);
