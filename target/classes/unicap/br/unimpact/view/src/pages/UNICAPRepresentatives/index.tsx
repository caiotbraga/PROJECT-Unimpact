import React from "react";
import BasePage from "../../components/BasePage";
import PageTitle from "../../components/PageTitle";
import Representatives from "../../components/Representatives";

const UNICAPRepresentativesPage = () => {
    return (
        <BasePage>
            <PageTitle title="Representantes" />
            <p className="medium-text">Representantes são responsáveis por gerenciar as demandas recebidas.</p>
            <div className="d-flex align-items-start">
                <Representatives
                    fetchRepresentativesEndpoint="http://localhost:9090/university/representatives"
                    removeRepresentativeEndpoint="http://localhost:9090/university/representatives"
                />
            </div>
        </BasePage>
    );
};

export default UNICAPRepresentativesPage;
