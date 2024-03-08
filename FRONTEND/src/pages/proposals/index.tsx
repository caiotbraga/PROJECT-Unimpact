import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { getRequestBasicConfig } from "../../utils";

import axios from "axios";
import BasePage from "../../components/BasePage";
import PageTitle from "../../components/PageTitle";
import LabeledButton from "../../components/Buttons/LabeledButton";
import BoxedItem from "../../components/BoxedItem";
import { TProposal } from "../../types/ProposalTypes";

const ProposalsPage = () => {
    const [proposals, setProposals] = useState<TProposal[]>([]);

    const navigator = useNavigate();

    const fetchProposals = () => {
        axios
            .get("http://localhost:9090/project/by/owner", getRequestBasicConfig())
            .then(({ data }) => {
                setProposals(data.object);
            })
            .catch((error) => {
                console.log(error.response);
            });
    };

    const deleteProposal = (id: number) => {
        console.log(`Vai remover ${id}`);
        axios.delete(`http://localhost:9090/project/${id}`, getRequestBasicConfig()).then(() => {
            fetchProposals();
        });
    };

    useEffect(() => {
        fetchProposals();
    }, []);

    return (
        <BasePage>
            <PageTitle title="Solicitações" />
            <LabeledButton type="submit" className="d-block" size="small" onClick={() => navigator("nova-proposta")}>
                Nova proposta
            </LabeledButton>
            <div className="__proposals">
                {!proposals.length && <p>Você não possui solicitações em andamento.</p>}
                {proposals.map((proposal) => (
                    <BoxedItem
                        key={proposal.metadataProject.id}
                        href={`/voce/solicitacoes/${proposal.id}`}
                        title={proposal.metadataProject.title}
                        rightContent={<span className="__status">Estado: em análise</span>}
                    />
                ))}
            </div>
        </BasePage>
    );
};

export default ProposalsPage;
