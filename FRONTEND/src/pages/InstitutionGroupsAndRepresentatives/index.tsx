import React, { useState } from "react";
import BasePage from "../../components/BasePage";

import Groups from "./Groups";
import Representatives from "../../components/Representatives";
import PageTitle from "../../components/PageTitle";

const InstitutionGroupsAndRepresentativesPage: React.FC = () => {
    const [showRepresentatives, setShowRepresentatives] = useState(true);

    return (
        <BasePage id="groups-and-representatives">
            <PageTitle title="Grupos e representantes"/>
            <div className="d-flex flex-column">
                <div id="table-controls" className="w-100 d-flex align-items-center justify-content-between">
                    <label id="current-table-section" className="main-item-bg pt-1 pe-3 pb-2 ps-3">
                        {showRepresentatives ? "Representantes" : "Grupos"}
                    </label>
                    <button
                        id="btn-alternative-table-section"
                        className="ps-3 pe-3"
                        onClick={() => setShowRepresentatives(!showRepresentatives)}
                    >
                        {showRepresentatives ? "Visualizar grupos" : "Visualizar representantes"}
                    </button>
                </div>
                <div id="content" className="d-flex align-items-start">
                    {showRepresentatives && <Representatives />}
                    {!showRepresentatives && <Groups />}
                </div>
            </div>
        </BasePage>
    );
};

export default InstitutionGroupsAndRepresentativesPage;
