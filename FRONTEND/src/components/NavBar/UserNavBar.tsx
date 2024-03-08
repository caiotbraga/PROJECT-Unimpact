  import React, { useEffect, useState } from "react";

  import NavBar from ".";

  // import { Button } from "react-bootstrap";

  import { AccountCircleRounded, ExitToApp } from "@material-ui/icons";

  import { TNavPath } from ".";
  import Notifications from "../Notifications";
  import axios from "axios";
  import { getRequestBasicConfig, handleLogOutUser } from "../../utils";

  import Dropdown from "react-bootstrap/Dropdown";
  import DropdownButton from "react-bootstrap/DropdownButton";

  const UserNavBar: React.FC = () => {
    const [userName, setUserName] = useState("VOCÊ");

    const [paths, setPaths] = useState<TNavPath[]>([]);

    const fetchUserName = () => {
      const userType = window.sessionStorage.getItem("user_type");
      if (!userType) return;

      const isJuridicalUser = userType === "ROLE_REQUESTING_MANAGER";

      const endPoint = `http://localhost:9090/person/${
        isJuridicalUser ? "juridical" : "physical"
      }/`;

      axios
        .get(endPoint, getRequestBasicConfig())
        .then(({ data }) => {
          setUserName(data.object.name);
          setPaths([
            {
              pathName: "Solicitações",
              url: "solicitacoes",
            },
            {
              pathName: `Grupos${
                window.sessionStorage.getItem("user_type") ===
                "ROLE_REQUESTING_MANAGER"
                  ? " e representantes"
                  : ""
              }`,
              url: `grupos${
                window.sessionStorage.getItem("user_type") ===
                "ROLE_REQUESTING_MANAGER"
                  ? "-e-representantes"
                  : ""
              }`,
            },
            {
              pathName: "Seus dados",
              url: "seus-dados",
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
        id="rounded-element shadow-box-element sign_method"
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

  export default UserNavBar;
