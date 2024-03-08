import React, { useEffect, useState } from 'react'


import NavBar, { TNavPath } from '.';
import { getRequestBasicConfig, handleLogOutUser } from '../../utils';

import { AccountCircleRounded, ExitToApp } from '@material-ui/icons';
import axios from 'axios';
import Notifications from '../Notifications';


import Dropdown from "react-bootstrap/Dropdown";
import DropdownButton from "react-bootstrap/DropdownButton";

const MiddleUserNavBar: React.FC = () => {
    const [userName, setUserName] = useState("VOCÊ");

    const [paths, setPaths] = useState<TNavPath[]>([]);

    const fetchUserName = () => {
        axios
            .get("http://localhost:9090/person/physical/", getRequestBasicConfig())
            .then(({ data }) => {
                setUserName(data.object.name);
                setPaths([
                    {
                        pathName: "Solicitações",
                        url: "solicitacoes",
                    },
                    {
                        pathName: "Setores",
                        url: "setores",
                    },
                ]);
            })
            .catch(() => {
              handleLogOutUser();
            });
    };

    useEffect(() => {
        fetchUserName();
    }, []);

        const navOptions = [
            //Substituido botão de Log Out(Comentado a baixo) por DropDown menu
            // <Button key={1} className="rounded-element shadow-box-element sign_method" onClick={handleLogOutUser}>
            //     {<AccountCircleRounded className="me-2" />}
            //     {userName.split(" ")[0].toUpperCase()}
            // </Button>,
            <DropdownButton
              key={2}
              id=" rounded-element shadow-box-element sign_method"
              title={<AccountCircleRounded className="me-2" />}
            >
              <Dropdown.Item onClick={handleLogOutUser}>
                {<ExitToApp className="me-2" />}
                Log Out
              </Dropdown.Item>
            </DropdownButton>,
          ];
      
          return <NavBar paths={paths} navOptions={navOptions} />;
        };

export default MiddleUserNavBar;