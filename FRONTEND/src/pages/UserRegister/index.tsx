import { Route, Routes } from "react-router-dom";

import BasePage from "../../components/BasePage";
import RegisterJuridicalPerson from "./RegisterJuridicalPerson";
import RegisterPhysicalPerson from "./RegisterPhysicalPerson";
import RegisterTypeChoose from "./RegisterTypeChoose";

const UserRegisterPage = () => {
    return (
        <BasePage id="user_login_method_page">
            <Routes>
                <Route index element={<RegisterTypeChoose />} />
                <Route path="/pessoa-fisica" element={<RegisterPhysicalPerson />} />
                <Route path="/pessoa-juridica" element={<RegisterJuridicalPerson />} />
            </Routes>
        </BasePage>
    );
};

export default UserRegisterPage;
