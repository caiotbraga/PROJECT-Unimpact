import React, { useCallback, useEffect, useState } from "react";

import BasePage from "../../../components/BasePage";
import BoxedItem from "../../../components/BoxedItem";
import SingleFilterButton from "../../../components/Buttons/SingleFilterButton";
import HighlightedLabel from "../../../components/HighlightedLabel";
import PageTitle from "../../../components/PageTitle";
import { fetchAllProposals, fetchAssignedProposals } from "../../../services/ProposalServices";
import { TProposal } from "../../../types/ProposalTypes";

interface props {
    isMiddleUser?: boolean;
}

const ProposalsToReviewPage: React.FC<props> = ({ isMiddleUser }) => {
    const [proposals, setProposals] = useState<TProposal[]>([]);
    const [fetchByAssignedProposals, setFetchByAssignedProposals] = useState(false);

    const fetchProposals = useCallback(() => {
        const fetcher = fetchByAssignedProposals ? fetchAssignedProposals : fetchAllProposals;

        fetcher(
            ({ data }) => {
                setProposals(data.object);
                console.log(data.object);
            },
            (e) => {
                console.log(e.response);
                setProposals([]);
            }
        );
    }, [fetchByAssignedProposals]);

    useEffect(() => {
        fetchProposals();
    }, [fetchByAssignedProposals, fetchProposals]);

    return (
        <BasePage>
            <PageTitle title={isMiddleUser ? "Solicitações" : "Novas solicitações"} />
            <HighlightedLabel label={`Gerencie as solicitações ${isMiddleUser ? "da UNICAP" : "do seu setor"}.`} />
            <div>
                <SingleFilterButton
                    label={fetchByAssignedProposals ? "Atribuídas a mim" : "Todas"}
                    onClick={() => {
                        setFetchByAssignedProposals(!fetchByAssignedProposals);
                    }}
                />
                {proposals.map((proposal) => (
                    <BoxedItem
                        key={proposal.metadataProject.id}
                        href={`/inter/solicitacoes/${proposal.id}`}
                        title={proposal.metadataProject.title}
                        rightContent={<span className="__status">Estado: em análise</span>}
                    />
                ))}
            </div>
        </BasePage>
    );
};

export default ProposalsToReviewPage;
