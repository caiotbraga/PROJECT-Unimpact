import React from "react";
import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";

import { ReactComponent as UnimpactLogo } from "../../resources/unimpact_logo.svg";

export type TNavPath = {
    pathName: String;
    url: String;
};

interface props {
    paths: TNavPath[];
    navOptions: JSX.Element[] | HTMLElement[];
}

const NavBar: React.FC<props> = ({ paths, navOptions }) => {
    return (
        <Nav activeKey="/" className="p-3 w-100" id="navbar">
            <div className="w-100 d-flex align-items-center mx-auto row">
                <a href="/" id="_logo" className="mr-4 col-md-auto">
                    <UnimpactLogo />
                </a>
                <div id="_paths" className="d-flex align-items-center col">
                    {paths.map((path, i) => (
                        <Nav.Item key={i}>
                            <Link to={`${path.url}`} style={{color: "#3c3c3c"}}>{path.pathName}</Link>
                        </Nav.Item>
                    ))}
                </div>
                <div id="_nav_options" className="col-md-auto d-flex">
                    {navOptions}
                </div>
            </div>
        </Nav>
    );
};

export default NavBar;
