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

    useEffect(() => {
        fetchLocations();
    }, []);

  return (
      <div className="app-container">
          <Sidebar/>
          <MapView
              locations={allLocations}
          />
      </div>
  )
}

export default App
