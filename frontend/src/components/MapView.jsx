import '../App.css';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

function MapView() {
    return (
        <div className="map-view">
            <MapContainer
                center={[58.5953, 25.0136]}
                zoom={8}
                minZoom={8}
                maxBounds={[
                    [57.0, 21.5],
                    [59.75, 28.2],
                ]}
                maxBoundsViscosity={1.0}
                style={{ height: "100%", width: "100%" }}
            >
                <TileLayer
                    attribution='&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <Marker position={[59.39607906341282, 24.658063490719883]}>
                    <Popup>SMIT</Popup>
                </Marker>
            </MapContainer>
        </div>
    );
}

export default MapView;
