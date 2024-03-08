import React, { useEffect, useState } from "react";

import NavBar from ".";


import { Button } from "react-bootstrap";

import { AccountCircleRounded } from "@material-ui/icons";

import Notifications from "../Notifications";

import { handleLogOutUser } from "../../utils";
import { TNavPath } from ".";

const AdminNavBar: React.FC = () => {
    const [userName, setUserName] = useState("VOCÊ");

    const [paths, setPaths] = useState<TNavPath[]>([]);

    useEffect(() => {
        setUserName("UNICAP");
        setPaths([
            {
                pathName: "Solicitações",
                url: "solicitacoes",
            },
            {
                pathName: "Representantes",
                url: "representantes",
            },
            {
                pathName: "Setores",
                url: "setores",
            },
        ]);
    }, []);

    const navOptions = [
        <Notifications key={0} />,
        <Button key={1} className="navbar-link rounded-element shadow-box-element sign_method" onClick={handleLogOutUser}>
        {<AccountCircleRounded className="me-2" />}
        {userName.split(" ")[0].toUpperCase()}
        </Button>,
    ];

    return <NavBar paths={paths} navOptions={navOptions} />;
};

export default AdminNavBar;
