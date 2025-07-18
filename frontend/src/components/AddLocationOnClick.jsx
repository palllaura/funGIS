import '../App.css';
import {useMapEvents} from 'react-leaflet';
import {useState} from 'react';
import {Popup as LeafletPopup} from 'leaflet';

export default function AddLocationOnClick({onAdd}) {
    const [popup, setPopup] = useState(null);
    const [clickedPosition, setClickedPosition] = useState(null);

    const openConfirmPopup = (map, latlng) => {
        const confirmPopup = new LeafletPopup()
            .setLatLng(latlng)
            .setContent(`
                <div>
                    <p>Lisa siia uus leiukoht?</p>
                    <button id="confirmAddLocation">Jah</button>
                    <button id="cancelAddLocation">Sulge</button>
                </div>
            `)
            .openOn(map);

        setPopup(confirmPopup);
        setClickedPosition(latlng);

        setTimeout(() => {
            document.getElementById('confirmAddLocation')?.addEventListener('click', () => {
                openInputPopup(map, latlng);
            });

            document.getElementById('cancelAddLocation')?.addEventListener('click', () => {
                confirmPopup.remove();
                setPopup(null);
                setClickedPosition(null);
            });
        }, 0);
    };

    const openInputPopup = (map, latlng) => {
        const inputPopup = new LeafletPopup()
            .setLatLng(latlng)
            .setContent(`
                <div>
                    <input type="text" id="locationDescription" maxlength="100"  placeholder="Lisa kirjeldus"/>
                    <br/><br/>
                    <button id="submitLocation">Lisa</button>
                    <button id="cancelInput">Tühista</button>
                </div>
            `)
            .openOn(map);

        setPopup(inputPopup);

        setTimeout(() => {
            document.getElementById('submitLocation')?.addEventListener('click', () => {
                const description = document.getElementById('locationDescription')?.value;
                if (description && description.trim()) {
                    onAdd({
                        lat: latlng.lat,
                        lng: latlng.lng,
                        description: description.trim(),
                    });
                    inputPopup.remove();
                    setPopup(null);
                    setClickedPosition(null);
                }
            });

            document.getElementById('cancelInput')?.addEventListener('click', () => {
                inputPopup.remove();
                setPopup(null);
                setClickedPosition(null);
            });
        }, 0);
    };

    useMapEvents({
        click(e) {
            const map = e.target;
            if (popup) {
                popup.remove();
            }
            openConfirmPopup(map, e.latlng);
        },
    });

    return null;
}
