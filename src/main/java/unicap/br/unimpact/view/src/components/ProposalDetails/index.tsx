import React, { useEffect, useState } from "react";

import { ProposalSection } from "../../pages/proposals/NewProposal";
import { fetchProposal } from "../../services/ProposalServices";
import { TProposal } from "../../types/ProposalTypes";
import BasePage from "../BasePage";
import FormFloatingInput from "../FormsFields/FormInput";
import PageTitle from "../PageTitle";

interface props {
    proposalId: number;
}

const ProposalDetails: React.FC<props> = ({ proposalId, children }) => {
    const [proposal, setProposal] = useState<TProposal | null>(null);

    useEffect(() => {
        fetchProposal(
            ({ data }) => {
                setProposal(data.object);
            },
            proposalId,
            (e) => {
                console.log(e.response);
            }
        );
    }, []);

    if (!proposal) return <PageTitle title={"Carregando..."} />;

    return (
        <BasePage id="proposal-details">
            <div>
                <PageTitle title={proposal.metadataProject.title}>
                    <div className="proposal-metadata">
                        <span>
                            <span className="medium-text">Código: </span>
                            {proposal.id}
                        </span>
                        <span>
                            <span className="medium-text">Autor(a): </span>
                            {proposal.responsible.physicalPersonResponsible.name}
                        </span>
                    </div>
                    <div className="proposal-metadata">
                        <span>
                            <span className="medium-text">Status: </span>
                            {proposal.actualState.actualStatus.status}
                        </span>
                        <span>
                            <span className="medium-text">Criado em: </span>
                            {new Date(proposal.actualState.createdAt).toLocaleDateString()}
                        </span>
                    </div>
                </PageTitle>
                <ProposalSection sectionTitle="Texto da proposta">
                    <textarea
                        className={`fill-input __text_area no-outline-focus`}
                        defaultValue={proposal.metadataProject.summary}
                        name="proposaSummary"
                        id="inSummary"
                        readOnly
                        placeholder="Utilize este campo para descrever a ideia e execução da proposta"
                    ></textarea>
                </ProposalSection>

                <ProposalSection
                    sectionTitle="Público alvo e quantidade de beneficiados"
                    bodyClassname="d-flex align-items-center"
                >
                    <FormFloatingInput
                        id={"inTargetPublic"}
                        label={"Público alvo"}
                        placeholder={"Ex: professores, alunos"}
                        inputSize="large"
                        isReadOnly
                        defaultValue={proposal.metadataProject.targetPublic.join(", ")}
                    />
                    <FormFloatingInput
                        parentClassName="ms-4"
                        id={"inQntPeople"}
                        label={"Qtd. beneficiados"}
                        inputSize="medium"
                        fieldType="number"
                        isReadOnly
                        defaultValue={proposal.metadataProject.numberBeneficiaries.toString()}
                    />
                </ProposalSection>

                <ProposalSection sectionTitle="Data/período de execução">
                    <div className="d-flex align-items-center mt-2">
                        <FormFloatingInput
                            id={"start-date"}
                            label={"Data inicial"}
                            fieldType="date"
                            inputSize="undefined"
                            className="me-4"
                            isReadOnly
                        />
                        <FormFloatingInput
                            id={"end-date"}
                            label={"Data final"}
                            fieldType="date"
                            inputSize="undefined"
                            isReadOnly
                        />
                    </div>
                </ProposalSection>

                <ProposalSection sectionTitle="Informações adicionais">
                    <textarea
                        className={`fill-input __text_area no-outline-focus`}
                        name="proposalSummary"
                        id="inSummary"
                        placeholder="Utilize este campo para informar à UNICAP informações que agreguem entendimento sobre o atendimento da solicitação"
                        readOnly
                    ></textarea>
                </ProposalSection>
            </div>
            {children}
        </BasePage>
    );
};

export default ProposalDetails;
