import Sidebar from './components/Sidebar';
import MapView from './components/MapView';
import './App.css'
import {useEffect, useState} from "react";

function App() {
    const [allLocations, setAllLocations] = useState([]);

    const fetchLocations = () => {
        return fetch('http://localhost:8080/api/locations')
            .then(res => res.json())
            .then(data => setAllLocations(data));
    };

    const handleAddLocation = (newLocation) => {
        fetch('http://localhost:8080/api/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                type: "Feature",
                geometry: {
                    type: "Point",
                    coordinates: [newLocation.lng, newLocation.lat]
                },
                properties: {
                    description: newLocation.description
                }
            })
        })
            .then(res => {
                if (!res.ok) {
                    return res.json().then(err => {
                        throw new Error(err.message || "Failed to add location");
                    });
                }
                return res.json();
            })
            .then(() => fetchLocations())
            .catch(err => {
                console.error("Error adding location:", err);
            });
    };


    useEffect(() => {
        fetchLocations();
    }, []);

  return (
      <div className="app-container">
          <Sidebar/>
          <MapView
              locations={allLocations}
              handleAddLocation={handleAddLocation}
          />
      </div>
  )
}

export default App
