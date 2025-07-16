import '../App.css';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

const estoniaCenter = [58.5953, 25.0136];

export default function MapView({ locations }) {

    return (
        <MapContainer
            center={estoniaCenter}
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
                attribution='&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a>'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />

            {locations.features?.map((feature) => (
                <Marker
                    key={feature.properties.id}
                    position={[feature.geometry.coordinates[1], feature.geometry.coordinates[0]]}
                >
                    <Popup>{feature.properties.description}</Popup>
                </Marker>
            ))}
        </MapContainer>
    );
}
