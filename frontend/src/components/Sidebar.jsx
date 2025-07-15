import logo from "../assets/logo.png";
import '../App.css';

function Sidebar() {
    return (
        <div className="sidebar">
            <img src={logo} className="logo" alt="funGIS logo" />
            <h2>funGIS</h2>
            <nav>
                <button>+</button>
            </nav>
        </div>
    );
}

export default Sidebar;
