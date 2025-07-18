import logo from "../assets/logo.png";
import '../App.css';
import { useState } from "react";

function Sidebar() {
    const [showHelp, setShowHelp] = useState(false);

    return (
        <>
            <div className="sidebar">
                <img src={logo} className="logo" alt="funGIS logo" />
                <h2>funGIS</h2>
                <p>Siin kaardil saab vaadata ja lisada seente leiukohti Eestis.</p>
                <nav>
                    <button onClick={() => setShowHelp(true)}>Abi</button>
                </nav>
            </div>

            {showHelp && (
                <div className="modal-overlay" onClick={() => setShowHelp(false)}>
                    <div className="modal-content" onClick={e => e.stopPropagation()}>
                        <p>Uue leiukoha lisamiseks klõpsa kaardil soovitud kohas.</p>
                        <button onClick={() => setShowHelp(false)}>Sulge</button>
                    </div>
                </div>
            )}
        </>
    );
}

export default Sidebar;
