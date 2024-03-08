import React, { createContext, useState } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Footer from "./components/Footer";
import DefaultNavBar from "./components/NavBar/DefaultNavBar";
import UserNavBar from "./components/NavBar/UserNavBar";
import InstitutionGroupsAndRepresentativesPage from "./pages/InstitutionGroupsAndRepresentatives";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/Login";
import PhysicalUserDataPage from "./pages/UserData";
import UserRegisterPage from "./pages/UserRegister";

import Toaster, { TToast } from "./components/Toaster/";

import "./styles/App.scss";
import ProposalsPage from "./pages/proposals";
import NewProposal from "./pages/proposals/NewProposal";
import RepresentativeGroupsPage from "./pages/RepresentativeGroups";
import UNICAPSectorsPage from "./pages/UNICAPSectors";
import UNICAPRepresentativesPage from "./pages/UNICAPRepresentatives";
import AdminNavBar from "./components/NavBar/AdminNavBar";
import UNICAPSectorPage from "./pages/UNICAPSectors/UNICAPSectorPage";
import MiddleUserNavBar from "./components/NavBar/MiddleUserNavBar";
import ProposalsToReviewPage from "./pages/ManagerProposals/ProposalsToReview";
import MiddleUserProposalManagementPage from "./pages/ManagerProposals/MiddleUserProposalManagement";

import ChooseProposalTimeLine from "./pages/proposals/ChooseProposalTimeLine";
import ProposalTimeLine from "./pages/proposals/ProposalTimeLine";

type TAlertContext = {
    alerts: TToast[];
    addToast: (newToast: TToast) => void;
};

export const AlertsContext = createContext<TAlertContext>({ alerts: [], addToast: (s: TToast) => {} });

const App: React.FC = () => {
    const [alerts, setAlerts] = useState<TToast[]>([]);

    const addToast = (newToast: TToast) => {
        const auxAlerts: TToast[] = JSON.parse(JSON.stringify(alerts));
        auxAlerts.push(newToast);
        setAlerts(auxAlerts);
    };

    return (
        <BrowserRouter>
            <AlertsContext.Provider
                value={{
                    alerts: alerts,
                    addToast: addToast,
                }}
            >
                <Toaster toasts={alerts} setToasts={setAlerts} />
                <Routes>
                    {["/"].map((path, i) => (
                        <Route key={i} path={path} element={<DefaultNavBar />} />
                    ))}
                    <Route path={"/voce/*"} element={<UserNavBar />} />
                    <Route path={"admin/*"} element={<AdminNavBar />} />
                    <Route path={"inter/*"} element={<MiddleUserNavBar />} />
                </Routes>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/cadastro/*" element={<UserRegisterPage />} />
                    <Route path="/entrar" element={<LoginPage />} />

                    <Route path="/voce">
                        <Route path="seus-dados" element={<PhysicalUserDataPage />} />
                        <Route path="grupos-e-representantes" element={<InstitutionGroupsAndRepresentativesPage />} />
                        <Route path="grupos" element={<RepresentativeGroupsPage />} />
                        <Route path="solicitacoes" element={<ProposalsPage />} />
                        <Route path="solicitacoes/:proposalId" element={<ChooseProposalTimeLine />} />
                        <Route path="solicitacoes/nova-proposta" element={<NewProposal />} />
                        <Route path="history/:proposalId" element={<ProposalTimeLine />} />
                    </Route>

                    <Route path="/admin">
                        <Route path="representantes" element={<UNICAPRepresentativesPage />} />
                        <Route path="setores" element={<UNICAPSectorsPage />} />
                        <Route path="setor/:sectorId" element={<UNICAPSectorPage />} />
                    </Route>

                    <Route path="/inter">
                        <Route path="solicitacoes" element={<ProposalsToReviewPage isMiddleUser />} />
                        <Route path="solicitacoes/:proposalId" element={<MiddleUserProposalManagementPage />} />
                    </Route>
                </Routes>
                
            </AlertsContext.Provider>
        </BrowserRouter>
    );
};

export default App;
